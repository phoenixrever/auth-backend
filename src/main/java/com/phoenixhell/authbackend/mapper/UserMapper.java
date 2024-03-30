package com.phoenixhell.authbackend.mapper;

import com.phoenixhell.authbackend.entity.UserEntity;
import com.phoenixhell.authbackend.entity.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author チヨウ　カツヒ
 * @email phoenixrever@gmail.com
 * @date 2024/03/30 11:11
 * @throws
 *
 * 这边加 @Repository 主要是为了消除idea 不认识mybatis注入的mapper组件
 * 还有另外一种方法就是使用@Resource注解
 *查询
 * - `selectByExample`：按条件查询，需要传入一个 example 对象或者 null；如果传入一个 null，则表示没有条件，也就是查询所有数据
 *
 * - `example.createCriteria().xxx`：创建条件对象，通过 andXXX 方法为 SQL 添加查询添加，每个条件之间是 and 关系
 * - `example.or().xxx`：将之前添加的条件通过 or 拼接其他条件
 *
 * 增改
 * - `updateByPrimaryKey`：通过主键进行数据修改，如果某一个值为 null，也会将对应的字段改为 null
 *     - `mapper.updateByPrimaryKey(new Emp(1,"admin",22,null,"456@qq.com",3));`
 * - `updateByPrimaryKeySelective()`：通过主键进行选择性数据修改，如果某个值为 null，则不修改这个字段
 *     - `mapper.updateByPrimaryKeySelective(new Emp(2,"admin2",22,null,"456@qq.com",3));`
 */
@Repository
public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    List<UserEntity> selectByExample(UserExample example);

    UserEntity selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") UserEntity record, @Param("example") UserExample example);

    int updateByExample(@Param("record") UserEntity record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);
}