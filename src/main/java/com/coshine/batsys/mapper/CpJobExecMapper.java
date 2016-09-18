package com.coshine.batsys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.coshine.batsys.entity.CpJobExec;
import com.coshine.batsys.mybatis.PageBounds;

public interface CpJobExecMapper {

	Long insertCpJobExec(CpJobExec cpJobExec);

    Long deleteCpJobExecById(Long id);

    Long deleteCpJobExecByParams(@Param("map") Map<String, String> map);

    Long updateCpJobExec(CpJobExec cpJobExec);

    Long updateCpJobExecByParams(@Param("map") Map<String, String> map);

	CpJobExec searchCpJobExecById(String id);

	List<CpJobExec> searchCpJobExecByParams(@Param("map") Map<String, String> map , PageBounds pageBounds);

	List<CpJobExec> searchCpJobExecByParams(@Param("map") Map<String, String> map);

} 
