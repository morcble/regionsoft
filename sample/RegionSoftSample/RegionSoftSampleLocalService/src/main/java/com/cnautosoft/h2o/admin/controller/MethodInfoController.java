package com.cnautosoft.h2o.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cnautosoft.h2o.admin.dto.MethodInfoDto;
import com.cnautosoft.h2o.admin.resource.MethodInfoResource;
import com.cnautosoft.h2o.admin.resource.impl.MethodInfoResourceImpl;
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
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.core.RespCode;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;
import com.cnautosoft.h2o.web.wrapper.ResourceReqWrapper;
import com.cnautosoft.h2o.web.wrapper.ResourceResWrapper;
import com.cnautosoft.h2o.web.wrapper.WebReqWrapper; 

@Controller
@RequestMapping("/admin/methodinfo")
public class MethodInfoController {
	public static Logger logger = Logger.getLogger(MethodInfoController.class);
	
	@Autowired(targetClass = MethodInfoResourceImpl.class)
	private MethodInfoResource methodInfoResource;
	
	@RequestMapping(value="/list",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String methodInfoList(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse result = sublist(headerInfo,requestStr);
    	if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
    	else return JsonUtil.objectToJson(result);
    }

    private ResourceResponse sublist( Map<String,String> headerInfo,String requestStr){
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	MethodInfoDto methodInfo = (MethodInfoDto) JsonUtil.jsonToBean(webReqWrapper.getData(), MethodInfoDto.class);
    	paginationTO.setRequestData(methodInfo);
    	methodInfo.setWebReqWrapper(webReqWrapper);
		String errorMsg = null;
		try {	
			ResourceResponse<PaginationTO> result = methodInfoResource.getList(ResourceReqWrapper.listRequest(paginationTO, methodInfo));
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
    
    @RequestMapping(value="/configlist",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String configlist(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse resResponse = subConfiglist(headerInfo,requestStr);
		return JsonUtil.objectToJsonDateSerializer(resResponse,Constants.DATE_FORMAT1);
    }
    
    private ResourceResponse subConfiglist( Map<String,String> headerInfo,String requestStr){
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	MethodInfoDto methodInfo = (MethodInfoDto) JsonUtil.jsonToBean(webReqWrapper.getData(), MethodInfoDto.class);
    	paginationTO.setRequestData(methodInfo);
    	methodInfo.setWebReqWrapper(webReqWrapper);
    	String errorMsg = null;

    	String orderBy = paginationTO.getOrderBy();
    	if(CommonUtil.isEmpty(orderBy)){
    		orderBy = "createDt desc";
    		paginationTO.setOrderBy(orderBy);
    	}
		try {	
			paginationTO.setPageSize(200);
			paginationTO.setCurrentPageNo(1);
			ResourceResponse<List<MethodInfoDto>> listResult = methodInfoResource.getConfigtList(ResourceReqWrapper.listRequest(paginationTO, methodInfo));
			
	
			if(listResult.isSuccess()){
				paginationTO.setList(listResult.getData());
				paginationTO.setTotalPageAmount(1);
				paginationTO.setTotalAmount(paginationTO.getList().size());
			}
			else{
				return listResult;
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
