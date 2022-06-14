package zust.competition.sys.service;

import org.apache.ibatis.annotations.Param;
import zust.competition.sys.dto.SelectDto;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.TeamTeacherDto;

import java.util.List;

public interface SelectService {

    /**
     * 增加一条Select记录
     */
//    Integer insertSelect(SelectDto slectDto);

    /**
     * 根据老师id查找申请指导记录
     */
    List<TeamTeacherDto> getRequestList(Integer id);

    /**
     * 更改选择记录状态
     */
    Integer updateRequestStatus(Integer id, Integer status);

    /**
     * 根据老师id查找指导团队记录
     */
    List<TeamDto> getAgreeList(Integer id);




    /**
     * 根据id更新选择记录
     */
    Integer updateSelect(Integer id);

    /**
     * 获取已反选团队
     */
//    List<TeamDto> selectTeams(Integer id);

    /**
     * 根据teamId删除选择关系
     */
    Integer deleteByTeamId(Integer teamId);

    /**
     * 根据teamId查找老师选择情况
     */
//    List<SelectDto> getTeacherByTeamId(Integer teamId);


}
