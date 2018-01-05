package com.cnautosoft.h2o.admin.entity;

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
@Table(name = "m_errorlog")
public class ErrorLog extends BaseEntityWithLongID implements Serializable{
	 @Column(name = "systemId" ,length = 20)
	 private String systemId;
	 
	 @Column(name = "requestId" ,length = 50)
	 private String requestId;
	 
	 @Column(name = "errorType" ,length = 20)
	 private String errorType;
	 
	 @Text
	 @Column(name = "detail")
	 private String detail;
	 
	 public String getSystemId() {
		 return systemId;
	 }

	 public void setSystemId(String systemId) {
		 this.systemId = systemId;
	 }
	 
	 public String getRequestId() {
		 return requestId;
	 }

	 public void setRequestId(String requestId) {
		 this.requestId = requestId;
	 }
	 
	 public String getErrorType() {
		 return errorType;
	 }

	 public void setErrorType(String errorType) {
		 this.errorType = errorType;
	 }
	 
	 public String getDetail() {
		 return detail;
	 }

	 public void setDetail(String detail) {
		 this.detail = detail;
	 }
	 
}
