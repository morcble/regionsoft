package com.cnautosoft.h2o.admin.dto;
 
import com.cnautosoft.h2o.web.core.BaseDtoWithLongID;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.cnautosoft.h2o.common.Constants;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CommonUtil;

public class GroupToResMapDto extends BaseDtoWithLongID{
	private static final Logger logger = Logger.getLogger(GroupToResMapDto.class);
	 private String systemId;
	 
	 private Long groupId;
	 
	 private String resource;
	 
	 private String detailIdStr;
	 
	 private String resType;
	 
	 public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getDetailIdStr() {
		return detailIdStr;
	}

	public void setDetailIdStr(String detailIdStr) {
		this.detailIdStr = detailIdStr;
	}

	public String getSystemId() {
		 return systemId;
	 }

	 public void setSystemId(String systemId) {
		 this.systemId = systemId;
	 }
	 
	 public Long getGroupId() {
		 return groupId;
	 }

	 public void setGroupId(Long groupId) {
		 this.groupId = groupId;
	 }
	 
	 public String getResource() {
		 return resource;
	 }

	 public void setResource(String resource) {
		 this.resource = resource;
	 }
	 
}
