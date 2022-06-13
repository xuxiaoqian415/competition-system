package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zust.competition.sys.dao.SelectDao;

@Controller
@RequestMapping("/dao")
public class SelectDaoController {

    @Autowired
    private SelectDao selectDao;

    @ResponseBody
    @RequestMapping("/deleteByTeamId")
    public Integer deleteByTeamId(Integer teamId) {
        return selectDao.deleteByTeamId(teamId);
    }
}
