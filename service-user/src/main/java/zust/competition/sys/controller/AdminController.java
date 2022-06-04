package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.entity.Query;
import zust.competition.sys.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class AdminController {

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public String addUser(UserDto userDto,Model model){
        String msg = "";
        if(!userDto.getNowpsw().equals(userDto.getRpsw())) {
            msg = "两次密码不一致!";
            model.addAttribute("msg", msg);
            return "admin/addUser";
        }
        userService.addUser(userDto);
        msg = "添加用户成功!";
        model.addAttribute("msg", msg);
        return "admin/addUser";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model){
        String msg = "";
        if(-1 == userService.deleteUser(id)){
            msg = "删除失败!";
            model.addAttribute("msg", msg);
        }
        else{
            msg = "删除成功!";
            model.addAttribute("msg", msg);
        }
        return toUserList(model);
    }

    @GetMapping("/list")
    public String toUserList(Model model) {
        List<UserDto> userList = userService.getAllUser();
        model.addAttribute("userList",userList);
        return "admin/userList";
    }

    @PostMapping("/search")
    public String searchUser(Query query, Model model) {
        List<UserDto> userList = userService.searchUser(query);
        model.addAttribute("userList",userList);
        return "admin/userList";
    }

    @GetMapping("/update/{id}")
    public String toUpdateUser(@PathVariable("id") Integer userId, Model model){
        UserDto thisUser = userService.getUserById(userId);
        model.addAttribute("userInfo",thisUser);
        return "admin/adminUpdateUser";
    }

    @PostMapping("/update")
    public String updateUser(UserDto userDto, HttpSession session, Model model){
        String msg = "";
        Integer flag = userService.updateUser(userDto);
        if(-1 == flag){
            msg = "修改信息失败!";
            model.addAttribute("msg", msg);
            return toUpdateUser(userDto.getId(),model);
        }
        msg = "修改信息成功!";
        UserDto thisUser = userService.getUserById(userDto.getId());
        session.setAttribute("thisUser", thisUser);
        model.addAttribute("msg", msg);
        return toUpdateUser(userDto.getId(),model);
    }
}
