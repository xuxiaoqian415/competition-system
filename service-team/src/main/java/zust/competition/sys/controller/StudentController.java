package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.TeamTeacherDto;
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
    public String toJoinTeam(@PathVariable("cpId") Integer cpId, Model model) {
        model.addAttribute("cpId", cpId);
        return "student/join_team";
    }

    /**
     * 加入团队
     */
    @PostMapping("/join")
    public String joinTeam(UserTeamDto dto, HttpSession session, Model model) {
        String msg = "";
        UserDto user = ((UserDto) session.getAttribute("thisUser"));
        dto.setStudentId(user.getId());
        dto.setStudentName(user.getName());
        if (-1 == teamService.joinTeam(dto)) {
            msg = "申请加入失败";
        } else msg = "加入申请已发送";
        model.addAttribute("msg", msg);
        return toJoinTeam(dto.getCpId(), model);
    }

    /**
     * 竞赛详情-负责人进入已有团队页面
     */
    @GetMapping("/lead/{cpId}")
    public String leadTeam(@PathVariable("cpId") Integer cpId, HttpSession session, Model model) {
        Integer leaderId = ((UserDto) session.getAttribute("thisUser")).getId();
        TeamDto leaderTeam = teamService.getLeaderTeam(leaderId, cpId);
        if (leaderTeam.getStatus() == 1) {
            List<UserDto> teacherList = userService.getTeacherList();
            model.addAttribute("teacherList", teacherList);
            // 已发出邀请的老师
            List<String> inviteTeacherList = teamService.getInviteTeacher(leaderTeam.getId());
            if (inviteTeacherList.size() != 0) {
                String inviteTeacher = "您已邀请老师：";
                for (String s : inviteTeacherList) {
                    inviteTeacher += s;
                }
                model.addAttribute("inviteTeacher",inviteTeacher);
            }
        }
        model.addAttribute("teamDto", leaderTeam);
        return "student/lead_hasTeam";
    }

    /**
     * 向老师发出邀请
     */
    @PostMapping("/invite")
    public String inviteTeacher(TeamTeacherDto dto, HttpSession session, Model model) {
        String msg = "";
        UserDto user = ((UserDto) session.getAttribute("thisUser"));
        dto.setOperatorId(user.getId());
        dto.setLeaderName(user.getName());
        if (-1 == teamService.inviteTeacher(dto)) {
            msg = "邀请发送失败";
        } else msg = "已成功发送邀请";
        model.addAttribute("msg", msg);
        return leadTeam(dto.getCpId(), session, model);
    }

    /**
     * 我加入的团队
     */
    @GetMapping("/joined/list")
    public String toJoin(HttpSession session, Model model) {
        Integer userId = ((UserDto) session.getAttribute("thisUser")).getId();
        List<TeamDto> dtos = teamService.myJoin(userId);
        if (dtos.size() == 0)
            model.addAttribute("msg", "您当前没有加入任何团队");
        model.addAttribute("teamList", dtos);
        return "student/joined_team_list";
    }

    /**
     * 团队详情页
     */
    @GetMapping("/detail/{id}")
    public String toTeamDetail(@PathVariable Integer id, HttpSession session,Model model) {
        TeamDto teamDto= teamService.getTeamDetail(id);
        Integer thisId=((UserDto)session.getAttribute("thisUser")).getId();
        if(teamDto.getLeaderId().equals(thisId)) teamDto.setIsLeader(1);
        else teamDto.setIsLeader(0);
        model.addAttribute("detail", teamDto);
        model.addAttribute("memberList", teamService.getMember(id));
        return "student/teamDetail";
    }

    /**
     * 团队详情页-组队完成请求
     */
    @GetMapping("/update/status/{id}")
    public String updateStatus(@PathVariable Integer id, HttpSession session,Model model) {
        teamService.updateStatus(id);
        model.addAttribute("msg", "组队完成，可以去选择指导老师了。");
        return toTeamDetail(id,session,model);
    }

    /**
     * 我负责的团队
     */
    @GetMapping("/lead/list")
    public String toLead(HttpSession session, Model model) {
        Integer userId = ((UserDto)session.getAttribute("thisUser")).getId();
        List<TeamDto> dtos = teamService.ownLead(userId);
        System.out.println("===dtos"+dtos);
        if (dtos.size() == 0)
            model.addAttribute("msg", "您当前没有负责任何团队");
        model.addAttribute("teamList", dtos);
        return "student/lead_team_list";
    }

    /**
     * 修改团队信息页面
     */
    @GetMapping("/update/info/{id}")
    public String toUpdateName(@PathVariable("id") Integer id, Model model) {
        TeamDto team = teamService.getTeamById(id);
        model.addAttribute("team", team);
        return "student/update_team";
    }

    /**
     * 修改团队信息
     */
    @PostMapping("/update/info")
    public String updateTeamName(TeamDto dto, Model model) {
        String msg = "";
        if (teamService.updateTeam(dto) == 1) msg = "修改成功";
        else msg = "修改失败";
        model.addAttribute("msg", msg);
        return toUpdateName(dto.getId(), model);
    }

    /**
     * 组队请求处理页面
     */
    @GetMapping("/request/list")
    public String requestTeam(HttpSession session, Model model) {
        Integer userId = ((UserDto)session.getAttribute("thisUser")).getId();
        List<UserTeamDto> dtos = teamService.requestTeam(userId);
        System.out.println("===dtos"+dtos);
        if (dtos.size() == 0) model.addAttribute("msg", "当前没有组队申请");
        model.addAttribute("UserTeamDto", dtos);
        return "student/team_request_list";
    }


}