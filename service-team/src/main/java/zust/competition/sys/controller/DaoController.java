package zust.competition.sys.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zust.competition.sys.dao.TeamDao;
import zust.competition.sys.dto.query.TeamQuery;
import zust.competition.sys.entity.Team;

@Controller
@RequestMapping("/dao")
public class DaoController {

    @Autowired
    private TeamDao teamDao;

    @ResponseBody
    @RequestMapping("/getTeam")
    public Team getTeam(@RequestBody TeamQuery query) {
        return teamDao.getTeam(query);
    }

    @ResponseBody
    @RequestMapping("/getMyTeamByCpId")
    public Team getMyTeamByCpId(@RequestParam("userId") Integer userId, @RequestParam("cpId") Integer cpId) {
        return teamDao.getMyTeamByCpId(userId, cpId);
    }
}
