package com.cnautosoft.h2o.admin.service;

import java.util.List;

import com.cnautosoft.h2o.admin.entity.ManagedBeanDetail;

import java.util.Date;

public interface ManagedBeanDetailService {
	ManagedBeanDetail create(ManagedBeanDetail instance,String operator) throws Exception;
	ManagedBeanDetail getByID(Long primaryKey) throws Exception;
	ManagedBeanDetail update(ManagedBeanDetail instance,String operator) throws Exception;
	void deleteByID(Long primaryKey, String operator) throws Exception;
	void deleteAll(String operator) throws Exception;
	void batchDelete(String[] deleteIds, String operator) throws Exception;
	List<ManagedBeanDetail> getList(Integer pageNo, Integer pageSize, String orderBy, Integer softDelete,String systemId,String contextName,String name,String packageName,String beanType,String svcType) throws Exception;
	List<ManagedBeanDetail> findAll() throws Exception;
	Long getAmount(Integer softDelete,String systemId,String contextName,String name,String packageName,String beanType,String svcType) throws Exception;
}
