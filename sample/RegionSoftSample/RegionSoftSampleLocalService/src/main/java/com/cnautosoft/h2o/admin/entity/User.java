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
@Table(name = "m_user")
public class User extends BaseEntityWithLongID implements Serializable{
	 @Column(name = "account" ,length = 20)
	 private String account;
	 
	 @Column(name = "pwd" ,length = 100)
	 private String pwd;
	 
	 @Column(name = "status")
	 private Integer status;
	 
	 @Column(name = "description" ,length = 50)
	 private String description;
	 
	 public String getAccount() {
		 return account;
	 }

	 public void setAccount(String account) {
		 this.account = account;
	 }
	 
	 public String getPwd() {
		 return pwd;
	 }

	 public void setPwd(String pwd) {
		 this.pwd = pwd;
	 }
	 
	 public Integer getStatus() {
		 return status;
	 }

	 public void setStatus(Integer status) {
		 this.status = status;
	 }
	 
	 public String getDescription() {
		 return description;
	 }

	 public void setDescription(String description) {
		 this.description = description;
	 }
	 
}
