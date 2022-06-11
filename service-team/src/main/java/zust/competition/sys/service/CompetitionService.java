package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zust.competition.sys.dto.CompetitionDto;
import zust.competition.sys.entity.UserTeam;

@FeignClient("service-competition")
public interface CompetitionService {

    @RequestMapping("/competition/insertUserTeam")
    void insertUserTeam(UserTeam stuComp);

    @RequestMapping("/competition/getCompetition")
    CompetitionDto getCompetitionById(@RequestParam("id") Integer id);

}
