package zust.competition.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zust.competition.sys.dto.SelectDto;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.entity.Select;

import java.util.List;

@Mapper
public interface SelectDao {
    /**
     * 添加一条互选记录
     */
    Integer insertSelect(Select select);

    /**
     * 根据老师id查找申请指导记录
     */
    List<TeamDto> noSelectTeams(Integer id);

    /**
     * 修改反选字段无效——1无效
     */
    Integer updateSelectFlag(Integer id);
    /**
     * 修改反选字段成功——1反选
     */
    Integer updateSelectType(Integer id);
    /**
     * 根据id查询互选记录
     */
    Select selectById(Integer id);

    /**
     * 获取已反选团队
     */
    List<TeamDto> selectTeams(Integer id);

    /**
     * 根据teamId删除选择关系
     */
    Integer deleteByTeamId(@Param("teamId") Integer teamId);

    /**
     * 根据teamId查找老师选择情况
     */
    List<SelectDto> getTeacherByTeamId(@Param("teamId") Integer teamId);

}
