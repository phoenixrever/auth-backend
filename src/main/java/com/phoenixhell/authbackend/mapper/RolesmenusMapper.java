package com.phoenixhell.authbackend.mapper;

import com.phoenixhell.authbackend.entity.RolesmenusExample;
import com.phoenixhell.authbackend.entity.RolesmenusEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolesmenusMapper {
    long countByExample(RolesmenusExample example);

    int deleteByExample(RolesmenusExample example);

    int deleteByPrimaryKey(RolesmenusEntity key);

    int insert(RolesmenusEntity record);

    int insertSelective(RolesmenusEntity record);

    List<RolesmenusEntity> selectByExample(RolesmenusExample example);

    int updateByExampleSelective(@Param("record") RolesmenusEntity record, @Param("example") RolesmenusExample example);

    int updateByExample(@Param("record") RolesmenusEntity record, @Param("example") RolesmenusExample example);
}