package com.cnautosoft.h2o.admin.dto;
 
import com.cnautosoft.h2o.web.core.BaseDtoWithLongID;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.cnautosoft.h2o.common.Constants;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CommonUtil;

public class GroupToUserMapDto extends BaseDtoWithLongID{
	private static final Logger logger = Logger.getLogger(GroupToUserMapDto.class);
	 private String systemId;
	 
	 private Long userId;
	 
	 private Long groupId;
	 
	 public String getSystemId() {
		 return systemId;
	 }

	 public void setSystemId(String systemId) {
		 this.systemId = systemId;
	 }
	 
	 public Long getUserId() {
		 return userId;
	 }

	 public void setUserId(Long userId) {
		 this.userId = userId;
	 }
	 
	 public Long getGroupId() {
		 return groupId;
	 }

	 public void setGroupId(Long groupId) {
		 this.groupId = groupId;
	 }
	 
}
