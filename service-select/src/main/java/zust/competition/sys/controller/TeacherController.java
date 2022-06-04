package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.service.SelectService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/teacher/select")
public class TeacherController {

    @Autowired
    SelectService selectService;

    @GetMapping("/request/list")
    public String toRequestList(HttpSession session, Model model) {
        UserDto u = (UserDto) session.getAttribute("thisUser");
        List<TeamDto> noSelectTeams = selectService.noSelectTeams(u.getId());
        model.addAttribute("noSelectTeams", noSelectTeams);
        return "teacher/requestList";
    }

    @GetMapping("/agree")
    public String selectTeam(@RequestParam("id") Integer id, Model model, HttpSession session){
        selectService.updateSelect(id);
        return toRequestList(session, model);
    }

    @GetMapping("/agree/list")
    public String agreeTeam( Model model, HttpSession session){
        UserDto u=(UserDto) session.getAttribute("thisUser");
        List<TeamDto> teamDtos = selectService.selectTeams(u.getId());
        model.addAttribute("teamDtos",teamDtos);
        return "teacher/agreeList";
    }
}
