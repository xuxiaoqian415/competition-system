package zust.competition.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zust.competition.sys.entity.Message;

import java.util.List;

@Mapper
public interface MessageDao {

    /**
     * 按照创建时间新旧对message进行排序查询（收件箱）
     */
    List<Message> receiveMessage(@Param("userId") Integer id);

    /**
     * 按照创建时间新旧对message进行排序查询（发件箱）
     */
    List<Message> sendMessage(@Param("userId") Integer id);

    /**
     * 根据主键查找Message
     */
    Message getMessage(@Param("id") Integer id);

    /**
     * 设置已读状态
     */
    Integer setIsRead(@Param("id") Integer id);

    /**
     * 当前未读消息计数
     */
    Integer countNotRead(@Param("userId") Integer id);

}
