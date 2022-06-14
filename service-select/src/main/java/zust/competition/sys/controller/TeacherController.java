package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zust.competition.sys.dto.*;
import zust.competition.sys.service.SelectService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/teacher/select")
public class TeacherController {

    @Autowired
    SelectService selectService;

    /**
     * 访问指导请求
     */
    @GetMapping("/request/list")
    public String toRequestList() {
        return "teacher/requestList";
    }

    /**
     * 指导请求列表
     */
    @ResponseBody
    @GetMapping("/request")
    public TableVo requestList(HttpSession session) {
        UserDto u = (UserDto) session.getAttribute("thisUser");
        List<TeamTeacherDto> requestList = selectService.getRequestList(u.getId());
        TableVo tableVo = new TableVo(0, requestList);
        return tableVo;
    }

    /**
     * 同意/拒绝指导请求
     */
    @ResponseBody
    @GetMapping("/request/choice")
    public TableVo updateRequestStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status,
                                       HttpSession session) {
        selectService.updateRequestStatus(id,status);
        return requestList(session);
    }

    /**
     * 访问指导团队
     */
    @GetMapping("/agree/list")
    public String toAgreeList() {
        return "teacher/agreeList";
    }

    /**
     * 指导团队列表
     */
    @ResponseBody
    @GetMapping("/agree")
    public TableVo agreeList(HttpSession session) {
        UserDto u = (UserDto) session.getAttribute("thisUser");
        List<TeamDto> agreeList = selectService.getAgreeList(u.getId());
        TableVo tableVo = new TableVo(0, agreeList);
        return tableVo;
    }
}
