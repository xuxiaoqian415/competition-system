package zust.competition.sys.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.competition.sys.dao.SelectDao;
import zust.competition.sys.dto.SelectDto;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.TeamTeacherDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.dto.query.TeamQuery;
import zust.competition.sys.entity.Team;
import zust.competition.sys.entity.TeamTeacher;

import java.util.ArrayList;
import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {

    @Autowired
    SelectDao selectDao;
    @Autowired
    UserService userService;
    @Autowired
    TeamService teamService;

//    @Override
//    public Integer insertSelect(SelectDto selectDto) {
//        TeamTeacher teamTeacher = new TeamTeacher();
//        teamTeacher.setFlag(0);
//        teamTeacher.setTeamId(selectDto.getTeamId());
//        Integer i;
//        teamTeacher.setTeacherId(selectDto.getTeacher1Id());
//        try {
//            i = selectDao.insertSelect(teamTeacher);
//        } catch (Exception e) {
//            i = -1;
//        }
//        teamTeacher.setTeacherId(selectDto.getTeacher2Id());
//        try {
//            i = selectDao.insertSelect(teamTeacher);
//        } catch (Exception e) {
//            i = -1;
//        }
//        teamTeacher.setTeacherId(selectDto.getTeacher3Id());
//        try {
//            i = selectDao.insertSelect(teamTeacher);
//        } catch (Exception e) {
//            i = -1;
//        }
//        return i;
//    }

    @Override
    public List<TeamTeacherDto> getRequestList(Integer id) {
        List<TeamTeacher> teamTeacherList = selectDao.getRequestList(id);
        List<TeamTeacherDto> teamTeacherDtoList = new ArrayList<>();
        for (TeamTeacher teamTeacher :teamTeacherList) {
            TeamTeacherDto teamTeacherDto = new TeamTeacherDto();
            BeanUtils.copyProperties(teamTeacher,teamTeacherDto);
            Integer teamId = teamTeacher.getTeamId();
            TeamQuery query = new TeamQuery();
            query.setTeamId(teamId);
            TeamDto teamDto = teamService.getTeamDto(query);
            teamTeacherDto.setTeamName(teamDto.getTeamName());
            teamTeacherDto.setCpName(teamDto.getCpName());
            teamTeacherDto.setLeaderId(teamDto.getLeaderId());
            teamTeacherDto.setLeaderName(teamDto.getLeaderName());
            teamTeacherDto.setCpId(teamDto.getCpId());
            teamTeacherDtoList.add(teamTeacherDto);
        }
        return teamTeacherDtoList;
    }

    @Override
    public Integer updateRequestStatus(Integer id, Integer status) {
        TeamTeacher teamTeacher = selectDao.selectById(id);
        Team teamModel = new Team();
        teamModel.setId(teamTeacher.getTeamId());
        // 取消
        if (status.equals(0)) {
            selectDao.updateFlag(teamTeacher.getTeamId(),id,0);
            teamModel.setTeacherId(0);
            teamModel.setStatus(1);
            teamService.updateTeam(teamModel);
        }
        // 同意
        if (status.equals(2)) {
            selectDao.updateFlag(teamTeacher.getTeamId(),id,1);
            teamModel.setTeacherId(teamTeacher.getTeacherId());
            teamModel.setStatus(2);
            teamService.updateTeam(teamModel);
        }
        return selectDao.updateSelectStatus(id,status);
    }


    @Override
    public List<TeamDto> getAgreeList(Integer id) {
        List<TeamTeacher> teamTeacherList = selectDao.getAgreeList(id);
        List<TeamDto> teamDtoList = new ArrayList<>();
        for (TeamTeacher teamTeacher : teamTeacherList){
            TeamQuery query = new TeamQuery();
            query.setTeamId(teamTeacher.getTeamId());
            TeamDto teamDto = teamService.getTeamDto(query);
            teamDtoList.add(teamDto);
        }
        return teamDtoList;
    }

    public List<TeamDto> getMember(List<TeamDto> list) {
//        for (TeamDto t : list) {
//            StringBuffer memberNames = new StringBuffer();
//            if (t.getMember() != null && !t.getMember().equals("")) {
//                String[] members = t.getMember().split(";");
//                for (String m : members) {
//                    UserDto u = userService.selectUserById(Integer.parseInt(m));
//                    if (u != null) {
//                        memberNames.append(u.getName() + ",");
//                    }
//                }
//                t.setMemberNames(memberNames.toString());
//            }
//            else
//                t.setMemberNames("");
//        }
        return list;
    }

    @Override
    public Integer updateSelect(Integer id) {
        int count=0;
        int i=selectDao.updateSelectType(id);
        int j=selectDao.updateSelectFlag(selectDao.selectById(id).getTeamId());
        if(i!=1||j!=1) count=1;
        return count;
    }

//    @Override
//    public List<TeamDto> selectTeams(Integer id) {
//        List<TeamDto> list = selectDao.selectTeams(id);
//        return getMember(list);
//    }

    @Override
    public Integer deleteByTeamId(Integer teamId) {
        return selectDao.deleteByTeamId(teamId);
    }

//    @Override
//    public List<SelectDto> getTeacherByTeamId(Integer teamId) {
//        return selectDao.getTeacherByTeamId(teamId);
//    }
}
