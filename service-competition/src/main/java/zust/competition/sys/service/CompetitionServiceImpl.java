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
    public List<CompetitionDto> getCompetitionList(Integer status) {
        List<Competition> competitionList = competitionDao.getCompetitionList();
        Date currentTime = new Date();
        List<CompetitionDto> competitionDtoList = new ArrayList<>();
        for(Competition competition : competitionList)  {
            CompetitionDto competitionDto = new CompetitionDto();
            BeanUtils.copyProperties(competition,competitionDto);
            if (currentTime.compareTo(competitionDto.getApplyStart()) < 0) {
                competitionDto.setStatus(1);
            }
            else if (currentTime.compareTo(competitionDto.getApplyStart()) >= 0 &&
                     currentTime.compareTo(competitionDto.getApplyEnd()) <= 0) {
                competitionDto.setStatus(2);
            }
            else if (currentTime.compareTo(competitionDto.getStart()) >= 0 &&
                     currentTime.compareTo(competitionDto.getEnd()) <= 0) {
                competitionDto.setStatus(3);
            }
            else {
                competitionDto.setStatus(4);
            }
            if (status.equals(0) || status.equals(competitionDto.getStatus())) {
                competitionDtoList.add(competitionDto);
            }
        }
        return competitionDtoList;
    }

    //    @Override
//    public List<Competition> getCompetitionList() {
//        Query query = new Query();
//        return competitionDao.getCompetitionList(query);
//    }
//
//    @Override
//    public List<Competition> searchCompetition(Query query) {
//        return competitionDao.getCompetitionList(query);
//    }
//
//    @Override
//    public int addCompetition(Competition competition) { return competitionDao.addCompetition(competition); }
//
//    @Override
//    public List<CompetitionDto> getCompetitionByApply() {
//        //return competitionDao.getCompetitionByApply(new Date());
//    }
//
//    @Override
//    public CompetitionDto getCompetitionDetail(Integer id) {
//        return competitionDao.getCompetitionDetail(id);
//    }
//
//    @Override
//    public Integer deleteCompetition(Integer id) {
//        Integer i;
//        try {
//            i = competitionDao.deleteCompetition(id);
//            teamService.deleteStuCompByCompId(id);
//            Query query = new Query();
//            query.setCpId(id);
//            List<TeamDto> teamList = teamService.selectTeamList(query);
//            if (teamList != null) {
//                for (TeamDto t : teamList) {
//                    selectService.deleteByTeamId(t.getId());
//                }
//                teamService.deleteTeamByCpiD(id);
//            }
//        } catch (Exception e) {
//            i = -1;
//        }
//        return i;
//    }
//
//    @Override
//    public Integer updateCompetition(CompetitionDto competitionDto) {
//        Competition competition = new Competition();
//        competition.setId(competitionDto.getId());
//        competition.setCpName(competitionDto.getCpName());
//        competition.setCpContent(competitionDto.getCpContent());
//        competition.setApplyStart(competitionDto.getApplyStart());
//        competition.setApplyEnd(competitionDto.getApplyEnd());
//        competition.setCpStart(competitionDto.getCpStart());
//        competition.setApplyEnd(competitionDto.getApplyEnd());
//        return competitionDao.updateCompetition(competition);
//    }
//
//    @Override
//    public List<CompetitionDto> getApplyList(Integer id) {
//        List<CompetitionDto> list = competitionDao.getApplyList(id);
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
//        return list;
//    }
//
//    @Override
//    public Integer ifHaveApply(StuComp stuComp) {
//        if (competitionDao.ifHaveApply(stuComp) == null || competitionDao.ifHaveApply(stuComp).size() == 0)
//            return 0;
//        else
//            return 1;
//    }
//
//    @Override
//    public void insertStuComp(StuComp stuComp) {
//        competitionDao.insertStuComp(stuComp);
//    }
}