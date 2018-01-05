package com.cnautosoft.h2o.admin.resource.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.cnautosoft.h2o.admin.dto.UserDto;
import com.cnautosoft.h2o.admin.entity.User;
import com.cnautosoft.h2o.admin.resource.UserResource;
import com.cnautosoft.h2o.admin.service.UserService;
import com.cnautosoft.h2o.admin.service.impl.UserServiceImpl;
import com.cnautosoft.h2o.annotation.Resource;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.common.MD5Util;
import com.cnautosoft.h2o.core.H2OResourceImpl;
import com.cnautosoft.h2o.core.exception.ValidationException;
import com.cnautosoft.h2o.rpc.common.to.LongIDDto;
import com.cnautosoft.h2o.rpc.common.to.LongIDDto;
import com.cnautosoft.h2o.core.CommonUtil;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.core.RespCode;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;
import com.cnautosoft.h2o.web.wrapper.ResourceResWrapper;


@Resource
public class UserResourceImpl extends H2OResourceImpl implements UserResource{
	private static final Logger logger = Logger.getLogger(UserResourceImpl.class);
	
	@Autowired(targetClass = UserServiceImpl.class)
	private UserService userService;
	
	private boolean enableFastCopy = true;
	
	/**
	 * backend common validation 
	 * @param dto
	 * @throws Exception
	 */
	private void valid(UserDto dto) throws Exception{
		    if(CommonUtil.isEmpty(dto.getAccount()))throw new ValidationException(getMessage("morcble.user.account.isnull"));
		    if(CommonUtil.isEmpty(dto.getPwd()))throw new ValidationException(getMessage("morcble.user.pwd.isnull"));
		    if(CommonUtil.isEmpty(dto.getStatus()))throw new ValidationException(getMessage("morcble.user.status.isnull"));
		    
		    User valEntity = userService.getUserByAccount(dto.getAccount());
		    if(dto.getId()==null){//create
				if(valEntity!=null){
					throw new ValidationException(getMessage("账号已存在"));
				}
			}
			else{//update
				if(valEntity!=null&&valEntity.getId().longValue()!=dto.getId().longValue()){
					throw new ValidationException(getMessage("账号已存在"));
				}
			}
	}
	

	/**
	 * copy utils
	 */
	private void copyDtoToEntity(UserDto dto,User user){
		if(enableFastCopy){
			user.setAccount(dto.getAccount());		
			user.setPwd(dto.getPwd());		
			user.setStatus(dto.getStatus());		
			user.setDescription(dto.getDescription());		
			user.setId(dto.getId());		
			user.setCreateBy(dto.getCreateBy());
			user.setCreateDt(dto.getCreateDt());
			user.setUpdateBy(dto.getUpdateBy());
			user.setUpdateDt(dto.getUpdateDt());
			user.setVersion(dto.getVersion());
			user.setSoftDelete(dto.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(dto,user);
		}
	}
	
	
	private void copyEntityToDto(User user,UserDto dto){
		if(enableFastCopy){
			dto.setAccount(user.getAccount());	
			dto.setPwd(user.getPwd());	
			dto.setStatus(user.getStatus());	
			dto.setDescription(user.getDescription());	
			dto.setId(user.getId());		
			dto.setCreateBy(user.getCreateBy());
			dto.setCreateDt(user.getCreateDt());
			dto.setUpdateBy(user.getUpdateBy());
			dto.setUpdateDt(user.getUpdateDt());
			dto.setVersion(user.getVersion());
			dto.setSoftDelete(user.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(user, dto);
		}
	}
	
	private List<UserDto> copyListPropertiesFromEntityToDto(List<User> src){
		if(src==null)return null;
		if(enableFastCopy){
			List<UserDto> ls = new ArrayList<UserDto>();
			for(User entity:src){
				UserDto dto = new UserDto();
				copyEntityToDto(entity,dto);
				ls.add(dto);
			}
			return ls;
		}
		else{
			return CommonUtil.copyListProperties(src,UserDto.class);
		}
	}
	
	
	@Override
	public ResourceResponse<UserDto> create(UserDto dto,String operator){
		try{
			valid(dto);
			User user = new User();
			copyDtoToEntity(dto, user);
			user.setPwd(MD5Util.encode(user.getPwd()));
			user = userService.create(user, operator);
			UserDto result = new UserDto();
			copyEntityToDto(user, result);
			result.setPwd(null);
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
	public ResourceResponse<UserDto> getByID(Long primaryKey) {
		try{
			User user = userService.getByID(primaryKey);
			if(user==null)return ResourceResWrapper.successResult("Record not found, primaryKey:"+primaryKey,null,RespCode._500);
			user.setPwd(null);
			UserDto result = new UserDto();
			copyEntityToDto(user, result);
			return ResourceResWrapper.successResult(null, result);
		} 
		catch(Exception e){
			logger.error(e, "getByID");
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	
	@Override
	public ResourceResponse<UserDto> update(UserDto userTO,String operator){
		try{
			valid(userTO);
			User user = new User();
			copyDtoToEntity(userTO, user);
			user.setPwd(MD5Util.encode(user.getPwd()));
			user = userService.update(user, operator);
			if(user==null)return null;
			user.setPwd(null);
			UserDto result = new UserDto();
			copyEntityToDto(user, result);
			
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), result);
		}
		catch(ValidationException e){
			logger.warn(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), userTO, RespCode._700);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+": "+e.getMessage(), userTO, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<?> deleteByID(Long primaryKey,String operator){
		try{
			userService.deleteByID(primaryKey, operator);
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
			userService.batchDelete(deleteIds, operator);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), null);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<List<UserDto>> findAll(){
		try{
			List<User> users = userService.findAll();
			for(User user:users){
				user.setPwd(null);
			}
			List<UserDto> result = copyListPropertiesFromEntityToDto(users);
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
			userService.deleteAll(operator);
			return ResourceResWrapper.successResult(null, null);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	
	@Override
	public ResourceResponse<PaginationTO> getList(ResourceRequest<UserDto> listRequest){
		try{
			UserDto dto = listRequest.getData();
			Long amount = userService.getAmount(PaginationTO.NOT_DELETED 
						,dto.getAccount()
						,null
						,dto.getStatus()
						,null
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
				
				List<User> users = userService.getList(paginationTO.getCurrentPageNo(),paginationTO.getPageSize(),paginationTO.getOrderBy(), PaginationTO.NOT_DELETED
							,dto.getAccount()
							,null
							,dto.getStatus()
							,null
						);
				List<UserDto> result = copyListPropertiesFromEntityToDto(users);
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
	public ResourceResponse<?> lockUser(LongIDDto idDto, String operator) {
		try{
			User user = new User();
			user.setId(idDto.getId());
			user.setVersion(idDto.getVersion());
			user = userService.lockUser(user, operator);
			if(user==null)return null;
			user.setPwd(null);
			UserDto result = new UserDto();
			copyEntityToDto(user, result);
			
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), result);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+": "+e.getMessage(), idDto, RespCode._500);
		}
	}


	@Override
	public ResourceResponse<?> unlockUser(LongIDDto idDto, String operator) {
		try{
			User user = new User();
			user.setId(idDto.getId());
			user.setVersion(idDto.getVersion());
			user = userService.unlockUser(user, operator);
			if(user==null)return null;
			user.setPwd(null);
			UserDto result = new UserDto();
			copyEntityToDto(user, result);
			
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), result);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+": "+e.getMessage(), idDto, RespCode._500);
		}
	}


	@Override
	public ResourceResponse<Long> getAmountNotInGroup(ResourceRequest<UserDto> listRequest) {
		try{
			UserDto dto = listRequest.getData();
			Long amount = userService.getAmountNotInGroup(PaginationTO.NOT_DELETED 
						,dto.getAccount()
						,dto.getGroupId()
			);
			return ResourceResWrapper.successResult(null, amount);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(),null, RespCode._500);
		}
	}


	@Override
	public ResourceResponse<List<UserDto>> getListNotInGroup(ResourceRequest<UserDto> listRequest) {
		try{
			PaginationTO paginationTO = listRequest.getPaginationTO();
			UserDto dto = listRequest.getData();
			List<User> users = userService.getListNotInGroup(paginationTO.getCurrentPageNo(),paginationTO.getPageSize(),paginationTO.getOrderBy(), PaginationTO.NOT_DELETED
					,dto.getAccount()
					,dto.getGroupId()
					);
			for(User user:users){
				user.setPwd(null);
			}
			List<UserDto> result = copyListPropertiesFromEntityToDto(users);
			return ResourceResWrapper.successResult(null, result);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(),null, RespCode._500);
		}
	}
}
