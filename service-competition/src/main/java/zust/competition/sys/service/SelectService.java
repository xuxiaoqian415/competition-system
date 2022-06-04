package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import zust.competition.sys.dto.SelectDto;

import java.util.List;

@FeignClient("service-select")
public interface SelectService {

    @RequestMapping("/student/select/deleteByTeamId")
    Integer deleteByTeamId(Integer teamId);

    @RequestMapping("/student/select/getTeacherByTeamId")
    List<SelectDto> getTeacherByTeamId(Integer teamId);
}
