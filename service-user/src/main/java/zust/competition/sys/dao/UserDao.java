package zust.competition.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.dto.query.UserQuery;
import zust.competition.sys.entity.Academy;
import zust.competition.sys.entity.Message;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.User;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserDao {

    /**
     * 查询所有学院
     */
    List<Academy> academyList();
    /**
     * 根据id查询学院
     */
    Academy getAcademy(Integer id);

    /**
     * 根据主键查找唯一用户
     */
    UserDto selectUser(Query query);

    /**
     * 根据Id查找用户
     */
    User selectUserById(Integer id);

    /**
     * 根据学号或工号查询用户
     */
    User selectUserByNumber(String number);

    /**
     * 根据条件查询所有用户
     */
    List<UserDto> selectUsers(UserQuery query);

    /**
     * 增加用户
     */
    Integer insertUser(User user);

    /**
     * 用户更新
     */
    Integer updateUser(User user);

    /**
     * 获取学生列表
     */
    List<UserDto> getStudentList();

    /**
     * 获取老师列表
     */
    List<UserDto> getTeacherList();

}
