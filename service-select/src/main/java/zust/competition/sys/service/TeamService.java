package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zust.competition.sys.dto.TeamDto;

@FeignClient("service-team")
public interface TeamService {
    @RequestMapping("/team/getTeam")
    TeamDto getTeam(@RequestParam("id") Integer id);
}
