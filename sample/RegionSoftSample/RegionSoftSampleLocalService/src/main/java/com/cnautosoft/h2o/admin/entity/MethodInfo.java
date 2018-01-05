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
@Table(name = "m_methodinfo")
public class MethodInfo extends BaseEntityWithLongID implements Serializable{
	 @Column(name = "detailId")
	 private Long detailId;
	 
	 @Column(name = "name" ,length = 100)
	 private String name;
	 
	 @Column(name = "url" ,length = 200)
	 private String url;
	 
	 @Column(name = "inputType" ,length = 1000)
	 private String inputType;
	 
	 @Column(name = "returnType" ,length = 100)
	 private String returnType;
	 
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
