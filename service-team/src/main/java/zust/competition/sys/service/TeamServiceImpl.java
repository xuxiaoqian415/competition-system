package zust.competition.sys.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.competition.sys.dao.TeamDao;
import zust.competition.sys.dto.*;
import zust.competition.sys.entity.*;

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
            message.setOperatorId(thisId);
            message.setReceiverId(team.getLeaderId());
            String content= dto.getStudentName()+"同学申请加入您所带领的"+
                    team.getTeamName()+"团队,一起参加***比赛,希望得到您的同意!";
            message.setContent(content);
            message.setJumpType(1);
            if(1==teamDao.insertMessage(message))
                return 1;
        }
        return -1;
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
    public TeamDto getTeamList(Integer leaderId,Integer cpId) {
        TeamDto dto=Te2d(teamDao.getTeamList(leaderId,cpId));
        String teacherName=userService.selectUserById(dto.getTeacherId()).getName();
        if(dto.getStatus()==2) dto.setTeamName(teacherName);
        else dto.setTeamName("待定");
        return dto;
    }

    @Override
    public Integer inviteTeacher(TeamTeacherDto dto) {
        TeamTeacher teamTeacher=TeamTeacher2d(dto);
        if(1 == teamDao.insertTeamTeacher(teamTeacher)){
            String teamName=teamDao.getTeamById(dto.getTeamId()).getTeamName();
            Message message=new Message();
            message.setReceiverId(dto.getTeacherId());
            message.setSenderId(dto.getOperatorId());
            message.setOperatorId(dto.getOperatorId());
            String content= "老师你好，我是"+teamName+"团队负责人"+dto.getLeaderName()+"，现想邀请您成为我们团队的指导老师"+
                   "一起参加***比赛,希望得到您的同意!";
            message.setContent(content);
            message.setJumpType(2);
            if(1==teamDao.insertMessage(message))
                return 1;
        }
        return -1;
    }

    @Override
    public TeamDto getTeamDetail(Integer id) {
        Team t= teamDao.getTeamById(id);
        return Te2d(t);
    }

    @Override
    public List<UserDto> getMember(Integer teamId) {
        List<UserDto> users=new ArrayList<>();
        List<UserTeam> dtos= teamDao.getMember(teamId);
        if(dtos.size()>=0&&dtos!=null) {
            for (UserTeam ut : dtos) {
                UserDto u = userService.selectUserById(ut.getStudentId());
                u.setRole(ut.getRole());
                users.add(u);
            }
        }
        return users;
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
                //        dto.setCpName();
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public List<TeamDto> ownLead(Integer id) {
        List<TeamDto> dtos=new ArrayList<>();
        List<Team> lists=teamDao.ownLead(id);
        if(lists.size()>=0){
            for (Team t:lists) {
                TeamDto dto = Te2d(t);
                dto.setLeaderName(userService.selectUserById(dto.getLeaderId()).getName());
                //        根据cp_id查询竞赛名称
                //        dto.setCpName();
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
        if(lists.size()>=0 && lists!=null){
            for (UserTeam ut:lists) {
                UserTeamDto dto = UserTeam2d(ut);
                dto.setStudentName(userService.selectUserById(dto.getStudentId()).getName());
                dto.setTeamName(teamDao.getTeamById(dto.getTeamId()).getTeamName());
                dtos.add(dto);
            }
        }
        return dtos;
    }


//    @Override
//    public Integer buildTeam(TeamDto teamDto) {
//        Team team = new Team();
//        team.setCpId(teamDto.getCpId());
//        team.setTeamName(teamDto.getTeamName());
//        team.setLeaderId(teamDto.getLeaderId());
//        team.setTeamIntro(teamDto.getTeamIntro());
//        List<Integer> memberList = teamDto.getMemberList();
//        StringBuffer memberBuffer = new StringBuffer();
//        for (Integer mId : memberList) {
//            memberBuffer.append(mId + ";");
//        }
//        String member = memberBuffer.toString();
//        team.setMember(member);
//        if(1 == teamDao.insertTeam(team)){
//            Integer teamId = team.getId();
//            StuComp stuComp = new StuComp();
//            stuComp.setCompetitionId(teamDto.getCpId());
//            stuComp.setTeamId(teamId);
//            for (Integer mId : memberList) {
//                stuComp.setStudentId(mId);
//                competitionService.insertStuComp(stuComp);
//            }
//            stuComp.setStudentId(teamDto.getLeaderId());
//            competitionService.insertStuComp(stuComp);
//            return teamId;
//        }
//        return -1;
//    }

//    @Override
//    public List<TeamDto> getAllTeam() {
//        Query query = new Query();
//        List<TeamDto> list = teamDao.selectTeamList(query);
//        return getMember(list);
//    }
//
//    @Override
//    public List<TeamDto> searchTeam(Query query) {
//        List<TeamDto> list = teamDao.selectTeamList(query);
//        return getMember(list);
//    }
//
//    @Override
//    public Integer deleteTeam(Integer id) {
//        Integer i;
//        try {
//            i = teamDao.deleteTeam(id);
//            teamDao.deleteStuCompByTeamId(id);
//            selectService.deleteByTeamId(id);
//        } catch (Exception e) {
//            i = -1;
//        }
//        return i;
//    }
//
//    @Override
//    public Integer updateTeam(TeamDto teamDto) {
//        Team team = new Team();
//        team.setId(teamDto.getId());
//        team.setTeamName(teamDto.getTeamName());
//        team.setTeamIntro(teamDto.getTeamIntro());
//        ArrayList<Integer> memberList = new ArrayList<>();
//        StringBuffer memberBuffer = new StringBuffer();
//        if(teamDto.getMember1Id() != null){
//            memberList.add(teamDto.getMember1Id());
//            memberBuffer.append(teamDto.getMember1Id() + ";");
//        }
//        if(teamDto.getMember2Id() != null){
//            memberList.add(teamDto.getMember2Id());
//            memberBuffer.append(teamDto.getMember2Id() + ";");
//        }
//        if(teamDto.getMember3Id() != null){
//            memberList.add(teamDto.getMember3Id());
//            memberBuffer.append(teamDto.getMember3Id() + ";");
//        }
//        if(teamDto.getMember4Id() != null){
//            memberList.add(teamDto.getMember4Id());
//            memberBuffer.append(teamDto.getMember4Id() + ";");
//        }
//        if (memberList.size() != 0) {
//            team.setMember(memberBuffer.toString());
//            teamDao.deleteStuCompByTeamId(teamDto.getId());
//            StuComp stuComp = new StuComp();
//            stuComp.setCompetitionId(teamDto.getCpId());
//            stuComp.setTeamId(teamDto.getId());
//            for (Integer mId : memberList) {
//                stuComp.setStudentId(mId);
//                competitionService.insertStuComp(stuComp);
//            }
//            stuComp.setStudentId(teamDto.getLeaderId());
//            competitionService.insertStuComp(stuComp);
//        }
//        return teamDao.updateTeam(team);
//    }
//
//    @Override
//    public Integer adminUpdateTeam(TeamDto teamDto) {
//        Team team = new Team();
//        team.setId(teamDto.getId());
//        team.setTeamName(teamDto.getTeamName());
//        team.setTeamIntro(teamDto.getTeamIntro());
//        ArrayList<Integer> memberList = new ArrayList<>();
//        StringBuffer memberBuffer = new StringBuffer();
//        if(teamDto.getMember1Id() != null){
//            memberList.add(teamDto.getMember1Id());
//            memberBuffer.append(teamDto.getMember1Id() + ";");
//        }
//        if(teamDto.getMember2Id() != null){
//            memberList.add(teamDto.getMember2Id());
//            memberBuffer.append(teamDto.getMember2Id() + ";");
//        }
//        if(teamDto.getMember3Id() != null){
//            memberList.add(teamDto.getMember3Id());
//            memberBuffer.append(teamDto.getMember3Id() + ";");
//        }
//        if(teamDto.getMember4Id() != null){
//            memberList.add(teamDto.getMember4Id());
//            memberBuffer.append(teamDto.getMember4Id() + ";");
//        }
//        // 如果改了负责人、成员都没改
//        if (teamDto.getNewLeaderId() == null && memberList.size() == 0) {
//            return teamDao.updateTeam(team);
//        }
//        StuComp stuComp = new StuComp();
//        stuComp.setCompetitionId(teamDto.getCpId());
//        stuComp.setTeamId(teamDto.getId());
//        // 如果改了负责人，没改成员
//        if (teamDto.getNewLeaderId() != null && memberList.size() == 0) {
//            team.setLeaderId(teamDto.getNewLeaderId());
//            // 删除原负责人的StuComp
//            StuComp delet = new StuComp();
//            delet.setCompetitionId(teamDto.getCpId());
//            delet.setStudentId(teamDto.getLeaderId());
//            teamDao.deleteStuCompByStuId(delet);
//            stuComp.setStudentId(teamDto.getNewLeaderId());
//            competitionService.insertStuComp(stuComp);
//            return teamDao.updateTeam(team);
//        }
//        // 如果改了负责人，改了成员
//        if (teamDto.getNewLeaderId() != null && memberList.size() != 0) {
//            team.setLeaderId(teamDto.getNewLeaderId());
//            team.setMember(memberBuffer.toString());
//            teamDao.deleteStuCompByTeamId(teamDto.getId());
//            for (Integer mId : memberList) {
//                stuComp.setStudentId(mId);
//                competitionService.insertStuComp(stuComp);
//            }
//            stuComp.setStudentId(teamDto.getNewLeaderId());
//            competitionService.insertStuComp(stuComp);
//            return teamDao.updateTeam(team);
//        }
//        // 如果没改负责人，改了成员
//        if (teamDto.getNewLeaderId() == null && memberList.size() != 0) {
//            team.setMember(memberBuffer.toString());
//            teamDao.deleteStuCompByTeamId(teamDto.getId());
//            for (Integer mId : memberList) {
//                stuComp.setStudentId(mId);
//                competitionService.insertStuComp(stuComp);
//            }
//            stuComp.setStudentId(teamDto.getLeaderId());
//            competitionService.insertStuComp(stuComp);
//        }
//        return teamDao.updateTeam(team);
//    }
//
//    @Override
//    public TeamDto getTeamById(Integer id) {
//        TeamDto team = teamDao.selectTeamById(id);
//        if(team.getMember() != null){
//            String[] members = team.getMember().split(";");
//            ArrayList<Integer> memberList = new ArrayList<>();
//            for (String m : members) {
//                memberList.add(Integer.parseInt(m));
//            }
//            team.setMemberList(memberList);
//        }
//        return team;
//    }
//
//    @Override
//    public List<TeamDto> getOwnTeam(Integer id) {
//        Query query= new Query();
//        query.setLeaderId(id);
//        List<TeamDto> list = teamDao.selectTeamList(query);
//        return getMember(list);
//    }
//
//    public List<TeamDto> getMember(List<TeamDto> list) {
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
//        return list;
//    }
//
//    @Override
//    public Integer deleteStuCompByCompId(Integer compId) {
//        return teamDao.deleteStuCompByCompId(compId);
//    }
//
//    @Override
//    public List<TeamDto> selectTeamList(Query query) {
//        return teamDao.selectTeamList(query);
//    }
//
//    @Override
//    public Integer deleteTeamByCpiD(Integer cpId) {
//        return teamDao.deleteTeamByCpiD(cpId);
//    }
}
