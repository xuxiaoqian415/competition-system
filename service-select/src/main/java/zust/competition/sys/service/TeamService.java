package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.query.TeamQuery;

@FeignClient("service-team")
public interface TeamService {
    @RequestMapping("/dao/getTeam")
    TeamDto getTeam(TeamQuery query);
}
