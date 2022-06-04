package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zust.competition.sys.dto.CompetitionDto;
import zust.competition.sys.entity.Competition;
import zust.competition.sys.entity.Query;
import zust.competition.sys.service.CompetitionService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/competition")
public class AdminController {

    @Autowired
    CompetitionService competitionService;

    @PostMapping("/add")
    public String postCompetition(Competition competition, Model model){
        int i = competitionService.addCompetition(competition);
        if(i == 0)
            model.addAttribute("msg","发布失败");
        else
            model.addAttribute("msg","发布成功");
        return "admin/postCompetition";
    }

    @GetMapping("/list")
    public String toCompetitionList(Model model) {
        List<Competition> competitionList = competitionService.getCompetitionList();
        model.addAttribute("competitionList", competitionList);
        return "admin/competitionList";
    }

    @PostMapping("/search")
    public String searchCompetition(Query query, Model model) {
        List<Competition> competitionList = competitionService.searchCompetition(query);
        model.addAttribute("competitionList", competitionList);
        return "admin/competitionList";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompetition(@PathVariable("id") Integer id, Model model){
        String msg = "";
        if(-1 == competitionService.deleteCompetition(id)){
            msg = "删除失败!";
            model.addAttribute("msg", msg);
        }
        else{
            msg = "删除成功!";
            model.addAttribute("msg", msg);
        }
        return toCompetitionList(model);
    }

    @GetMapping("/update/{id}")
    public String toUpdateCompetition(@PathVariable("id") Integer id,Model model){
        CompetitionDto competition = competitionService.getCompetitionDetail(id);
        model.addAttribute("thisCompetition",competition);
        return "admin/updateCompetition";
    }

    @PostMapping("/update")
    public String updateCompetition(CompetitionDto dto, HttpSession session, Model model){
        String msg = "";
        if(competitionService.updateCompetition(dto) != 1) {
            msg = "修改竞赛信息失败!";
            model.addAttribute("msg", msg);
            return toUpdateCompetition(dto.getId(),model);
        }
        msg = "修改竞赛信息成功!";
        model.addAttribute("msg", msg);
        return toUpdateCompetition(dto.getId(),model);
    }

}
