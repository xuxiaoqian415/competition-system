package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import zust.competition.sys.dto.SelectDto;

import java.util.List;

@FeignClient("service-select")
public interface SelectService {

    @RequestMapping("/dao/deleteByTeamId")
    Integer deleteByTeamId(Integer teamId);

}
