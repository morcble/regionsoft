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
@Table(name = "m_grouptousermap")
public class GroupToUserMap extends BaseEntityWithLongID implements Serializable{
	 @Column(name = "systemId" ,length = 20)
	 private String systemId;
	 
	 @Column(name = "userId")
	 private Long userId;
	 
	 @Column(name = "groupId")
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
