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
import zust.competition.sys.dto.UserTeamDto;
import zust.competition.sys.dto.query.TeamQuery;
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
    public String searchTeam(TeamQuery query, Model model) {
        List<TeamDto> teamList = teamService.searchTeam(query);
        model.addAttribute("teamList",teamList);
        return "admin/teamList";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable("id") Integer id,Model model){
        Integer code = teamService.deleteTeam(id);
        if (-1 == code){
            model.addAttribute("msg", "该团队有成员，无法删除！");
            return toTeamList(model);
        }
        if (-2 == code){
            model.addAttribute("msg", "该团队已有指导老师，无法删除！");
            return toTeamList(model);
        }
        model.addAttribute("msg", "删除成功!");
        return toTeamList(model);
    }

    @GetMapping("/update/leader/{id}")
    public String toUpdateTeamLeader(@PathVariable("id") Integer id,Model model){
        TeamDto team = teamService.getTeamById(id);
        model.addAttribute("team", team);
        List<UserTeamDto> memberList = teamService.getMember(id);
        model.addAttribute("memberList", memberList);
        return "admin/adminUpdateTeam";
    }

    @PostMapping("/update/leader")
    public String updateTeamLeader(TeamDto dto, Model model){
        if (dto.getId() == null || dto.getNewLeaderId() == null) {
            model.addAttribute("msg", "请选择新负责人");
        }
        else {
            teamService.updateTeamLeader(dto.getId(), dto.getNewLeaderId());
        }
        model.addAttribute("msg", "修改负责人成功！");
        return toUpdateTeamLeader(dto.getId(),model);
    }

}
