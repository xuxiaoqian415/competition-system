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

    @PostMapping("/teacher")
    public String choose(SelectDto selectDto, HttpSession session, Model model){
//        if (selectDto.getTeacher1Id() == null || selectDto.getTeacher2Id() == null || selectDto.getTeacher3Id() == null) {
//            String msg = "请选择三个老师";
//            model.addAttribute("msg", msg);
//            return toChoose(session, model);
//        }
//        if (selectDto.getTeacher1Id() == selectDto.getTeacher2Id() ||
//                selectDto.getTeacher1Id() == selectDto.getTeacher3Id() ||
//                selectDto.getTeacher2Id() == selectDto.getTeacher3Id()) {
//            String msg = "三个志愿不能选同样的老师";
//            model.addAttribute("msg", msg);
//            return toChoose(session, model);
//        }
//        if(selectService.insertSelect(selectDto) == -1){
//            String msg = "选择教师失败";
//            model.addAttribute("msg", msg);
//            return toChoose(session, model);
//        }
//        String msg = "选择教师成功";
//        model.addAttribute("msg", msg);
        return toChoose(session, model);
    }

    @ResponseBody
    @RequestMapping("/deleteByTeamId")
    public Integer deleteByTeamId(Integer teamId) {
        return selectService.deleteByTeamId(teamId);
    }

//    @ResponseBody
//    @RequestMapping("/getTeacherByTeamId")
//    public List<SelectDto> getTeacherByTeamId(Integer teamId) {
//        return selectService.getTeacherByTeamId(teamId);
//    }
}
