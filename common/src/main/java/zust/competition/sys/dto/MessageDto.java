package zust.competition.sys.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDto {
    private Integer id;

    /**
     * 消息发送方id
     */
    private Integer senderId;

    /**
     * 消息接收方id
     */
    private Integer receiverId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 0-未读 1-已读
     */
    private Integer isRead;

    /**
     * 0-无跳转 1-学生组队请求页面 2-老师指导请求页面
     */
    private Integer jumpType;

    /**
     * 是否删除：0-未删除 1-已删除
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 操作人id
     */
    private Integer operatorId;
}
