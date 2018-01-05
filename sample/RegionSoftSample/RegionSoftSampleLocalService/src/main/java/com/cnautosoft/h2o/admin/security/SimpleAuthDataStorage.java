package com.cnautosoft.h2o.admin.security;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cnautosoft.h2o.admin.dto.UserDto;
import com.cnautosoft.h2o.admin.entity.User;
import com.cnautosoft.h2o.admin.service.UserService;
import com.cnautosoft.h2o.admin.service.impl.UserServiceImpl;
import com.cnautosoft.h2o.annotation.Component;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CommonUtil;
import com.cnautosoft.h2o.core.auth.LogoutResponseType;
import com.cnautosoft.h2o.core.auth.dto.LoginDto;
import com.cnautosoft.h2o.core.auth.dto.UserInfoDto;
import com.cnautosoft.h2o.core.auth.impl.BasicSecurityFactory;
import com.cnautosoft.h2o.core.auth.impl.DefaultBasicSecurityProvider;
import com.cnautosoft.h2o.core.auth.AuthDataStorage;
import com.cnautosoft.h2o.core.auth.BasicSecurity;

@Component
public class SimpleAuthDataStorage implements AuthDataStorage{
	private static final Logger logger = Logger.getLogger(SimpleAuthDataStorage.class);
	/**
	 * user info in memory
	 */
	private Map<String,String> userIdToAccountMap = new ConcurrentHashMap<String,String>();
	private Map<String,String> userAccountToIdMap = new ConcurrentHashMap<String,String>();

	/**
	 * define public resource url
	 */
	private Set<String> publicRes = new HashSet<String>();
	
	@Autowired(targetClass = UserServiceImpl.class)
	private UserService userService;
	
	private BasicSecurity basicSecurity;
	
	public SimpleAuthDataStorage(){
		publicRes.add("/login");
		publicRes.add("/logout");
		publicRes.add("/errorlog/reportFrontendError");
		publicRes.add("/combinedreq/resolvcombinedrequests");
		
		basicSecurity = BasicSecurityFactory.getBasicSecurityImpl();
		if(basicSecurity!=null)
			basicSecurity.setAuthDataStorage(this);
	}
//-----------------------------

	@Override
	public UserInfoDto getUserInfoByAccount(String account) {
		try{
			User user = userService.getUserByAccount(account);
			if(user==null)return null;
			if(user.getSoftDelete()==1){//deleted user
				return null;
			}
			if(user.getStatus()==1){//locked user
				return null;
			}
			UserInfoDto result = new UserInfoDto();
			result.setAccount(user.getAccount());
			result.setPwd(user.getPwd());
			result.setStatus(user.getStatus());
			return result;
		} 
		catch(Exception e){
			logger.error(e, "getUserByAccount");
			return null;
		}
	}

	@Override
	public String getUserAccountByLoginId(String loginId) {
		try{
			return userIdToAccountMap.get(loginId);
		}
		catch(Exception e){
			logger.error(e, "register");
			return null;
		}
	}
	
	@Override
	public Boolean saveLoginStatus(String account, String loginId) {
		try{
			userIdToAccountMap.put(loginId,account);
			userAccountToIdMap.put(account, loginId);

			return true;
		}
		catch(Exception e){
			logger.error(e, "saveLoginStatus");
			return false;
		}
	}
	
	@Override
	public Boolean removeLoginStatus(String loginId) {
		try{
			String account = userIdToAccountMap.remove(loginId);
			if(account!=null)userIdToAccountMap.remove(account);
			return true;
		}
		catch(Exception e){
			logger.error(e, "deregister");
			return false;
		}
	}
	
	@Override
	public LoginDto login(String account, String password, HttpServletRequest request, HttpServletResponse response) {
		return basicSecurity.login(account, password, request, response);
	}

	@Override
	public LogoutResponseType logout(String loginToken, HttpServletRequest request, HttpServletResponse response) {
		return basicSecurity.logout(loginToken, request, response);
	}
	
	@Override
	public Set<String> getPublicRes() {
		return publicRes;
	}
}
