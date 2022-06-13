package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zust.competition.sys.dto.UserDto;

import java.util.List;

@FeignClient("service-user")
public interface UserService {
    @RequestMapping("/user/getTeacherList")
    List<UserDto> getTeacherList();

    @RequestMapping("/user/getStudentList")
    List<UserDto> getStudentList();

    @RequestMapping("/user/selectUserById")
    UserDto selectUserById(@RequestParam("id") Integer id);

}
