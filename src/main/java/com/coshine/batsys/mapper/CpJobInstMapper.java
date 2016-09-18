package com.coshine.batsys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.coshine.batsys.entity.CpJobInst;
import com.coshine.batsys.mybatis.PageBounds;

public interface CpJobInstMapper {

	Long insertCpJobInst(CpJobInst cpJobInst);

    Long deleteCpJobInstById(Long id);

    Long deleteCpJobInstByParams(@Param("map") Map<String, String> map);

    Long updateCpJobInst(CpJobInst cpJobInst);

    Long updateCpJobInstByParams(@Param("map") Map<String, String> map);

	CpJobInst searchCpJobInstById(String id);

	List<CpJobInst> searchCpJobInstByParams(@Param("map") Map<String, String> map , PageBounds pageBounds);

	List<CpJobInst> searchCpJobInstByParams(@Param("map") Map<String, String> map);

} 
