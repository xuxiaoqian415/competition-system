package zust.competition.sys.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.competition.sys.dao.UserDao;
import zust.competition.sys.dto.LoginDto;
import zust.competition.sys.dto.MessageDto;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.entity.Message;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.Team;
import zust.competition.sys.entity.User;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public Integer login(LoginDto loginDto){
        User user = userDao.selectUserByNumber(loginDto.getNumber());
        if(user != null){
            if(loginDto.getPassword().equals(user.getPassword())){
                if(loginDto.getType().equals(user.getType())){
                    return user.getId();    //登录验证成功
                }
                else{
                    return -1;  //类型不匹配
                }

            }
            else{
                return -2;  //密码错误
            }
        }
        else{
            return -3;  //无该学号/工号的用户
        }
    }

    @Override
    public UserDto getUserById(Integer userId) {
        return userDao.selectUserById(userId);
    }

    @Override
    public Integer updateUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setMobile(userDto.getMobile());
        user.setEmail(userDto.getEmail());
        user.setEmail(userDto.getEmail());
        user.setIntro(userDto.getIntro());
        if (userDto.getNumber() != null) {
            user.setNumber(userDto.getNumber());
        }
        Integer i;
        try {
            i = userDao.updateUser(user);
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }

    @Override
    public Integer updatePsw(UserDto userDto) {
        Query query = new Query();
        query.setId(userDto.getId());
        query.setPassword(userDto.getNowpsw());
        if (userDao.selectUser(query) == null) {
            return -1;     // 原密码错误
        }
        User newUser = new User();
        newUser.setId(userDto.getId());
        newUser.setPassword(userDto.getNewpsw());
        Integer i;
        try {
            i = userDao.updateUser(newUser);
        } catch (Exception e) {
            i = -2; // 修改密码失败
        }
        return i;
    }

    @Override
    public Integer deleteUser(Integer id) {
        Integer i;
        try {
            i = userDao.deleteUser(id);
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }

    @Override
    public List<UserDto> getAllUser() {
        Query query = new Query();
        return userDao.selectUsers(query);
    }

    @Override
    public Integer addUser(UserDto userDto){
        User user = new User();
        user.setType(userDto.getType());
        user.setNumber(userDto.getNumber());
        user.setName(userDto.getName());
        user.setMobile(userDto.getMobile());
        user.setPassword(userDto.getNowpsw());
        user.setEmail(userDto.getEmail());
        user.setIntro(userDto.getIntro());
        return userDao.insertUser(user);
    }

    @Override
    public List<UserDto> searchUser(Query query) {
        List<UserDto> list = userDao.selectUsers(query);
        return list;
    }

    @Override
    public List<UserDto> getStudentList() {
        return userDao.getStudentList();
    }

    @Override
    public List<UserDto> getTeacherList(){
        return userDao.getTeacherList();
    }

    @Override
    public UserDto selectUserById(Integer id) {
        return userDao.selectUserById(id);
    }
}
