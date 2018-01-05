package com.cnautosoft.h2o.admin.controller;

import java.util.List;
import java.util.Map;

import com.cnautosoft.h2o.admin.dto.UserDto;
import com.cnautosoft.h2o.admin.resource.UserResource;
import com.cnautosoft.h2o.admin.resource.impl.UserResourceImpl;
import com.cnautosoft.h2o.annotation.Controller;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.annotation.tag.HeaderInfo;
import com.cnautosoft.h2o.annotation.tag.Parameter;
import com.cnautosoft.h2o.annotation.tag.PathVariable;
import com.cnautosoft.h2o.annotation.tag.RequestMapping;
import com.cnautosoft.h2o.annotation.tag.ResponseBody;
import com.cnautosoft.h2o.common.Constants;
import com.cnautosoft.h2o.common.JsonUtil;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CommonUtil;
import com.cnautosoft.h2o.core.auth.AuthUtil;
import com.cnautosoft.h2o.core.ids.IDGenerator;
import com.cnautosoft.h2o.enums.RequestMethod;
import com.cnautosoft.h2o.rpc.common.to.LongIDDto;
import com.cnautosoft.h2o.rpc.common.to.LongIDDto;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.core.RespCode;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;
import com.cnautosoft.h2o.web.wrapper.ResourceReqWrapper;
import com.cnautosoft.h2o.web.wrapper.ResourceResWrapper;
import com.cnautosoft.h2o.web.wrapper.WebReqWrapper; 

@Controller
@RequestMapping("/admin/user")
public class UserController {
	public static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired(targetClass = UserResourceImpl.class)
	private UserResource userResource;
	
	
	@RequestMapping(value = "/save" ,method ={RequestMethod.POST},responseHeader= {"Content-Type == application/json;charset=UTF-8"})
    public @ResponseBody String save(@HeaderInfo Map<String,String> headerInfo,	@Parameter(value = "requestStr") String requestStr) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse<?> result = null;
    	UserDto userDto = (UserDto) JsonUtil.jsonToBean(webReqWrapper.getData(), UserDto.class);
    	try {
    		userDto.setWebReqWrapper(webReqWrapper);
    		if(userDto.getId()==null||Long.valueOf(userDto.getId())<1){
    			userDto.setId(IDGenerator.getLongID());
    			result = userResource.create(userDto, operator);
    		}
    		else{
    			result = userResource.update(userDto, operator); 
    		}
    		
		} catch (Exception e) {
			logger.error(e);
			result = ResourceResWrapper.failResult(e.getMessage(), userDto, RespCode._508);
		}
    	if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
    	else  return JsonUtil.objectToJson(result);
    }
	
	
	@RequestMapping(value = "/get", method = { RequestMethod.POST }, responseHeader = {"Content-Type == application/json;charset=UTF-8" })
	public @ResponseBody String get(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
		ResourceResponse<?> result = null;
		try {
			LongIDDto idDto = (LongIDDto) JsonUtil.jsonToBean(webReqWrapper.getData(), LongIDDto.class);
			result = userResource.getByID(idDto.getId());
		} catch (Exception e) {
			logger.error(e);
			result = ResourceResWrapper.failResult(e.getMessage(), webReqWrapper.getData(), RespCode._508);
		}
		
		if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
		else return JsonUtil.objectToJson(result);
	}
	
	
	@RequestMapping(value = "/delete" ,method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String delete(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse<?> result = null;
    	try {
    		LongIDDto idDto = (LongIDDto) JsonUtil.jsonToBean(webReqWrapper.getData(), LongIDDto.class);
			result = userResource.deleteByID(idDto.getId(), operator);
		} catch (Exception e) {
			logger.error(e);
			result = ResourceResWrapper.failResult(e.getMessage(), webReqWrapper.getData(), RespCode._508);
		}
    	if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
		else return JsonUtil.objectToJson(result);
    }
	
	@RequestMapping(value="/list/delete",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String userListDelete(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	UserDto user = (UserDto) JsonUtil.jsonToBean(webReqWrapper.getData(), UserDto.class);
    	paginationTO.setRequestData(user);
    	user.setWebReqWrapper(webReqWrapper);
    	String deleteId = paginationTO.getDeleteId();
    	String deleteIds = paginationTO.getDeleteIds();
    	ResourceResponse<?> result = null;
    	if(!CommonUtil.isEmpty(deleteId)){
    		result = userResource.deleteByID(Long.parseLong(deleteId), operator);
    	}
    	else if(!CommonUtil.isEmpty(deleteIds)){
    		String[] idArray = deleteIds.split(";");
			result = userResource.batchDelete(idArray,  operator);
    	}
    	if(!result.isSuccess()) return JsonUtil.objectToJson(result);
    	ResourceResponse resResponse = sublist(headerInfo,requestStr);
    	if(resResponse.isSuccess()) return JsonUtil.objectToJsonDateSerializer(resResponse, Constants.DATE_FORMAT1);
    	else return JsonUtil.objectToJson(resResponse);
 	}
	
	@RequestMapping(value="/list",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String userList(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse result = sublist(headerInfo,requestStr);
    	if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
    	else return JsonUtil.objectToJson(result);
    }

    private ResourceResponse sublist( Map<String,String> headerInfo,String requestStr){
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	UserDto user = (UserDto) JsonUtil.jsonToBean(webReqWrapper.getData(), UserDto.class);
    	paginationTO.setRequestData(user);
    	user.setWebReqWrapper(webReqWrapper);
		String errorMsg = null;
		try {	
			ResourceResponse<PaginationTO> result = userResource.getList(ResourceReqWrapper.listRequest(paginationTO, user));
			if(!result.isSuccess())return result;
			paginationTO = result.getData();
		} catch (Exception e) {
			logger.error(e);
			errorMsg = e.getMessage();
		}
		
		if(errorMsg!=null){
			return  ResourceResWrapper.failResult(errorMsg, paginationTO, RespCode._508);
		}
		return ResourceResWrapper.successResult(null, paginationTO);
    }
    
    
    @RequestMapping(value = "/lockuser" ,method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String lockuser(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse<?> result = null;
    	try {
    		LongIDDto idDto = (LongIDDto) JsonUtil.jsonToBean(webReqWrapper.getData(), LongIDDto.class);
			result = userResource.lockUser(idDto, operator);
		} catch (Exception e) {
			logger.error(e);
			result = ResourceResWrapper.failResult(e.getMessage(), webReqWrapper.getData(), RespCode._508);
		}
    	if(result!=null) return JsonUtil.objectToJson(result);
    	else  return JsonUtil.objectToJson(ResourceResWrapper.failResult("Deletion failed with id="+webReqWrapper.getData(), webReqWrapper.getData(), RespCode._508));
    }
    
    @RequestMapping(value = "/unlockuser" ,method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String unlockuser(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse<?> result = null;
    	try {
    		LongIDDto idDto = (LongIDDto) JsonUtil.jsonToBean(webReqWrapper.getData(), LongIDDto.class);
			result = userResource.unlockUser(idDto, operator);
		} catch (Exception e) {
			logger.error(e);
			result = ResourceResWrapper.failResult(e.getMessage(), webReqWrapper.getData(), RespCode._508);
		}
    	if(result!=null) return JsonUtil.objectToJson(result);
    	else  return JsonUtil.objectToJson(ResourceResWrapper.failResult("Deletion failed with id="+webReqWrapper.getData(), webReqWrapper.getData(), RespCode._508));
    }
    
    @RequestMapping(value="/listnongroupuser",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String listnongroupuser(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse resResponse = nongrouplist(headerInfo,requestStr);
		return JsonUtil.objectToJsonDateSerializer(resResponse,Constants.DATE_FORMAT1);
    }
    
    private ResourceResponse nongrouplist(Map<String,String> headerInfo,String requestStr){
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	UserDto user = (UserDto) JsonUtil.jsonToBean(webReqWrapper.getData(), UserDto.class);
    	paginationTO.setRequestData(user);
    	user.setWebReqWrapper(webReqWrapper);
    	String errorMsg = null;

    	String orderBy = paginationTO.getOrderBy();
    	if(CommonUtil.isEmpty(orderBy)){
    		orderBy = "createDt desc";
    		paginationTO.setOrderBy(orderBy);
    	}
    	int totalAmount = 0;
    	int totalPageAmount = 0;
    	int currentPageNo = paginationTO.getCurrentPageNo();
		try {	
			ResourceResponse<Long> countResult = userResource.getAmountNotInGroup(ResourceReqWrapper.listRequest(null, user));
			if(!countResult.isSuccess()){
				return countResult;
			}
	
			paginationTO.setTotalAmount(countResult.getData().intValue());
			totalAmount = countResult.getData().intValue();
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
				
				ResourceResponse<List<UserDto>> listResult = userResource.getListNotInGroup(ResourceReqWrapper.listRequest(paginationTO, user));

				if(listResult.isSuccess()){
					paginationTO.setList(listResult.getData());
				}
				else{
					return listResult;
				}
			}

		} catch (Exception e) {
			logger.error(e);
			errorMsg = e.getMessage();
		}
		
		if(errorMsg!=null){
			return  ResourceResWrapper.failResult(errorMsg, paginationTO, RespCode._508);
		}
		return ResourceResWrapper.successResult(null, paginationTO);
    }

}
