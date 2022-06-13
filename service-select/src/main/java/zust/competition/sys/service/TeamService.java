package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.query.TeamQuery;
import zust.competition.sys.entity.Team;

@FeignClient("service-team")
public interface TeamService {

    @RequestMapping("/dao/getTeam")
    TeamDto getTeam(TeamQuery query);

    @RequestMapping("/dao/updateTeam")
    Integer updateTeam(Team team);
}
