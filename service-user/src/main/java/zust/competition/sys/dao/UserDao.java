package zust.competition.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.entity.Message;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.User;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserDao {
    /**
     * 按照创建时间新旧对message进行排序查询（收件箱）
     */
    List<Message> receiveMessage( Integer id);

    /**
     * 按照创建时间新旧对message进行排序查询（发件箱）
     */
    List<Message> sendMessage(Integer id);

    /**
     * 根据主键查找Message
     */
    Message getMessage(Integer id);

    /**
     * 根据主键查找唯一用户
     */
    User selectUser(Query query);

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
    List<UserDto> selectUsers(Query query);

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

    /**
     * 根据id删除用户
     */
    Integer deleteUser(@Param("id") Integer id);

}
