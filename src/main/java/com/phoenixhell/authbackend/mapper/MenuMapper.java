package com.phoenixhell.authbackend.mapper;

import com.phoenixhell.authbackend.entity.MenuEntity;
import com.phoenixhell.authbackend.entity.MenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuMapper {
    long countByExample(MenuExample example);

    int deleteByExample(MenuExample example);

    int deleteByPrimaryKey(Long menuId);

    int insert(MenuEntity record);

    int insertSelective(MenuEntity record);

    List<MenuEntity> selectByExample(MenuExample example);

    MenuEntity selectByPrimaryKey(Long menuId);

    int updateByExampleSelective(@Param("record") MenuEntity record, @Param("example") MenuExample example);

    int updateByExample(@Param("record") MenuEntity record, @Param("example") MenuExample example);

    int updateByPrimaryKeySelective(MenuEntity record);

    int updateByPrimaryKey(MenuEntity record);
}