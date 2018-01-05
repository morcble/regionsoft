package com.cnautosoft.h2o.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cnautosoft.h2o.admin.dto.ContextTypeDto;
import com.cnautosoft.h2o.admin.dto.GroupToResMapDto;
import com.cnautosoft.h2o.admin.dto.ManagedBeanDetailDto;
import com.cnautosoft.h2o.admin.resource.ManagedBeanDetailResource;
import com.cnautosoft.h2o.admin.resource.impl.ManagedBeanDetailResourceImpl;
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
import com.cnautosoft.h2o.core.H2OContext;
import com.cnautosoft.h2o.core.SystemContext;
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
@RequestMapping("/admin/managedbeandetail")
public class ManagedBeanDetailController {
	public static Logger logger = Logger.getLogger(ManagedBeanDetailController.class);
	
	@Autowired(targetClass = ManagedBeanDetailResourceImpl.class)
	private ManagedBeanDetailResource managedBeanDetailResource;
	
	
	@RequestMapping(value = "/scanmanagedbeans" ,method ={RequestMethod.POST},responseHeader= {"Content-Type == application/json;charset=UTF-8"})
    public @ResponseBody String scanManagedBeans(@HeaderInfo Map<String,String> headerInfo,	@Parameter(value = "requestStr") String requestStr) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	ManagedBeanDetailDto managedBeanDetail = (ManagedBeanDetailDto) JsonUtil.jsonToBean(webReqWrapper.getData(), ManagedBeanDetailDto.class);
    	try {
    		ResourceResponse<?> result = managedBeanDetailResource.scanManagedBeans(managedBeanDetail.getSystemId());
    		return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
		} catch (Exception e) {
			logger.error(e);
			return JsonUtil.objectToJson(ResourceResWrapper.failResult("scanManagedBeans failed", managedBeanDetail.getSystemId(), RespCode._508));
		}
    }
	
	@RequestMapping(value="/list",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String managedBeanDetailList(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse result = sublist(headerInfo,requestStr);
    	if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
    	else return JsonUtil.objectToJson(result);
    }

    private ResourceResponse sublist( Map<String,String> headerInfo,String requestStr){
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	ManagedBeanDetailDto managedBeanDetail = (ManagedBeanDetailDto) JsonUtil.jsonToBean(webReqWrapper.getData(), ManagedBeanDetailDto.class);
    	paginationTO.setRequestData(managedBeanDetail);
    	managedBeanDetail.setWebReqWrapper(webReqWrapper);
		String errorMsg = null;
		try {	
			ResourceResponse<PaginationTO> result = managedBeanDetailResource.getList(ResourceReqWrapper.listRequest(paginationTO, managedBeanDetail));
			if(!result.isSuccess())return result;
			paginationTO = result.getData();
		} catch (Exception e) {
			logger.error(e);
			errorMsg = e.getMessage();
		}
		
		if(errorMsg!=null){
			return  ResourceResWrapper.failResult(errorMsg, paginationTO, RespCode._508);
		}
		
		Map<String, H2OContext> contextMap = SystemContext.getInstance().getContextsMap();
    	List<ContextTypeDto> contextLs = new ArrayList<ContextTypeDto>();
    	for(String key:contextMap.keySet()){
    		ContextTypeDto contextTypeDto = new ContextTypeDto();
    		contextTypeDto.setVal(key);
    		contextTypeDto.setDescription(key);
    		contextLs.add(contextTypeDto);
    	}
    	paginationTO.setAttachedObj(contextLs);
		return ResourceResWrapper.successResult(null, paginationTO);
    }

}
