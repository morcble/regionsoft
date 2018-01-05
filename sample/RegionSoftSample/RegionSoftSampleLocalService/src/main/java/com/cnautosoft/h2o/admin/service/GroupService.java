package com.cnautosoft.h2o.admin.service;

import java.util.List;

import com.cnautosoft.h2o.admin.entity.Group;

import java.util.Date;

public interface GroupService {
	Group create(Group instance,String operator) throws Exception;
	Group getByID(Long primaryKey) throws Exception;
	Group update(Group instance,String operator) throws Exception;
	void deleteByID(Long primaryKey, String operator) throws Exception;
	void deleteAll(String operator) throws Exception;
	void batchDelete(String[] deleteIds, String operator) throws Exception;
	List<Group> getList(Integer pageNo, Integer pageSize, String orderBy, Integer softDelete,String systemId,String groupNm,String groupDesc,Long parentGroupId,Integer depth) throws Exception;
	List<Group> findAll() throws Exception;
	Long getAmount(Integer softDelete,String systemId,String groupNm,String groupDesc,Long parentGroupId,Integer depth) throws Exception;
	List<Group> getGroupsByIds(String[] groupIds) throws Exception;
}
