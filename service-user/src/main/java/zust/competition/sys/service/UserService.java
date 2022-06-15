package zust.competition.sys.service;

import zust.competition.sys.dto.AcademyDto;
import zust.competition.sys.dto.LoginDto;
import zust.competition.sys.dto.MessageDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.dto.query.UserQuery;
import zust.competition.sys.entity.Query;

import java.util.List;

public interface UserService {

    /**
     * 用户登录
     */
    Integer login(LoginDto loginDto);

    /**
     * 根据Id获取用户信息
     */
    UserDto getUserById(Integer userId);

    /**
     * 更新用户
     */
    Integer updateUser(UserDto userDto);

    /**
     * 修改密码
     */
    Integer updatePsw(UserDto userDto);

    /**
     * 获取所有用户
     */
    List<UserDto> getAllUser();

    /**
     * 添加用户
     */
    Integer addUser(UserDto userDto);

    /**
     * 根据条件查询用户列表
     */
    List<UserDto> searchUser(UserQuery query);

    /**
     * 查询所有学院
     */
    List<AcademyDto> academyList();


}
