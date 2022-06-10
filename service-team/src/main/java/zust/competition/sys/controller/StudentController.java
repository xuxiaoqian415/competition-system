package zust.competition.sys.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.entity.Query;
import zust.competition.sys.service.TeamService;
import zust.competition.sys.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/team")
public class StudentController {

    @Autowired
    TeamService teamService;
    @Autowired
    UserService userService;

    /**
     * 到报名页面(创建团队)
     */
    @GetMapping("/build/{cpId}")
    public String toBuildTeam(@PathVariable("cpId") Integer cpId, Model model){
        List<UserDto> studentList = userService.getStudentList();
        model.addAttribute("cpId", cpId);
        model.addAttribute("studentList",studentList);
        return "student/build_team";
    }

    @PostMapping("/build")
    public String buildTeam(TeamDto teamDto, HttpSession session, Model model){
        String msg = "";
        Integer id = ((UserDto)session.getAttribute("thisUser")).getId();
        ArrayList<Integer> memberList = new ArrayList<>();
        Map<Integer,Integer> memberMap = new HashMap<>();
        memberMap.put(id,id);
//        if(teamDto.getMember1Id() != null){
//            memberMap.put(teamDto.getMember1Id(),teamDto.getMember1Id());
//        }
//        if(teamDto.getMember2Id() != null){
//            memberMap.put(teamDto.getMember2Id(),teamDto.getMember2Id());
//        }
//        if(teamDto.getMember3Id() != null){
//            memberMap.put(teamDto.getMember3Id(),teamDto.getMember3Id());
//        }
//        if(teamDto.getMember4Id() != null){
//            memberMap.put(teamDto.getMember4Id(),teamDto.getMember4Id());
//        }
//        memberMap.remove(id);
//        Iterator<Integer> it = memberMap.keySet().iterator();
//        while(it.hasNext()){
//            memberList.add(memberMap.get(it.next()));
//        }
//        teamDto.setMemberList(memberList);
//        teamDto.setLeaderId(((UserDto)session.getAttribute("thisUser")).getId());
//        Integer teamId = teamService.buildTeam(teamDto);
//        if(-1 == teamId){
//            msg = "报名失败";
//            model.addAttribute("msg",msg);
//            return toBuildTeam(teamDto.getCpId(), model);
//        }
//        session.setAttribute("teamId",teamId);
        return "student/build_team_success";
    }

    @GetMapping("/update/{id}")
    public String toUpdateTeam(@PathVariable("id") Integer id,Model model){
        TeamDto teamDto = teamService.getTeamById(id);
        List<UserDto> studentList = userService.getStudentList();
        model.addAttribute("thisTeam",teamDto);
        model.addAttribute("studentList",studentList);
        return "student/updateTeam";
    }

    @PostMapping("/update")
    public String updateTeam(TeamDto dto, Model model){
        String msg = "";
        if(teamService.updateTeam(dto)!= 1){
            msg = "修改团队信息失败!";
            model.addAttribute("msg", msg);
            return toUpdateTeam(dto.getId(),model);
        }
        msg = "修改团队信息成功!";
        model.addAttribute("msg", msg);
        return toUpdateTeam(dto.getId(),model);
    }

    @GetMapping("/lead/list")
    public String toLeadTeamList(HttpSession session, Model model){
        UserDto u=(UserDto) session.getAttribute("thisUser");
        List<TeamDto> list = teamService.getOwnTeam(u.getId());
        model.addAttribute("leadTeamList",list);
        return "student/leadTeamList";
    }

    @ResponseBody
    @RequestMapping("/deleteStuCompByCompId")
    public Integer deleteStuCompByCompId(Integer compId) {
        return teamService.deleteStuCompByCompId(compId);
    }

    @ResponseBody
    @RequestMapping("/selectTeamList")
    public List<TeamDto> selectTeamList(@RequestBody Query query) {
        return teamService.selectTeamList(query);
    }

    @ResponseBody
    @RequestMapping("/deleteTeamByCpiD")
    public Integer deleteTeamByCpiD(Integer cpId) {
        return teamService.deleteTeamByCpiD(cpId);
    }
}
