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
        System.out.println("===status:" + status);
        List<CompetitionDto> informList = competitionService.getInformList(status);
        System.out.println("===informList:" + informList);
        TableVo tableVo = new TableVo(0, informList);
        return tableVo;
    }

    /**
     * 竞赛信息详情
     */
    @GetMapping("/detail/{id}")
    public String competitionDetail(@PathVariable("id") Integer id, Model model) {
        CompetitionDto detail = competitionService.getCompetitionDetail(id);
        model.addAttribute("detail", detail);
        return "user/competitionDetail";
    }

//    @GetMapping("/detail/{id}")
//    public String toCompetitionDetail(@PathVariable("id") Integer id, @RequestParam("back") String back,
//                                      Model model, HttpSession session) {
//        CompetitionDto detail = competitionService.getCompetitionDetail(id);
//        UserDto thisUser = (UserDto) session.getAttribute("thisUser");
//        if (thisUser.getType() == 2) {
//            UserTeam stuComp = new UserTeam();
//            stuComp.setStudentId(thisUser.getId());
////            stuComp.setCompetitionId(id);
////            detail.setHaveApply(competitionService.ifHaveApply(stuComp));
//        }
//        model.addAttribute("detail",detail);
//        if (back.equals("requestList")) {
//            model.addAttribute("back","/select-serv/teacher/select/request/list");
//        }
//        else if (back.equals("agreeList")) {
//            model.addAttribute("back","/select-serv/teacher/select/agree/list");
//        }
//        else if (back.equals("informList")) {
//            model.addAttribute("back","/competition-serv/competition/inform/list");
//        }
//        else if (back.equals("applyList")) {
//            model.addAttribute("back","/competition-serv/competition/applied/list");
//        }
//        else if (back.equals("leadTeamList")) {
//            model.addAttribute("back","/team-serv/team/lead/list");
//        }
//        return "user/competitionDetail";
//    }

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

}
