package com.cnautosoft.h2o.admin.dto;
 
import com.cnautosoft.h2o.web.core.BaseDtoWithLongID;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cnautosoft.h2o.common.Constants;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CommonUtil;

public class GroupDto extends BaseDtoWithLongID{
	private static final Logger logger = Logger.getLogger(GroupDto.class);
	 private String systemId;
	 
	 private String groupNm;
	 
	 private String groupDesc;
	 
	 private Long parentGroupId;
	 
	 private Integer depth;
	 
	 private GroupDto parentNode;
	 private List<GroupDto> childs;
	 private Set<String> resLs = new HashSet<String>();
	 
	 public Set<String> getResLs() {
		return resLs;
	}

	public void addResource(String resource){
		 resLs.add(resource);
	 }
	 
	 public GroupDto getParentNode() {
		return parentNode;
	}

	public void setParentNode(GroupDto parentNode) {
		this.parentNode = parentNode;
	}

	public List<GroupDto> getChilds() {
		return childs;
	 }

	 public void setChilds(List<GroupDto> childs) {
		this.childs = childs;
	 }

	 public String getSystemId() {
		 return systemId;
	 }

	 public void setSystemId(String systemId) {
		 this.systemId = systemId;
	 }
	 
	 public String getGroupNm() {
		 return groupNm;
	 }

	 public void setGroupNm(String groupNm) {
		 this.groupNm = groupNm;
	 }
	 
	 public String getGroupDesc() {
		 return groupDesc;
	 }

	 public void setGroupDesc(String groupDesc) {
		 this.groupDesc = groupDesc;
	 }
	 
	 public Long getParentGroupId() {
		 return parentGroupId;
	 }

	 public void setParentGroupId(Long parentGroupId) {
		 this.parentGroupId = parentGroupId;
	 }
	 
	 public Integer getDepth() {
		 return depth;
	 }

	 public void setDepth(Integer depth) {
		 this.depth = depth;
	 }
}
