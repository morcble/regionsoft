package com.cnautosoft.h2o.admin.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cnautosoft.h2o.core.auth.SecurityFilter;
import com.cnautosoft.h2o.core.auth.SecurityFilterResult;
import com.cnautosoft.h2o.enums.UserToDoAction;

public class SecurityFilterImpl implements SecurityFilter{
	@Override
	public SecurityFilterResult checkAccess(String requestURI, String method, Map<String, String[]> requestMap,Map<String,String> headerInfo,HttpServletRequest request, HttpServletResponse response){
		String account = null;
		String token = null;
		return new SecurityFilterResult(UserToDoAction.VALID_ACCESS,account,token);
	}
}
