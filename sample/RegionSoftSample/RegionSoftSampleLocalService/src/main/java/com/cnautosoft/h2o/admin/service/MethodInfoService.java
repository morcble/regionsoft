package com.cnautosoft.h2o.admin.service;

import java.util.List;

import com.cnautosoft.h2o.admin.entity.MethodInfo;

import java.util.Date;

public interface MethodInfoService {
	MethodInfo create(MethodInfo instance,String operator) throws Exception;
	MethodInfo getByID(Long primaryKey) throws Exception;
	MethodInfo update(MethodInfo instance,String operator) throws Exception;
	void deleteByID(Long primaryKey, String operator) throws Exception;
	void deleteAll(String operator) throws Exception;
	void batchDelete(String[] deleteIds, String operator) throws Exception;
	List<MethodInfo> getList(Integer pageNo, Integer pageSize, String orderBy, Integer softDelete,Long detailId,String name,String url,String inputType,String returnType) throws Exception;
	List<MethodInfo> findAll() throws Exception;
	Long getAmount(Integer softDelete,Long detailId,String name,String url,String inputType,String returnType) throws Exception;
	List<MethodInfo> getMethodLsByDetailId(Long detailId) throws Exception;
}
