package zust.competition.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.competition.sys.dao.TeamDao;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.dto.UserTeamDto;
import zust.competition.sys.entity.Message;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.Team;
import zust.competition.sys.entity.UserTeam;

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
    public Integer joinTeam(UserTeamDto dto) {
        UserTeam userTeam=new UserTeam();
        Team team=teamDao.selectByCode(dto.getInvitationCode());
        Integer thisId= dto.getStudentId();
        userTeam.setTeamId(team.getId());
        userTeam.setStudentId(thisId);
        userTeam.setRole(dto.getRole());
        userTeam.setOperatorId(thisId);
        if(1 == teamDao.insertUserTeam(userTeam)){
            Message message=new Message();
            message.setSenderId(thisId);
            message.setOperatorId(thisId);
            message.setReceiverId(team.getLeaderId());
            String content= dto.getStudentName()+"同学申请加入您所带领的"+
                    team.getTeamName()+"团队,一起参加***比赛,希望得到您的同意!";
            message.setContent(content);
            message.setJumpType(1);
            if(1==teamDao.studentRequest(message)) return 1;

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
            teamDao.deleteUserTeamByTeamId(id);
            selectService.deleteByTeamId(id);
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }

    @Override
    public Integer adminUpdateTeam(TeamDto teamDto) {
//        Team team = new Team();
//        team.setId(teamDto.getId());
//        team.setTeamName(teamDto.getTeamName());
//        team.setTeamIntro(teamDto.getTeamIntro());
//        ArrayList<Integer> memberList = new ArrayList<>();
//        StringBuffer memberBuffer = new StringBuffer();
//        return teamDao.updateTeam(team);
        return null;
    }

    @Override
    public TeamDto getTeamById(Integer id) {
        TeamDto team = teamDao.selectTeamById(id);
//        if(team.getMember() != null){
//            String[] members = team.getMember().split(";");
//            ArrayList<Integer> memberList = new ArrayList<>();
//            for (String m : members) {
//                memberList.add(Integer.parseInt(m));
//            }
//            team.setMemberList(memberList);
//        }
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
    public List<TeamDto> selectTeamList(Query query) {
        return teamDao.selectTeamList(query);
    }

    @Override
    public Integer deleteTeamByCpiD(Integer cpId) {
        return teamDao.deleteTeamByCpiD(cpId);
    }
}
