package com.cnautosoft.h2o.admin.dto;
 
import com.cnautosoft.h2o.web.core.BaseDtoWithLongID;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.cnautosoft.h2o.common.Constants;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CommonUtil;

public class UserDto extends BaseDtoWithLongID{
	private static final Logger logger = Logger.getLogger(UserDto.class);
	 private String account;
	 
	 private String pwd;
	 
	 private Integer status;
	 
	 private String description;
	 
	 private String groupId;
	 
	 public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getAccount() {
		 return account;
	 }

	 public void setAccount(String account) {
		 this.account = account;
	 }
	 
	 public String getPwd() {
		 return pwd;
	 }

	 public void setPwd(String pwd) {
		 this.pwd = pwd;
	 }
	 
	 public Integer getStatus() {
		 return status;
	 }

	 public void setStatus(Integer status) {
		 this.status = status;
	 }
	 
	 public String getDescription() {
		 return description;
	 }

	 public void setDescription(String description) {
		 this.description = description;
	 }
	 
}
