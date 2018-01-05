package com.cnautosoft.regionsoftsample.dto;
 
import com.cnautosoft.h2o.web.core.BaseDtoWithLongID;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.cnautosoft.h2o.common.Constants;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CommonUtil;

public class RemoteSampleDto extends BaseDtoWithLongID{
	private static final Logger logger = Logger.getLogger(RemoteSampleDto.class);
	 private String remoteName;
	 
	 public String getRemoteName() {
		 return remoteName;
	 }

	 public void setRemoteName(String remoteName) {
		 this.remoteName = remoteName;
	 }
	 
}
