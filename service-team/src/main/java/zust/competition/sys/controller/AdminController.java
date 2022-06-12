package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.entity.Query;
import zust.competition.sys.service.TeamService;
import zust.competition.sys.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin/team")
public class AdminController {

    @Autowired
    TeamService teamService;
    @Autowired
    UserService userService;

    @GetMapping("/list")
    public String toTeamList(Model model) {
        List<TeamDto> teamList = teamService.getAllTeam();
        model.addAttribute("teamList",teamList);
        return "admin/teamList";
    }

    @PostMapping("/search")
    public String searchTeam(Query query, Model model) {
        List<TeamDto> teamList = teamService.searchTeam(query);
        model.addAttribute("teamList",teamList);
        return "admin/teamList";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable("id") Integer id,Model model){
        String msg = "";
        if(-1 == teamService.deleteTeam(id)){
            msg = "删除失败!";
            model.addAttribute("msg", msg);
        }
        else{
            msg = "删除成功!";
            model.addAttribute("msg", msg);
        }
        return toTeamList(model);
    }

    @GetMapping("/update/{id}")
    public String toUpdateTeam(@PathVariable("id") Integer id,Model model){
        TeamDto teamDto = teamService.getTeamById(id);
        List<UserDto> studentList = userService.getStudentList();
        model.addAttribute("thisTeam",teamDto);
        model.addAttribute("studentList",studentList);
        return "admin/adminUpdateTeam";
    }

    @PostMapping("/update")
    public String updateTeam(TeamDto dto, Model model){
        String msg = "";
        if(teamService.adminUpdateTeam(dto)!= 1){
            msg = "修改团队信息失败!";
            model.addAttribute("msg", msg);
            return toUpdateTeam(dto.getId(),model);
        }
        msg = "修改团队信息成功!";
        model.addAttribute("msg", msg);
        return toUpdateTeam(dto.getId(),model);
    }

}
