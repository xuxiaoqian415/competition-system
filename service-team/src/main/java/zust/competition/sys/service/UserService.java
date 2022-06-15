package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zust.competition.sys.dto.AcademyDto;
import zust.competition.sys.dto.UserDto;

import java.util.List;

@FeignClient("service-user")
public interface UserService {

    @RequestMapping("/dao/getTeacherList")
    List<UserDto> getTeacherList();

    @RequestMapping("/dao/selectUserById")
    UserDto selectUserById(@RequestParam("id") Integer id);

    @RequestMapping("/dao/getAcademyList")
    List<AcademyDto> getAcademyList();

}
