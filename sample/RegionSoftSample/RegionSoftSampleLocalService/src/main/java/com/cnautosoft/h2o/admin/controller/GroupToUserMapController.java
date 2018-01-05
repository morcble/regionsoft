package com.cnautosoft.h2o.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cnautosoft.h2o.admin.dto.GroupToUserMapDto;
import com.cnautosoft.h2o.admin.resource.GroupToUserMapResource;
import com.cnautosoft.h2o.admin.resource.impl.GroupToUserMapResourceImpl;
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
@RequestMapping("/admin/grouptousermap")
public class GroupToUserMapController {
	public static Logger logger = Logger.getLogger(GroupToUserMapController.class);
	
	@Autowired(targetClass = GroupToUserMapResourceImpl.class)
	private GroupToUserMapResource groupToUserMapResource;
	
	
	@RequestMapping(value="/list/delete",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String groupToUserMapListDelete(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	GroupToUserMapDto groupToUserMap = (GroupToUserMapDto) JsonUtil.jsonToBean(webReqWrapper.getData(), GroupToUserMapDto.class);
    	paginationTO.setRequestData(groupToUserMap);
    	groupToUserMap.setWebReqWrapper(webReqWrapper);
    	String deleteId = paginationTO.getDeleteId();
    	String deleteIds = paginationTO.getDeleteIds();
    	ResourceResponse<?> result = null;
    	if(!CommonUtil.isEmpty(deleteId)){
    		AdvancedSecurityProvider advancedSecurityProvider = SystemContext.getInstance().getAdvancedSecurityProvider();
    		List<Long> ls = new ArrayList<Long>();
    		ls.add(Long.valueOf(deleteId));
    		if(advancedSecurityProvider!=null)advancedSecurityProvider.updateGroupUserByMapIds(ls);
    			
    		result = groupToUserMapResource.deleteByID(Long.parseLong(deleteId), operator);
    	}
    	else if(!CommonUtil.isEmpty(deleteIds)){
    		String[] idArray = deleteIds.split(";");
    			
    		AdvancedSecurityProvider advancedSecurityProvider = SystemContext.getInstance().getAdvancedSecurityProvider();
    		List<Long> ls = new ArrayList<Long>();
    		for(String tmp:idArray){
    			ls.add(Long.valueOf(tmp));
    		}
    		if(advancedSecurityProvider!=null)advancedSecurityProvider.updateGroupUserByMapIds(ls);
    			
    		result = groupToUserMapResource.batchDelete(idArray,  operator);
    	}
    	if(!result.isSuccess()) return JsonUtil.objectToJson(result);
    	ResourceResponse resResponse = sublist(headerInfo,requestStr);
    	if(resResponse.isSuccess()) return JsonUtil.objectToJsonDateSerializer(resResponse, Constants.DATE_FORMAT1);
    	else return JsonUtil.objectToJson(resResponse);
 	}
	
	@RequestMapping(value = "/adduserstogroup" ,method ={RequestMethod.POST},responseHeader= {"Content-Type == application/json;charset=UTF-8"})
    public @ResponseBody String adduserstogroup(@HeaderInfo Map<String,String> headerInfo,	@Parameter(value = "requestStr") String requestStr) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	Map<String,String> paraMap = JsonUtil.jsonToMap(webReqWrapper.getData());
    	String chosenIds = paraMap.get("chosenIds");
    	String systemId = paraMap.get("systemId");
    	String groupId = paraMap.get("groupId");
    	ResourceResponse<?> result = null;
    	GroupToUserMapDto groupToUserMapDto = (GroupToUserMapDto) JsonUtil.jsonToBean(webReqWrapper.getData(), GroupToUserMapDto.class);
    	try {
    		groupToUserMapDto.setWebReqWrapper(webReqWrapper);
    		String[] idArray = chosenIds.split(";");
    		result = groupToUserMapResource.addUsersToGroup(idArray,systemId,groupId, operator); 
    		AdvancedSecurityProvider advancedSecurityProvider = SystemContext.getInstance().getAdvancedSecurityProvider();
    		if(advancedSecurityProvider!=null)advancedSecurityProvider.updateGroupUserByMapIds((List<Long>)result.getData());
    		
		} catch (Exception e) {
			logger.error(e);
			result = ResourceResWrapper.failResult(e.getMessage(), groupToUserMapDto, RespCode._508);
		}
    	if(result!=null) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
    	else  return JsonUtil.objectToJson(ResourceResWrapper.failResult("Save data failed,"+groupToUserMapDto, groupToUserMapDto, RespCode._508));
    }
	
	@RequestMapping(value="/list",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String groupToUserMapList(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse result = sublist(headerInfo,requestStr);
    	if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
    	else return JsonUtil.objectToJson(result);
    }

    private ResourceResponse sublist( Map<String,String> headerInfo,String requestStr){
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	GroupToUserMapDto groupToUserMap = (GroupToUserMapDto) JsonUtil.jsonToBean(webReqWrapper.getData(), GroupToUserMapDto.class);
    	paginationTO.setRequestData(groupToUserMap);
    	groupToUserMap.setWebReqWrapper(webReqWrapper);
		String errorMsg = null;
		try {	
			ResourceResponse<PaginationTO> result = groupToUserMapResource.getList(ResourceReqWrapper.listRequest(paginationTO, groupToUserMap));
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
