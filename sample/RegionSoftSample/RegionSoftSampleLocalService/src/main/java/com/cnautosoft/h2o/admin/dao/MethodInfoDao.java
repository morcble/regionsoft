package com.cnautosoft.h2o.admin.dao;

import com.cnautosoft.h2o.admin.entity.MethodInfo;
import com.cnautosoft.h2o.annotation.Dao;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CountResult;
import com.cnautosoft.h2o.core.H2ODao;
import com.cnautosoft.h2o.core.CommonUtil;
import java.util.List;
import java.util.Date;

@Dao
public class MethodInfoDao extends H2ODao<MethodInfo,Long>{
	private static final Logger logger = Logger.getLogger(MethodInfoDao.class);
	
	public List<MethodInfo> getList(Integer pageNo ,Integer pageSize, String orderBy, Integer softDelete
			,Long detailId,String name,String url,String inputType,String returnType
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
		
		if(!CommonUtil.isEmpty(detailId)){
			sqlBuf.append(" and detailId = ?");
		  paraCount++;
		}
		else{
			detailId = null;
		}
		if(!CommonUtil.isEmpty(name)){
			sqlBuf.append(" and name like ?");
		  paraCount++;
		}
		else{
			name = null;
		}
		if(!CommonUtil.isEmpty(url)){
			sqlBuf.append(" and url = ?");
		  paraCount++;
		}
		else{
			url = null;
		}
		if(!CommonUtil.isEmpty(inputType)){
			sqlBuf.append(" and inputType = ?");
		  paraCount++;
		}
		else{
			inputType = null;
		}
		if(!CommonUtil.isEmpty(returnType)){
			sqlBuf.append(" and returnType = ?");
		  paraCount++;
		}
		else{
			returnType = null;
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
		
		if(!CommonUtil.isEmpty(detailId)){
		  	sqlParas[paraCount] = detailId;	
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(name)){
		  	sqlParas[paraCount] = "%"+name+"%";
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(url)){
		  	sqlParas[paraCount] = url;	
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(inputType)){
		  	sqlParas[paraCount] = inputType;	
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(returnType)){
		  	sqlParas[paraCount] = returnType;	
		  paraCount++;
		}
		
		List<MethodInfo> result = getEntityList(sqlBuf.toString(),sqlParas,pageNo, pageSize);
		return result;
	}
	
	
	public Long getAmount(Integer softDelete ,Long detailId,String name,String url,String inputType,String returnType) throws Exception{		
		String tableName = getBindTableName();
		
		StringBuilder sqlBuf = new StringBuilder("select count(1) as count from ");
		
		sqlBuf.append(tableName);
		sqlBuf.append(" where 1=1 ");
		
		int paraCount = 0;
		if(softDelete!=null){
			sqlBuf.append(" and softDelete = ?");
			paraCount++;
		}
		
		if(!CommonUtil.isEmpty(detailId)){
			sqlBuf.append(" and detailId = ?");
		  paraCount++;
		}
		else{
			detailId = null;
		}
		if(!CommonUtil.isEmpty(name)){
			sqlBuf.append(" and name like ?");
		  paraCount++;
		}
		else{
			name = null;
		}
		if(!CommonUtil.isEmpty(url)){
			sqlBuf.append(" and url = ?");
		  paraCount++;
		}
		else{
			url = null;
		}
		if(!CommonUtil.isEmpty(inputType)){
			sqlBuf.append(" and inputType = ?");
		  paraCount++;
		}
		else{
			inputType = null;
		}
		if(!CommonUtil.isEmpty(returnType)){
			sqlBuf.append(" and returnType = ?");
		  paraCount++;
		}
		else{
			returnType = null;
		}
	
		Object[] sqlParas =  new Object[paraCount];
		paraCount = 0;
		if(softDelete!=null){
			sqlParas[paraCount] = softDelete;
			paraCount++;
		}
		
		if(!CommonUtil.isEmpty(detailId)){
		  	sqlParas[paraCount] = detailId;
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(name)){
		  	sqlParas[paraCount] = "%"+name+"%";
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(url)){
		  	sqlParas[paraCount] = url;
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(inputType)){
		  	sqlParas[paraCount] = inputType;
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(returnType)){
		  	sqlParas[paraCount] = returnType;
		  paraCount++;
		}
		
		CountResult countResult = getEntityManager().getObject(sqlBuf.toString(), sqlParas, CountResult.class);
		return countResult.getCount();
	}


	public List<MethodInfo> getMethodLsByDetailId(Long detailId) throws Exception {
		String tableName = getBindTableName();
		StringBuilder sqlBuf = new StringBuilder("select * from ");
		sqlBuf.append(tableName);
		sqlBuf.append(" where url is not null and detailId = ? order by id asc ");
		List<MethodInfo> methodInfoLs = getEntityList(sqlBuf.toString(),new Object[]{detailId});
		return methodInfoLs;
	}
}
