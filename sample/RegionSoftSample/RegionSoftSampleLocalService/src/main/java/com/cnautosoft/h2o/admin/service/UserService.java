package com.cnautosoft.h2o.admin.service;

import java.util.List;

import com.cnautosoft.h2o.admin.entity.User;

import java.util.Date;

public interface UserService {
	User create(User instance,String operator) throws Exception;
	User getByID(Long primaryKey) throws Exception;
	User update(User instance,String operator) throws Exception;
	void deleteByID(Long primaryKey, String operator) throws Exception;
	void deleteAll(String operator) throws Exception;
	void batchDelete(String[] deleteIds, String operator) throws Exception;
	List<User> getList(Integer pageNo, Integer pageSize, String orderBy, Integer softDelete,String account,String pwd,Integer status,String description) throws Exception;
	List<User> findAll() throws Exception;
	Long getAmount(Integer softDelete,String account,String pwd,Integer status,String description) throws Exception;
	User getUserByAccount(String account) throws Exception;
	User unlockUser(User user, String operator) throws Exception;
	User lockUser(User user, String operator) throws Exception;
	Long getAmountNotInGroup(int softDelete, String account, String groupId) throws Exception;
	List<User> getListNotInGroup(Integer pageNo, Integer pageSize, String orderBy, int softDelete,String account, String groupId) throws Exception;
}
