package com.cnautosoft.h2o.admin.dao;

import com.cnautosoft.h2o.admin.entity.GroupToUserMap;
import com.cnautosoft.h2o.annotation.Dao;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CountResult;
import com.cnautosoft.h2o.core.H2ODao;
import com.cnautosoft.h2o.core.CommonUtil;
import java.util.List;
import java.util.Date;

@Dao
public class GroupToUserMapDao extends H2ODao<GroupToUserMap,Long>{
	private static final Logger logger = Logger.getLogger(GroupToUserMapDao.class);
	
	public List<GroupToUserMap> getList(Integer pageNo ,Integer pageSize, String orderBy, Integer softDelete
			,String systemId,Long userId,Long groupId
					) throws Exception{
		String tableName = getBindTableName();
		
		StringBuilder sqlBuf = new StringBuilder("select a.id,a.updateBy,a.updateDt,b.account as createBy from ");
		sqlBuf.append(tableName);
		sqlBuf.append(" a inner join m_user b on a.userId = b.id");
		
		int paraCount = 0;
		if(softDelete!=null){
			sqlBuf.append(" and a.softDelete = ?");
			paraCount++;
		}
		
		if(!CommonUtil.isEmpty(systemId)){
			sqlBuf.append(" and a.systemId = ?");
		  paraCount++;
		}
		else{
			systemId = null;
		}
		if(!CommonUtil.isEmpty(userId)){
			sqlBuf.append(" and a.userId = ?");
		  paraCount++;
		}
		else{
			userId = null;
		}
		if(!CommonUtil.isEmpty(groupId)){
			sqlBuf.append(" and a.groupId = ?");
		  paraCount++;
		}
		else{
			groupId = null;
		}
		


		if(CommonUtil.isEmpty(orderBy)){
			sqlBuf.append(" order by a.id desc ");
		}
		else{
			orderBy = orderBy.replace("'", "").replace("%", "");
			sqlBuf.append(" order by a."+orderBy);
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
		if(!CommonUtil.isEmpty(userId)){
		  	sqlParas[paraCount] = userId;	
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(groupId)){
		  	sqlParas[paraCount] = groupId;	
		  paraCount++;
		}
		
		List<GroupToUserMap> result = getEntityList(sqlBuf.toString(),sqlParas,pageNo, pageSize);
		return result;
	}
	
	
	public Long getAmount(Integer softDelete ,String systemId,Long userId,Long groupId) throws Exception{		
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
		if(!CommonUtil.isEmpty(userId)){
			sqlBuf.append(" and userId = ?");
		  paraCount++;
		}
		else{
			userId = null;
		}
		if(!CommonUtil.isEmpty(groupId)){
			sqlBuf.append(" and groupId = ?");
		  paraCount++;
		}
		else{
			groupId = null;
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
		if(!CommonUtil.isEmpty(userId)){
		  	sqlParas[paraCount] = userId;
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(groupId)){
		  	sqlParas[paraCount] = groupId;
		  paraCount++;
		}
		
		CountResult countResult = getEntityManager().getObject(sqlBuf.toString(), sqlParas, CountResult.class);
		return countResult.getCount();
	}


	public List<GroupToUserMap> getGroupsByAccount(String account) throws Exception{	
		String tableName = getBindTableName();
		StringBuilder sqlBuf = new StringBuilder("select * from ");
		sqlBuf.append(tableName);
		sqlBuf.append(" where softDelete=0 and userid in (select id from m_user where account = ?)");
		return this.getEntityList(sqlBuf.toString(),new Object[]{account});
	}


	public List<String> getGroupsByAccount(List<Long> mapIds) throws Exception {
		Long[] sqlParas = new Long[mapIds.size()];
		StringBuilder sb = new StringBuilder("SELECT account FROM m_user where id in (SELECT distinct(userId) FROM m_grouptousermap where id in (");
		for(int i = 0; i <mapIds.size() ; i++){
			sqlParas[i] = mapIds.get(i);
			
			sb.append("?");
			if(i!=(mapIds.size()-1)){
				sb.append(",");
			}
		}
		sb.append("))");
		return getEntityManager().getList(sb.toString(), sqlParas, String.class);
	}


	public void deleteByGroupId(Long grouopId) throws Exception{
		String tableName = getBindTableName();
		StringBuilder sqlBuf = new StringBuilder("update ");
		sqlBuf.append(tableName);
		sqlBuf.append(" set softDelete = 1 where groupid = ?");
		this.excute(sqlBuf.toString(), new Object[]{grouopId});
	}
}
