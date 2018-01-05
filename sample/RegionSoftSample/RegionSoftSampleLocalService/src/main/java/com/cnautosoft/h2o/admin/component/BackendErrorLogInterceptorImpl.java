package com.cnautosoft.h2o.admin.component;

import com.cnautosoft.h2o.admin.dto.ErrorLogDto;
import com.cnautosoft.h2o.admin.resource.ErrorLogResource;
import com.cnautosoft.h2o.admin.resource.impl.ErrorLogResourceImpl;
import com.cnautosoft.h2o.annotation.Component;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.core.RequestIdManager;
import com.cnautosoft.h2o.core.aop.BackendErrorLogInterceptor;
import com.cnautosoft.h2o.core.auth.AuthUtil;
import com.cnautosoft.h2o.core.exception.BizException;
import com.cnautosoft.h2o.core.ids.IDGenerator;

@Component
public class BackendErrorLogInterceptorImpl implements BackendErrorLogInterceptor{
	@Autowired(targetClass = ErrorLogResourceImpl.class)
	private ErrorLogResource errorLogResource;
	/**
	 * if return false, will skip to log in log file
	 */
	@Override
	public boolean debug(Object... objs) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean error(Object... objs) {
		for(int i = 0,length = objs.length ; i <length; i ++){
			if(objs[i] instanceof BizException){
				return true;
			}
		}
		
		
		String errorDetail = BackendErrorLogInterceptor.constructErrorStr(objs);
		ErrorLogDto errorLogDto = new ErrorLogDto();
		try {
			errorLogDto.setId(IDGenerator.getLongID());
		} catch (Exception e) {
			e.printStackTrace();
		}
		errorLogDto.setSystemId("LOCAL");
		errorLogDto.setRequestId(RequestIdManager.getInstance().getRequestId());
		errorLogDto.setErrorType("BackendError");
		errorLogDto.setDetail(errorDetail);
		errorLogDto.setWebReqWrapper(null);
		errorLogResource.create(errorLogDto, AuthUtil.getCurrentUserAccount());
		
		return true;
	}

	@Override
	public boolean info(Object... objs) {
		// TODO Auto-generated method stub
		return true;
	}
}
