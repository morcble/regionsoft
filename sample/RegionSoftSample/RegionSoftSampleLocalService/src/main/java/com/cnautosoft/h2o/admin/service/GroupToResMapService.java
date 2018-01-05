package com.cnautosoft.h2o.admin.service;

import java.util.List;
import java.util.Set;

import com.cnautosoft.h2o.admin.entity.GroupToResMap;
import com.cnautosoft.h2o.admin.entity.MethodInfo;

import java.util.Date;

public interface GroupToResMapService {
	GroupToResMap create(GroupToResMap instance,String operator) throws Exception;
	GroupToResMap getByID(Long primaryKey) throws Exception;
	GroupToResMap update(GroupToResMap instance,String operator) throws Exception;
	void deleteByID(Long primaryKey, String operator) throws Exception;
	void deleteAll(String operator) throws Exception;
	void batchDelete(String[] deleteIds, String operator) throws Exception;
	List<GroupToResMap> getList(Integer pageNo, Integer pageSize, String orderBy, Integer softDelete,String systemId,Long groupId,String resource) throws Exception;
	List<GroupToResMap> findAll() throws Exception;
	Long getAmount(Integer softDelete,String systemId,Long groupId,String resource) throws Exception;
	List<GroupToResMap> getListByGroupId(String groupId) throws Exception;
	List<GroupToResMap> getExistMapByControllerBeanMethods(String systemId,Long groupId, List<MethodInfo> methods) throws Exception;
	void updateResMap(Set<String> insertSet, Set<String> deleteSet, String systemId, Long groupId,String operator) throws Exception;
}
