package com.cnautosoft.regionsoftsample.entity;

import java.io.Serializable;
import com.cnautosoft.h2o.data.persistence.Column;
import com.cnautosoft.h2o.data.persistence.Entity;
import com.cnautosoft.h2o.data.persistence.Id;
import com.cnautosoft.h2o.data.persistence.Inheritance;
import com.cnautosoft.h2o.data.persistence.InheritanceType;
import com.cnautosoft.h2o.data.persistence.Table;
import com.cnautosoft.h2o.data.persistence.BaseEntityWithLongID;
import com.cnautosoft.h2o.data.persistence.Text;
import java.util.Date;


@Entity
@Table(name = "m_localsample")
public class LocalSample extends BaseEntityWithLongID implements Serializable{
	 @Column(name = "name" ,length = 20)
	 private String name;
	 
	 public String getName() {
		 return name;
	 }

	 public void setName(String name) {
		 this.name = name;
	 }
	 
}
