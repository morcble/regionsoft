package com.cnautosoft.h2o.admin.dao;

import com.cnautosoft.h2o.admin.entity.Group;
import com.cnautosoft.h2o.annotation.Dao;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CountResult;
import com.cnautosoft.h2o.core.H2ODao;
import com.cnautosoft.h2o.core.CommonUtil;
import java.util.List;
import java.util.Date;

@Dao
public class GroupDao extends H2ODao<Group,Long>{
	private static final Logger logger = Logger.getLogger(GroupDao.class);
	
	public List<Group> getList(Integer pageNo ,Integer pageSize, String orderBy, Integer softDelete
			,String systemId,String groupNm,String groupDesc,Long parentGroupId,Integer depth
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
		if(!CommonUtil.isEmpty(groupNm)){
			sqlBuf.append(" and groupNm like ?");
		  paraCount++;
		}
		else{
			groupNm = null;
		}
		if(!CommonUtil.isEmpty(groupDesc)){
			sqlBuf.append(" and groupDesc = ?");
		  paraCount++;
		}
		else{
			groupDesc = null;
		}
		if(!CommonUtil.isEmpty(parentGroupId)){
			sqlBuf.append(" and parentGroupId = ?");
		  paraCount++;
		}
		else{
			parentGroupId = null;
		}
		if(!CommonUtil.isEmpty(depth)){
			sqlBuf.append(" and depth = ?");
		  paraCount++;
		}
		else{
			depth = null;
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
		if(!CommonUtil.isEmpty(groupNm)){
		  	sqlParas[paraCount] = "%"+groupNm+"%";
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(groupDesc)){
		  	sqlParas[paraCount] = groupDesc;	
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(parentGroupId)){
		  	sqlParas[paraCount] = parentGroupId;	
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(depth)){
		  	sqlParas[paraCount] = depth;	
		  paraCount++;
		}
		
		List<Group> result = getEntityList(sqlBuf.toString(),sqlParas,pageNo, pageSize);
		return result;
	}
	
	
	public Long getAmount(Integer softDelete ,String systemId,String groupNm,String groupDesc,Long parentGroupId,Integer depth) throws Exception{		
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
		if(!CommonUtil.isEmpty(groupNm)){
			sqlBuf.append(" and groupNm like ?");
		  paraCount++;
		}
		else{
			groupNm = null;
		}
		if(!CommonUtil.isEmpty(groupDesc)){
			sqlBuf.append(" and groupDesc = ?");
		  paraCount++;
		}
		else{
			groupDesc = null;
		}
		if(!CommonUtil.isEmpty(parentGroupId)){
			sqlBuf.append(" and parentGroupId = ?");
		  paraCount++;
		}
		else{
			parentGroupId = null;
		}
		if(!CommonUtil.isEmpty(depth)){
			sqlBuf.append(" and depth = ?");
		  paraCount++;
		}
		else{
			depth = null;
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
		if(!CommonUtil.isEmpty(groupNm)){
		  	sqlParas[paraCount] = "%"+groupNm+"%";
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(groupDesc)){
		  	sqlParas[paraCount] = groupDesc;
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(parentGroupId)){
		  	sqlParas[paraCount] = parentGroupId;
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(depth)){
		  	sqlParas[paraCount] = depth;
		  paraCount++;
		}
		
		CountResult countResult = getEntityManager().getObject(sqlBuf.toString(), sqlParas, CountResult.class);
		return countResult.getCount();
	}


	public Integer countChildByParentId(Long id) throws Exception {
		String sb = "select count(*) from m_group where softDelete=0 and parentGroupId = ?";
		return getEntityManager().getObject(sb.toString(), new Long[]{id}, Integer.class);
	}


	public List<Group> getGroupsByIds(String[] groupIds) throws Exception {
		String tableName = getBindTableName();
		Long[] sqlParas = new Long[groupIds.length];
		StringBuilder sb = new StringBuilder("select * from "+tableName+" where id in (");
		for(int i = 0; i <groupIds.length ; i++){
			sqlParas[i] = Long.valueOf(groupIds[i]);
			
			sb.append("?");
			if(i!=(groupIds.length-1)){
				sb.append(",");
			}
		}
		sb.append(")");
		return getEntityList(sb.toString(), sqlParas);
	}
}
