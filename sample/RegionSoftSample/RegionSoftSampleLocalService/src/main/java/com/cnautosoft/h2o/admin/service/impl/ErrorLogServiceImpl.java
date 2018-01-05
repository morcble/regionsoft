package com.cnautosoft.h2o.admin.service.impl;

import java.util.Date;
import java.util.List;

import com.cnautosoft.h2o.admin.dao.ErrorLogDao;
import com.cnautosoft.h2o.admin.entity.ErrorLog;
import com.cnautosoft.h2o.admin.service.ErrorLogService;
import com.cnautosoft.h2o.annotation.Service;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.data.persistence.Transactional;

@Service
public class ErrorLogServiceImpl implements ErrorLogService{
	private static final Logger logger = Logger.getLogger(ErrorLogServiceImpl.class);
	
	@Autowired
	private ErrorLogDao errorLogDao;
	
	@Override
	public ErrorLog create(ErrorLog entity,String operator) throws Exception {
		Date now = new Date();
		entity.setUpdateDt(now);
		entity.setCreateDt(now);
		entity.setUpdateBy(operator);
		entity.setCreateBy(operator);
		return errorLogDao.insert(entity);
	}
	
	@Override
	public ErrorLog getByID(Long primaryKey) throws Exception {
		if(primaryKey==null)return null;
		ErrorLog entity = errorLogDao.find(primaryKey);
		if(entity==null||entity.getSoftDelete()!=0)return null;
		else return entity;
	}
	
	@Override
	public ErrorLog update(ErrorLog entity,String operator) throws Exception{
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		return errorLogDao.update(entity,"systemId","requestId","errorType","detail","updateDt","updateBy");
	}
	
	@Override
	public void deleteByID(Long primaryKey,String operator)  throws Exception{
		if(primaryKey==null)throw new Exception("primaryKey is null");
		ErrorLog entity = errorLogDao.find(primaryKey);
		entity.setSoftDelete(1);
		entity.setUpdateDt(new Date());
		entity.setUpdateBy(operator);
		errorLogDao.update(entity, "softDelete","updateDt","updateBy");
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void deleteAll(String operator) throws Exception{
		List<ErrorLog> ls = findAll();
		for(ErrorLog tmp:ls){
			deleteByID(tmp.getId(),operator);
		}
	}
	
	@Override
	public List<ErrorLog> findAll() throws Exception{
		 return (List<ErrorLog>) errorLogDao.findAll();
	}
	
	@Override
	@Transactional(rollBackFor=Exception.class)
	public void batchDelete(String[] deleteIds,String operator) throws Exception{
		for(String tempId:deleteIds){
			deleteByID(Long.parseLong(tempId),operator);
		}
	}
	
	@Override
	public List<ErrorLog> getList(Integer pageNo ,Integer pageSize ,String orderBy,Integer softDelete
				,String systemId
				,String requestId
				,String errorType
				,String detail
		) throws Exception{
		return errorLogDao.getList(pageNo,pageSize,orderBy,softDelete,systemId,requestId,errorType,detail);
	}
	
	@Override
	public Long getAmount(Integer softDelete
				,String systemId
				,String requestId
				,String errorType
				,String detail
			) throws Exception{		
		return errorLogDao.getAmount(softDelete,systemId,requestId,errorType,detail);
	}
}
