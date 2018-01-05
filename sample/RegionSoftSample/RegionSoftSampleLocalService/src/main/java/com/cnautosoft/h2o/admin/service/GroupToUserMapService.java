package com.cnautosoft.h2o.admin.service;

import java.util.List;

import com.cnautosoft.h2o.admin.entity.GroupToUserMap;

import java.util.Date;

public interface GroupToUserMapService {
	GroupToUserMap create(GroupToUserMap instance,String operator) throws Exception;
	GroupToUserMap getByID(Long primaryKey) throws Exception;
	GroupToUserMap update(GroupToUserMap instance,String operator) throws Exception;
	void deleteByID(Long primaryKey, String operator) throws Exception;
	void deleteAll(String operator) throws Exception;
	void batchDelete(String[] deleteIds, String operator) throws Exception;
	List<GroupToUserMap> getList(Integer pageNo, Integer pageSize, String orderBy, Integer softDelete,String systemId,Long userId,Long groupId) throws Exception;
	List<GroupToUserMap> findAll() throws Exception;
	Long getAmount(Integer softDelete,String systemId,Long userId,Long groupId) throws Exception;
	List<Long> addUsersToGroup(String[] chosenIds,String systemId,String groupId, String operator) throws Exception;
	List<GroupToUserMap> getGroupsByAccount(String account) throws Exception;
	List<String> getAccountLsByUserMapIds(List<Long> mapIds) throws Exception;
}
