package com.phoenixhell.authbackend.mapper;

import com.phoenixhell.authbackend.entity.UsersrolesExample;
import com.phoenixhell.authbackend.entity.UsersrolesEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersrolesMapper {
    long countByExample(UsersrolesExample example);

    int deleteByExample(UsersrolesExample example);

    int deleteByPrimaryKey(UsersrolesEntity key);

    int insert(UsersrolesEntity record);

    int insertSelective(UsersrolesEntity record);

    List<UsersrolesEntity> selectByExample(UsersrolesExample example);

    int updateByExampleSelective(@Param("record") UsersrolesEntity record, @Param("example") UsersrolesExample example);

    int updateByExample(@Param("record") UsersrolesEntity record, @Param("example") UsersrolesExample example);
}