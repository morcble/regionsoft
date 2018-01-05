package com.cnautosoft.h2o.admin.entity;

import java.io.Serializable;
import com.cnautosoft.h2o.data.persistence.Column;
import com.cnautosoft.h2o.data.persistence.Entity;
import com.cnautosoft.h2o.data.persistence.Id;
import com.cnautosoft.h2o.data.persistence.Inheritance;
import com.cnautosoft.h2o.data.persistence.InheritanceType;
import com.cnautosoft.h2o.data.persistence.Table;
import com.cnautosoft.h2o.data.persistence.BaseEntityWithLongID;
import java.util.Date;


@Entity
@Table(name = "m_managedbeandetail")
public class ManagedBeanDetail extends BaseEntityWithLongID implements Serializable{
	 @Column(name = "systemId" ,length = 20)
	 private String systemId;
	 @Column(name = "contextName" ,length = 50)
	 private String contextName;
	 
	 @Column(name = "name" ,length = 200)
	 private String name;
	 
	 @Column(name = "packageName" ,length = 200)
	 private String packageName;
	 
	 @Column(name = "beanType" ,length = 20)
	 private String beanType;
	 
	 @Column(name = "svcType" ,length = 20)
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
