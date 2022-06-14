package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zust.competition.sys.dto.AcademyDto;
import zust.competition.sys.dto.LoginDto;
import zust.competition.sys.dto.MessageDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.entity.Query;
import zust.competition.sys.service.MessageService;
import zust.competition.sys.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/login")
    public String login(LoginDto loginDto, HttpSession session, Model model) {
        String msg = "";
        if (loginDto.getNumber() == null || loginDto.getNumber() == "") {
            msg = "请输入学号/工号";
        }
        else if (loginDto.getPassword() == null || loginDto.getPassword() == "") {
            msg = "请输入密码";
        }
        else {
            Integer userId = userService.login(loginDto);
            if (-1 == userId) {
                msg = "类型错误";
            }
            else if (-2 ==userId) {
                msg = "密码错误，请重新输入";
            }
            else if (-3 ==userId) {
                msg = "无该用户，请注册";
            }
            else {
                UserDto thisUser = userService.getUserById(userId);
                session.setAttribute("thisUser", thisUser);
                return index(session, model);
            }
        }
        model.addAttribute("msg", msg);
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        UserDto thisUser = (UserDto)session.getAttribute("thisUser");
        model.addAttribute("thisUser",thisUser);
        model.addAttribute("notRead",messageService.countNotRead(thisUser.getId()));
        return "index2";
    }


    @GetMapping("/update/info")
    public String toUpdateInfo(HttpSession session, Model model){
        UserDto thisUser = (UserDto) session.getAttribute("thisUser");
        model.addAttribute("userInfo",thisUser);
        return "user/updateInfo";
    }

    @PostMapping("/update/info")
    public String updateInfo(UserDto userDto,HttpSession session,Model model){
        String msg = "";
        Integer id = ((UserDto) session.getAttribute("thisUser")).getId();
        userDto.setId(id);
        Integer flag = userService.updateUser(userDto);
        if(-1 == flag){
            msg = "修改信息失败!";
            model.addAttribute("msg", msg);
            return toUpdateInfo(session,model);
        }
        else{
            msg = "修改信息成功!";
            UserDto thisUser = userService.getUserById(id);
            session.setAttribute("thisUser", thisUser);
            model.addAttribute("msg", msg);
            return toUpdateInfo(session,model);
        }
    }

    @GetMapping("/update/psw")
    public String toUpdatePsw(Model model){
        return "user/updatePsw";
    }

    @PostMapping("/update/psw")
    public String updatePsw(UserDto userDto,HttpSession session,Model model){
        String msg = "";
        if(!userDto.getNewpsw().equals(userDto.getRpsw())) {
            msg = "两次密码不一致!";
            model.addAttribute("msg", msg);
            return toUpdatePsw(model); //返回修改密码页面
        }
        Integer id  = ((UserDto) session.getAttribute("thisUser")).getId();
        userDto.setId(id);
        Integer flag = userService.updatePsw(userDto);
        if(-1 == flag){
            msg = "原密码错误!";
            model.addAttribute("msg", msg);
            return toUpdatePsw(model);  //返回修改密码页面
        }
        if (-2 == flag){
            msg = "修改密码失败!";
            model.addAttribute("msg", msg);
            return toUpdatePsw(model);  //返回修改密码页面
        }
        msg = "修改密码成功，请重新登录！";
        UserDto thisUser = userService.getUserById(id);
        session.setAttribute("thisUser", thisUser);
        model.addAttribute("msg", msg);
        session.invalidate();
        return toUpdatePsw(model);  //返回修改密码页面
    }

    @GetMapping("/showHome")
    public String showHome(@RequestParam("id") Integer id,Model model) {
        UserDto userDto = userService.getUserById(id);
        model.addAttribute("userDto",userDto);
        return "user/userHome";
    }

}
