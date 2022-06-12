package zust.competition.sys.service;

import zust.competition.sys.dto.MessageDto;

import java.util.List;

public interface MessageService {

    /**
     * 收件箱
     */
    List<MessageDto> getReceive(Integer id);

    /**
     * 发件箱
     */
    List<MessageDto> getSend(Integer id);

    /**
     * 详情
     */
    MessageDto getMessage(Integer id);

    /**
     * 当前未读消息计数
     */
    Integer countNotRead(Integer userId);

}
