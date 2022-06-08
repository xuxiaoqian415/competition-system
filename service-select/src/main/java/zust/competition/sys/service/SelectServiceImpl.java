package zust.competition.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.competition.sys.dao.SelectDao;
import zust.competition.sys.dto.SelectDto;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.entity.TeamTeacher;

import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {

    @Autowired
    SelectDao selectDao;
    @Autowired
    UserService userService;

    @Override
    public Integer insertSelect(SelectDto selectDto) {
        TeamTeacher teamTeacher = new TeamTeacher();
        teamTeacher.setFlag(0);
        teamTeacher.setTeamId(selectDto.getTeamId());
        Integer i;
        teamTeacher.setTeacherId(selectDto.getTeacher1Id());
        try {
            i = selectDao.insertSelect(teamTeacher);
        } catch (Exception e) {
            i = -1;
        }
        teamTeacher.setTeacherId(selectDto.getTeacher2Id());
        try {
            i = selectDao.insertSelect(teamTeacher);
        } catch (Exception e) {
            i = -1;
        }
        teamTeacher.setTeacherId(selectDto.getTeacher3Id());
        try {
            i = selectDao.insertSelect(teamTeacher);
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }

    @Override
    public List<TeamDto> noSelectTeams(Integer id) {
        List<TeamDto> list = selectDao.noSelectTeams(id);
        return getMember(list);
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

    @Override
    public List<TeamDto> selectTeams(Integer id) {
        List<TeamDto> list = selectDao.selectTeams(id);
        return getMember(list);
    }

    @Override
    public Integer deleteByTeamId(Integer teamId) {
        return selectDao.deleteByTeamId(teamId);
    }

    @Override
    public List<SelectDto> getTeacherByTeamId(Integer teamId) {
        return selectDao.getTeacherByTeamId(teamId);
    }
}
