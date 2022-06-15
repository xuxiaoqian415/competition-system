package zust.competition.sys.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.competition.sys.dao.UserDao;
import zust.competition.sys.dto.*;
import zust.competition.sys.dto.query.UserQuery;
import zust.competition.sys.entity.*;

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
        User user = userDao.selectUserById(userId);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }

    @Override
    public Integer updateUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
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
    public List<UserDto> getAllUser() {
        UserQuery query = new UserQuery();
        return userDao.selectUsers(query);
    }

    @Override
    public Integer addUser(UserDto userDto){
        User u=User2d(userDto);
        u.setAcademy(userDao.getAcademy(u.getAcademyId()).getName());
        u.setPassword(userDto.getRpsw());
        return userDao.insertUser(u);
    }

    @Override
    public List<UserDto> searchUser(UserQuery query) {
        List<UserDto> list = userDao.selectUsers(query);
        return list;
    }

    @Override
    public List<AcademyDto> academyList() {
        List<AcademyDto> dtos=new ArrayList<>();
        List<Academy> academies=userDao.academyList();
        if(academies!=null && academies.size()>0) {
            for (Academy a : academies) {
                AcademyDto dto = academy2d(a);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    private AcademyDto academy2d(Academy a){
        if(a==null)
            return null;
        AcademyDto dto=new AcademyDto();
        BeanUtils.copyProperties(a,dto);
        return dto;
    }

    private User User2d(UserDto dto){
        if(dto==null)
            return null;
        User u=new User();
        BeanUtils.copyProperties(dto,u);
        return u;
    }

}
