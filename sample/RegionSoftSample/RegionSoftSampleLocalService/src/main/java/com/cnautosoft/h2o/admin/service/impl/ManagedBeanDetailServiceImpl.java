package com.cnautosoft.h2o.admin.service.impl;

import java.util.Date;
import java.util.List;

import com.cnautosoft.h2o.admin.dao.ManagedBeanDetailDao;
import com.cnautosoft.h2o.admin.entity.ManagedBeanDetail;
import com.cnautosoft.h2o.admin.service.ManagedBeanDetailService;
import com.cnautosoft.h2o.annotation.Service;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.data.persistence.Transactional;

@Service
public class ManagedBeanDetailServiceImpl implements ManagedBeanDetailService{
	private static final Logger logger = Logger.getLogger(ManagedBeanDetailServiceImpl.class);
	
	@Autowired
	private ManagedBeanDetailDao managedBeanDetailDao;
	
	@Override
	public ManagedBeanDetail create(ManagedBeanDetail entity,String operator) throws Exception {
		Date now = new Date();
		entity.setUpdateDt(now);
		entity.setCreateDt(now);
		entity.setUpdateBy(operator);
		entity.setCreateBy(operator);
		return managedBeanDetailDao.insert(entity);
	}
	
	@Override
	public ManagedBeanDetail getByID(Long primaryKey) throws Exception {
		if(primaryKey==null)return null;
		ManagedBeanDetail entity = managedBeanDetailDao.find(primaryKey);
		if(entity==null||entity.getSoftDelete()!=0)return null;
		else return entity;
	}
	
	@Override
	public ManagedBeanDetail update(ManagedBeanDetail entity,String operator) throws Exception{
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		return managedBeanDetailDao.update(entity,"systemId","contextName","name","packageName","beanType","svcType","updateDt","updateBy");
	}
	
	@Override
	public void deleteByID(Long primaryKey,String operator)  throws Exception{
		if(primaryKey==null)throw new Exception("primaryKey is null");
		ManagedBeanDetail entity = managedBeanDetailDao.find(primaryKey);
		entity.setSoftDelete(1);
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		managedBeanDetailDao.update(entity, "softDelete","updateDt","updateBy");
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void deleteAll(String operator) throws Exception{
		List<ManagedBeanDetail> ls = findAll();
		for(ManagedBeanDetail tmp:ls){
			deleteByID(tmp.getId(),operator);
		}
	}
	
	@Override
	public List<ManagedBeanDetail> findAll() throws Exception{
		 return (List<ManagedBeanDetail>) managedBeanDetailDao.findAll();
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void batchDelete(String[] deleteIds,String operator) throws Exception{
		for(String tempId:deleteIds){
			deleteByID(Long.parseLong(tempId),operator);
		}
	}
	
	@Override
	public List<ManagedBeanDetail> getList(Integer pageNo ,Integer pageSize ,String orderBy,Integer softDelete
				,String systemId
				,String contextName
				,String name
				,String packageName
				,String beanType
				,String svcType
		) throws Exception{
		return managedBeanDetailDao.getList(pageNo,pageSize,orderBy,softDelete,systemId,contextName,name,packageName,beanType,svcType);
	}
	
	@Override
	public Long getAmount(Integer softDelete
				,String systemId
				,String contextName
				,String name
				,String packageName
				,String beanType
				,String svcType
			) throws Exception{		
		return managedBeanDetailDao.getAmount(softDelete,systemId,contextName,name,packageName,beanType,svcType);
	}
}
