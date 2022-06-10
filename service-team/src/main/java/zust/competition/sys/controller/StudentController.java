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
     * 加入团队(跳转加入团队页面)
     */
    @GetMapping("/join/{cpId}")
    public String toBuildTeam(@PathVariable("cpId") Integer cpId, Model model){

        model.addAttribute("cpId", cpId);
        return "student/join_team";
    }
    /**
     * 加入团队(加入操作)
     */
    @PostMapping("/join")
    public String joinTeam(@RequestBody UserTeamDto dto,HttpSession session, Model model){
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

    /**
     * 负责人进入已有团队页面
     */
    @GetMapping("/lead/{cpId}")
    public String leadTeam(@PathVariable("cpId") Integer cpId,HttpSession session,Model model){
        String msg="";
        UserDto u=(UserDto) session.getAttribute("thisUser");
        TeamDto t=teamService.getTeamList(u.getId(),cpId);
        List<UserDto> teacherList=userService.getTeacherList();
        if(teacherList.size()==0) msg="当前竞赛您并没有负责的团队";
        model.addAttribute("msg",msg);
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("teamDto", t);
        return "student/lead_hasTeam";
    }

    /**
     * 向老师发出邀请
     */
    @PostMapping("/invite")
    public String inviteTeacher(@RequestBody TeamTeacherDto dto, HttpSession session, Model model){
        String msg = "";
        UserDto user= ((UserDto)session.getAttribute("thisUser"));
        dto.setOperatorId(user.getId());
        dto.setLeaderName(user.getName());
        if(-1 == teamService.inviteTeacher(dto)){
            msg = "邀请发送失败";
        }
        else msg = "已成功发送邀请";
        model.addAttribute("msg",msg);
        return toBuildTeam(dto.getCpId(), model);
    }

    /**
     * 根据主键id查询团队详情
     */
    @GetMapping("/teamDetail/{id}")
    public String toTeamDetail(@PathVariable Integer id,Model model){
        model.addAttribute("detail", teamService.getTeamDetail(id));
        model.addAttribute("member",teamService.getMember(id));
        return "";
    }
    /**
     * 我加入的团队
     */
    @GetMapping("/toJoin/{id}")
    public String toJoin(@PathVariable Integer id,Model model){
        String msg="";
        List<TeamDto> dtos=teamService.myJoin(id);
        if(dtos.size()==0) msg="您当前没有加入任何团队";
        model.addAttribute("teamDtos",dtos);
        model.addAttribute("msg",msg);
        return "";
    }
    /**
     * 我负责的团队
     */
    @GetMapping("/toLead/{id}")
    public String toLead(@PathVariable Integer id,Model model){
        String msg="";
        List<TeamDto> dtos=teamService.ownLead(id);
        if(dtos.size()==0) msg="您当前没有负责任何团队";
        model.addAttribute("teamDtos",dtos);
        model.addAttribute("msg",msg);
        return "";
    }
    /**
     * 修改团队名称
     */
    @PostMapping("/updateTeamName")
    public String updateTeamName(TeamDto dto,Model model){
        String msg="";
        if(teamService.updateTeam(dto)==1) msg="修改成功";
        else msg="修改失败";
        model.addAttribute("msg",msg);
        return "";
    }
    /**
     * 组队请求
     */
    @GetMapping("/requestTeam/{id}")
    public String requestTeam(@PathVariable Integer id,Model model){
        String msg="";
        List<TeamDto> dtos=teamService.ownLead(id);
        if(dtos.size()==0) msg="当前没有组队申请";
        model.addAttribute("teamDtos",dtos);
        model.addAttribute("msg",msg);
        return "";
    }







//    @GetMapping("/build/{cpId}")
//    public String toBuildTeam(@PathVariable("cpId") Integer cpId, Model model){
//        model.addAttribute("cpId", cpId);
//        return "student/build_team";
//    }


//    @PostMapping("/build")
//    public String buildTeam(TeamDto teamDto, HttpSession session, Model model){
//        String msg = "";
//        Integer id = ((UserDto)session.getAttribute("thisUser")).getId();
//        ArrayList<Integer> memberList = new ArrayList<>();
//        Map<Integer,Integer> memberMap = new HashMap<>();
//        memberMap.put(id,id);
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
//        return "student/build_team_success";
//    }
//
//    @GetMapping("/update/{id}")
//    public String toUpdateTeam(@PathVariable("id") Integer id,Model model){
//        TeamDto teamDto = teamService.getTeamById(id);
//        List<UserDto> studentList = userService.getStudentList();
//        model.addAttribute("thisTeam",teamDto);
//        model.addAttribute("studentList",studentList);
//        return "student/updateTeam";
//    }
//
//    @PostMapping("/update")
//    public String updateTeam(TeamDto dto, Model model){
//        String msg = "";
//        if(teamService.updateTeam(dto)!= 1){
//            msg = "修改团队信息失败!";
//            model.addAttribute("msg", msg);
//            return toUpdateTeam(dto.getId(),model);
//        }
//        msg = "修改团队信息成功!";
//        model.addAttribute("msg", msg);
//        return toUpdateTeam(dto.getId(),model);
//    }
//
//    @GetMapping("/lead/list")
//    public String toLeadTeamList(HttpSession session, Model model){
//        UserDto u=(UserDto) session.getAttribute("thisUser");
//        List<TeamDto> list = teamService.getOwnTeam(u.getId());
//        model.addAttribute("leadTeamList",list);
//        return "student/leadTeamList";
//    }
//
//    @ResponseBody
//    @RequestMapping("/deleteStuCompByCompId")
//    public Integer deleteStuCompByCompId(Integer compId) {
//        return teamService.deleteStuCompByCompId(compId);
//    }
//
//    @ResponseBody
//    @RequestMapping("/selectTeamList")
//    public List<TeamDto> selectTeamList(Query query) {
//        return teamService.selectTeamList(query);
//    }
//
//    @ResponseBody
//    @RequestMapping("/deleteTeamByCpiD")
//    public Integer deleteTeamByCpiD(Integer cpId) {
//        return teamService.deleteTeamByCpiD(cpId);
//    }
}
