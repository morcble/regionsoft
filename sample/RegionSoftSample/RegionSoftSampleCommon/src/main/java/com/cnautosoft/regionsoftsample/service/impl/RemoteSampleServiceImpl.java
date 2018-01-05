package com.cnautosoft.regionsoftsample.service.impl;

import java.util.Date;
import java.util.List;
import com.cnautosoft.h2o.annotation.Service;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.data.persistence.Transactional;
import com.cnautosoft.regionsoftsample.dao.RemoteSampleDao;
import com.cnautosoft.regionsoftsample.entity.RemoteSample;
import com.cnautosoft.regionsoftsample.service.RemoteSampleService;

@Service
public class RemoteSampleServiceImpl implements RemoteSampleService{
	private static final Logger logger = Logger.getLogger(RemoteSampleServiceImpl.class);
	
	@Autowired
	private RemoteSampleDao remoteSampleDao;
	
	@Override
	public RemoteSample create(RemoteSample entity,String operator) throws Exception {
		Date now = new Date();
		entity.setUpdateDt(now);
		entity.setCreateDt(now);
		entity.setUpdateBy(operator);
		entity.setCreateBy(operator);
		return remoteSampleDao.insert(entity);
	}
	
	@Override
	public RemoteSample getByID(Long primaryKey) throws Exception {
		if(primaryKey==null)return null;
		RemoteSample entity = remoteSampleDao.find(primaryKey);
		if(entity==null||entity.getSoftDelete()!=0)return null;
		else return entity;
	}
	
	@Override
	public RemoteSample update(RemoteSample entity,String operator) throws Exception{
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		return remoteSampleDao.update(entity,"remoteName","updateDt","updateBy");
	}
	
	@Override
	public void deleteByID(Long primaryKey,String operator)  throws Exception{
		if(primaryKey==null)throw new Exception("primaryKey is null");
		RemoteSample entity = remoteSampleDao.find(primaryKey);
		entity.setSoftDelete(1);
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		remoteSampleDao.update(entity, "softDelete","updateDt","updateBy");
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void deleteAll(String operator) throws Exception{
		List<RemoteSample> ls = findAll();
		for(RemoteSample tmp:ls){
			deleteByID(tmp.getId(),operator);
		}
	}
	
	@Override
	public List<RemoteSample> findAll() throws Exception{
		 return (List<RemoteSample>) remoteSampleDao.findAll();
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void batchDelete(String[] deleteIds,String operator) throws Exception{
		for(String tempId:deleteIds){
			deleteByID(Long.parseLong(tempId),operator);
		}
	}
	
	@Override
	public List<RemoteSample> getList(Integer pageNo ,Integer pageSize ,String orderBy,Integer softDelete
				,String remoteName
		) throws Exception{
		return remoteSampleDao.getList(pageNo,pageSize,orderBy,softDelete,remoteName);
	}
	
	@Override
	public Long getAmount(Integer softDelete
				,String remoteName
			) throws Exception{		
		return remoteSampleDao.getAmount(softDelete,remoteName);
	}
}
