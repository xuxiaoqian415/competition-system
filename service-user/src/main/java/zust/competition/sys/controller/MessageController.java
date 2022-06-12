package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import zust.competition.sys.dto.MessageDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.service.MessageService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    /**
     * 收件箱
     */
    @GetMapping("/received")
    public String toReceived(HttpSession session, Model model){
        Integer userId = ((UserDto) session.getAttribute("thisUser")).getId();
        List<MessageDto> dtos = messageService.getReceive(userId);
        if(dtos.size()==0)
            model.addAttribute("msg","当前没有消息");
        model.addAttribute("messageList",dtos);
        return "user/received_list";
    }

    /**
     * 发件箱
     */
    @GetMapping("/sent")
    public String toSent(HttpSession session, Model model){
        Integer userId = ((UserDto) session.getAttribute("thisUser")).getId();
        List<MessageDto> dtos = messageService.getSend(userId);
        if(dtos.size()==0)
            model.addAttribute("msg","当前没有已发送的消息");
        model.addAttribute("messageList",dtos);
        return "user/sent_list";
    }

    /**
     * 收件-详情
     */
    @GetMapping("/detail/{id}")
    public String toDetail(@PathVariable Integer id, Model model){
        MessageDto dto= messageService.getMessage(id);
        model.addAttribute("message",dto);
        return "user/messageDetail";
    }

}
