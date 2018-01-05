package com.cnautosoft.h2o.admin.service.impl;

import java.util.Date;
import java.util.List;

import com.cnautosoft.h2o.admin.dao.MethodInfoDao;
import com.cnautosoft.h2o.admin.entity.MethodInfo;
import com.cnautosoft.h2o.admin.service.MethodInfoService;
import com.cnautosoft.h2o.annotation.Service;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.data.persistence.Transactional;

@Service
public class MethodInfoServiceImpl implements MethodInfoService{
	private static final Logger logger = Logger.getLogger(MethodInfoServiceImpl.class);
	
	@Autowired
	private MethodInfoDao methodInfoDao;
	
	@Override
	public MethodInfo create(MethodInfo entity,String operator) throws Exception {
		Date now = new Date();
		entity.setUpdateDt(now);
		entity.setCreateDt(now);
		entity.setUpdateBy(operator);
		entity.setCreateBy(operator);
		return methodInfoDao.insert(entity);
	}
	
	@Override
	public MethodInfo getByID(Long primaryKey) throws Exception {
		if(primaryKey==null)return null;
		MethodInfo entity = methodInfoDao.find(primaryKey);
		if(entity==null||entity.getSoftDelete()!=0)return null;
		else return entity;
	}
	
	@Override
	public MethodInfo update(MethodInfo entity,String operator) throws Exception{
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		return methodInfoDao.update(entity,"detailId","name","url","inputType","returnType","updateDt","updateBy");
	}
	
	@Override
	public void deleteByID(Long primaryKey,String operator)  throws Exception{
		if(primaryKey==null)throw new Exception("primaryKey is null");
		MethodInfo entity = methodInfoDao.find(primaryKey);
		entity.setSoftDelete(1);
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		methodInfoDao.update(entity, "softDelete","updateDt","updateBy");
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void deleteAll(String operator) throws Exception{
		List<MethodInfo> ls = findAll();
		for(MethodInfo tmp:ls){
			deleteByID(tmp.getId(),operator);
		}
	}
	
	@Override
	public List<MethodInfo> findAll() throws Exception{
		 return (List<MethodInfo>) methodInfoDao.findAll();
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void batchDelete(String[] deleteIds,String operator) throws Exception{
		for(String tempId:deleteIds){
			deleteByID(Long.parseLong(tempId),operator);
		}
	}
	
	@Override
	public List<MethodInfo> getList(Integer pageNo ,Integer pageSize ,String orderBy,Integer softDelete
				,Long detailId
				,String name
				,String url
				,String inputType
				,String returnType
		) throws Exception{
		return methodInfoDao.getList(pageNo,pageSize,orderBy,softDelete,detailId,name,url,inputType,returnType);
	}
	
	@Override
	public Long getAmount(Integer softDelete
				,Long detailId
				,String name
				,String url
				,String inputType
				,String returnType
			) throws Exception{		
		return methodInfoDao.getAmount(softDelete,detailId,name,url,inputType,returnType);
	}

	@Override
	public List<MethodInfo> getMethodLsByDetailId(Long detailId) throws Exception {
		return methodInfoDao.getMethodLsByDetailId(detailId);
	}
}
