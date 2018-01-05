package com.cnautosoft.h2o.admin.resource.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.cnautosoft.h2o.admin.dto.GroupToUserMapDto;
import com.cnautosoft.h2o.admin.entity.GroupToUserMap;
import com.cnautosoft.h2o.admin.resource.GroupToUserMapResource;
import com.cnautosoft.h2o.admin.service.GroupToUserMapService;
import com.cnautosoft.h2o.admin.service.impl.GroupToUserMapServiceImpl;
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
public class GroupToUserMapResourceImpl extends H2OResourceImpl implements GroupToUserMapResource{
	private static final Logger logger = Logger.getLogger(GroupToUserMapResourceImpl.class);
	
	@Autowired(targetClass = GroupToUserMapServiceImpl.class)
	private GroupToUserMapService groupToUserMapService;
	
	private boolean enableFastCopy = true;
	
	/**
	 * backend common validation 
	 * @param dto
	 * @throws Exception
	 */
	private void valid(GroupToUserMapDto dto) throws Exception{
		    if(CommonUtil.isEmpty(dto.getSystemId()))throw new ValidationException(getMessage("morcble.grouptousermap.systemId.isnull"));
		    if(CommonUtil.isEmpty(dto.getUserId()))throw new ValidationException(getMessage("morcble.grouptousermap.userId.isnull"));
		    if(CommonUtil.isEmpty(dto.getGroupId()))throw new ValidationException(getMessage("morcble.grouptousermap.groupId.isnull"));
	}
	

	/**
	 * copy utils
	 */
	private void copyDtoToEntity(GroupToUserMapDto dto,GroupToUserMap groupToUserMap){
		if(enableFastCopy){
			groupToUserMap.setSystemId(dto.getSystemId());		
			groupToUserMap.setUserId(dto.getUserId());		
			groupToUserMap.setGroupId(dto.getGroupId());		
			groupToUserMap.setId(dto.getId());		
			groupToUserMap.setCreateBy(dto.getCreateBy());
			groupToUserMap.setCreateDt(dto.getCreateDt());
			groupToUserMap.setUpdateBy(dto.getUpdateBy());
			groupToUserMap.setUpdateDt(dto.getUpdateDt());
			groupToUserMap.setVersion(dto.getVersion());
			groupToUserMap.setSoftDelete(dto.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(dto,groupToUserMap);
		}
	}
	
	
	private void copyEntityToDto(GroupToUserMap groupToUserMap,GroupToUserMapDto dto){
		if(enableFastCopy){
			dto.setSystemId(groupToUserMap.getSystemId());	
			dto.setUserId(groupToUserMap.getUserId());	
			dto.setGroupId(groupToUserMap.getGroupId());	
			dto.setId(groupToUserMap.getId());		
			dto.setCreateBy(groupToUserMap.getCreateBy());
			dto.setCreateDt(groupToUserMap.getCreateDt());
			dto.setUpdateBy(groupToUserMap.getUpdateBy());
			dto.setUpdateDt(groupToUserMap.getUpdateDt());
			dto.setVersion(groupToUserMap.getVersion());
			dto.setSoftDelete(groupToUserMap.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(groupToUserMap, dto);
		}
	}
	
	private List<GroupToUserMapDto> copyListPropertiesFromEntityToDto(List<GroupToUserMap> src){
		if(src==null)return null;
		if(enableFastCopy){
			List<GroupToUserMapDto> ls = new ArrayList<GroupToUserMapDto>();
			for(GroupToUserMap entity:src){
				GroupToUserMapDto dto = new GroupToUserMapDto();
				copyEntityToDto(entity,dto);
				ls.add(dto);
			}
			return ls;
		}
		else{
			return CommonUtil.copyListProperties(src,GroupToUserMapDto.class);
		}
	}
	
	
	@Override
	public ResourceResponse<GroupToUserMapDto> create(GroupToUserMapDto dto,String operator){
		try{
			valid(dto);
			GroupToUserMap groupToUserMap = new GroupToUserMap();
			copyDtoToEntity(dto, groupToUserMap);
			groupToUserMap = groupToUserMapService.create(groupToUserMap, operator);
			GroupToUserMapDto result = new GroupToUserMapDto();
			copyEntityToDto(groupToUserMap, result);
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
	public ResourceResponse<GroupToUserMapDto> getByID(Long primaryKey) {
		try{
			GroupToUserMap groupToUserMap = groupToUserMapService.getByID(primaryKey);
			if(groupToUserMap==null)return ResourceResWrapper.successResult("Record not found, primaryKey:"+primaryKey,null,RespCode._500);
			GroupToUserMapDto result = new GroupToUserMapDto();
			copyEntityToDto(groupToUserMap, result);
			return ResourceResWrapper.successResult(null, result);
		} 
		catch(Exception e){
			logger.error(e, "getByID");
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<GroupToUserMapDto> update(GroupToUserMapDto groupToUserMapTO,String operator){
		try{
			valid(groupToUserMapTO);
			GroupToUserMap groupToUserMap = new GroupToUserMap();
			copyDtoToEntity(groupToUserMapTO, groupToUserMap);
			groupToUserMap = groupToUserMapService.update(groupToUserMap, operator);
			if(groupToUserMap==null)return null;
			GroupToUserMapDto result = new GroupToUserMapDto();
			copyEntityToDto(groupToUserMap, result);
			
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), result);
		}
		catch(ValidationException e){
			logger.warn(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), groupToUserMapTO, RespCode._700);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+": "+e.getMessage(), groupToUserMapTO, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<?> deleteByID(Long primaryKey,String operator){
		try{
			groupToUserMapService.deleteByID(primaryKey, operator);
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
			groupToUserMapService.batchDelete(deleteIds, operator);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), null);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<List<GroupToUserMapDto>> findAll(){
		try{
			List<GroupToUserMap> groupToUserMaps = groupToUserMapService.findAll();
			List<GroupToUserMapDto> result = copyListPropertiesFromEntityToDto(groupToUserMaps);
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
			groupToUserMapService.deleteAll(operator);
			return ResourceResWrapper.successResult(null, null);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	
	@Override
	public ResourceResponse<PaginationTO> getList(ResourceRequest<GroupToUserMapDto> listRequest){
		try{
			GroupToUserMapDto dto = listRequest.getData();
			Long amount = groupToUserMapService.getAmount(PaginationTO.NOT_DELETED 
						,dto.getSystemId()
						,dto.getUserId()
						,dto.getGroupId()
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
				
				List<GroupToUserMap> groupToUserMaps = groupToUserMapService.getList(paginationTO.getCurrentPageNo(),paginationTO.getPageSize(),paginationTO.getOrderBy(), PaginationTO.NOT_DELETED
							,dto.getSystemId()
							,dto.getUserId()
							,dto.getGroupId()
						);
				List<GroupToUserMapDto> result = copyListPropertiesFromEntityToDto(groupToUserMaps);
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
	public ResourceResponse<List<Long>> addUsersToGroup(String[] chosenIds,String systemId,String groupId, String operator) {
		try{
			List<Long> result = groupToUserMapService.addUsersToGroup(chosenIds,systemId,groupId, operator);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), result);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}

}
