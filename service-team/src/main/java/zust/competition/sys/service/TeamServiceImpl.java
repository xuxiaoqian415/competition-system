package zust.competition.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.competition.sys.dao.TeamDao;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.StuComp;
import zust.competition.sys.entity.Team;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamDao teamDao;
    @Autowired
    UserService userService;
    @Autowired
    CompetitionService competitionService;
    @Autowired
    SelectService selectService;

    @Override
    public Integer buildTeam(TeamDto teamDto) {
        Team team = new Team();
        team.setCpId(teamDto.getCpId());
        team.setTeamName(teamDto.getTeamName());
        team.setLeaderId(teamDto.getLeaderId());
        team.setTeamIntro(teamDto.getTeamIntro());
        List<Integer> memberList = teamDto.getMemberList();
        StringBuffer memberBuffer = new StringBuffer();
        for (Integer mId : memberList) {
            memberBuffer.append(mId + ";");
        }
        String member = memberBuffer.toString();
        team.setMember(member);
        if(1 == teamDao.insertTeam(team)){
            Integer teamId = team.getId();
            StuComp stuComp = new StuComp();
            stuComp.setCompetitionId(teamDto.getCpId());
            stuComp.setTeamId(teamId);
            for (Integer mId : memberList) {
                stuComp.setStudentId(mId);
                competitionService.insertStuComp(stuComp);
            }
            stuComp.setStudentId(teamDto.getLeaderId());
            competitionService.insertStuComp(stuComp);
            return teamId;
        }
        return -1;
    }

    @Override
    public List<TeamDto> getAllTeam() {
        Query query = new Query();
        List<TeamDto> list = teamDao.selectTeamList(query);
        return getMember(list);
    }

    @Override
    public List<TeamDto> searchTeam(Query query) {
        List<TeamDto> list = teamDao.selectTeamList(query);
        return getMember(list);
    }

    @Override
    public Integer deleteTeam(Integer id) {
        Integer i;
        try {
            i = teamDao.deleteTeam(id);
            teamDao.deleteStuCompByTeamId(id);
            selectService.deleteByTeamId(id);
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }

    @Override
    public Integer updateTeam(TeamDto teamDto) {
        Team team = new Team();
        team.setId(teamDto.getId());
        team.setTeamName(teamDto.getTeamName());
        team.setTeamIntro(teamDto.getTeamIntro());
        ArrayList<Integer> memberList = new ArrayList<>();
        StringBuffer memberBuffer = new StringBuffer();
        if(teamDto.getMember1Id() != null){
            memberList.add(teamDto.getMember1Id());
            memberBuffer.append(teamDto.getMember1Id() + ";");
        }
        if(teamDto.getMember2Id() != null){
            memberList.add(teamDto.getMember2Id());
            memberBuffer.append(teamDto.getMember2Id() + ";");
        }
        if(teamDto.getMember3Id() != null){
            memberList.add(teamDto.getMember3Id());
            memberBuffer.append(teamDto.getMember3Id() + ";");
        }
        if(teamDto.getMember4Id() != null){
            memberList.add(teamDto.getMember4Id());
            memberBuffer.append(teamDto.getMember4Id() + ";");
        }
        if (memberList.size() != 0) {
            team.setMember(memberBuffer.toString());
            teamDao.deleteStuCompByTeamId(teamDto.getId());
            StuComp stuComp = new StuComp();
            stuComp.setCompetitionId(teamDto.getCpId());
            stuComp.setTeamId(teamDto.getId());
            for (Integer mId : memberList) {
                stuComp.setStudentId(mId);
                competitionService.insertStuComp(stuComp);
            }
            stuComp.setStudentId(teamDto.getLeaderId());
            competitionService.insertStuComp(stuComp);
        }
        return teamDao.updateTeam(team);
    }

    @Override
    public Integer adminUpdateTeam(TeamDto teamDto) {
        Team team = new Team();
        team.setId(teamDto.getId());
        team.setTeamName(teamDto.getTeamName());
        team.setTeamIntro(teamDto.getTeamIntro());
        ArrayList<Integer> memberList = new ArrayList<>();
        StringBuffer memberBuffer = new StringBuffer();
        if(teamDto.getMember1Id() != null){
            memberList.add(teamDto.getMember1Id());
            memberBuffer.append(teamDto.getMember1Id() + ";");
        }
        if(teamDto.getMember2Id() != null){
            memberList.add(teamDto.getMember2Id());
            memberBuffer.append(teamDto.getMember2Id() + ";");
        }
        if(teamDto.getMember3Id() != null){
            memberList.add(teamDto.getMember3Id());
            memberBuffer.append(teamDto.getMember3Id() + ";");
        }
        if(teamDto.getMember4Id() != null){
            memberList.add(teamDto.getMember4Id());
            memberBuffer.append(teamDto.getMember4Id() + ";");
        }
        // 如果改了负责人、成员都没改
        if (teamDto.getNewLeaderId() == null && memberList.size() == 0) {
            return teamDao.updateTeam(team);
        }
        StuComp stuComp = new StuComp();
        stuComp.setCompetitionId(teamDto.getCpId());
        stuComp.setTeamId(teamDto.getId());
        // 如果改了负责人，没改成员
        if (teamDto.getNewLeaderId() != null && memberList.size() == 0) {
            team.setLeaderId(teamDto.getNewLeaderId());
            // 删除原负责人的StuComp
            StuComp delet = new StuComp();
            delet.setCompetitionId(teamDto.getCpId());
            delet.setStudentId(teamDto.getLeaderId());
            teamDao.deleteStuCompByStuId(delet);
            stuComp.setStudentId(teamDto.getNewLeaderId());
            competitionService.insertStuComp(stuComp);
            return teamDao.updateTeam(team);
        }
        // 如果改了负责人，改了成员
        if (teamDto.getNewLeaderId() != null && memberList.size() != 0) {
            team.setLeaderId(teamDto.getNewLeaderId());
            team.setMember(memberBuffer.toString());
            teamDao.deleteStuCompByTeamId(teamDto.getId());
            for (Integer mId : memberList) {
                stuComp.setStudentId(mId);
                competitionService.insertStuComp(stuComp);
            }
            stuComp.setStudentId(teamDto.getNewLeaderId());
            competitionService.insertStuComp(stuComp);
            return teamDao.updateTeam(team);
        }
        // 如果没改负责人，改了成员
        if (teamDto.getNewLeaderId() == null && memberList.size() != 0) {
            team.setMember(memberBuffer.toString());
            teamDao.deleteStuCompByTeamId(teamDto.getId());
            for (Integer mId : memberList) {
                stuComp.setStudentId(mId);
                competitionService.insertStuComp(stuComp);
            }
            stuComp.setStudentId(teamDto.getLeaderId());
            competitionService.insertStuComp(stuComp);
        }
        return teamDao.updateTeam(team);
    }

    @Override
    public TeamDto getTeamById(Integer id) {
        TeamDto team = teamDao.selectTeamById(id);
        if(team.getMember() != null){
            String[] members = team.getMember().split(";");
            ArrayList<Integer> memberList = new ArrayList<>();
            for (String m : members) {
                memberList.add(Integer.parseInt(m));
            }
            team.setMemberList(memberList);
        }
        return team;
    }

    @Override
    public List<TeamDto> getOwnTeam(Integer id) {
        Query query= new Query();
        query.setLeaderId(id);
        List<TeamDto> list = teamDao.selectTeamList(query);
        return getMember(list);
    }

    public List<TeamDto> getMember(List<TeamDto> list) {
        for (TeamDto t : list) {
            StringBuffer memberNames = new StringBuffer();
            if (t.getMember() != null && !t.getMember().equals("")) {
                String[] members = t.getMember().split(";");
                for (String m : members) {
                    UserDto u = userService.selectUserById(Integer.parseInt(m));
                    if (u != null) {
                        memberNames.append(u.getName() + ",");
                    }
                }
                t.setMemberNames(memberNames.toString());
            }
            else
                t.setMemberNames("");
        }
        return list;
    }

    @Override
    public Integer deleteStuCompByCompId(Integer compId) {
        return teamDao.deleteStuCompByCompId(compId);
    }

    @Override
    public List<TeamDto> selectTeamList(Query query) {
        return teamDao.selectTeamList(query);
    }

    @Override
    public Integer deleteTeamByCpiD(Integer cpId) {
        return teamDao.deleteTeamByCpiD(cpId);
    }
}
