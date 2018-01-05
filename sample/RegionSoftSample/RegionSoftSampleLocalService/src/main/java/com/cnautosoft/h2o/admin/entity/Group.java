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
@Table(name = "m_group")
public class Group extends BaseEntityWithLongID implements Serializable{
	 @Column(name = "systemId" ,length = 20)
	 private String systemId;
	 
	 @Column(name = "groupNm" ,length = 20)
	 private String groupNm;
	 
	 @Column(name = "groupDesc" ,length = 50)
	 private String groupDesc;
	 
	 @Column(name = "parentGroupId")
	 private Long parentGroupId;
	 
	 @Column(name = "depth")
	 private Integer depth;
	 
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
