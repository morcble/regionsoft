package com.cnautosoft.h2o.admin.service.impl;

import java.util.Date;
import java.util.List;

import com.cnautosoft.h2o.admin.dao.GroupDao;
import com.cnautosoft.h2o.admin.dao.GroupToResMapDao;
import com.cnautosoft.h2o.admin.dao.GroupToUserMapDao;
import com.cnautosoft.h2o.admin.entity.Group;
import com.cnautosoft.h2o.admin.service.GroupService;
import com.cnautosoft.h2o.annotation.Service;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.exception.BizException;
import com.cnautosoft.h2o.data.persistence.Transactional;

@Service
public class GroupServiceImpl implements GroupService{
	private static final Logger logger = Logger.getLogger(GroupServiceImpl.class);
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private GroupToResMapDao groupToResMapDao;
	
	@Autowired
	private GroupToUserMapDao groupToUserMapDao;
	
	@Override
	public Group create(Group entity,String operator) throws Exception {
		Date now = new Date();
		
		entity.setDepth(0);
		if(entity.getParentGroupId()!=null){
			Group parentGroup = getByID(entity.getParentGroupId());
			if(parentGroup!=null){
				entity.setDepth(parentGroup.getDepth()+1);
			}
		}
		
		entity.setUpdateDt(now);
		entity.setCreateDt(now);
		entity.setUpdateBy(operator);
		entity.setCreateBy(operator);
		return groupDao.insert(entity);
	}
	
	@Override
	public Group getByID(Long primaryKey) throws Exception {
		if(primaryKey==null)return null;
		Group entity = groupDao.find(primaryKey);
		if(entity==null||entity.getSoftDelete()!=0)return null;
		else return entity;
	}
	
	@Override
	public Group update(Group entity,String operator) throws Exception{
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		return groupDao.update(entity,"systemId","groupNm","groupDesc","parentGroupId","updateDt","updateBy");
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void deleteByID(Long primaryKey,String operator)  throws Exception{
		if(primaryKey==null)throw new Exception("primaryKey is null");
		Group entity = groupDao.find(primaryKey);
		if(entity==null){
			throw new Exception("Group not found");
		}
		Integer result = groupDao.countChildByParentId(entity.getId());
		if(result!=0){
			throw new BizException("Please delete child group first before delete this group");
		}
		
		groupToResMapDao.deleteByGroupId(primaryKey);
		groupToUserMapDao.deleteByGroupId(primaryKey);
		
		entity.setSoftDelete(1);
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		groupDao.update(entity, "softDelete","updateDt","updateBy");
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void deleteAll(String operator) throws Exception{
		List<Group> ls = findAll();
		for(Group tmp:ls){
			deleteByID(tmp.getId(),operator);
		}
	}
	
	@Override
	public List<Group> findAll() throws Exception{
		 return (List<Group>) groupDao.findAll();
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void batchDelete(String[] deleteIds,String operator) throws Exception{
		for(String tempId:deleteIds){
			deleteByID(Long.parseLong(tempId),operator);
		}
	}
	
	@Override
	public List<Group> getList(Integer pageNo ,Integer pageSize ,String orderBy,Integer softDelete
				,String systemId
				,String groupNm
				,String groupDesc
				,Long parentGroupId
				,Integer depth
		) throws Exception{
		return groupDao.getList(pageNo,pageSize,orderBy,softDelete,systemId,groupNm,groupDesc,parentGroupId,depth);
	}
	
	@Override
	public Long getAmount(Integer softDelete
				,String systemId
				,String groupNm
				,String groupDesc
				,Long parentGroupId
				,Integer depth
			) throws Exception{		
		return groupDao.getAmount(softDelete,systemId,groupNm,groupDesc,parentGroupId,depth);
	}

	@Override
	public List<Group> getGroupsByIds(String[] groupIds) throws Exception {
		return groupDao.getGroupsByIds(groupIds);
	}
}
