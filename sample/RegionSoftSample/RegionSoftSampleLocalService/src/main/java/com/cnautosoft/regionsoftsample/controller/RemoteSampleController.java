package com.cnautosoft.regionsoftsample.controller;

import java.util.List;
import java.util.Map;
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
import com.cnautosoft.regionsoftsample.dto.RemoteSampleDto;
import com.cnautosoft.regionsoftsample.resource.RemoteSampleResource;

@Controller
@RequestMapping("/regionsoftsample/remotesample")
public class RemoteSampleController {
	public static Logger logger = Logger.getLogger(RemoteSampleController.class);
	
	/*@Autowired(remoteEndPoint="/com/cnautosoft/regionsoftsample/resources")
	private RemoteSampleResource remoteSampleResource;*/
	
	
	@RequestMapping(value = "/save" ,method ={RequestMethod.POST},responseHeader= {"Content-Type == application/json;charset=UTF-8"})
    public @ResponseBody String save(@HeaderInfo Map<String,String> headerInfo,	@Parameter(value = "requestStr") String requestStr) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse<?> result = null;
    	RemoteSampleDto remoteSampleDto = (RemoteSampleDto) JsonUtil.jsonToBean(webReqWrapper.getData(), RemoteSampleDto.class);
    	try {
    		remoteSampleDto.setWebReqWrapper(webReqWrapper);
    		if(remoteSampleDto.getId()==null||remoteSampleDto.getId()<1){
    			remoteSampleDto.setId(IDGenerator.getLongID());
    			result = remoteSampleResource.create(remoteSampleDto, operator);
    		}
    		else{
    			result = remoteSampleResource.update(remoteSampleDto, operator); 
    		}
    		
		} catch (Exception e) {
			logger.error(e);
			result = ResourceResWrapper.failResult(e.getMessage(), remoteSampleDto, RespCode._508);
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
			result = remoteSampleResource.getByID(idDto.getId());
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
			result = remoteSampleResource.deleteByID(idDto.getId(), operator);
		} catch (Exception e) {
			logger.error(e);
			result = ResourceResWrapper.failResult(e.getMessage(), webReqWrapper.getData(), RespCode._508);
		}
    	if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
		else return JsonUtil.objectToJson(result);
    }
	
	@RequestMapping(value="/list/delete",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String remoteSampleListDelete(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	String operator = AuthUtil.getCurrentUserAccount();
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	RemoteSampleDto remoteSample = (RemoteSampleDto) JsonUtil.jsonToBean(webReqWrapper.getData(), RemoteSampleDto.class);
    	paginationTO.setRequestData(remoteSample);
    	remoteSample.setWebReqWrapper(webReqWrapper);
    	String deleteId = paginationTO.getDeleteId();
    	String deleteIds = paginationTO.getDeleteIds();
    	ResourceResponse<?> result = null;
    	if(!CommonUtil.isEmpty(deleteId)){
    		result = remoteSampleResource.deleteByID(Long.parseLong(deleteId), operator);
    	}
    	else if(!CommonUtil.isEmpty(deleteIds)){
    		String[] idArray = deleteIds.split(";");
			result = remoteSampleResource.batchDelete(idArray,  operator);
    	}
    	if(!result.isSuccess()) return JsonUtil.objectToJson(result);
    	ResourceResponse resResponse = sublist(headerInfo,requestStr);
    	if(resResponse.isSuccess()) return JsonUtil.objectToJsonDateSerializer(resResponse, Constants.DATE_FORMAT1);
    	else return JsonUtil.objectToJson(resResponse);
 	}
	
	@RequestMapping(value="/list",method ={RequestMethod.POST},responseHeader = {"Content-Type == application/json;charset=UTF-8" })
    public @ResponseBody String remoteSampleList(@HeaderInfo Map<String,String> headerInfo,@Parameter(value = "requestStr") String requestStr) {
    	String operator = AuthUtil.getCurrentUserAccount();
    	ResourceResponse result = sublist(headerInfo,requestStr);
    	if(result.isSuccess()) return JsonUtil.objectToJsonDateSerializer(result, Constants.DATE_FORMAT1);
    	else return JsonUtil.objectToJson(result);
    }

    private ResourceResponse sublist( Map<String,String> headerInfo,String requestStr){
    	WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
    	PaginationTO paginationTO = (PaginationTO) JsonUtil.jsonToBean(webReqWrapper.getData(), PaginationTO.class);
    	RemoteSampleDto remoteSample = (RemoteSampleDto) JsonUtil.jsonToBean(webReqWrapper.getData(), RemoteSampleDto.class);
    	paginationTO.setRequestData(remoteSample);
    	remoteSample.setWebReqWrapper(webReqWrapper);
		String errorMsg = null;
		try {	
			ResourceResponse<PaginationTO> result = remoteSampleResource.getList(ResourceReqWrapper.listRequest(paginationTO, remoteSample));
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
