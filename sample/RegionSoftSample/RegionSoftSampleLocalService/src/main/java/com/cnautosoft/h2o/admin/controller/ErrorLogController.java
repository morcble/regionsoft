package com.cnautosoft.h2o.admin.controller;

import java.util.List;
import java.util.Map;
import com.cnautosoft.h2o.admin.dto.ErrorLogDto;
import com.cnautosoft.h2o.admin.resource.ErrorLogResource;
import com.cnautosoft.h2o.admin.resource.impl.ErrorLogResourceImpl;
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
@RequestMapping("/admin/errorlog")
public class ErrorLogController {
	public static Logger logger = Logger.getLogger(ErrorLogController.class);
	
	@Autowired(targetClass = ErrorLogResourceImpl.class)
	private ErrorLogResource errorLogResource;

	@RequestMapping(value = "/get", method = { RequestMethod.POST }, responseHeader = {"Content-Type == application/json;charset=UTF-8" })
	public @ResponseBody String get(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
		ResourceResponse<?> result = null;
		try {
			LongIDDto idDto = (LongIDDto) JsonUtil.jsonToBean(webReqWrapper.getData(), LongIDDto.class);
			result = errorLogResource.getByID(idDto.getId());
		} catch (Exception e) {
			logger.error(e);
			result = ResourceResWrapper.failResult(e.getMessage(), webReqWrapper.getData(), RespCode._508);
		}
		
		if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
		else return JsonUtil.objectToJson(result);
	}
	
	@RequestMapping(value = "/reportFrontendError", method = { RequestMethod.POST }, responseHeader = {"Content-Type == application/json;charset=UTF-8" })
	public @ResponseBody String reportFrontendError(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
		String json = webReqWrapper.getData();
		String operator = AuthUtil.getCurrentUserAccount();
		Map map = JsonUtil.jsonToMap(webReqWrapper.getData());
    	try {
    		ErrorLogDto errorLogDto = new ErrorLogDto();
    		errorLogDto.setId(IDGenerator.getLongID());
    		errorLogDto.setSystemId("LOCAL");
    		errorLogDto.setRequestId((String) map.get("requestId"));
    		errorLogDto.setErrorType("FrontError");
    		errorLogDto.setDetail(json);
    		errorLogDto.setWebReqWrapper(webReqWrapper);
			errorLogResource.create(errorLogDto, operator);
    	} catch (Exception e) {
			logger.error(e);
		}
    	return JsonUtil.objectToJson(ResourceResWrapper.successResult(null, null));
	}
	
	@RequestMapping(value="/list",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String errorLogList(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse result = sublist(headerInfo,requestStr);
    	if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
    	else return JsonUtil.objectToJson(result);
    }

    private ResourceResponse sublist( Map<String,String> headerInfo,String requestStr){
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	ErrorLogDto errorLog = (ErrorLogDto) JsonUtil.jsonToBean(webReqWrapper.getData(), ErrorLogDto.class);
    	paginationTO.setRequestData(errorLog);
    	errorLog.setWebReqWrapper(webReqWrapper);
		String errorMsg = null;
		try {	
			ResourceResponse<PaginationTO> result = errorLogResource.getList(ResourceReqWrapper.listRequest(paginationTO, errorLog));
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
