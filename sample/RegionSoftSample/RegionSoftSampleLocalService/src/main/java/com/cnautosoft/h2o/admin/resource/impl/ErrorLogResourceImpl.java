package com.cnautosoft.h2o.admin.resource.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.cnautosoft.h2o.admin.dto.ErrorLogDto;
import com.cnautosoft.h2o.admin.entity.ErrorLog;
import com.cnautosoft.h2o.admin.resource.ErrorLogResource;
import com.cnautosoft.h2o.admin.service.ErrorLogService;
import com.cnautosoft.h2o.admin.service.impl.ErrorLogServiceImpl;
import com.cnautosoft.h2o.annotation.Resource;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.H2OResourceImpl;
import com.cnautosoft.h2o.core.exception.ValidationException;
import com.cnautosoft.h2o.core.CommonUtil;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.core.RespCode;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;
import com.cnautosoft.h2o.web.wrapper.ResourceResWrapper;


@Resource
public class ErrorLogResourceImpl extends H2OResourceImpl implements ErrorLogResource{
	private static final Logger logger = Logger.getLogger(ErrorLogResourceImpl.class);
	
	@Autowired(targetClass = ErrorLogServiceImpl.class)
	private ErrorLogService errorLogService;
	
	private boolean enableFastCopy = true;
	
	/**
	 * backend common validation 
	 * @param dto
	 * @throws Exception
	 */
	private void valid(ErrorLogDto dto) throws Exception{
		    if(CommonUtil.isEmpty(dto.getSystemId()))throw new ValidationException(getMessage("morcble.errorlog.systemId.isnull"));
		    if(CommonUtil.isEmpty(dto.getErrorType()))throw new ValidationException(getMessage("morcble.errorlog.errorType.isnull"));
	}
	

	/**
	 * copy utils
	 */
	private void copyDtoToEntity(ErrorLogDto dto,ErrorLog errorLog){
		if(enableFastCopy){
			errorLog.setSystemId(dto.getSystemId());		
			errorLog.setRequestId(dto.getRequestId());		
			errorLog.setErrorType(dto.getErrorType());		
			errorLog.setDetail(dto.getDetail());		
			errorLog.setId(dto.getId());		
			errorLog.setCreateBy(dto.getCreateBy());
			errorLog.setCreateDt(dto.getCreateDt());
			errorLog.setUpdateBy(dto.getUpdateBy());
			errorLog.setUpdateDt(dto.getUpdateDt());
			errorLog.setVersion(dto.getVersion());
			errorLog.setSoftDelete(dto.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(dto,errorLog);
		}
	}
	
	
	private void copyEntityToDto(ErrorLog errorLog,ErrorLogDto dto){
		if(enableFastCopy){
			dto.setSystemId(errorLog.getSystemId());	
			dto.setRequestId(errorLog.getRequestId());	
			dto.setErrorType(errorLog.getErrorType());	
			dto.setDetail(errorLog.getDetail());	
			dto.setId(errorLog.getId());		
			dto.setCreateBy(errorLog.getCreateBy());
			dto.setCreateDt(errorLog.getCreateDt());
			dto.setUpdateBy(errorLog.getUpdateBy());
			dto.setUpdateDt(errorLog.getUpdateDt());
			dto.setVersion(errorLog.getVersion());
			dto.setSoftDelete(errorLog.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(errorLog, dto);
		}
	}
	
	private List<ErrorLogDto> copyListPropertiesFromEntityToDto(List<ErrorLog> src){
		if(src==null)return null;
		if(enableFastCopy){
			List<ErrorLogDto> ls = new ArrayList<ErrorLogDto>();
			for(ErrorLog entity:src){
				ErrorLogDto dto = new ErrorLogDto();
				copyEntityToDto(entity,dto);
				ls.add(dto);
			}
			return ls;
		}
		else{
			return CommonUtil.copyListProperties(src,ErrorLogDto.class);
		}
	}
	
	
	@Override
	public ResourceResponse<ErrorLogDto> create(ErrorLogDto dto,String operator){
		try{
			valid(dto);
			ErrorLog errorLog = new ErrorLog();
			copyDtoToEntity(dto, errorLog);
			errorLog = errorLogService.create(errorLog, operator);
			ErrorLogDto result = new ErrorLogDto();
			copyEntityToDto(errorLog, result);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), result);
		}
		catch(ValidationException e){
			logger.warn(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), dto, RespCode._700);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), dto, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<ErrorLogDto> getByID(Long primaryKey) {
		try{
			ErrorLog errorLog = errorLogService.getByID(primaryKey);
			if(errorLog==null)return ResourceResWrapper.successResult("Record not found, primaryKey:"+primaryKey,null,RespCode._500);
			ErrorLogDto result = new ErrorLogDto();
			copyEntityToDto(errorLog, result);
			return ResourceResWrapper.successResult(null, result);
		} 
		catch(Exception e){
			logger.error(e, "getByID");
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<ErrorLogDto> update(ErrorLogDto errorLogTO,String operator){
		try{
			valid(errorLogTO);
			ErrorLog errorLog = new ErrorLog();
			copyDtoToEntity(errorLogTO, errorLog);
			errorLog = errorLogService.update(errorLog, operator);
			if(errorLog==null)return null;
			ErrorLogDto result = new ErrorLogDto();
			copyEntityToDto(errorLog, result);
			
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), result);
		}
		catch(ValidationException e){
			logger.warn(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), errorLogTO, RespCode._700);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+": "+e.getMessage(), errorLogTO, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<?> deleteByID(Long primaryKey,String operator){
		try{
			errorLogService.deleteByID(primaryKey, operator);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), null);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<?> batchDelete(String[] deleteIds,String operator){
		try{
			errorLogService.batchDelete(deleteIds, operator);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), null);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<List<ErrorLogDto>> findAll(){
		try{
			List<ErrorLog> errorLogs = errorLogService.findAll();
			List<ErrorLogDto> result = copyListPropertiesFromEntityToDto(errorLogs);
			return ResourceResWrapper.successResult(null, result);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<?> deleteAll(String operator){
		try{
			errorLogService.deleteAll(operator);
			return ResourceResWrapper.successResult(null, null);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	private String appendix = " ......";
	@Override
	public ResourceResponse<PaginationTO> getList(ResourceRequest<ErrorLogDto> listRequest){
		try{
			ErrorLogDto dto = listRequest.getData();
			Long amount = errorLogService.getAmount(PaginationTO.NOT_DELETED 
						,dto.getSystemId()
						,dto.getRequestId()
						,dto.getErrorType()
						,dto.getDetail()
			);
			
			PaginationTO paginationTO = listRequest.getPaginationTO();
	    	String orderBy = paginationTO.getOrderBy();
	    	if(CommonUtil.isEmpty(orderBy)){
	    		orderBy = "createDt desc";
	    		paginationTO.setOrderBy(orderBy);
	    	}
			int totalAmount = 0;
	    	int totalPageAmount = 0;
	    	int currentPageNo = paginationTO.getCurrentPageNo();
			paginationTO.setTotalAmount(amount.intValue());
			totalAmount = paginationTO.getTotalAmount();
			if(totalAmount==0){
				paginationTO.setCurrentPageNo(0);
				paginationTO.setTotalPageAmount(0);
			}
			else{
				totalPageAmount = totalAmount / paginationTO.getPageSize();
				int temp = totalAmount % paginationTO.getPageSize();
				if (temp > 0)
					totalPageAmount++;

				if (currentPageNo < 1)
					currentPageNo = 1;
				if (currentPageNo > totalPageAmount)
					currentPageNo = totalPageAmount;
				paginationTO.setCurrentPageNo(currentPageNo);
				paginationTO.setTotalPageAmount(totalPageAmount);
				
				List<ErrorLog> errorLogs = errorLogService.getList(paginationTO.getCurrentPageNo(),paginationTO.getPageSize(),paginationTO.getOrderBy(), PaginationTO.NOT_DELETED
							,dto.getSystemId()
							,dto.getRequestId()
							,dto.getErrorType()
							,dto.getDetail()
						);
				List<ErrorLogDto> result = copyListPropertiesFromEntityToDto(errorLogs);
				for(ErrorLogDto tmp:result){
					if(tmp.getDetail()!=null&&tmp.getDetail().length()>200){
						tmp.setDetail(tmp.getDetail().substring(0, 200)+appendix);
					}
				}
				paginationTO.setList(result);
			}
			return ResourceResWrapper.successResult(null, paginationTO);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(),null, RespCode._500);
		}
	}

}
