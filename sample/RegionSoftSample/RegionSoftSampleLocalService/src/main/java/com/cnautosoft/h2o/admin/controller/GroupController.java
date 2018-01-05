package com.cnautosoft.h2o.admin.controller;

import java.util.List;
import java.util.Map;

import com.cnautosoft.h2o.admin.dto.GroupDto;
import com.cnautosoft.h2o.admin.resource.GroupResource;
import com.cnautosoft.h2o.admin.resource.impl.GroupResourceImpl;
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
import com.cnautosoft.h2o.core.SystemContext;
import com.cnautosoft.h2o.core.auth.AdvancedSecurityProvider;
import com.cnautosoft.h2o.core.auth.AuthUtil;
import com.cnautosoft.h2o.core.ids.IDGenerator;
import com.cnautosoft.h2o.enums.RequestMethod;
import com.cnautosoft.h2o.rpc.common.to.LongIDDto;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.core.RespCode;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;
import com.cnautosoft.h2o.web.wrapper.ResourceReqWrapper;
import com.cnautosoft.h2o.web.wrapper.ResourceResWrapper;
import com.cnautosoft.h2o.web.wrapper.WebReqWrapper; 

@Controller
@RequestMapping("/admin/group")
public class GroupController {
	public static Logger logger = Logger.getLogger(GroupController.class);
	
	@Autowired(targetClass = GroupResourceImpl.class)
	private GroupResource groupResource;
	
	
	@RequestMapping(value = "/save" ,method ={RequestMethod.POST},responseHeader= {"Content-Type == application/json;charset=UTF-8"})
    public @ResponseBody String save(@HeaderInfo Map<String,String> headerInfo,	@Parameter(value = "requestStr") String requestStr) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse<?> result = null;
    	GroupDto groupDto = (GroupDto) JsonUtil.jsonToBean(webReqWrapper.getData(), GroupDto.class);
    	try {
    		groupDto.setWebReqWrapper(webReqWrapper);
    		if(groupDto.getId()==null||Long.valueOf(groupDto.getId())<1){
    			groupDto.setId(IDGenerator.getLongID());
    			result = groupResource.create(groupDto, operator);
    			if(result.isSuccess()){
    				AdvancedSecurityProvider advancedSecurityProvider = SystemContext.getInstance().getAdvancedSecurityProvider();
    				if(advancedSecurityProvider!=null){
    					GroupDto tmp = (GroupDto) result.getData();
    					advancedSecurityProvider.updateGroup(new String[]{String.valueOf(tmp.getId())}, "add");
    				}
    			}
    		}
    		else{
    			result = groupResource.update(groupDto, operator); 
    		}
    		
		} catch (Exception e) {
			logger.error(e);
			result = ResourceResWrapper.failResult(e.getMessage(), groupDto, RespCode._508);
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
			result = groupResource.getByID(idDto.getId());
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
			result = groupResource.deleteByID(idDto.getId(), operator);
			
			if(result.isSuccess()){
				AdvancedSecurityProvider advancedSecurityProvider = SystemContext.getInstance().getAdvancedSecurityProvider();
				if(advancedSecurityProvider!=null){
					advancedSecurityProvider.updateGroup((String[])(result.getData()), "delete");
				}
			}
		} catch (Exception e) {
			logger.error(e);
			result = ResourceResWrapper.failResult(e.getMessage(), webReqWrapper.getData(), RespCode._508);
		}
    	if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
		else return JsonUtil.objectToJson(result);
    }
	
	@RequestMapping(value="/list/delete",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String groupListDelete(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	GroupDto group = (GroupDto) JsonUtil.jsonToBean(webReqWrapper.getData(), GroupDto.class);
    	paginationTO.setRequestData(group);
    	group.setWebReqWrapper(webReqWrapper);
    	String deleteId = paginationTO.getDeleteId();
    	String deleteIds = paginationTO.getDeleteIds();
    	ResourceResponse<?> result = null;
    	if(!CommonUtil.isEmpty(deleteId)){
    		result = groupResource.deleteByID(Long.parseLong(deleteId), operator);
    	}
    	else if(!CommonUtil.isEmpty(deleteIds)){
    		String[] idArray = deleteIds.split(";");
			result = groupResource.batchDelete(idArray,  operator);
    	}
    	if(!result.isSuccess()) return JsonUtil.objectToJson(result);
    	if(result.isSuccess()){
			AdvancedSecurityProvider advancedSecurityProvider = SystemContext.getInstance().getAdvancedSecurityProvider();
			if(advancedSecurityProvider!=null){
				advancedSecurityProvider.updateGroup((String[])(result.getData()), "delete");
			}
		}
    	
    	ResourceResponse resResponse = sublist(headerInfo,requestStr);
    	if(resResponse.isSuccess()) return JsonUtil.objectToJsonDateSerializer(resResponse, Constants.DATE_FORMAT1);
    	else return JsonUtil.objectToJson(resResponse);
 	}
	
	@RequestMapping(value="/list",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String groupList(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse result = sublist(headerInfo,requestStr);
    	if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
    	else return JsonUtil.objectToJson(result);
    }

     private ResourceResponse sublist( Map<String,String> headerInfo,String requestStr){
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	GroupDto group = (GroupDto) JsonUtil.jsonToBean(webReqWrapper.getData(), GroupDto.class);
    	paginationTO.setRequestData(group);
    	group.setWebReqWrapper(webReqWrapper);
		String errorMsg = null;
		try {	
			ResourceResponse<PaginationTO> result = groupResource.getList(ResourceReqWrapper.listRequest(paginationTO, group));
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

}
