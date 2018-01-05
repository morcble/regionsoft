package com.cnautosoft.h2o.admin.dto;


import com.cnautosoft.h2o.web.core.BaseDtoWithLongID;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.cnautosoft.h2o.common.Constants;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CommonUtil;

public class ContextTypeDto extends BaseDtoWithLongID implements Comparable<ContextTypeDto>{
	private static final Logger logger = Logger.getLogger(ContextTypeDto.class);
	 private Long projectId;
	 private String valType;
	 
	 private String val;
	 
	 private String description;
	 
	 private Integer orderSeq;
	 
	 private Long sysVerion;
	 public Long getProjectId() {
		 return projectId;
	 }
	 public void setProjectId(Long projectId) {
		 this.projectId = projectId;
	 }
	 
	 public String getValType() {
		 return valType;
	 }

	 public void setValType(String valType) {
		 this.valType = valType;
	 }
	 
	 public String getVal() {
		 return val;
	 }

	 public void setVal(String val) {
		 this.val = val;
	 }
	 
	 public String getDescription() {
		 return description;
	 }

	 public void setDescription(String description) {
		 this.description = description;
	 }
	 
	 public Integer getOrderSeq() {
		 return orderSeq;
	 }

	 public void setOrderSeq(Integer orderSeq) {
		 this.orderSeq = orderSeq;
	 }
	 
	 public Long getSysVerion() {
		 return sysVerion;
	 }

	 public void setSysVerion(Long sysVerion) {
		 this.sysVerion = sysVerion;
	 }
	 
	@Override
	public int compareTo(ContextTypeDto o) {
		return (this.getOrderSeq().intValue() - o.getOrderSeq().intValue()) ;
	}
}