package zust.competition.sys.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zust.competition.sys.dao.TeamDao;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.query.TeamQuery;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.Team;
import zust.competition.sys.service.TeamService;

import java.util.List;

@Controller
@RequestMapping("/dao")
public class DaoController {

    @Autowired
    private TeamDao teamDao;
    @Autowired
    private TeamService teamService;

    @ResponseBody
    @RequestMapping("/getTeam")
    public TeamDto getTeam(@RequestBody TeamQuery query) {
        return teamDao.getTeam(query);
    }

    @ResponseBody
    @RequestMapping("/getTeamDto")
    public TeamDto getTeamDto(@RequestBody TeamQuery query) {
        return teamService.getTeamDto(query);
    }

    @ResponseBody
    @RequestMapping("/getMyTeamByCpId")
    public Team getMyTeamByCpId(@RequestParam("userId") Integer userId, @RequestParam("cpId") Integer cpId) {
        return teamDao.getMyTeamByCpId(userId, cpId);
    }

    @ResponseBody
    @RequestMapping("/selectTeamList")
    public List<TeamDto> selectTeamList(@RequestBody Query query) {
        return teamService.selectTeamList(query);
    }

    @ResponseBody
    @RequestMapping("/updateTeam")
    public Integer updateTeam(@RequestBody Team team) {
        System.out.println("===team"+team);
        return teamDao.updateTeam(team);
    }
}
