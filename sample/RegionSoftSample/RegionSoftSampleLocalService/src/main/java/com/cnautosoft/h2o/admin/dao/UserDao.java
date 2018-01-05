package com.cnautosoft.h2o.admin.dao;

import com.cnautosoft.h2o.admin.entity.User;
import com.cnautosoft.h2o.annotation.Dao;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CountResult;
import com.cnautosoft.h2o.core.H2ODao;
import com.cnautosoft.h2o.core.CommonUtil;
import java.util.List;
import java.util.Date;

@Dao
public class UserDao extends H2ODao<User,Long>{
	private static final Logger logger = Logger.getLogger(UserDao.class);
	
	public List<User> getList(Integer pageNo ,Integer pageSize, String orderBy, Integer softDelete
			,String account,String pwd,Integer status,String description
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
		
		if(!CommonUtil.isEmpty(account)){
			sqlBuf.append(" and account like ?");
		  paraCount++;
		}
		else{
			account = null;
		}
		if(!CommonUtil.isEmpty(pwd)){
			sqlBuf.append(" and pwd = ?");
		  paraCount++;
		}
		else{
			pwd = null;
		}
		if(!CommonUtil.isEmpty(status)){
			sqlBuf.append(" and status = ?");
		  paraCount++;
		}
		else{
			status = null;
		}
		if(!CommonUtil.isEmpty(description)){
			sqlBuf.append(" and description = ?");
		  paraCount++;
		}
		else{
			description = null;
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
		
		if(!CommonUtil.isEmpty(account)){
		  	sqlParas[paraCount] = "%"+account+"%";
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(pwd)){
		  	sqlParas[paraCount] = pwd;	
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(status)){
		  	sqlParas[paraCount] = status;	
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(description)){
		  	sqlParas[paraCount] = description;	
		  paraCount++;
		}
		
		List<User> result = getEntityList(sqlBuf.toString(),sqlParas,pageNo, pageSize);
		return result;
	}
	
	
	public Long getAmount(Integer softDelete ,String account,String pwd,Integer status,String description) throws Exception{		
		String tableName = getBindTableName();
		
		StringBuilder sqlBuf = new StringBuilder("select count(1) as count from ");
		
		sqlBuf.append(tableName);
		sqlBuf.append(" where 1=1 ");
		
		int paraCount = 0;
		if(softDelete!=null){
			sqlBuf.append(" and softDelete = ?");
			paraCount++;
		}
		
		if(!CommonUtil.isEmpty(account)){
			sqlBuf.append(" and account like ?");
		  paraCount++;
		}
		else{
			account = null;
		}
		if(!CommonUtil.isEmpty(pwd)){
			sqlBuf.append(" and pwd = ?");
		  paraCount++;
		}
		else{
			pwd = null;
		}
		if(!CommonUtil.isEmpty(status)){
			sqlBuf.append(" and status = ?");
		  paraCount++;
		}
		else{
			status = null;
		}
		if(!CommonUtil.isEmpty(description)){
			sqlBuf.append(" and description = ?");
		  paraCount++;
		}
		else{
			description = null;
		}
	
		Object[] sqlParas =  new Object[paraCount];
		paraCount = 0;
		if(softDelete!=null){
			sqlParas[paraCount] = softDelete;
			paraCount++;
		}
		
		if(!CommonUtil.isEmpty(account)){
		  	sqlParas[paraCount] = "%"+account+"%";
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(pwd)){
		  	sqlParas[paraCount] = pwd;
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(status)){
		  	sqlParas[paraCount] = status;
		  paraCount++;
		}
		if(!CommonUtil.isEmpty(description)){
		  	sqlParas[paraCount] = description;
		  paraCount++;
		}
		
		CountResult countResult = getEntityManager().getObject(sqlBuf.toString(), sqlParas, CountResult.class);
		return countResult.getCount();
	}


	public User getUserByAccount(String account) throws Exception {
		String tableName = getBindTableName();
		StringBuilder sqlBuf = new StringBuilder("select * from ");
		sqlBuf.append(tableName);
		sqlBuf.append(" where account=? ");
		User user = getEntity(sqlBuf.toString(),new Object[]{account});
		return user;
	}


	public Long getAmountNotInGroup(Integer softDelete, String account, String groupId)  throws Exception {
		String tableName = getBindTableName();
		
		StringBuilder sqlBuf = new StringBuilder("select count(1) as count from ");
		sqlBuf.append(tableName);
		sqlBuf.append(" where 1=1 ");
		
		int paraCount = 0;
		if(softDelete!=null){
			sqlBuf.append(" and softDelete = ?");
			paraCount++;
		}
		
		if(!CommonUtil.isEmpty(account)){
			sqlBuf.append(" and account like ?");
			paraCount++;
		}
		else{
			account = null;
		}
		
		paraCount++;
		sqlBuf.append(" and id not in (select userid from m_grouptousermap where groupid = ? and softDelete=0)");
	
		Object[] sqlParas =  new Object[paraCount];
		paraCount = 0;
		if(softDelete!=null){
			sqlParas[paraCount] = softDelete;
			paraCount++;
		}
		
		if(!CommonUtil.isEmpty(account)){
		  	sqlParas[paraCount] = "%"+account+"%";
		  	paraCount++;
		}
		sqlParas[paraCount] = groupId;	
	  	paraCount++;
	  	
		CountResult countResult = getEntityManager().getObject(sqlBuf.toString(), sqlParas, CountResult.class);
		return countResult.getCount();
	}


	public List<User> getListNotInGroup(Integer pageNo, Integer pageSize, String orderBy, Integer softDelete,String account, String groupId)  throws Exception {
		String tableName = getBindTableName();
		
		StringBuilder sqlBuf = new StringBuilder("select * from ");
		sqlBuf.append(tableName);
		sqlBuf.append(" where 1=1 ");
		
		int paraCount = 0;
		if(softDelete!=null){
			sqlBuf.append(" and softDelete = ?");
			paraCount++;
		}
		
		if(!CommonUtil.isEmpty(account)){
			sqlBuf.append(" and account like ?");
			paraCount++;
		}
		else{
			account = null;
		}
		
		paraCount++;
		sqlBuf.append(" and id not in (select userid from m_grouptousermap where groupid = ? and softDelete=0)");
		
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
		
		if(!CommonUtil.isEmpty(account)){
		  	sqlParas[paraCount] = "%"+account+"%";
		  	paraCount++;
		}
		sqlParas[paraCount] = groupId;	
	  	paraCount++;
		
		List<User> result = getEntityList(sqlBuf.toString(),sqlParas,pageNo, pageSize);
		return result;
	}
}
