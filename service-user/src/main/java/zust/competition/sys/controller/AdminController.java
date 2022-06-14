package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zust.competition.sys.dto.AcademyDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.dto.query.UserQuery;
import zust.competition.sys.entity.Query;
import zust.competition.sys.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/add")
    public String toAddUser(Model model) {
        List<AcademyDto> academyList =userService.academyList();
        model.addAttribute("academyList",academyList);
        return "admin/addUser";
    }

    @PostMapping("/add")
    public String addUser(UserDto userDto,HttpSession session,Model model){
        String msg = "";
        if(!userDto.getNowpsw().equals(userDto.getRpsw())) {
            msg = "两次密码不一致!";
            model.addAttribute("msg", msg);
            return toAddUser(model);
        }
        Integer id = ((UserDto) session.getAttribute("thisUser")).getId();
        userDto.setOperatorId(id);
        userService.addUser(userDto);
        msg = "添加用户成功!";
        model.addAttribute("msg", msg);
        return toAddUser(model);
    }

    @GetMapping("/list")
    public String toUserList(Model model) {
        List<UserDto> userList = userService.getAllUser();
        model.addAttribute("userList",userList);
        List<AcademyDto> academyList =userService.academyList();
        model.addAttribute("academyList",academyList);
        return "admin/userList";
    }

    @PostMapping("/search")
    public String searchUser(UserQuery query, Model model) {
        List<UserDto> userList = userService.searchUser(query);
        model.addAttribute("userList",userList);
        List<AcademyDto> academyList =userService.academyList();
        model.addAttribute("academyList",academyList);
        return "admin/userList";
    }

    @GetMapping("/update/{id}")
    public String toUpdateUser(@PathVariable("id") Integer userId, Model model){
        UserDto thisUser = userService.getUserById(userId);
        model.addAttribute("thisUser",thisUser);
        List<AcademyDto> academyList =userService.academyList();
        model.addAttribute("academyList",academyList);
        return "admin/adminUpdateUser";
    }

    @PostMapping("/update")
    public String updateUser(UserDto userDto, HttpSession session, Model model){
        Integer flag = userService.updateUser(userDto);
        if(-1 == flag){
            model.addAttribute("msg", "修改信息失败!");
            return toUpdateUser(userDto.getId(),model);
        }
        UserDto thisUser = userService.getUserById(userDto.getId());
        session.setAttribute("thisUser", thisUser);
        model.addAttribute("msg", "修改信息成功!");
        return toUpdateUser(userDto.getId(),model);
    }
}
