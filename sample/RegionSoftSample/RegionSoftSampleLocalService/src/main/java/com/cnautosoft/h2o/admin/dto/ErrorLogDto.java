package com.cnautosoft.h2o.admin.dto;
 
import com.cnautosoft.h2o.web.core.BaseDtoWithLongID;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.cnautosoft.h2o.common.Constants;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CommonUtil;

public class ErrorLogDto extends BaseDtoWithLongID{
	 private static final Logger logger = Logger.getLogger(ErrorLogDto.class);
	 private String systemId;
	 
	 private String requestId;
	 
	 private String errorType;
	 
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
