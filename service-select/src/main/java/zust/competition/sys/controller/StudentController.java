package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zust.competition.sys.dto.*;
import zust.competition.sys.service.SelectService;
import zust.competition.sys.service.TeamService;
import zust.competition.sys.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    SelectService selectService;
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
        Integer code = teamService.joinTeam(dto);
        if (-1 == code) {
            msg = " 邀请码错误";
        }
        else if (-2 == code) {
            msg = "该团队不是该竞赛下的团队，加入失败";
        }
        else if (-3 == code) {
            msg = "该团队已组队完成，加入失败";
        }
        else if (-4 == code) {
            msg = "加入失败";
        }
        else {
            msg = "加入申请已发送";
        }
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
     * 组队请求处理页面
     */
    @GetMapping("/request/list")
    public String requestTeam(HttpSession session, Model model) {
        Integer userId = ((UserDto)session.getAttribute("thisUser")).getId();
        List<UserTeamDto> dtos = teamService.requestTeam(userId);
        if (dtos.size() == 0) model.addAttribute("msg", "当前没有组队申请");
        model.addAttribute("UserTeamDto", dtos);
        return "student/team_request_list";
    }

    /**
     * 同意/拒绝组队请求
     */
    @GetMapping("/request/choice")
    public String updateRequestStatus(@RequestParam("id") Integer id, @RequestParam("type") Integer type,
                                      HttpSession session, Model model) {
        if (type == -10) { // 请求从团队详情过来
            teamService.updateRequestStatus(id, -1);
            Integer teamId = teamService.getUserTeam(id).getTeamId();
//            return toTeamDetail(teamId, session, model);
            return "redirect:/team-serv/team/detail/"+teamId;
        }
        Integer code = teamService.updateRequestStatus(id, type);
        if (code == -1) {
            model.addAttribute("msg", "当前团队已达到最大人数");
        }
        return requestTeam(session, model);
    }

    /**
     * 我发送的组队请求
     */
    @GetMapping("/my/request")
    public String ownRequest(HttpSession session,Model model) {
        UserDto u = (UserDto) session.getAttribute("thisUser");
        model.addAttribute("UserTeamDto", teamService.ownRequest(u.getId()));
        return "student/my_request_list";
    }
}
