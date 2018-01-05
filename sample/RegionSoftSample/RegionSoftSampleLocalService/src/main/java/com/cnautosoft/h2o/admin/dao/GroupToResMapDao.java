package com.cnautosoft.h2o.admin.dao;

import com.cnautosoft.h2o.admin.entity.GroupToResMap;
import com.cnautosoft.h2o.annotation.Dao;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CountResult;
import com.cnautosoft.h2o.core.H2ODao;
import com.cnautosoft.h2o.core.CommonUtil;
import java.util.List;
import java.util.Date;

@Dao
public class GroupToResMapDao extends H2ODao<GroupToResMap,Long>{
	private static final Logger logger = Logger.getLogger(GroupToResMapDao.class);
	
	public List<GroupToResMap> getList(Integer pageNo ,Integer pageSize, String orderBy, Integer softDelete
			,String systemId,Long groupId,String resource
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
		if(!CommonUtil.isEmpty(groupId)){
			sqlBuf.append(" and groupId = ?");
		  paraCount++;
		}
		else{
			groupId = null;
		}
		if(!CommonUtil.isEmpty(resource)){
			sqlBuf.append(" and resource like ?");
		  paraCount++;
		}
		else{
			resource = null;
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
		if(!CommonUtil.isEmpty(groupId)){
		  	sqlParas[paraCount] = groupId;	
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(resource)){
		  	sqlParas[paraCount] = "%"+resource+"%";
		  paraCount++;
		}
		
		List<GroupToResMap> result = getEntityList(sqlBuf.toString(),sqlParas,pageNo, pageSize);
		return result;
	}
	
	
	public Long getAmount(Integer softDelete ,String systemId,Long groupId,String resource) throws Exception{		
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
		if(!CommonUtil.isEmpty(groupId)){
			sqlBuf.append(" and groupId = ?");
		  paraCount++;
		}
		else{
			groupId = null;
		}
		if(!CommonUtil.isEmpty(resource)){
			sqlBuf.append(" and resource like ?");
		  paraCount++;
		}
		else{
			resource = null;
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
		if(!CommonUtil.isEmpty(groupId)){
		  	sqlParas[paraCount] = groupId;
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(resource)){
		  	sqlParas[paraCount] = "%"+resource+"%";
		  paraCount++;
		}
		
		CountResult countResult = getEntityManager().getObject(sqlBuf.toString(), sqlParas, CountResult.class);
		return countResult.getCount();
	}
	
	public List<GroupToResMap> getListByGroupId(String groupId) throws Exception {
		String tableName = getBindTableName();
		StringBuilder sqlBuf = new StringBuilder("select * from ");
		sqlBuf.append(tableName);
		sqlBuf.append(" where groupid = ? and softDelete = 0 ");
		List<GroupToResMap> resToGroupMap = getEntityList(sqlBuf.toString(),new Object[]{groupId});
		return resToGroupMap;
	}


	public List<GroupToResMap> getExistMapByControllerBeanMethods(String systemId,Long groupId ,String sqlPart) throws Exception {
		String tableName = getBindTableName();
		StringBuilder sqlBuf = new StringBuilder("select * from ");
		sqlBuf.append(tableName);
		sqlBuf.append(" where urlCode in "+sqlPart+" and softDelete = 0 and groupId =? and systemId =?");
		List<GroupToResMap> resToGroupMap = getEntityList(sqlBuf.toString(),new Object[]{groupId,systemId});
		return resToGroupMap;
	}


	public void getDeleteExsitMap(String systemId, Long groupId,String deleteSqlPart) throws Exception {
		String tableName = getBindTableName();
		StringBuilder sqlBuf = new StringBuilder("delete from ");
		sqlBuf.append(tableName);
		sqlBuf.append(" where urlCode in "+deleteSqlPart+" and softDelete = 0 and groupId =? and systemId =?");
		excute(sqlBuf.toString(),new Object[]{groupId,systemId});
	}


	public void deleteByGroupId(Long grouopId) throws Exception{
		String tableName = getBindTableName();
		StringBuilder sqlBuf = new StringBuilder("update ");
		sqlBuf.append(tableName);
		sqlBuf.append(" set softDelete = 1 where groupid = ?");
		this.excute(sqlBuf.toString(), new Object[]{grouopId});
	}
}
