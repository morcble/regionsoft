package com.cnautosoft.h2o.admin.security;

import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cnautosoft.h2o.admin.dto.UserDto;
import com.cnautosoft.h2o.admin.resource.UserResource;
import com.cnautosoft.h2o.admin.resource.impl.UserResourceImpl;
import com.cnautosoft.h2o.annotation.Controller;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.annotation.tag.HeaderInfo;
import com.cnautosoft.h2o.annotation.tag.Parameter;
import com.cnautosoft.h2o.annotation.tag.RequestMapping;
import com.cnautosoft.h2o.annotation.tag.ResponseBody;
import com.cnautosoft.h2o.common.JsonUtil;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.common.MD5Util;
import com.cnautosoft.h2o.core.CommonUtil;
import com.cnautosoft.h2o.core.auth.AuthDataStorage;
import com.cnautosoft.h2o.core.auth.AuthUtil;
import com.cnautosoft.h2o.core.auth.LoginHelper;
import com.cnautosoft.h2o.core.auth.LoginResponseType;
import com.cnautosoft.h2o.core.auth.LogoutResponseType;
import com.cnautosoft.h2o.core.auth.dto.LoginDto;
import com.cnautosoft.h2o.core.auth.dto.LogoutDto;
import com.cnautosoft.h2o.core.ids.IDGenerator;
import com.cnautosoft.h2o.enums.RequestMethod;
import com.cnautosoft.h2o.rpc.common.ServerConstant;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.core.RespCode;
import com.cnautosoft.h2o.web.wrapper.ResourceResWrapper;
import com.cnautosoft.h2o.web.wrapper.WebReqWrapper;

@Controller
@RequestMapping
public class LoginController {
	public static Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired(targetClass = SimpleAuthDataStorage.class)
	private AuthDataStorage authDataStorage;
	
	@Autowired(targetClass = UserResourceImpl.class)
	private UserResource userResource;
	
	public LoginController(){

	}
	
	@RequestMapping(value = "/login" ,method ={RequestMethod.POST},responseHeader= {"Content-Type == application/json;charset=UTF-8"})
    public @ResponseBody String login(@HeaderInfo Map<String,String> headerInfo,	@Parameter(value = "requestStr") String requestStr,HttpServletRequest request, HttpServletResponse response) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
		Map<String,String> loginMap = JsonUtil.jsonToMap(webReqWrapper.getData());
		
		LoginDto loginDto = new LoginDto();
		if(!validRefererForLogin(request)){
			loginDto.setLoginResponseType(LoginResponseType.INVALID_REQUEST);
		}
		else{
			String account = loginMap.get("account");
			String password = loginMap.get("password");
			
			loginDto = authDataStorage.login(account, MD5Util.encode(password),request,response);
		}
		return JsonUtil.objectToJson(loginDto);
    }
	
	@RequestMapping(value = "/logout" ,method ={RequestMethod.POST},responseHeader= {"Content-Type == application/json;charset=UTF-8"})
    public @ResponseBody String logout(@HeaderInfo Map<String,String> headerInfo,	@Parameter(value = "requestStr") String requestStr,HttpServletRequest request, HttpServletResponse response) {
		LogoutDto logoutDto = new LogoutDto();
		if(!validRefererForLogout(request)){
			logoutDto.setLogoutResponseType(LogoutResponseType.INVALID_REQUEST);
		}
		else{
			String loginToken = LoginHelper.getLoginToken(request);
			if(CommonUtil.isEmpty(loginToken)){
				logoutDto.setLogoutResponseType(LogoutResponseType.TOKEN_EMPTY);
			}
			else{
				LogoutResponseType logoutResponseType = authDataStorage.logout(loginToken,request,response);
				logoutDto.setLogoutResponseType(logoutResponseType);
				if(LogoutResponseType.LOGOUT_SUCCESSFULLY == logoutResponseType){
					logoutDto.setSuccess(true);
				}
			}
		}
		return JsonUtil.objectToJson(logoutDto);
    }
	
	@RequestMapping(value = "/register" ,method ={RequestMethod.POST},responseHeader= {"Content-Type == application/json;charset=UTF-8"})
    public @ResponseBody String register(@HeaderInfo Map<String,String> headerInfo,	@Parameter(value = "requestStr") String requestStr,HttpServletRequest request, HttpServletResponse response) {
		WebReqWrapper webReqWrapper = CommonUtil.resolveWebReqWrapper(requestStr);
		Map<String,String> loginMap = JsonUtil.jsonToMap(webReqWrapper.getData());
		
		LoginDto loginDto = new LoginDto();
		ResourceResponse<?> result = null;
		if(!validRefererForLogin(request)){
			loginDto.setLoginResponseType(LoginResponseType.INVALID_REQUEST);
			result = ResourceResWrapper.failResult("错误请求", loginDto, RespCode._508);
		}
		else{
			String account = loginMap.get("account");
			String password = loginMap.get("password");
			String verifycode = loginMap.get("verifycode");
			
			
			try {
				result = register(account, password,request,response);
			} catch (Exception e) {
				logger.error(e);
				result = ResourceResWrapper.failResult(e.getMessage(), loginDto, RespCode._508);
			}
		}
		return JsonUtil.objectToJson(result);
	}
	private boolean validRefererForLogin(HttpServletRequest request) {
		return true;
	}

	private boolean validRefererForLogout(HttpServletRequest request) {
		// TODO
		return true;
	}

	public ResourceResponse<?> register(String account, String password, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String operator = AuthUtil.getCurrentUserAccount();
		
		UserDto dto = new UserDto();
		dto.setAccount(account);
		dto.setPwd(password);
		dto.setStatus(0);
		dto.setId(IDGenerator.getLongID());
		ResourceResponse<?> result = userResource.create(dto, operator);
		return result;
	}
	
}
