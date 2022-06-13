package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("select-service")
public interface SelectService {

    @RequestMapping("/dao/deleteByTeamId")
    Integer deleteByTeamId(Integer teamId);
}
