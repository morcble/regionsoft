package com.cnautosoft.h2o.admin.dto;
 
import com.cnautosoft.h2o.web.core.BaseDtoWithLongID;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.cnautosoft.h2o.common.Constants;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CommonUtil;

public class MethodInfoDto extends BaseDtoWithLongID{
	private static final Logger logger = Logger.getLogger(MethodInfoDto.class);
	 private Long detailId;
	 
	 private String name;
	 
	 private String url;
	 
	 private String inputType;
	 
	 private String returnType;
	 
	 private String groupId;
	 
	 private String chosen;//0 unchosen, 1 chosen
	 
	 public String getChosen() {
		return chosen;
	}

	public void setChosen(String chosen) {
		this.chosen = chosen;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Long getDetailId() {
		 return detailId;
	 }

	 public void setDetailId(Long detailId) {
		 this.detailId = detailId;
	 }
	 
	 public String getName() {
		 return name;
	 }

	 public void setName(String name) {
		 this.name = name;
	 }
	 
	 public String getUrl() {
		 return url;
	 }

	 public void setUrl(String url) {
		 this.url = url;
	 }
	 
	 public String getInputType() {
		 return inputType;
	 }

	 public void setInputType(String inputType) {
		 this.inputType = inputType;
	 }
	 
	 public String getReturnType() {
		 return returnType;
	 }

	 public void setReturnType(String returnType) {
		 this.returnType = returnType;
	 }
	 
}
