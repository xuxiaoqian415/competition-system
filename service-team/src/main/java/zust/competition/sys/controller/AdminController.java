package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zust.competition.sys.dto.AcademyDto;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.dto.UserTeamDto;
import zust.competition.sys.dto.query.TeamQuery;
import zust.competition.sys.dto.query.CountQuery;
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

    /**
     * 进入获奖信息(未评奖)页面
     */
    @GetMapping("/noAward")
    public String toNoAward( Model model) {
        List<TeamDto> teamList = teamService.getTeamByTime(0);
        model.addAttribute("teamList",teamList);
        return "admin/no_isAwarded";
    }
    /**
     * 查询未评奖团队
     */
    @PostMapping("/searchNoAward")
    public String searchNoAward(Query query, Model model) {
        query.setIsAwarded(0);
        List<TeamDto> teamList = teamService.searchTeamAward(query);
        model.addAttribute("teamList",teamList);
        return "admin/no_isAwarded";
    }

    /**
     * 未获奖操作
     */
    @GetMapping("/noWin/{teamId}")
    public String noWin(@PathVariable("teamId") Integer teamId, Model model) {
        String msg="";
        if(teamService.noAwarded(teamId)==1) msg="该团队获奖信息已录入";
        else msg="录入失败 ";
        model.addAttribute("msg",msg);
        return toNoAward(model);
    }

    /**
     * 团队获奖情况录入
     */
    @GetMapping("/updateResult/{teamId}")
    public String toUpdateResult(@PathVariable("teamId") Integer teamId, Model model) {
        model.addAttribute("teamId",teamId);
        return "admin/update_result";
    }

    /**
     * 团队获奖情况录入
     */
    @PostMapping("/updateResult")
    public String updateResult(TeamDto dto, Model model) {
        String msg="";
        if(teamService.updateResult(dto)==1) msg="该团队获奖信息已录入";
        else msg="录入失败 ";
        model.addAttribute("msg",msg);
        return toNoAward(model);
    }

    /**
     * 进入获奖信息查询页面
     */
    @GetMapping("/toWin")
    public String toWinList( Model model) {
        List<TeamDto> teamList = teamService.getTeamByTime(1);
        List<AcademyDto> academyList=userService.getAcademyList();
        model.addAttribute("teamList",teamList);
        model.addAttribute("academyList",academyList);
        return "admin/winList";
    }

    /**
     * 模糊查询已评奖团队
     */
    @PostMapping("/searchWinList")
    public String searchWinList(Query query, Model model) {
        query.setIsAwarded(1);
        List<TeamDto> teamList = teamService.searchTeamAward(query);
        List<AcademyDto> academyList=userService.getAcademyList();
        model.addAttribute("academyList",academyList);
        model.addAttribute("teamList",teamList);
        return "admin/winList";
    }

    /**
     * 按照学院对获奖团队进行查询
     */
    @PostMapping("/getTeamByAcademy")
    public String getTeamByAcademy(Query query,Model model) {
        List<TeamDto> teamList = teamService.getTeamByAcademy(query.getAcademyId());
        List<AcademyDto> academyList=userService.getAcademyList();
        model.addAttribute("academyList",academyList);
        model.addAttribute("teamList",teamList);
        return "admin/teamList";
    }

    /**
     * 学院参赛信息统计
     */
    @GetMapping("/toCountParticipate")
    public String toCountParticipate( Model model) {
        CountQuery query=new CountQuery();
        List<CountQuery> queries=teamService.countByAcademy(query);
        model.addAttribute("queries",queries);
        return "";
    }
    /**
     * 学院获奖信息统计
     */
    @GetMapping("/toCountAward")
    public String toCountAward( Model model) {
        CountQuery query=new CountQuery();
        query.setIsWin(1);
        List<CountQuery> queries=teamService.countByAcademy(query);
        model.addAttribute("queries",queries);
        return "";
    }

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
