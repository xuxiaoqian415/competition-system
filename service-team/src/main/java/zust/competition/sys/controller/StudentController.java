package zust.competition.sys.controller;

import org.apache.ibatis.annotations.Param;
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
     * 根据主键id查询团队详情
     */
    @GetMapping("/teamDetail/{id}")
    public String toTeamDetail(@PathVariable Integer id, HttpSession session,Model model) {
        TeamDto teamDto= teamService.getTeamDetail(id);
        Integer thisId=((UserDto)session.getAttribute("thisUser")).getId();
        Integer isLeader=0;
        if(teamDto.getLeaderId()==thisId) isLeader=1;
        teamDto.setIsLeader(isLeader);
        model.addAttribute("detail", teamDto);
        model.addAttribute("member", teamService.getMember(id));
        return "student/teamDetail";
    }

    /**
     * 组队完成请求
     */
    @GetMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable Integer id, HttpSession session,Model model) {
        teamService.updateStatus(id);
        return toTeamDetail(id,session,model);
    }

    /**
     * 我加入的团队
     */
    @GetMapping("/applied/list")
    public String toJoin(HttpSession session, Model model) {
        Integer userId = ((UserDto) session.getAttribute("thisUser")).getId();
        List<TeamDto> dtos = teamService.myJoin(userId);
        if (dtos.size() == 0)
            model.addAttribute("msg", "您当前没有加入任何团队");
        model.addAttribute("teamList", dtos);
        return "student/applied_list";
    }








    /**
     * 我负责的团队
     */
    @GetMapping("/toLead/{id}")
    public String toLead(@PathVariable Integer id, Model model) {
        String msg = "";
        List<TeamDto> dtos = teamService.ownLead(id);
        if (dtos.size() == 0) msg = "您当前没有负责任何团队";
        model.addAttribute("teamDtos", dtos);
        model.addAttribute("msg", msg);
        return "";
    }

    /**
     * 修改团队名称
     */
    @PostMapping("/updateTeamName")
    public String updateTeamName(TeamDto dto, Model model) {
        String msg = "";
        if (teamService.updateTeam(dto) == 1) msg = "修改成功";
        else msg = "修改失败";
        model.addAttribute("msg", msg);
        return "";
    }

    /**
     * 组队请求
     */
    @GetMapping("/requestTeam/{id}")
    public String requestTeam(@PathVariable Integer id, Model model) {
        String msg = "";
        List<TeamDto> dtos = teamService.ownLead(id);
        if (dtos.size() == 0) msg = "当前没有组队申请";
        model.addAttribute("teamDtos", dtos);
        model.addAttribute("msg", msg);
        return "";
    }

    /**
     * 根据主键id获取团队信息
     */
    @ResponseBody
    @RequestMapping("/getTeam")
    public TeamDto getTeam(@RequestParam("id") Integer id) {
        return teamService.getTeamById(id);
    }

    @ResponseBody
    @RequestMapping("/deleteTeamByCpiD")
    public Integer deleteTeamByCpiD(Integer cpId) {
        return teamService.deleteTeamByCpiD(cpId);
    }

    @ResponseBody
    @RequestMapping("/selectTeamList")
    public List<TeamDto> selectTeamList(@RequestBody Query query) {
        return teamService.selectTeamList(query);
    }

    @GetMapping("/lead/list")
    public String toLeadTeamList(HttpSession session, Model model) {
        UserDto u = (UserDto) session.getAttribute("thisUser");
        List<TeamDto> list = teamService.getOwnTeam(u.getId());
        model.addAttribute("leadTeamList", list);
        return "student/leadTeamList";
    }
}