package com.cnautosoft.h2o.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.cnautosoft.h2o.admin.dao.GroupToResMapDao;
import com.cnautosoft.h2o.admin.entity.GroupToResMap;
import com.cnautosoft.h2o.admin.entity.MethodInfo;
import com.cnautosoft.h2o.admin.service.GroupToResMapService;
import com.cnautosoft.h2o.annotation.Service;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.ids.IDGenerator;
import com.cnautosoft.h2o.data.persistence.Transactional;

@Service
public class GroupToResMapServiceImpl implements GroupToResMapService{
	private static final Logger logger = Logger.getLogger(GroupToResMapServiceImpl.class);
	
	@Autowired
	private GroupToResMapDao groupToResMapDao;
	
	@Override
	public GroupToResMap create(GroupToResMap entity,String operator) throws Exception {
		Date now = new Date();
		entity.setUpdateDt(now);
		entity.setCreateDt(now);
		entity.setUpdateBy(operator);
		entity.setCreateBy(operator);
		return groupToResMapDao.insert(entity);
	}
	
	@Override
	public GroupToResMap getByID(Long primaryKey) throws Exception {
		if(primaryKey==null)return null;
		GroupToResMap entity = groupToResMapDao.find(primaryKey);
		if(entity==null||entity.getSoftDelete()!=0)return null;
		else return entity;
	}
	
	@Override
	public GroupToResMap update(GroupToResMap entity,String operator) throws Exception{
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		return groupToResMapDao.update(entity,"systemId","groupId","resource","urlCode","updateDt","updateBy");
	}
	
	@Override
	public void deleteByID(Long primaryKey,String operator)  throws Exception{
		if(primaryKey==null)throw new Exception("primaryKey is null");
		groupToResMapDao.delete(primaryKey);
/*		ResToGroupMap entity = resToGroupMapDao.find(primaryKey);
		entity.setSoftDelete(1);
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		resToGroupMapDao.update(entity, "softDelete","updateDt","updateBy");*/
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void deleteAll(String operator) throws Exception{
		List<GroupToResMap> ls = findAll();
		for(GroupToResMap tmp:ls){
			deleteByID(tmp.getId(),operator);
		}
	}
	
	@Override
	public List<GroupToResMap> findAll() throws Exception{
		 return (List<GroupToResMap>) groupToResMapDao.findAll();
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void batchDelete(String[] deleteIds,String operator) throws Exception{
		for(String tempId:deleteIds){
			deleteByID(Long.parseLong(tempId),operator);
		}
	}
	
	@Override
	public List<GroupToResMap> getList(Integer pageNo ,Integer pageSize ,String orderBy,Integer softDelete
				,String systemId
				,Long groupId
				,String resource
		) throws Exception{
		return groupToResMapDao.getList(pageNo,pageSize,orderBy,softDelete,systemId,groupId,resource);
	}
	
	@Override
	public Long getAmount(Integer softDelete
				,String systemId
				,Long groupId
				,String resource
			) throws Exception{		
		return groupToResMapDao.getAmount(softDelete,systemId,groupId,resource);
	}
	
	@Override
	public List<GroupToResMap> getListByGroupId(String groupId) throws Exception {
		return groupToResMapDao.getListByGroupId(groupId);
	}

	@Override
	public List<GroupToResMap> getExistMapByControllerBeanMethods(String systemId,Long groupId,List<MethodInfo> methods) throws Exception {
		StringBuilder sb = new StringBuilder("(");
		String groupStr = String.valueOf(groupId);
		for(MethodInfo methodInfo:methods){
			if(methodInfo.getUrl()!=null){
				sb.append((methodInfo.getUrl()).hashCode());
				sb.append(",");
			}
		}
		String sqlPart = null;
		if(sb.length()>1){
			sb.deleteCharAt(sb.length()-1);
			sb.append(")");
			sqlPart = sb.toString();
		}
		else{
			return new ArrayList<GroupToResMap>();
		}
		
		return groupToResMapDao.getExistMapByControllerBeanMethods(systemId,groupId,sqlPart);
	}

	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void updateResMap(Set<String> insertSet, Set<String> deleteSet,String systemId, Long groupId,String operator) throws Exception {
		String groupStr = String.valueOf(groupId);
		StringBuilder deleteSetSb = new StringBuilder("(");
		for(String url:deleteSet){
			deleteSetSb.append(url.hashCode());
			deleteSetSb.append(",");
		}
		String deleteSqlPart = null;
		if(deleteSetSb.length()>1){
			deleteSetSb.deleteCharAt(deleteSetSb.length()-1);
			deleteSetSb.append(")");
			deleteSqlPart = deleteSetSb.toString();
		}
		if(deleteSqlPart!=null){
			groupToResMapDao.getDeleteExsitMap(systemId,groupId,deleteSqlPart);
		}
		
		for(String url:insertSet){
			GroupToResMap resToGroupMap = new GroupToResMap();
			resToGroupMap.setId(IDGenerator.getLongID());
			resToGroupMap.setSystemId(systemId);
			resToGroupMap.setGroupId(groupId);
			resToGroupMap.setResource(url);
			resToGroupMap.setUrlCode(url.hashCode());
			resToGroupMap.setResType("0");
			create(resToGroupMap,operator);
		}
		
		
	}
}
