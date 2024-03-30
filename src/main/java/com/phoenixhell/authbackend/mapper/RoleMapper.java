package com.phoenixhell.authbackend.mapper;

import com.phoenixhell.authbackend.entity.RoleEntity;
import com.phoenixhell.authbackend.entity.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Long roleId);

    int insert(RoleEntity record);

    int insertSelective(RoleEntity record);

    List<RoleEntity> selectByExample(RoleExample example);

    RoleEntity selectByPrimaryKey(Long roleId);

    int updateByExampleSelective(@Param("record") RoleEntity record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") RoleEntity record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(RoleEntity record);

    int updateByPrimaryKey(RoleEntity record);
}