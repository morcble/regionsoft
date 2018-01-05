package com.cnautosoft.h2o.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cnautosoft.h2o.admin.dao.GroupToUserMapDao;
import com.cnautosoft.h2o.admin.entity.GroupToUserMap;
import com.cnautosoft.h2o.admin.service.GroupToUserMapService;
import com.cnautosoft.h2o.annotation.Service;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.ids.IDGenerator;
import com.cnautosoft.h2o.data.persistence.Transactional;

@Service
public class GroupToUserMapServiceImpl implements GroupToUserMapService{
	private static final Logger logger = Logger.getLogger(GroupToUserMapServiceImpl.class);
	
	@Autowired
	private GroupToUserMapDao groupToUserMapDao;
	
	@Override
	public GroupToUserMap create(GroupToUserMap entity,String operator) throws Exception {
		Date now = new Date();
		entity.setUpdateDt(now);
		entity.setCreateDt(now);
		entity.setUpdateBy(operator);
		entity.setCreateBy(operator);
		return groupToUserMapDao.insert(entity);
	}
	
	@Override
	public GroupToUserMap getByID(Long primaryKey) throws Exception {
		if(primaryKey==null)return null;
		GroupToUserMap entity = groupToUserMapDao.find(primaryKey);
		if(entity==null||entity.getSoftDelete()!=0)return null;
		else return entity;
	}
	
	@Override
	public GroupToUserMap update(GroupToUserMap entity,String operator) throws Exception{
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		return groupToUserMapDao.update(entity,"systemId","userId","groupId","updateDt","updateBy");
	}
	
	@Override
	public void deleteByID(Long primaryKey,String operator)  throws Exception{
		if(primaryKey==null)throw new Exception("primaryKey is null");
		groupToUserMapDao.delete(primaryKey);
		/*GroupToUserMap entity = groupToUserMapDao.find(primaryKey);
		entity.setSoftDelete(1);
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		groupToUserMapDao.update(entity, "softDelete","updateDt","updateBy");*/
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void deleteAll(String operator) throws Exception{
		List<GroupToUserMap> ls = findAll();
		for(GroupToUserMap tmp:ls){
			deleteByID(tmp.getId(),operator);
		}
	}
	
	@Override
	public List<GroupToUserMap> findAll() throws Exception{
		 return (List<GroupToUserMap>) groupToUserMapDao.findAll();
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void batchDelete(String[] deleteIds,String operator) throws Exception{
		for(String tempId:deleteIds){
			deleteByID(Long.parseLong(tempId),operator);
		}
	}
	
	@Override
	public List<GroupToUserMap> getList(Integer pageNo ,Integer pageSize ,String orderBy,Integer softDelete
				,String systemId
				,Long userId
				,Long groupId
		) throws Exception{
		return groupToUserMapDao.getList(pageNo,pageSize,orderBy,softDelete,systemId,userId,groupId);
	}
	
	@Override
	public Long getAmount(Integer softDelete
				,String systemId
				,Long userId
				,Long groupId
			) throws Exception{		
		return groupToUserMapDao.getAmount(softDelete,systemId,userId,groupId);
	}

	@Override
	@Transactional(rollBackFor=Exception.class)
	public List<Long> addUsersToGroup(String[] chosenIds,String systemId,String groupId, String operator) throws Exception {
		List<Long> result = new ArrayList<Long>();
		for(String userId:chosenIds){
			GroupToUserMap entity = new GroupToUserMap();
			entity.setSystemId(systemId);
			entity.setId(IDGenerator.getLongID());
			result.add(entity.getId());
			entity.setUserId(Long.valueOf(userId));
			entity.setGroupId(Long.valueOf(groupId));
			create(entity,operator);
		}
		return result;
	}
	
	/**
	 * get all groups owned by account
	 * @param account
	 * @throws Exception 
	 */
	@Override
	public List<GroupToUserMap> getGroupsByAccount(String account) throws Exception{
		return groupToUserMapDao.getGroupsByAccount(account);
	}
	
	@Override
	public List<String> getAccountLsByUserMapIds(List<Long> mapIds) throws Exception{
		return groupToUserMapDao.getGroupsByAccount(mapIds);
	}
}
