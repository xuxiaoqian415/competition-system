package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zust.competition.sys.dto.SelectDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.service.SelectService;
import zust.competition.sys.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/student/select")
public class StudentController {

    @Autowired
    SelectService selectService;
    @Autowired
    UserService userService;

    @GetMapping("/teacher")
    public String toChoose(HttpSession session, Model model){
        List<UserDto> teacherList = userService.getTeacherList();
        Integer teamId = (Integer) session.getAttribute("teamId");
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("teamId",teamId);
        return "student/choose_teacher";
    }
}
