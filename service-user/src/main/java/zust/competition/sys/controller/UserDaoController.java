package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zust.competition.sys.dao.UserDao;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.entity.Query;

import java.util.List;

@Controller
@RequestMapping("/dao")
public class UserDaoController {

    @Autowired
    private UserDao userDao;

    @ResponseBody
    @RequestMapping("/getStudentList")
    public List<UserDto> getStudentList() {
        return userDao.getStudentList();
    }

    @ResponseBody
    @RequestMapping("/getTeacherList")
    public List<UserDto> getTeacherList() {
        return userDao.getTeacherList();
    }

    @ResponseBody
    @RequestMapping("/selectUserById")
    public UserDto selectUserById(@RequestParam("id") Integer id) {
        Query query = new Query();
        query.setId(id);
        return userDao.selectUser(query);
    }


}
