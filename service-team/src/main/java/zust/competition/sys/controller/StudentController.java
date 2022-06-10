package zust.competition.sys.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.dto.UserTeamDto;
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
     * 加入团队页面
     */
    @GetMapping("/join/{cpId}")
    public String toBuildTeam(@PathVariable("cpId") Integer cpId, Model model){
        model.addAttribute("cpId", cpId);
        return "student/join_team";
    }

    /**
     * 加入团队请求
     */
    @PostMapping("/join")
    public String joinTeam(UserTeamDto dto,HttpSession session, Model model){
        String msg = "";
        UserDto user= ((UserDto)session.getAttribute("thisUser"));
        dto.setStudentId(user.getId());
        dto.setStudentName(user.getName());
        if(-1 == teamService.joinTeam(dto)){
            msg = "申请加入失败";
        }
        else msg = "加入申请已发送";
        model.addAttribute("msg",msg);
        return toBuildTeam(dto.getCpId(), model);
    }

    @GetMapping("/lead/list")
    public String toLeadTeamList(HttpSession session, Model model){
        UserDto u=(UserDto) session.getAttribute("thisUser");
        List<TeamDto> list = teamService.getOwnTeam(u.getId());
        model.addAttribute("leadTeamList",list);
        return "student/leadTeamList";
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
