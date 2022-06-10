package zust.competition.sys.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.competition.sys.dao.CompetitionDao;
import zust.competition.sys.dto.CompetitionDto;
import zust.competition.sys.dto.SelectDto;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.entity.Competition;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.UserTeam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    CompetitionDao competitionDao;
    @Autowired
    TeamService teamService;
    @Autowired
    SelectService selectService;

    @Override
    public List<CompetitionDto> getCompetitionList() {
        Query query = new Query();
        List<Competition> list = competitionDao.getCompetitionList(query);
        ArrayList<CompetitionDto> dtos = new ArrayList<>();
        int i=0;
        for (Competition c : list) {
            CompetitionDto dto = new CompetitionDto();
            BeanUtils.copyProperties(c, dto);
            dtos.add(i++, dto);
        }
        return dtos;
    }

    @Override
    public List<CompetitionDto> searchCompetition(Query query) {
        List<Competition> list = competitionDao.getCompetitionList(query);
        ArrayList<CompetitionDto> dtos = new ArrayList<>();
        int i=0;
        for (Competition c : list) {
            CompetitionDto dto = new CompetitionDto();
            BeanUtils.copyProperties(c, dto);
            dtos.add(i++, dto);
        }
        return dtos;
    }

    @Override
    public Integer addCompetition(Competition competition) {
        try {
            competitionDao.addCompetition(competition);
        } catch (Exception e) {
            return -1;
        }
        return competition.getId();
    }

    @Override
    public List<Competition> getCompetitionByApply() {
        return competitionDao.getCompetitionByApply(new Date());
    }

    @Override
    public CompetitionDto getCompetitionDetail(Integer id) {
        Competition competition = competitionDao.getCompetitionDetail(id);
        CompetitionDto dto = new CompetitionDto();
        BeanUtils.copyProperties(competition, dto);
        return dto;
    }

    @Override
    public Integer deleteCompetition(Integer id) {
        Query query = new Query();
        query.setCpId(id);
        List<TeamDto> teamList = teamService.selectTeamList(query);
        if (teamList.size() != 0) {
            return -1; // 该竞赛下已有报名团队，不能删除
        }
        return competitionDao.deleteCompetition(id);
    }

    @Override
    public Integer updateCompetition(CompetitionDto dto) {
        Competition competition = new Competition();
        BeanUtils.copyProperties(dto, competition);
        try {
            competitionDao.updateCompetition(competition);
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }

    @Override
    public List<CompetitionDto> getApplyList(Integer id) {
        List<CompetitionDto> list = competitionDao.getApplyList(id);
//        for (CompetitionDto c : list) {
//            Integer teamId = c.getTeamId();
//            List<SelectDto> teachers = selectService.getTeacherByTeamId(teamId);
//            if (teachers!=null && teachers.size()!=0) {
//                c.setHaveChoose(1);
//                c.setTeacherName(teachers.get(0).getTeacherName());
//            }
//            else {
//                c.setHaveChoose(0);
//            }
//        }
        return list;
    }

    @Override
    public Integer ifHaveApply(UserTeam stuComp) {
        if (competitionDao.ifHaveApply(stuComp) == null || competitionDao.ifHaveApply(stuComp).size() == 0)
            return 0;
        else
            return 1;
    }

    @Override
    public void insertUserTeam(UserTeam stuComp) {
        competitionDao.insertUserTeam(stuComp);
    }
}