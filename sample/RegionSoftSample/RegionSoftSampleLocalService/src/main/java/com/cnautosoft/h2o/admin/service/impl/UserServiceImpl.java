package com.cnautosoft.h2o.admin.service.impl;

import java.util.Date;
import java.util.List;

import com.cnautosoft.h2o.admin.dao.UserDao;
import com.cnautosoft.h2o.admin.entity.User;
import com.cnautosoft.h2o.admin.service.UserService;
import com.cnautosoft.h2o.annotation.Service;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.data.persistence.Transactional;

@Service
public class UserServiceImpl implements UserService{
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public User create(User entity,String operator) throws Exception {
		Date now = new Date();
		entity.setUpdateDt(now);
		entity.setCreateDt(now);
		entity.setUpdateBy(operator);
		entity.setCreateBy(operator);
		return userDao.insert(entity);
	}
	
	@Override
	public User getByID(Long primaryKey) throws Exception {
		if(primaryKey==null)return null;
		User entity = userDao.find(primaryKey);
		if(entity==null||entity.getSoftDelete()!=0)return null;
		else return entity;
	}
	
	@Override
	public User update(User entity,String operator) throws Exception{
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		return userDao.update(entity,"account","pwd","status","description","updateDt","updateBy");
	}
	
	@Override
	public void deleteByID(Long primaryKey,String operator)  throws Exception{
		if(primaryKey==null)throw new Exception("primaryKey is null");
		User entity = userDao.find(primaryKey);
		entity.setSoftDelete(1);
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		userDao.update(entity, "softDelete","updateDt","updateBy");
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void deleteAll(String operator) throws Exception{
		List<User> ls = findAll();
		for(User tmp:ls){
			deleteByID(tmp.getId(),operator);
		}
	}
	
	@Override
	public List<User> findAll() throws Exception{
		 return (List<User>) userDao.findAll();
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void batchDelete(String[] deleteIds,String operator) throws Exception{
		for(String tempId:deleteIds){
			deleteByID(Long.parseLong(tempId),operator);
		}
	}
	
	@Override
	public List<User> getList(Integer pageNo ,Integer pageSize ,String orderBy,Integer softDelete
				,String account
				,String pwd
				,Integer status
				,String description
		) throws Exception{
		return userDao.getList(pageNo,pageSize,orderBy,softDelete,account,pwd,status,description);
	}
	
	@Override
	public Long getAmount(Integer softDelete
				,String account
				,String pwd
				,Integer status
				,String description
			) throws Exception{		
		return userDao.getAmount(softDelete,account,pwd,status,description);
	}

	@Override
	public User getUserByAccount(String account) throws Exception {
		return userDao.getUserByAccount(account);
	}

	@Override
	public User unlockUser(User user, String operator) throws Exception {
		user.setUpdateDt(new Date());
		user.setUpdateBy(operator);
		user.setStatus(0);
		return userDao.update(user,"status","updateDt","updateBy");
	}

	@Override
	public User lockUser(User user, String operator) throws Exception {
		user.setUpdateDt(new Date());
		user.setUpdateBy(operator);
		user.setStatus(1);
		return userDao.update(user,"status","updateDt","updateBy");
	}

	@Override
	public Long getAmountNotInGroup(int softDelete, String account, String groupId) throws Exception {
		return userDao.getAmountNotInGroup(softDelete,account,groupId);
	}

	@Override
	public List<User> getListNotInGroup(Integer pageNo, Integer pageSize, String orderBy, int softDelete,
			String account, String groupId) throws Exception {
		return userDao.getListNotInGroup(pageNo,pageSize,orderBy,softDelete,account,groupId);
	}
}
