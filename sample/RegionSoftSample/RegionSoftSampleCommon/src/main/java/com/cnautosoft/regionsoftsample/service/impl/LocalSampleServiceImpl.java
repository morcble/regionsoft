package com.cnautosoft.regionsoftsample.service.impl;

import java.util.Date;
import java.util.List;
import com.cnautosoft.h2o.annotation.Service;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.data.persistence.Transactional;
import com.cnautosoft.regionsoftsample.dao.LocalSampleDao;
import com.cnautosoft.regionsoftsample.entity.LocalSample;
import com.cnautosoft.regionsoftsample.service.LocalSampleService;

@Service
public class LocalSampleServiceImpl implements LocalSampleService{
	private static final Logger logger = Logger.getLogger(LocalSampleServiceImpl.class);
	
	@Autowired
	private LocalSampleDao localSampleDao;
	
	@Override
	public LocalSample create(LocalSample entity,String operator) throws Exception {
		Date now = new Date();
		entity.setUpdateDt(now);
		entity.setCreateDt(now);
		entity.setUpdateBy(operator);
		entity.setCreateBy(operator);
		return localSampleDao.insert(entity);
	}
	
	@Override
	public LocalSample getByID(Long primaryKey) throws Exception {
		if(primaryKey==null)return null;
		LocalSample entity = localSampleDao.find(primaryKey);
		if(entity==null||entity.getSoftDelete()!=0)return null;
		else return entity;
	}
	
	@Override
	public LocalSample update(LocalSample entity,String operator) throws Exception{
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		return localSampleDao.update(entity,"name","updateDt","updateBy");
	}
	
	@Override
	public void deleteByID(Long primaryKey,String operator)  throws Exception{
		if(primaryKey==null)throw new Exception("primaryKey is null");
		LocalSample entity = localSampleDao.find(primaryKey);
		entity.setSoftDelete(1);
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		localSampleDao.update(entity, "softDelete","updateDt","updateBy");
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void deleteAll(String operator) throws Exception{
		List<LocalSample> ls = findAll();
		for(LocalSample tmp:ls){
			deleteByID(tmp.getId(),operator);
		}
	}
	
	@Override
	public List<LocalSample> findAll() throws Exception{
		 return (List<LocalSample>) localSampleDao.findAll();
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void batchDelete(String[] deleteIds,String operator) throws Exception{
		for(String tempId:deleteIds){
			deleteByID(Long.parseLong(tempId),operator);
		}
	}
	
	@Override
	public List<LocalSample> getList(Integer pageNo ,Integer pageSize ,String orderBy,Integer softDelete
				,String name
		) throws Exception{
		return localSampleDao.getList(pageNo,pageSize,orderBy,softDelete,name);
	}
	
	@Override
	public Long getAmount(Integer softDelete
				,String name
			) throws Exception{		
		return localSampleDao.getAmount(softDelete,name);
	}
}
