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
import zust.competition.sys.dto.TeamTeacherDto;
import zust.competition.sys.dto.UserTeamDto;
import zust.competition.sys.dto.query.TeamQuery;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.Team;
import zust.competition.sys.entity.UserTeam;
import zust.competition.sys.service.TeamService;

import java.util.List;

@Controller
@RequestMapping("/service")
public class TeamController {

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
    public List<TeamDto> selectTeamList(@RequestBody TeamQuery query) {
        return teamService.selectTeamList(query);
    }

    @ResponseBody
    @RequestMapping("/updateTeam")
    public Integer updateTeam(@RequestBody Team team) {
        return teamDao.updateTeam(team);
    }

    @ResponseBody
    @RequestMapping("/joinTeam")
    public Integer joinTeam(@RequestBody UserTeamDto userTeamDto) {
        return teamService.joinTeam(userTeamDto);
    }

    @ResponseBody
    @RequestMapping("/getLeaderTeam")
    public TeamDto getLeaderTeam(@RequestParam("leaderId") Integer leaderId, @RequestParam("cpId") Integer cpId) {
        return teamService.getLeaderTeam(leaderId, cpId);
    }

    @ResponseBody
    @RequestMapping("/getInviteTeacher")
    public List<String> getInviteTeacher(@RequestParam("teamId") Integer teamId) {
        return teamService.getInviteTeacher(teamId);
    }

    @ResponseBody
    @RequestMapping("/inviteTeacher")
    public Integer inviteTeacher(@RequestBody TeamTeacherDto dto) {
        return teamService.inviteTeacher(dto);
    }

    @ResponseBody
    @RequestMapping("/requestTeam")
    public List<UserTeamDto> requestTeam(@RequestParam("id") Integer id) {
        return teamService.requestTeam(id);
    }

    @ResponseBody
    @RequestMapping("/updateRequestStatus")
    public Integer updateRequestStatus(@RequestParam("id") Integer id, @RequestParam("type") Integer type) {
        return teamService.updateRequestStatus(id, type);
    }

    @ResponseBody
    @RequestMapping("/getUserTeam")
    public UserTeam getUserTeam(@RequestParam("id") Integer id) {
        return teamService.getUserTeam(id);
    }

    @ResponseBody
    @RequestMapping("/ownRequest")
    public List<UserTeamDto> ownRequest(@RequestParam("id") Integer id) {
        return teamService.ownRequest(id);
    }
}
