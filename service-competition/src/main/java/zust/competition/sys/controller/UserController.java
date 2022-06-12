package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zust.competition.sys.dto.CompetitionDto;
import zust.competition.sys.dto.ResponseVo;
import zust.competition.sys.dto.TableVo;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.entity.Competition;
import zust.competition.sys.entity.UserTeam;
import zust.competition.sys.service.CompetitionService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/competition")
public class UserController {

    @Autowired
    CompetitionService competitionService;

    /**
     * 访问竞赛信息列表
     */
    @GetMapping("/inform")
    public String toCompetitionInformList() {
        return "user/informList";
    }

    /**
     * 竞赛信息列表
     */
    @ResponseBody
    @GetMapping("/inform/{status}")
    public TableVo competitionInformList(@PathVariable("status") Integer status) {
        List<CompetitionDto> informList = competitionService.getInformList(status);
        TableVo tableVo = new TableVo(0, informList);
        return tableVo;
    }

    /**
     * 竞赛信息详情
     */
    @GetMapping("/detail/{id}")
    public String competitionDetail(@PathVariable("id") Integer id, Model model, HttpSession session) {
        UserDto thisUser = (UserDto) session.getAttribute("thisUser");
        CompetitionDto detail = competitionService.getCompetitionDetail(id);
        // 设置当前用户的类型 0-不可报名（教师/管理员）1-未报名过该竞赛 2-已创建团队 3-已加入团队
        if (thisUser.getType() != 2) { // 不是学生
            detail.setUserType(0);
        }
        else {
            Integer userType = competitionService.getUserType(thisUser.getId(), id);
            detail.setUserType(userType);
        }
        model.addAttribute("detail", detail);
        return "user/competitionDetail";
    }










    /**
     * 已报名竞赛列表
     */
    @GetMapping("/applied/list")
    public String toApplyList(HttpSession session, Model model) {
        UserDto thisUser = (UserDto) session.getAttribute("thisUser");
        List<CompetitionDto> list = competitionService.getApplyList(thisUser.getId());
        model.addAttribute("applyList",list);
        return "student/applyList";
    }

    @ResponseBody
    @RequestMapping("/insertUserTeam")
    public void insertUserTeam(@RequestBody UserTeam stuComp) {
        competitionService.insertUserTeam(stuComp);
    }

    @ResponseBody
    @RequestMapping("/getCompetitionTile")
    public String getCompetitionTile(Integer id) {
        CompetitionDto detail = competitionService.detail(id);
        if (detail != null)
            return detail.getTitle();
        return "0";
    }

}
