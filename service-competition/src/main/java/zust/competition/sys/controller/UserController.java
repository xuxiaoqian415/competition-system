package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zust.competition.sys.dto.CompetitionDto;
import zust.competition.sys.dto.ResponseVo;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.entity.Competition;
import zust.competition.sys.service.CompetitionService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/competition")
public class UserController {

    @Autowired
    CompetitionService competitionService;

    /**
     *
     */
    @GetMapping("/inform/list/{status}")
    public String competitionInformList(@PathVariable("status") Integer status, Model model) {
        List<CompetitionDto> competitionDtoList = competitionService.getCompetitionList(status);
        model.addAttribute("competitionList",competitionDtoList);
        return "student/competitionList";
    }


//    @GetMapping("/detail/{id}")
//    public String toCompetitionDetail(@PathVariable("id") Integer id, @RequestParam("back") String back,
//                                      Model model, HttpSession session) {
//        CompetitionDto detail = competitionService.getCompetitionDetail(id);
//        UserDto thisUser = (UserDto) session.getAttribute("thisUser");
//        if (thisUser.getType() == 2) {
//            StuComp stuComp = new StuComp();
//            stuComp.setStudentId(thisUser.getId());
//            stuComp.setCompetitionId(id);
//            detail.setHaveApply(competitionService.ifHaveApply(stuComp));
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
//
//    /**
//     * 已报名竞赛列表
//     */
//    @GetMapping("/applied/list")
//    public String toApplyList(HttpSession session, Model model) {
//        UserDto thisUser = (UserDto) session.getAttribute("thisUser");
//        List<CompetitionDto> list = competitionService.getApplyList(thisUser.getId());
//        model.addAttribute("applyList",list);
//        return "student/applyList";
//    }
//
//    @ResponseBody
//    @RequestMapping("/insertStuComp")
//    public void insertStuComp(@RequestBody StuComp stuComp) {
//        competitionService.insertStuComp(stuComp);
//    }

}
