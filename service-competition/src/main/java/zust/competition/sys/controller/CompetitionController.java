package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zust.competition.sys.dao.CompetitionDao;
import zust.competition.sys.dto.CompetitionDto;
import zust.competition.sys.entity.Competition;
import zust.competition.sys.entity.UserTeam;

@Controller
@RequestMapping("/dao")
public class CompetitionController {

    @Autowired
    private CompetitionDao competitionDao;


    @ResponseBody
    @RequestMapping("/insertUserTeam")
    public void insertUserTeam(@RequestBody UserTeam stuComp) {
        competitionDao.insertUserTeam(stuComp);
    }

    @ResponseBody
    @RequestMapping("/getCompetitionDetail")
    public Competition getCompetitionDetail(@RequestParam("id") Integer id) {
        Competition detail = competitionDao.getCompetitionDetail(id);
        if (detail == null)
            return new Competition();
        return detail;
    }

}
