package com.cnautosoft.h2o.admin.dao;

import com.cnautosoft.h2o.admin.entity.ErrorLog;
import com.cnautosoft.h2o.annotation.Dao;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CountResult;
import com.cnautosoft.h2o.core.H2ODao;
import com.cnautosoft.h2o.core.CommonUtil;
import java.util.List;
import java.util.Date;

@Dao
public class ErrorLogDao extends H2ODao<ErrorLog,Long>{
	private static final Logger logger = Logger.getLogger(ErrorLogDao.class);
	
	public List<ErrorLog> getList(Integer pageNo ,Integer pageSize, String orderBy, Integer softDelete
			,String systemId,String requestId,String errorType,String detail
					) throws Exception{
		String tableName = getBindTableName();
		
		StringBuilder sqlBuf = new StringBuilder("select * from ");
		sqlBuf.append(tableName);
		sqlBuf.append(" where 1=1 ");
		
		int paraCount = 0;
		if(softDelete!=null){
			sqlBuf.append(" and softDelete = ?");
			paraCount++;
		}
		
		if(!CommonUtil.isEmpty(systemId)){
			sqlBuf.append(" and systemId = ?");
		  paraCount++;
		}
		else{
			systemId = null;
		}
		if(!CommonUtil.isEmpty(requestId)){
			sqlBuf.append(" and requestId = ?");
		  paraCount++;
		}
		else{
			requestId = null;
		}
		if(!CommonUtil.isEmpty(errorType)){
			sqlBuf.append(" and errorType = ?");
		  paraCount++;
		}
		else{
			errorType = null;
		}
		if(!CommonUtil.isEmpty(detail)){
			sqlBuf.append(" and detail like ?");
		  paraCount++;
		}
		else{
			detail = null;
		}
		


		if(CommonUtil.isEmpty(orderBy)){
			sqlBuf.append(" order by id desc ");
		}
		else{
			orderBy = orderBy.replace("'", "").replace("%", "");
			sqlBuf.append(" order by "+orderBy);
		}
	
		Object[] sqlParas =  new Object[paraCount];
		paraCount = 0;
		if(softDelete!=null){
			sqlParas[paraCount] = softDelete;
			paraCount++;
		}
		
		if(!CommonUtil.isEmpty(systemId)){
		  	sqlParas[paraCount] = systemId;	
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(requestId)){
		  	sqlParas[paraCount] = requestId;	
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(errorType)){
		  	sqlParas[paraCount] = errorType;	
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(detail)){
		  	sqlParas[paraCount] = "%"+detail+"%";
		  paraCount++;
		}
		
		List<ErrorLog> result = getEntityList(sqlBuf.toString(),sqlParas,pageNo, pageSize);
		return result;
	}
	
	
	public Long getAmount(Integer softDelete ,String systemId,String requestId,String errorType,String detail) throws Exception{		
		String tableName = getBindTableName();
		
		StringBuilder sqlBuf = new StringBuilder("select count(1) as count from ");
		
		sqlBuf.append(tableName);
		sqlBuf.append(" where 1=1 ");
		
		int paraCount = 0;
		if(softDelete!=null){
			sqlBuf.append(" and softDelete = ?");
			paraCount++;
		}
		
		if(!CommonUtil.isEmpty(systemId)){
			sqlBuf.append(" and systemId = ?");
		  paraCount++;
		}
		else{
			systemId = null;
		}
		if(!CommonUtil.isEmpty(requestId)){
			sqlBuf.append(" and requestId = ?");
		  paraCount++;
		}
		else{
			requestId = null;
		}
		if(!CommonUtil.isEmpty(errorType)){
			sqlBuf.append(" and errorType = ?");
		  paraCount++;
		}
		else{
			errorType = null;
		}
		if(!CommonUtil.isEmpty(detail)){
			sqlBuf.append(" and detail like ?");
		  paraCount++;
		}
		else{
			detail = null;
		}
	
		Object[] sqlParas =  new Object[paraCount];
		paraCount = 0;
		if(softDelete!=null){
			sqlParas[paraCount] = softDelete;
			paraCount++;
		}
		
		if(!CommonUtil.isEmpty(systemId)){
		  	sqlParas[paraCount] = systemId;
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(requestId)){
		  	sqlParas[paraCount] = requestId;
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(errorType)){
		  	sqlParas[paraCount] = errorType;
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(detail)){
		  	sqlParas[paraCount] = "%"+detail+"%";
		  paraCount++;
		}
		
		CountResult countResult = getEntityManager().getObject(sqlBuf.toString(), sqlParas, CountResult.class);
		return countResult.getCount();
	}
}
