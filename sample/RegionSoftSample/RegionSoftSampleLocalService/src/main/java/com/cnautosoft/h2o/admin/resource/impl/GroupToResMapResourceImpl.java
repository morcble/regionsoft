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

import com.cnautosoft.h2o.admin.dto.GroupToResMapDto;
import com.cnautosoft.h2o.admin.entity.GroupToResMap;
import com.cnautosoft.h2o.admin.entity.MethodInfo;
import com.cnautosoft.h2o.admin.resource.GroupToResMapResource;
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
public class GroupToResMapResourceImpl extends H2OResourceImpl implements GroupToResMapResource{
	private static final Logger logger = Logger.getLogger(GroupToResMapResourceImpl.class);
	
	@Autowired(targetClass = GroupToResMapServiceImpl.class)
	private GroupToResMapService groupToResMapService;
	
	@Autowired(targetClass = MethodInfoServiceImpl.class)
	private MethodInfoService methodInfoService;
	
	private boolean enableFastCopy = true;
	
	/**
	 * backend common validation 
	 * @param dto
	 * @throws Exception
	 */
	private void valid(GroupToResMapDto dto) throws Exception{
		    if(CommonUtil.isEmpty(dto.getSystemId()))throw new ValidationException(getMessage("morcble.grouptoresmap.systemId.isnull"));
		    if(CommonUtil.isEmpty(dto.getGroupId()))throw new ValidationException(getMessage("morcble.grouptoresmap.groupId.isnull"));
		    if(CommonUtil.isEmpty(dto.getResource()))throw new ValidationException(getMessage("morcble.grouptoresmap.resource.isnull"));
	}
	

	/**
	 * copy utils
	 */
	private void copyDtoToEntity(GroupToResMapDto dto,GroupToResMap resToGroupMap){
		if(enableFastCopy){
			resToGroupMap.setSystemId(dto.getSystemId());		
			resToGroupMap.setGroupId(dto.getGroupId());		
			resToGroupMap.setResource(dto.getResource());		
			resToGroupMap.setId(dto.getId());		
			resToGroupMap.setCreateBy(dto.getCreateBy());
			resToGroupMap.setCreateDt(dto.getCreateDt());
			resToGroupMap.setUpdateBy(dto.getUpdateBy());
			resToGroupMap.setUpdateDt(dto.getUpdateDt());
			resToGroupMap.setVersion(dto.getVersion());
			resToGroupMap.setSoftDelete(dto.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(dto,resToGroupMap);
		}
	}
	
	
	private void copyEntityToDto(GroupToResMap resToGroupMap,GroupToResMapDto dto){
		if(enableFastCopy){
			dto.setSystemId(resToGroupMap.getSystemId());	
			dto.setGroupId(resToGroupMap.getGroupId());	
			dto.setResource(resToGroupMap.getResource());	
			dto.setId(resToGroupMap.getId());		
			dto.setCreateBy(resToGroupMap.getCreateBy());
			dto.setCreateDt(resToGroupMap.getCreateDt());
			dto.setUpdateBy(resToGroupMap.getUpdateBy());
			dto.setUpdateDt(resToGroupMap.getUpdateDt());
			dto.setVersion(resToGroupMap.getVersion());
			dto.setSoftDelete(resToGroupMap.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(resToGroupMap, dto);
		}
	}
	
	private List<GroupToResMapDto> copyListPropertiesFromEntityToDto(List<GroupToResMap> src){
		if(src==null)return null;
		if(enableFastCopy){
			List<GroupToResMapDto> ls = new ArrayList<GroupToResMapDto>();
			for(GroupToResMap entity:src){
				GroupToResMapDto dto = new GroupToResMapDto();
				copyEntityToDto(entity,dto);
				ls.add(dto);
			}
			return ls;
		}
		else{
			return CommonUtil.copyListProperties(src,GroupToResMapDto.class);
		}
	}
	
	
	@Override
	public ResourceResponse<GroupToResMapDto> create(GroupToResMapDto dto,String operator){
		try{
			valid(dto);
			GroupToResMap resToGroupMap = new GroupToResMap();
			copyDtoToEntity(dto, resToGroupMap);
			resToGroupMap = groupToResMapService.create(resToGroupMap, operator);
			GroupToResMapDto result = new GroupToResMapDto();
			copyEntityToDto(resToGroupMap, result);
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
	public ResourceResponse<GroupToResMapDto> getByID(Long primaryKey) {
		try{
			GroupToResMap resToGroupMap = groupToResMapService.getByID(primaryKey);
			if(resToGroupMap==null)return ResourceResWrapper.successResult("Record not found, primaryKey:"+primaryKey,null,RespCode._500);
			GroupToResMapDto result = new GroupToResMapDto();
			copyEntityToDto(resToGroupMap, result);
			return ResourceResWrapper.successResult(null, result);
		} 
		catch(Exception e){
			logger.error(e, "getByID");
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<GroupToResMapDto> update(GroupToResMapDto resToGroupMapTO,String operator){
		try{
			valid(resToGroupMapTO);
			GroupToResMap resToGroupMap = new GroupToResMap();
			copyDtoToEntity(resToGroupMapTO, resToGroupMap);
			resToGroupMap = groupToResMapService.update(resToGroupMap, operator);
			if(resToGroupMap==null)return null;
			GroupToResMapDto result = new GroupToResMapDto();
			copyEntityToDto(resToGroupMap, result);
			
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), result);
		}
		catch(ValidationException e){
			logger.warn(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), resToGroupMapTO, RespCode._700);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+": "+e.getMessage(), resToGroupMapTO, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<?> deleteByID(Long primaryKey,String operator){
		try{
			groupToResMapService.deleteByID(primaryKey, operator);
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
			groupToResMapService.batchDelete(deleteIds, operator);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), null);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<List<GroupToResMapDto>> findAll(){
		try{
			List<GroupToResMap> resToGroupMaps = groupToResMapService.findAll();
			List<GroupToResMapDto> result = copyListPropertiesFromEntityToDto(resToGroupMaps);
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
			groupToResMapService.deleteAll(operator);
			return ResourceResWrapper.successResult(null, null);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	
	@Override
	public ResourceResponse<PaginationTO> getList(ResourceRequest<GroupToResMapDto> listRequest){
		try{
			GroupToResMapDto dto = listRequest.getData();
			Long amount = groupToResMapService.getAmount(PaginationTO.NOT_DELETED 
						,dto.getSystemId()
						,dto.getGroupId()
						,dto.getResource()
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
				
				List<GroupToResMap> groupToResMaps = groupToResMapService.getList(paginationTO.getCurrentPageNo(),paginationTO.getPageSize(),paginationTO.getOrderBy(), PaginationTO.NOT_DELETED
							,dto.getSystemId()
							,dto.getGroupId()
							,dto.getResource()
						);
				List<GroupToResMapDto> result = copyListPropertiesFromEntityToDto(groupToResMaps);
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
	public ResourceResponse<?> changeGroupRes(List<GroupToResMapDto> changeDtoLs,String systemId,String operator,Long controllerBeanId,Long groupId) {
		try{
			/**
			 * retrieve all method for the controller
			 */
			List<MethodInfo> methods = methodInfoService.getMethodLsByDetailId(controllerBeanId);
			
			/**
			 * retrieve existing map record by available methods
			 */
			List<GroupToResMap> ls = groupToResMapService.getExistMapByControllerBeanMethods(systemId,groupId,methods);
			Set<String> existSet = new HashSet<String>();
			for(GroupToResMap tmp:ls){
				existSet.add(tmp.getResource());
			}

			Set<String> retainSet = new HashSet<String>();
			Set<String> insertSet = new HashSet<String>();
			for(GroupToResMapDto resToGroupMapDto:changeDtoLs){
				if(existSet.contains(resToGroupMapDto.getResource())){
					retainSet.add(resToGroupMapDto.getResource());
					continue;
				}
				else insertSet.add(resToGroupMapDto.getResource());
			}
			
			Set<String> deleteSet = new HashSet<String>();
			for(MethodInfo methodInfo:methods){
				if(methodInfo.getUrl()!=null){
					if(!retainSet.contains(methodInfo.getUrl())&&!insertSet.contains(methodInfo.getUrl())){
						deleteSet.add(methodInfo.getUrl());
					}
				}
			}
			
			groupToResMapService.updateResMap(insertSet,deleteSet,systemId,groupId,operator);
			
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), null);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}

}
