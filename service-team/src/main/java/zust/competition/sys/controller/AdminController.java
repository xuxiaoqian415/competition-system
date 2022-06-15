package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zust.competition.sys.dto.*;
import zust.competition.sys.dto.query.TeamQuery;
import zust.competition.sys.dto.query.CountQuery;
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
     * 获奖信息录入页面
     */
    @GetMapping("/award/entry/list")
    public String toAwardList(Model model) {
        TeamQuery query = new TeamQuery();
        query.setStatus(2); // 报名成功
        List<TeamDto> teamList = teamService.selectTeamList(query);
        model.addAttribute("teamList",teamList);
        return "admin/award_entry_list";
    }

    /**
     * 获奖信息录入页面查询
     */
    @PostMapping("/award/entry/search")
    public String searchNoAward(TeamQuery query, Model model) {
        query.setStatus(2); // 报名成功
        List<TeamDto> teamList = teamService.selectTeamList(query);
        model.addAttribute("teamList",teamList);
        return "admin/award_entry_list";
    }

    /**
     * 未获奖操作
     */
    @GetMapping("/award/entry/noWin/{teamId}")
    public String noWin(@PathVariable("teamId") Integer teamId, Model model) {
        String msg="";
        if(teamService.noAwarded(teamId)==1) msg="该团队获奖信息已录入";
        else msg="录入失败 ";
        model.addAttribute("msg",msg);
        return toAwardList(model);
    }

    /**
     * 团队获奖情况录入页面
     */
    @GetMapping("/award/entry/update/result/{teamId}")
    public String toUpdateResult(@PathVariable("teamId") Integer teamId, Model model) {
        model.addAttribute("teamId",teamId);
        return "admin/update_result";
    }

    /**
     * 团队获奖情况录入
     */
    @PostMapping("/award/entry/update/result")
    public String updateResult(TeamDto dto, Model model) {
        String msg="";
        if(teamService.updateResult(dto)==1) msg="该团队获奖信息已录入";
        else msg="录入失败 ";
        model.addAttribute("msg",msg);
        return toUpdateResult(dto.getId(), model);
    }

    /**
     * 获奖信息查询页面
     */
    @GetMapping("/award/info/list")
    public String toWinList(Model model) {
        List<TeamDto> teamList = teamService.getAllAwardedTeam();
        model.addAttribute("teamList",teamList);
        List<AcademyDto> academyList=userService.getAcademyList();
        model.addAttribute("academyList",academyList);
        return "admin/award_info_list";
    }

    /**
     * 获奖信息查询页面-查询
     */
    @PostMapping("/award/info/search")
    public String searchWinList(TeamQuery query, Model model) {
        List<TeamDto> teamList = teamService.searchTeamAward(query);
        model.addAttribute("teamList",teamList);
        List<AcademyDto> academyList=userService.getAcademyList();
        model.addAttribute("academyList",academyList);
        return "admin/award_info_list";
    }

    /**
     * 学院参赛信息统计
     */
    @GetMapping("/count/participate")
    public String toCountParticipate() {
        return "user/participate_count";
    }
    @ResponseBody
    @GetMapping("/count/participate2")
    public TableVo count() {
        CountQuery query = new CountQuery();
        List<CountQuery> result = teamService.countByAcademy(query);
        TableVo tableVo = new TableVo(0, result);
        return tableVo;
    }

    /**
     * 学院获奖信息统计
     */
    @GetMapping("/count/win")
    public String toCountWin() {
        return "user/win_count";
    }
    @ResponseBody
    @GetMapping("/count/win2")
    public TableVo countWin() {
        CountQuery query=new CountQuery();
        query.setIsWin(1);
        List<CountQuery> result = teamService.countByAcademy(query);
        TableVo tableVo = new TableVo(0, result);
        return tableVo;
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
