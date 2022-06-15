package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.query.TeamQuery;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.Team;

import java.util.List;

@FeignClient("service-team")
public interface TeamService {

    @RequestMapping("/service/selectTeamList")
    List<TeamDto> selectTeamList(TeamQuery query);

    @RequestMapping("/service/getTeam")
    TeamDto getTeam(TeamQuery query);

    @RequestMapping("/service/getMyTeamByCpId")
    Team getMyTeamByCpId(@RequestParam("userId") Integer userId, @RequestParam("cpId") Integer cpId);
}
