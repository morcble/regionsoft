package com.cnautosoft.h2o.admin.service;

import java.util.List;

import com.cnautosoft.h2o.admin.entity.ErrorLog;

import java.util.Date;

public interface ErrorLogService {
	ErrorLog create(ErrorLog instance,String operator) throws Exception;
	ErrorLog getByID(Long primaryKey) throws Exception;
	ErrorLog update(ErrorLog instance,String operator) throws Exception;
	void deleteByID(Long primaryKey, String operator) throws Exception;
	void deleteAll(String operator) throws Exception;
	void batchDelete(String[] deleteIds, String operator) throws Exception;
	List<ErrorLog> getList(Integer pageNo, Integer pageSize, String orderBy, Integer softDelete,String systemId,String requestId,String errorType,String detail) throws Exception;
	List<ErrorLog> findAll() throws Exception;
	Long getAmount(Integer softDelete,String systemId,String requestId,String errorType,String detail) throws Exception;
}
