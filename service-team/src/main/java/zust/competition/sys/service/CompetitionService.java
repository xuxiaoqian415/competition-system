package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zust.competition.sys.entity.Competition;
import zust.competition.sys.entity.UserTeam;

@FeignClient("service-competition")
public interface CompetitionService {

    @RequestMapping("/dao/insertUserTeam")
    void insertUserTeam(UserTeam stuComp);

    @RequestMapping("/dao/getCompetitionDetail")
    Competition getCompetitionDetail(@RequestParam("id") Integer id);
}
