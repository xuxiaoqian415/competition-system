package zust.competition.sys.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.competition.sys.dao.TeamDao;
import zust.competition.sys.dto.*;
import zust.competition.sys.dto.query.TeamQuery;
import zust.competition.sys.entity.*;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.dto.UserTeamDto;
import zust.competition.sys.entity.Message;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.Team;
import zust.competition.sys.entity.UserTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public Integer buildTeam(TeamDto dto) {
        Team t=DtoT2d(dto);
        String invitation=getCharAndNumr(6);
        t.setInvitationCode(invitation);
        t.setOperatorId(dto.getLeaderId());
        return teamDao.insertTeam(t);
    }

    public String getCharAndNumr(Integer length)
    {
        String val = "";

        Random random = new Random();
        for(int i = 0; i < length; i++)
        {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            }
            else if("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }

        return val;
    }


    @Override
    public List<UserTeamDto> ownRequest(Integer id) {
        List<UserTeamDto> userTeamDtos=new ArrayList<>();
        List<UserTeam> userTeams=teamDao.ownRequest(id);
        if(userTeams!=null && userTeams.size()!=0){
            for (UserTeam ut:userTeams) {
                UserTeamDto dto= UserTeam2d(ut);
                TeamQuery query=new TeamQuery();
                query.setTeamId(dto.getTeamId());
                Team t= teamDao.getTeam(query);
                dto.setTeamName(t.getTeamName());
                dto.setLeaderId(t.getLeaderId());
                dto.setLeaderName(userService.selectUserById(t.getLeaderId()).getName());
                dto.setCpId(t.getCpId());
                dto.setCpName(competitionService.getCompetitionTile(t.getCpId()));
                userTeamDtos.add(dto);
            }
        }
        return userTeamDtos;
    }

    @Override
    public Integer updateStatus(Integer id) {
        Team team=new Team();
        team.setId(id);
        team.setStatus(1);
        return teamDao.updateTeam(team);
    }

    @Override
    public Integer joinTeam(UserTeamDto dto) {
        UserTeam userTeam=new UserTeam();
        TeamQuery query = new TeamQuery();
        query.setInvitationCode(dto.getInvitationCode());
        Team team=teamDao.getTeam(query);
        if (team == null) { // 邀请码错误
            return -1;
        }
        query.setCpId(dto.getCpId());
        if (teamDao.getTeam(query) == null) { // 不是该竞赛下的团队
            return -2;
        }
        Integer thisId= dto.getStudentId();
        userTeam.setTeamId(team.getId());
        userTeam.setStudentId(thisId);
        userTeam.setRole(dto.getRole());
        userTeam.setOperatorId(thisId);
        if(1 != teamDao.insertUserTeam(userTeam)){
            return -3;
        }
        Message message=new Message();
        message.setSenderId(thisId);
        message.setOperatorId(thisId);
        message.setReceiverId(team.getLeaderId());
        String content= dto.getStudentName()+"同学申请加入您所带领的"+
                team.getTeamName()+"团队,一起参加" +
        competitionService.getCompetitionTile(team.getCpId()) + "比赛,希望得到您的同意!";
        message.setContent(content);
        message.setJumpType(1);
        if(1 != teamDao.insertMessage(message))
            return -3;
        return 1;
    }
    private Team DtoT2d(TeamDto dto){
        if(dto==null)
            return null;
        Team team=new Team();
        BeanUtils.copyProperties(dto,team);
        return team;
    }
    private TeamDto Te2d(Team team){
        if(team==null)
            return null;
        TeamDto dto=new TeamDto();
        BeanUtils.copyProperties(team,dto);
        return dto;
    }
    private TeamTeacher TeamTeacher2d(TeamTeacherDto dto){
        if(dto==null)
            return null;
        TeamTeacher t=new TeamTeacher();
        BeanUtils.copyProperties(dto,t);
        return t;
    }
    private UserTeamDto UserTeam2d(UserTeam ut){
        if(ut==null)
            return null;
        UserTeamDto t=new UserTeamDto();
        BeanUtils.copyProperties(ut,t);
        return t;
    }

    @Override
    public TeamDto getLeaderTeam(Integer leaderId,Integer cpId) {
        TeamQuery query = new TeamQuery();
        query.setLeaderId(leaderId);
        query.setCpId(cpId);
        TeamDto leadTeam = Te2d(teamDao.getTeam(query));
        if (leadTeam.getStatus() == 2 && leadTeam.getTeacherId() != 0) {
                UserDto teacher = userService.selectUserById(leadTeam.getTeacherId());
            if (teacher != null) leadTeam.setTeacherName(teacher.getName());
        }
        if (leadTeam.getStatus() != 2) {
            leadTeam.setTeacherName("待定");
        }
        return leadTeam;
    }

    @Override
    public List<String> getInviteTeacher(Integer teamId) {
        List<TeamTeacher> inviteTeacher = teamDao.getInviteTeacher(teamId);
        List<String> list = new ArrayList<>();
        int i = 0;
        if (inviteTeacher != null && inviteTeacher.size() != 0) {
            for (TeamTeacher t : inviteTeacher) {
                UserDto u = userService.selectUserById(t.getTeacherId());
                if (u != null) {
                    list.add(i++, u.getName());
                }
            }
        }
        return list;
    }

    @Override
    public Integer inviteTeacher(TeamTeacherDto dto) {
        TeamTeacher teamTeacher=TeamTeacher2d(dto);
        if(1 == teamDao.insertTeamTeacher(teamTeacher)){
            TeamQuery query = new TeamQuery();
            query.setTeamId(dto.getTeamId());
            Team team = teamDao.getTeam(query);
            String teamName= team.getTeamName();
            Message message=new Message();
            message.setReceiverId(dto.getTeacherId());
            message.setSenderId(dto.getOperatorId());
            message.setOperatorId(dto.getOperatorId());
            String content= "老师你好，我是"+teamName+"团队负责人"+dto.getLeaderName()+"，现想邀请您成为我们团队的指导老师"+
                   "一起参加" + competitionService.getCompetitionTile(team.getCpId()) + "比赛,希望得到您的同意!";
            message.setContent(content);
            message.setJumpType(2);
            if(1==teamDao.insertMessage(message))
                return 1;
        }
        return -1;
    }

    @Override
    public List<TeamDto> myJoin(Integer id) {
        List<TeamDto> dtos=new ArrayList<>();
        List<Team> lists=teamDao.myJoin(id);
        if(lists.size()>=0){
            for (Team t:lists) {
                TeamDto dto=Te2d(t);
                dto.setLeaderName(userService.selectUserById(dto.getLeaderId()).getName());
                //        根据cp_id查询竞赛名称
                dto.setCpName(competitionService.getCompetitionTile(t.getCpId()));
                if (dto.getTeacherId() == 0) {
                    dto.setTeacherName("待定");
                }
                else {
                    UserDto teacher = userService.selectUserById(dto.getTeacherId());
                    if (teacher != null) {
                        dto.setTeacherName(teacher.getName());
                    }
                }
                dtos.add(dto);
            }
        }
        return dtos;
    }







    @Override
    public List<TeamDto> getAllTeam() {
        Query query = new Query();
        List<TeamDto> list = teamDao.selectTeamList(query);
        return getMember(list);
    }
    @Override
    public TeamDto getTeamDetail(Integer id) {
        TeamQuery query = new TeamQuery();
        query.setTeamId(id);
        Team team = teamDao.getTeam(query);
        TeamDto dto = Te2d(team);
        dto.setLeaderName(userService.selectUserById(dto.getLeaderId()).getName());
        dto.setCpName(competitionService.getCompetitionTile(team.getCpId()));
        if(dto.getIsAwarded()==0) dto.setResult("暂无");
        if(dto.getTeacherId()==0) dto.setTeacherName("待定");
        else {
            UserDto teacher = userService.selectUserById(dto.getTeacherId());
            if (teacher != null) {
                dto.setTeacherName(teacher.getName());
            }
        }
        return dto;
    }

    @Override
    public List<UserDto> getMember(Integer teamId) {
        List<UserDto> users=new ArrayList<>();
        List<UserTeam> dtos= teamDao.getMember(teamId);
        if(dtos!=null && dtos.size()!=0) {
            for (UserTeam ut : dtos) {
                UserDto u = userService.selectUserById(ut.getStudentId());
                if(ut.getRole()!=null) u.setRole(ut.getRole());
                users.add(u);
            }
        }
        return users;
    }

    @Override
    public List<TeamDto> ownLead(Integer id) {
        List<TeamDto> dtos=new ArrayList<>();
        List<Team> lists=teamDao.ownLead(id);
        if(lists != null && lists.size() != 0){
            for (Team t:lists) {
                TeamDto dto = Te2d(t);
                dto.setLeaderName(userService.selectUserById(dto.getLeaderId()).getName());
                dto.setCpName(competitionService.getCompetitionTile(dto.getCpId()));
                if(dto.getTeacherId()==0) dto.setTeacherName("待定");
                else {
                    UserDto teacher = userService.selectUserById(dto.getTeacherId());
                    if (teacher != null) {
                        dto.setTeacherName(teacher.getName());
                    }
                }
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public Integer updateTeam(TeamDto dto) {
        Team t=DtoT2d(dto);
        return teamDao.updateTeam(t);
    }

    @Override
    public List<UserTeamDto> requestTeam(Integer id) {
        List<UserTeamDto> dtos=new ArrayList<>();
        List<UserTeam> lists=teamDao.requestTeam(id);
        if(lists!=null && lists.size()!=0){
            for (UserTeam ut:lists) {
                System.out.println("===service ut"+ut);
                UserTeamDto dto = UserTeam2d(ut);
                dto.setStudentName(userService.selectUserById(dto.getStudentId()).getName());
                TeamQuery query = new TeamQuery();
                query.setTeamId(dto.getTeamId());
                dto.setTeamName(teamDao.getTeam(query).getTeamName());
                System.out.println("===service dto"+dto);
                dtos.add(dto);
            }
        }
        return dtos;
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
        TeamQuery query = new TeamQuery();
        query.setTeamId(id);
        Team team = teamDao.getTeam(query);
        TeamDto teamDto = new TeamDto();
        BeanUtils.copyProperties(team,teamDto);
        UserDto leader = userService.selectUserById(team.getLeaderId());
        teamDto.setLeaderName(leader.getName());
        teamDto.setCpName(competitionService.getCompetitionTile(team.getCpId()));
        return teamDto;
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

}
