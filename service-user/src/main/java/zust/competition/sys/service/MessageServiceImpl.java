package zust.competition.sys.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.competition.sys.dao.MessageDao;
import zust.competition.sys.dao.UserDao;
import zust.competition.sys.dto.MessageDto;
import zust.competition.sys.entity.Message;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;

    private MessageDto Message2d(Message m){
        if(m==null)
            return null;
        MessageDto dto=new MessageDto();
        BeanUtils.copyProperties(m,dto);
        return dto;
    }

    @Override
    public List<MessageDto> getReceive(Integer id) {
        List<MessageDto> dtos=new ArrayList<>();
        List<Message> messages = messageDao.receiveMessage(id);
        int i = 0;
        for (Message m: messages) {
            MessageDto dto=Message2d(m);
            dto.setName(userDao.selectUserById(m.getSenderId()).getName());
            dtos.add(i++, dto);
        }
        return dtos;
    }

    @Override
    public List<MessageDto> getSend(Integer id) {
        List<MessageDto> dtos=new ArrayList<>();
        List<Message> messages = messageDao.sendMessage(id);
        int i = 0;
        for (Message m: messages) {
            MessageDto dto=Message2d(m);
            dto.setName(userDao.selectUserById(m.getReceiverId()).getName());
            dtos.add(i++, dto);
        }
        return dtos;
    }

    @Override
    public MessageDto getMessage(Integer id) {
        Message m = messageDao.getMessage(id);
        MessageDto dto = Message2d(m);
        dto.setName(userDao.selectUserById(m.getSenderId()).getName());
        // 设置已读状态
        messageDao.setIsRead(id);
        return dto;
    }

    @Override
    public Integer countNotRead(Integer userId) {
        return messageDao.countNotRead(userId);
    }
}
