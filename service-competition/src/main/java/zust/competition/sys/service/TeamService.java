package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.entity.Query;

import java.util.List;

@FeignClient("service-team")
public interface TeamService {

    @RequestMapping("/team/deleteStuCompByCompId")
    Integer deleteStuCompByCompId(Integer compId);

    @RequestMapping("/team/selectTeamList")
    List<TeamDto> selectTeamList(Query query);

    @RequestMapping("/team/deleteTeamByCpiD")
    Integer deleteTeamByCpiD(Integer cpId);
}
