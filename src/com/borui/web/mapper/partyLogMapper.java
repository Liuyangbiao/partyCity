package com.borui.web.mapper;

import com.borui.web.model.partyLog;
import com.borui.web.model.partyLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface partyLogMapper {
    int countByExample(partyLogExample example);

    int deleteByExample(partyLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(partyLog record);

    int insertSelective(partyLog record);

    List<partyLog> selectByExample(partyLogExample example);

    partyLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") partyLog record, @Param("example") partyLogExample example);

    int updateByExample(@Param("record") partyLog record, @Param("example") partyLogExample example);

    int updateByPrimaryKeySelective(partyLog record);

    int updateByPrimaryKey(partyLog record);
}