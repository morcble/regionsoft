package com.cnautosoft.h2o.admin.resource.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.cnautosoft.h2o.admin.dto.MethodInfoDto;
import com.cnautosoft.h2o.admin.entity.GroupToResMap;
import com.cnautosoft.h2o.admin.entity.MethodInfo;
import com.cnautosoft.h2o.admin.resource.MethodInfoResource;
import com.cnautosoft.h2o.admin.service.GroupToResMapService;
import com.cnautosoft.h2o.admin.service.MethodInfoService;
import com.cnautosoft.h2o.admin.service.impl.GroupToResMapServiceImpl;
import com.cnautosoft.h2o.admin.service.impl.MethodInfoServiceImpl;
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
public class MethodInfoResourceImpl extends H2OResourceImpl implements MethodInfoResource{
	private static final Logger logger = Logger.getLogger(MethodInfoResourceImpl.class);
	
	@Autowired(targetClass = MethodInfoServiceImpl.class)
	private MethodInfoService methodInfoService;
	
	@Autowired(targetClass = GroupToResMapServiceImpl.class)
	private GroupToResMapService resToGroupMapService;
	
	private boolean enableFastCopy = true;
	
	/**
	 * backend common validation 
	 * @param dto
	 * @throws Exception
	 */
	private void valid(MethodInfoDto dto) throws Exception{
		    if(CommonUtil.isEmpty(dto.getDetailId()))throw new ValidationException(getMessage("morcble.methodinfo.detailId.isnull"));
		    if(CommonUtil.isEmpty(dto.getName()))throw new ValidationException(getMessage("morcble.methodinfo.name.isnull"));
		    if(CommonUtil.isEmpty(dto.getReturnType()))throw new ValidationException(getMessage("morcble.methodinfo.returnType.isnull"));
	}
	

	/**
	 * copy utils
	 */
	private void copyDtoToEntity(MethodInfoDto dto,MethodInfo methodInfo){
		if(enableFastCopy){
			methodInfo.setDetailId(dto.getDetailId());		
			methodInfo.setName(dto.getName());		
			methodInfo.setUrl(dto.getUrl());		
			methodInfo.setInputType(dto.getInputType());		
			methodInfo.setReturnType(dto.getReturnType());		
			methodInfo.setId(dto.getId());		
			methodInfo.setCreateBy(dto.getCreateBy());
			methodInfo.setCreateDt(dto.getCreateDt());
			methodInfo.setUpdateBy(dto.getUpdateBy());
			methodInfo.setUpdateDt(dto.getUpdateDt());
			methodInfo.setVersion(dto.getVersion());
			methodInfo.setSoftDelete(dto.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(dto,methodInfo);
		}
	}
	
	
	private void copyEntityToDto(MethodInfo methodInfo,MethodInfoDto dto){
		if(enableFastCopy){
			dto.setDetailId(methodInfo.getDetailId());	
			dto.setName(methodInfo.getName());	
			dto.setUrl(methodInfo.getUrl());	
			dto.setInputType(methodInfo.getInputType());	
			dto.setReturnType(methodInfo.getReturnType());	
			dto.setId(methodInfo.getId());		
			dto.setCreateBy(methodInfo.getCreateBy());
			dto.setCreateDt(methodInfo.getCreateDt());
			dto.setUpdateBy(methodInfo.getUpdateBy());
			dto.setUpdateDt(methodInfo.getUpdateDt());
			dto.setVersion(methodInfo.getVersion());
			dto.setSoftDelete(methodInfo.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(methodInfo, dto);
		}
	}
	
	private List<MethodInfoDto> copyListPropertiesFromEntityToDto(List<MethodInfo> src){
		if(src==null)return null;
		if(enableFastCopy){
			List<MethodInfoDto> ls = new ArrayList<MethodInfoDto>();
			for(MethodInfo entity:src){
				MethodInfoDto dto = new MethodInfoDto();
				copyEntityToDto(entity,dto);
				ls.add(dto);
			}
			return ls;
		}
		else{
			return CommonUtil.copyListProperties(src,MethodInfoDto.class);
		}
	}
	
	
	@Override
	public ResourceResponse<MethodInfoDto> create(MethodInfoDto dto,String operator){
		try{
			valid(dto);
			MethodInfo methodInfo = new MethodInfo();
			copyDtoToEntity(dto, methodInfo);
			methodInfo = methodInfoService.create(methodInfo, operator);
			MethodInfoDto result = new MethodInfoDto();
			copyEntityToDto(methodInfo, result);
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
	public ResourceResponse<MethodInfoDto> getByID(Long primaryKey) {
		try{
			MethodInfo methodInfo = methodInfoService.getByID(primaryKey);
			if(methodInfo==null)return ResourceResWrapper.successResult("Record not found, primaryKey:"+primaryKey,null,RespCode._500);
			MethodInfoDto result = new MethodInfoDto();
			copyEntityToDto(methodInfo, result);
			return ResourceResWrapper.successResult(null, result);
		} 
		catch(Exception e){
			logger.error(e, "getByID");
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<MethodInfoDto> update(MethodInfoDto methodInfoTO,String operator){
		try{
			valid(methodInfoTO);
			MethodInfo methodInfo = new MethodInfo();
			copyDtoToEntity(methodInfoTO, methodInfo);
			methodInfo = methodInfoService.update(methodInfo, operator);
			if(methodInfo==null)return null;
			MethodInfoDto result = new MethodInfoDto();
			copyEntityToDto(methodInfo, result);
			
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), result);
		}
		catch(ValidationException e){
			logger.warn(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), methodInfoTO, RespCode._700);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+": "+e.getMessage(), methodInfoTO, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<?> deleteByID(Long primaryKey,String operator){
		try{
			methodInfoService.deleteByID(primaryKey, operator);
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
			methodInfoService.batchDelete(deleteIds, operator);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), null);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<List<MethodInfoDto>> findAll(){
		try{
			List<MethodInfo> methodInfos = methodInfoService.findAll();
			List<MethodInfoDto> result = copyListPropertiesFromEntityToDto(methodInfos);
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
			methodInfoService.deleteAll(operator);
			return ResourceResWrapper.successResult(null, null);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	
	public ResourceResponse<PaginationTO> getList(ResourceRequest<MethodInfoDto> listRequest){
		try{
			MethodInfoDto dto = listRequest.getData();
			Long amount = methodInfoService.getAmount(PaginationTO.NOT_DELETED 
						,dto.getDetailId()
						,dto.getName()
						,null
						,null
						,dto.getReturnType()
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
				
				List<MethodInfo> methodInfos = methodInfoService.getList(paginationTO.getCurrentPageNo(),paginationTO.getPageSize(),paginationTO.getOrderBy(), PaginationTO.NOT_DELETED
							,dto.getDetailId()
							,dto.getName()
							,null
							,null
							,dto.getReturnType()
						);
				List<MethodInfoDto> result = copyListPropertiesFromEntityToDto(methodInfos);
				paginationTO.setList(result);
			}
			return ResourceResWrapper.successResult(null, paginationTO);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(),null, RespCode._500);
		}
	}


	@Override
	public ResourceResponse<List<MethodInfoDto>> getConfigtList(ResourceRequest<MethodInfoDto> listRequest) {
		try{
			PaginationTO paginationTO = listRequest.getPaginationTO();
			MethodInfoDto dto = listRequest.getData();
			
			List<MethodInfo> methodInfos = methodInfoService.getMethodLsByDetailId(dto.getDetailId());
			
			List<GroupToResMap> resToGroupMap = resToGroupMapService.getListByGroupId(dto.getGroupId());
			Set<String> set = new HashSet<String>();
			for(GroupToResMap tmp:resToGroupMap){
				set.add(tmp.getResource());
			}
			

			List<MethodInfoDto> result = copyListPropertiesFromEntityToDto(methodInfos);
			
			for(MethodInfoDto methodInfoDto:result){
				if(set.contains(methodInfoDto.getUrl())){
					methodInfoDto.setChosen("1");//chosen
				}
				else{
					methodInfoDto.setChosen("0");
				}
			}
			
			
			return ResourceResWrapper.successResult(null, result);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(),null, RespCode._500);
		}
	}

}
