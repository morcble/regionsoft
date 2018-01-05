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
import com.cnautosoft.regionsoftsample.dto.RemoteSampleDto;
import com.cnautosoft.regionsoftsample.entity.RemoteSample;
import com.cnautosoft.regionsoftsample.resource.RemoteSampleResource;
import com.cnautosoft.regionsoftsample.service.RemoteSampleService;
import com.cnautosoft.regionsoftsample.service.impl.RemoteSampleServiceImpl;
import com.cnautosoft.h2o.core.exception.ValidationException;


@Resource(remoteInterface = RemoteSampleResource.class)
public class RemoteSampleResourceImpl extends H2OResourceImpl implements RemoteSampleResource{
	private static final Logger logger = Logger.getLogger(RemoteSampleResourceImpl.class);
	
	@Autowired(targetClass = RemoteSampleServiceImpl.class)
	private RemoteSampleService remoteSampleService;
	
	private boolean enableFastCopy = true;
	
	/**
	 * backend common validation 
	 * @param dto
	 * @throws Exception
	 */
	private void valid(RemoteSampleDto dto) throws Exception{
		    if(CommonUtil.isEmpty(dto.getRemoteName()))throw new ValidationException(getMessage("regionsoftsample.remotesample.remoteName.isnull"));
	}
	

	/**
	 * copy utils
	 */
	private void copyDtoToEntity(RemoteSampleDto dto,RemoteSample remoteSample){
		if(enableFastCopy){
			remoteSample.setRemoteName(dto.getRemoteName());		
			remoteSample.setId(dto.getId());		
			remoteSample.setCreateBy(dto.getCreateBy());
			remoteSample.setCreateDt(dto.getCreateDt());
			remoteSample.setUpdateBy(dto.getUpdateBy());
			remoteSample.setUpdateDt(dto.getUpdateDt());
			remoteSample.setVersion(dto.getVersion());
			remoteSample.setSoftDelete(dto.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(dto,remoteSample);
		}
	}
	
	
	private void copyEntityToDto(RemoteSample remoteSample,RemoteSampleDto dto){
		if(enableFastCopy){
			dto.setRemoteName(remoteSample.getRemoteName());	
			dto.setId(remoteSample.getId());		
			dto.setCreateBy(remoteSample.getCreateBy());
			dto.setCreateDt(remoteSample.getCreateDt());
			dto.setUpdateBy(remoteSample.getUpdateBy());
			dto.setUpdateDt(remoteSample.getUpdateDt());
			dto.setVersion(remoteSample.getVersion());
			dto.setSoftDelete(remoteSample.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(remoteSample, dto);
		}
	}
	
	private List<RemoteSampleDto> copyListPropertiesFromEntityToDto(List<RemoteSample> src){
		if(src==null)return null;
		if(enableFastCopy){
			List<RemoteSampleDto> ls = new ArrayList<RemoteSampleDto>();
			for(RemoteSample entity:src){
				RemoteSampleDto dto = new RemoteSampleDto();
				copyEntityToDto(entity,dto);
				ls.add(dto);
			}
			return ls;
		}
		else{
			return CommonUtil.copyListProperties(src,RemoteSampleDto.class);
		}
	}
	
	
	@Override
	public ResourceResponse<RemoteSampleDto> create(RemoteSampleDto dto,String operator){
		try{
			valid(dto);
			RemoteSample remoteSample = new RemoteSample();
			copyDtoToEntity(dto, remoteSample);
			remoteSample = remoteSampleService.create(remoteSample, operator);
			RemoteSampleDto result = new RemoteSampleDto();
			copyEntityToDto(remoteSample, result);
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
	public ResourceResponse<RemoteSampleDto> getByID(Long primaryKey) {
		try{
			RemoteSample remoteSample = remoteSampleService.getByID(primaryKey);
			if(remoteSample==null)return ResourceResWrapper.successResult("Record not found, primaryKey:"+primaryKey,null,RespCode._500);
			RemoteSampleDto result = new RemoteSampleDto();
			copyEntityToDto(remoteSample, result);
			return ResourceResWrapper.successResult(null, result);
		} 
		catch(Exception e){
			logger.error(e, "getByID");
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<RemoteSampleDto> update(RemoteSampleDto dto,String operator){
		try{
			valid(dto);
			RemoteSample remoteSample = new RemoteSample();
			copyDtoToEntity(dto, remoteSample);
			remoteSample = remoteSampleService.update(remoteSample, operator);
			if(remoteSample==null)return null;
			RemoteSampleDto result = new RemoteSampleDto();
			copyEntityToDto(remoteSample, result);
			
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
			remoteSampleService.deleteByID(primaryKey, operator);
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
			remoteSampleService.batchDelete(deleteIds, operator);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), null);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<List<RemoteSampleDto>> findAll(){
		try{
			List<RemoteSample> remoteSamples = remoteSampleService.findAll();
			List<RemoteSampleDto> result = copyListPropertiesFromEntityToDto(remoteSamples);
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
			remoteSampleService.deleteAll(operator);
			return ResourceResWrapper.successResult(null, null);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	
	@Override
	public ResourceResponse<PaginationTO> getList(ResourceRequest<RemoteSampleDto> listRequest){
		try{
			RemoteSampleDto dto = listRequest.getData();
			Long amount = remoteSampleService.getAmount(PaginationTO.NOT_DELETED 
						,dto.getRemoteName()
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
				
				List<RemoteSample> remoteSamples = remoteSampleService.getList(paginationTO.getCurrentPageNo(),paginationTO.getPageSize(),paginationTO.getOrderBy(), PaginationTO.NOT_DELETED
							,dto.getRemoteName()
						);
				List<RemoteSampleDto> result = copyListPropertiesFromEntityToDto(remoteSamples);
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
