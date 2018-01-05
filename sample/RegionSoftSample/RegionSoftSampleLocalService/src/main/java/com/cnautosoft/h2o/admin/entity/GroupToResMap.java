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
@Table(name = "m_grouptoresmap")
public class GroupToResMap extends BaseEntityWithLongID implements Serializable{
	 @Column(name = "systemId" ,length = 20)
	 private String systemId;
	 
	 @Column(name = "groupId")
	 private Long groupId;
	 
	 @Column(name = "resource" ,length = 100)
	 private String resource;
	 
	 @Column(name = "urlCode")
	 private Integer urlCode;
	 
	 @Column(name = "resType" ,length = 20)
	 private String resType;
	 
	 
	 public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
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

	public Integer getUrlCode() {
		return urlCode;
	}

	public void setUrlCode(Integer urlCode) {
		this.urlCode = urlCode;
	}
	 
}
