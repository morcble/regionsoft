package com.cnautosoft.h2o.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cnautosoft.h2o.admin.dto.GroupToResMapDto;
import com.cnautosoft.h2o.admin.resource.GroupToResMapResource;
import com.cnautosoft.h2o.admin.resource.impl.GroupToResMapResourceImpl;
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
@RequestMapping("/admin/grouptoresmap")
public class GroupToResMapController {
	public static Logger logger = Logger.getLogger(GroupToResMapController.class);
	
	@Autowired(targetClass = GroupToResMapResourceImpl.class)
	private GroupToResMapResource groupToResMapResource;
	
	
	@RequestMapping(value = "/changegroupres" ,method ={RequestMethod.POST},responseHeader= {"Content-Type == application/json;charset=UTF-8"})
    public @ResponseBody String changeGroupRes(@HeaderInfo Map<String,String> headerInfo,	@Parameter(value = "requestStr") String requestStr) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	List<GroupToResMapDto> changeDtoLs = JsonUtil.jsonToList(webReqWrapper.getData(), GroupToResMapDto.class);
    	try {
    		GroupToResMapDto groupToResMapDto = changeDtoLs.get(0);
    		if(groupToResMapDto.getResource().equals("-1")){
    			changeDtoLs = new ArrayList<GroupToResMapDto>();
    		}
    		
    		String systemId = groupToResMapDto.getSystemId();
    		Long controllerBeanId = Long.valueOf(groupToResMapDto.getDetailIdStr());
    		Long groupId = groupToResMapDto.getGroupId();
    		ResourceResponse<?> result = groupToResMapResource.changeGroupRes(changeDtoLs,systemId,operator,controllerBeanId,groupId);
    		
    		AdvancedSecurityProvider advancedSecurityProvider = SystemContext.getInstance().getAdvancedSecurityProvider();
    		if(advancedSecurityProvider!=null)advancedSecurityProvider.updateGroupResourceByGroupId(String.valueOf(groupId));
    		
    		return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
		} catch (Exception e) {
			logger.error(e);
			return JsonUtil.objectToJson(ResourceResWrapper.failResult("changeGroupRes failed", changeDtoLs, RespCode._508));
		}
    }
	
	@RequestMapping(value="/list/delete",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String groupToResMapListDelete(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	GroupToResMapDto groupToResMap = (GroupToResMapDto) JsonUtil.jsonToBean(webReqWrapper.getData(), GroupToResMapDto.class);
    	paginationTO.setRequestData(groupToResMap);
    	groupToResMap.setWebReqWrapper(webReqWrapper);
    	String deleteId = paginationTO.getDeleteId();
    	String deleteIds = paginationTO.getDeleteIds();
    	ResourceResponse<?> result = null;
    	if(!CommonUtil.isEmpty(deleteId)){
    		result = groupToResMapResource.deleteByID(Long.parseLong(deleteId), operator);
    	}
    	else if(!CommonUtil.isEmpty(deleteIds)){
    		String[] idArray = deleteIds.split(";");
			result = groupToResMapResource.batchDelete(idArray,  operator);
    	}
		if(!result.isSuccess()) return JsonUtil.objectToJson(result);
		
    	AdvancedSecurityProvider advancedSecurityProvider = SystemContext.getInstance().getAdvancedSecurityProvider();
		if(advancedSecurityProvider!=null)advancedSecurityProvider.updateGroupResourceByGroupId(String.valueOf(groupToResMap.getGroupId()));
		
    	ResourceResponse resResponse = sublist(headerInfo,requestStr);
    	if(resResponse.isSuccess()) return JsonUtil.objectToJsonDateSerializer(resResponse, Constants.DATE_FORMAT1);
    	else return JsonUtil.objectToJson(resResponse);
 	}
	
	@RequestMapping(value="/list",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String groupToResMapList(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse result = sublist(headerInfo,requestStr);
    	if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
    	else return JsonUtil.objectToJson(result);
    }

    private ResourceResponse sublist( Map<String,String> headerInfo,String requestStr){
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	GroupToResMapDto groupToResMap = (GroupToResMapDto) JsonUtil.jsonToBean(webReqWrapper.getData(), GroupToResMapDto.class);
    	paginationTO.setRequestData(groupToResMap);
    	groupToResMap.setWebReqWrapper(webReqWrapper);
		String errorMsg = null;
		try {	
			ResourceResponse<PaginationTO> result = groupToResMapResource.getList(ResourceReqWrapper.listRequest(paginationTO, groupToResMap));
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
