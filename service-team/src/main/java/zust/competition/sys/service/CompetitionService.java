package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import zust.competition.sys.entity.UserTeam;

@FeignClient("service-competition")
public interface CompetitionService {

    @RequestMapping("/competition/insertUserTeam")
    void insertUserTeam(UserTeam stuComp);
}
