package zust.competition.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zust.competition.sys.entity.UserTeam;

@Mapper
public interface UserTeamDao {

    /**
     * 加入团队
     */
    Integer insertUserTeam(UserTeam userTeam);

    /**
     * 根据teamId删除所有UserTeam
     */
    void deleteUserTeamByTeamId(@Param("teamId") Integer teamId);

    /**
     * 删除一条UserTeam
     */
    void deleteUserTeam(@Param("studentId") Integer studentId, @Param("teamId") Integer teamId);
}
