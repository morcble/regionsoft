package com.cnautosoft.h2o.admin.dto;
 
import com.cnautosoft.h2o.web.core.BaseDtoWithLongID;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.cnautosoft.h2o.common.Constants;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CommonUtil;

public class ManagedBeanDetailDto extends BaseDtoWithLongID{
	private static final Logger logger = Logger.getLogger(ManagedBeanDetailDto.class);
	 private String systemId;
	 
	 private String contextName;
	 
	 private String name;
	 
	 private String packageName;
	 
	 private String beanType;
	 
	 private String svcType;
	 
	 public String getSystemId() {
		 return systemId;
	 }

	 public void setSystemId(String systemId) {
		 this.systemId = systemId;
	 }
	 
	 public String getContextName() {
		 return contextName;
	 }

	 public void setContextName(String contextName) {
		 this.contextName = contextName;
	 }
	 
	 public String getName() {
		 return name;
	 }

	 public void setName(String name) {
		 this.name = name;
	 }
	 
	 public String getPackageName() {
		 return packageName;
	 }

	 public void setPackageName(String packageName) {
		 this.packageName = packageName;
	 }
	 
	 public String getBeanType() {
		 return beanType;
	 }

	 public void setBeanType(String beanType) {
		 this.beanType = beanType;
	 }
	 
	 public String getSvcType() {
		 return svcType;
	 }

	 public void setSvcType(String svcType) {
		 this.svcType = svcType;
	 }
	 
}
