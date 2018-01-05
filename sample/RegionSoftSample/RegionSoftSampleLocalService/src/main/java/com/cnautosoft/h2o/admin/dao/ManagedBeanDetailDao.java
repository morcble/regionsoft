package com.cnautosoft.h2o.admin.dao;

import com.cnautosoft.h2o.admin.entity.ManagedBeanDetail;
import com.cnautosoft.h2o.annotation.Dao;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CountResult;
import com.cnautosoft.h2o.core.H2ODao;
import com.cnautosoft.h2o.core.CommonUtil;
import java.util.List;
import java.util.Date;

@Dao
public class ManagedBeanDetailDao extends H2ODao<ManagedBeanDetail,Long>{
	private static final Logger logger = Logger.getLogger(ManagedBeanDetailDao.class);
	
	public List<ManagedBeanDetail> getList(Integer pageNo ,Integer pageSize, String orderBy, Integer softDelete
			,String systemId,String contextName,String name,String packageName,String beanType,String svcType
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
		if(!CommonUtil.isEmpty(contextName)){
			sqlBuf.append(" and contextName like ?");
		  paraCount++;
		}
		else{
			contextName = null;
		}
		if(!CommonUtil.isEmpty(name)){
			sqlBuf.append(" and name like ?");
		  paraCount++;
		}
		else{
			name = null;
		}
		if(!CommonUtil.isEmpty(packageName)){
			sqlBuf.append(" and packageName like ?");
		  paraCount++;
		}
		else{
			packageName = null;
		}
		if(!CommonUtil.isEmpty(beanType)){
			sqlBuf.append(" and beanType = ?");
		  paraCount++;
		}
		else{
			beanType = null;
		}
		if(!CommonUtil.isEmpty(svcType)){
			sqlBuf.append(" and svcType = ?");
		  paraCount++;
		}
		else{
			svcType = null;
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
		if(!CommonUtil.isEmpty(contextName)){
		  	sqlParas[paraCount] = "%"+contextName+"%";
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(name)){
		  	sqlParas[paraCount] = "%"+name+"%";
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(packageName)){
		  	sqlParas[paraCount] = "%"+packageName+"%";
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(beanType)){
		  	sqlParas[paraCount] = beanType;	
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(svcType)){
		  	sqlParas[paraCount] = svcType;	
		  paraCount++;
		}
		
		List<ManagedBeanDetail> result = getEntityList(sqlBuf.toString(),sqlParas,pageNo, pageSize);
		return result;
	}
	
	
	public Long getAmount(Integer softDelete ,String systemId,String contextName,String name,String packageName,String beanType,String svcType) throws Exception{		
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
		if(!CommonUtil.isEmpty(contextName)){
			sqlBuf.append(" and contextName like ?");
		  paraCount++;
		}
		else{
			contextName = null;
		}
		if(!CommonUtil.isEmpty(name)){
			sqlBuf.append(" and name like ?");
		  paraCount++;
		}
		else{
			name = null;
		}
		if(!CommonUtil.isEmpty(packageName)){
			sqlBuf.append(" and packageName like ?");
		  paraCount++;
		}
		else{
			packageName = null;
		}
		if(!CommonUtil.isEmpty(beanType)){
			sqlBuf.append(" and beanType = ?");
		  paraCount++;
		}
		else{
			beanType = null;
		}
		if(!CommonUtil.isEmpty(svcType)){
			sqlBuf.append(" and svcType = ?");
		  paraCount++;
		}
		else{
			svcType = null;
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
		if(!CommonUtil.isEmpty(contextName)){
		  	sqlParas[paraCount] = "%"+contextName+"%";
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(name)){
		  	sqlParas[paraCount] = "%"+name+"%";
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(packageName)){
		  	sqlParas[paraCount] = "%"+packageName+"%";
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(beanType)){
		  	sqlParas[paraCount] = beanType;
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(svcType)){
		  	sqlParas[paraCount] = svcType;
		  paraCount++;
		}
		
		CountResult countResult = getEntityManager().getObject(sqlBuf.toString(), sqlParas, CountResult.class);
		return countResult.getCount();
	}
}
