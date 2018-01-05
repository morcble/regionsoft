package com.cnautosoft.h2o.admin.resource.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.cnautosoft.h2o.admin.dto.GroupDto;
import com.cnautosoft.h2o.admin.entity.Group;
import com.cnautosoft.h2o.admin.resource.GroupResource;
import com.cnautosoft.h2o.admin.service.GroupService;
import com.cnautosoft.h2o.admin.service.impl.GroupServiceImpl;
import com.cnautosoft.h2o.annotation.Resource;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.H2OResourceImpl;
import com.cnautosoft.h2o.core.exception.BizException;
import com.cnautosoft.h2o.core.exception.ValidationException;
import com.cnautosoft.h2o.core.CommonUtil;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.core.RespCode;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;
import com.cnautosoft.h2o.web.wrapper.ResourceResWrapper;


@Resource
public class GroupResourceImpl extends H2OResourceImpl implements GroupResource{
	private static final Logger logger = Logger.getLogger(GroupResourceImpl.class);
	
	@Autowired(targetClass = GroupServiceImpl.class)
	private GroupService groupService;
	
	private boolean enableFastCopy = true;
	
	/**
	 * backend common validation 
	 * @param dto
	 * @throws Exception
	 */
	private void valid(GroupDto dto) throws Exception{
		    if(CommonUtil.isEmpty(dto.getSystemId()))throw new ValidationException(getMessage("morcble.group.systemId.isnull"));
		    if(CommonUtil.isEmpty(dto.getGroupNm()))throw new ValidationException(getMessage("morcble.group.groupNm.isnull"));
	}
	

	/**
	 * copy utils
	 */
	private void copyDtoToEntity(GroupDto dto,Group group){
		if(enableFastCopy){
			group.setSystemId(dto.getSystemId());		
			group.setGroupNm(dto.getGroupNm());		
			group.setGroupDesc(dto.getGroupDesc());		
			group.setParentGroupId(dto.getParentGroupId());		
			group.setDepth(dto.getDepth());		
			group.setId(dto.getId());		
			group.setCreateBy(dto.getCreateBy());
			group.setCreateDt(dto.getCreateDt());
			group.setUpdateBy(dto.getUpdateBy());
			group.setUpdateDt(dto.getUpdateDt());
			group.setVersion(dto.getVersion());
			group.setSoftDelete(dto.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(dto,group);
		}
	}
	
	
	private void copyEntityToDto(Group group,GroupDto dto){
		if(enableFastCopy){
			dto.setSystemId(group.getSystemId());	
			dto.setGroupNm(group.getGroupNm());	
			dto.setGroupDesc(group.getGroupDesc());	
			dto.setParentGroupId(group.getParentGroupId());	
			dto.setDepth(group.getDepth());	
			dto.setId(group.getId());		
			dto.setCreateBy(group.getCreateBy());
			dto.setCreateDt(group.getCreateDt());
			dto.setUpdateBy(group.getUpdateBy());
			dto.setUpdateDt(group.getUpdateDt());
			dto.setVersion(group.getVersion());
			dto.setSoftDelete(group.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(group, dto);
		}
	}
	
	private List<GroupDto> copyListPropertiesFromEntityToDto(List<Group> src){
		if(src==null)return null;
		if(enableFastCopy){
			List<GroupDto> ls = new ArrayList<GroupDto>();
			for(Group entity:src){
				GroupDto dto = new GroupDto();
				copyEntityToDto(entity,dto);
				ls.add(dto);
			}
			return ls;
		}
		else{
			return CommonUtil.copyListProperties(src,GroupDto.class);
		}
	}
	
	
	@Override
	public ResourceResponse<GroupDto> create(GroupDto dto,String operator){
		try{
			valid(dto);
			Group group = new Group();
			copyDtoToEntity(dto, group);
			group = groupService.create(group, operator);
			GroupDto result = new GroupDto();
			copyEntityToDto(group, result);
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
	public ResourceResponse<GroupDto> getByID(Long primaryKey) {
		try{
			Group group = groupService.getByID(primaryKey);
			if(group==null)return ResourceResWrapper.successResult("Record not found, primaryKey:"+primaryKey,null,RespCode._500);
			GroupDto result = new GroupDto();
			copyEntityToDto(group, result);
			return ResourceResWrapper.successResult(null, result);
		} 
		catch(Exception e){
			logger.error(e, "getByID");
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<GroupDto> update(GroupDto groupTO,String operator){
		try{
			valid(groupTO);
			Group group = new Group();
			copyDtoToEntity(groupTO, group);
			group = groupService.update(group, operator);
			if(group==null)return null;
			GroupDto result = new GroupDto();
			copyEntityToDto(group, result);
			
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), result);
		} 
		catch(ValidationException e){
			logger.warn(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), groupTO, RespCode._700);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+": "+e.getMessage(), groupTO, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<String[]> deleteByID(Long primaryKey,String operator){
		try{
			groupService.deleteByID(primaryKey, operator);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), new String[]{String.valueOf(primaryKey)});
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<String[]> batchDelete(String[] deleteIds,String operator){
		try{
			groupService.batchDelete(deleteIds, operator);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), deleteIds);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<List<GroupDto>> findAll(){
		try{
			List<Group> groups = groupService.findAll();
			List<GroupDto> result = copyListPropertiesFromEntityToDto(groups);
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
			groupService.deleteAll(operator);
			return ResourceResWrapper.successResult(null, null);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	
	@Override
	public ResourceResponse<PaginationTO> getList(ResourceRequest<GroupDto> listRequest){
		try{
			GroupDto dto = listRequest.getData();
			Long amount = groupService.getAmount(PaginationTO.NOT_DELETED 
						,dto.getSystemId()
						,dto.getGroupNm()
						,null
						,dto.getParentGroupId()
						,dto.getDepth()
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
				
				List<Group> groups = groupService.getList(paginationTO.getCurrentPageNo(),paginationTO.getPageSize(),paginationTO.getOrderBy(), PaginationTO.NOT_DELETED
							,dto.getSystemId()
							,dto.getGroupNm()
							,null
							,dto.getParentGroupId()
							,dto.getDepth()
						);
				List<GroupDto> result = copyListPropertiesFromEntityToDto(groups);
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
