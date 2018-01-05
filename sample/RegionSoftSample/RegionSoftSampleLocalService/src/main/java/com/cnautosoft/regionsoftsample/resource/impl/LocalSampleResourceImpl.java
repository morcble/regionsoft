package com.cnautosoft.regionsoftsample.resource.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import com.cnautosoft.h2o.annotation.Resource;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.H2OResourceImpl;
import com.cnautosoft.h2o.core.CommonUtil;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.core.RespCode;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;
import com.cnautosoft.h2o.web.wrapper.ResourceResWrapper;
import com.cnautosoft.regionsoftsample.dto.LocalSampleDto;
import com.cnautosoft.regionsoftsample.entity.LocalSample;
import com.cnautosoft.regionsoftsample.resource.LocalSampleResource;
import com.cnautosoft.regionsoftsample.service.LocalSampleService;
import com.cnautosoft.regionsoftsample.service.impl.LocalSampleServiceImpl;
import com.cnautosoft.h2o.core.exception.ValidationException;


@Resource
public class LocalSampleResourceImpl extends H2OResourceImpl implements LocalSampleResource{
	private static final Logger logger = Logger.getLogger(LocalSampleResourceImpl.class);
	
	@Autowired(targetClass = LocalSampleServiceImpl.class)
	private LocalSampleService localSampleService;
	
	private boolean enableFastCopy = true;
	
	/**
	 * backend common validation 
	 * @param dto
	 * @throws Exception
	 */
	private void valid(LocalSampleDto dto) throws Exception{
		    if(CommonUtil.isEmpty(dto.getName()))throw new ValidationException(getMessage("regionsoftsample.localsample.name.isnull"));
	}
	

	/**
	 * copy utils
	 */
	private void copyDtoToEntity(LocalSampleDto dto,LocalSample localSample){
		if(enableFastCopy){
			localSample.setName(dto.getName());		
			localSample.setId(dto.getId());		
			localSample.setCreateBy(dto.getCreateBy());
			localSample.setCreateDt(dto.getCreateDt());
			localSample.setUpdateBy(dto.getUpdateBy());
			localSample.setUpdateDt(dto.getUpdateDt());
			localSample.setVersion(dto.getVersion());
			localSample.setSoftDelete(dto.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(dto,localSample);
		}
	}
	
	
	private void copyEntityToDto(LocalSample localSample,LocalSampleDto dto){
		if(enableFastCopy){
			dto.setName(localSample.getName());	
			dto.setId(localSample.getId());		
			dto.setCreateBy(localSample.getCreateBy());
			dto.setCreateDt(localSample.getCreateDt());
			dto.setUpdateBy(localSample.getUpdateBy());
			dto.setUpdateDt(localSample.getUpdateDt());
			dto.setVersion(localSample.getVersion());
			dto.setSoftDelete(localSample.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(localSample, dto);
		}
	}
	
	private List<LocalSampleDto> copyListPropertiesFromEntityToDto(List<LocalSample> src){
		if(src==null)return null;
		if(enableFastCopy){
			List<LocalSampleDto> ls = new ArrayList<LocalSampleDto>();
			for(LocalSample entity:src){
				LocalSampleDto dto = new LocalSampleDto();
				copyEntityToDto(entity,dto);
				ls.add(dto);
			}
			return ls;
		}
		else{
			return CommonUtil.copyListProperties(src,LocalSampleDto.class);
		}
	}
	
	
	@Override
	public ResourceResponse<LocalSampleDto> create(LocalSampleDto dto,String operator){
		try{
			valid(dto);
			LocalSample localSample = new LocalSample();
			copyDtoToEntity(dto, localSample);
			localSample = localSampleService.create(localSample, operator);
			LocalSampleDto result = new LocalSampleDto();
			copyEntityToDto(localSample, result);
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
	public ResourceResponse<LocalSampleDto> getByID(Long primaryKey) {
		try{
			LocalSample localSample = localSampleService.getByID(primaryKey);
			if(localSample==null)return ResourceResWrapper.successResult("Record not found, primaryKey:"+primaryKey,null,RespCode._500);
			LocalSampleDto result = new LocalSampleDto();
			copyEntityToDto(localSample, result);
			return ResourceResWrapper.successResult(null, result);
		} 
		catch(Exception e){
			logger.error(e, "getByID");
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<LocalSampleDto> update(LocalSampleDto dto,String operator){
		try{
			valid(dto);
			LocalSample localSample = new LocalSample();
			copyDtoToEntity(dto, localSample);
			localSample = localSampleService.update(localSample, operator);
			if(localSample==null)return null;
			LocalSampleDto result = new LocalSampleDto();
			copyEntityToDto(localSample, result);
			
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), result);
		}
		catch(ValidationException e){
			logger.warn(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), dto, RespCode._700);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+": "+e.getMessage(), dto, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<?> deleteByID(Long primaryKey,String operator){
		try{
			localSampleService.deleteByID(primaryKey, operator);
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
			localSampleService.batchDelete(deleteIds, operator);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), null);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<List<LocalSampleDto>> findAll(){
		try{
			List<LocalSample> localSamples = localSampleService.findAll();
			List<LocalSampleDto> result = copyListPropertiesFromEntityToDto(localSamples);
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
			localSampleService.deleteAll(operator);
			return ResourceResWrapper.successResult(null, null);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	
	@Override
	public ResourceResponse<PaginationTO> getList(ResourceRequest<LocalSampleDto> listRequest){
		try{
			LocalSampleDto dto = listRequest.getData();
			Long amount = localSampleService.getAmount(PaginationTO.NOT_DELETED 
						,dto.getName()
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
				
				List<LocalSample> localSamples = localSampleService.getList(paginationTO.getCurrentPageNo(),paginationTO.getPageSize(),paginationTO.getOrderBy(), PaginationTO.NOT_DELETED
							,dto.getName()
						);
				List<LocalSampleDto> result = copyListPropertiesFromEntityToDto(localSamples);
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
