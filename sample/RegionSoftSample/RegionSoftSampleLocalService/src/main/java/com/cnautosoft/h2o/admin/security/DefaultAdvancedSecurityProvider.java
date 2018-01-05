package com.cnautosoft.h2o.admin.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cnautosoft.h2o.admin.dto.GroupDto;
import com.cnautosoft.h2o.admin.dto.GroupToResMapDto;
import com.cnautosoft.h2o.admin.dto.GroupToUserMapDto;
import com.cnautosoft.h2o.admin.entity.Group;
import com.cnautosoft.h2o.admin.entity.GroupToResMap;
import com.cnautosoft.h2o.admin.entity.GroupToUserMap;
import com.cnautosoft.h2o.admin.service.GroupService;
import com.cnautosoft.h2o.admin.service.GroupToResMapService;
import com.cnautosoft.h2o.admin.service.GroupToUserMapService;
import com.cnautosoft.h2o.admin.service.impl.GroupServiceImpl;
import com.cnautosoft.h2o.admin.service.impl.GroupToResMapServiceImpl;
import com.cnautosoft.h2o.admin.service.impl.GroupToUserMapServiceImpl;
import com.cnautosoft.h2o.annotation.Component;
import com.cnautosoft.h2o.annotation.tag.AfterAutowired;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.common.TreeUtil;
import com.cnautosoft.h2o.core.CommonUtil;
import com.cnautosoft.h2o.core.auth.AuthDataStorage;
import com.cnautosoft.h2o.core.auth.AdvancedSecurityProvider;
import com.cnautosoft.h2o.enums.UserToDoAction;

@Component
public class DefaultAdvancedSecurityProvider implements AdvancedSecurityProvider{
	private static final Logger logger = Logger.getLogger(DefaultAdvancedSecurityProvider.class);
	
	@Autowired(targetClass = SimpleAuthDataStorage.class)
	private AuthDataStorage authDataStorage;
	
	@Autowired(targetClass = GroupServiceImpl.class)
	private GroupService groupService;
	
	@Autowired(targetClass = GroupToUserMapServiceImpl.class)
	private GroupToUserMapService groupToUserMapService;
	
	@Autowired(targetClass = GroupToResMapServiceImpl.class)
	private GroupToResMapService groupToResMapService;
	
	private Map<String,List<GroupToUserMapDto>> accountToGroupMapCache = new ConcurrentHashMap<String,List<GroupToUserMapDto>>();
	
	private Map<Long,GroupDto> groupMap = null;
	
	/*
	 * called when edit group resource
	 * @param groupId
	 * 增减URL
	 */
	@Override
	public void updateGroupResourceByGroupId(String groupId){
		try{
			List<GroupToResMap> res = groupToResMapService.getListByGroupId(groupId);
			GroupDto groupDto = groupMap.get(Long.valueOf(groupId));
			Set<String> resLs = groupDto.getResLs();
			resLs.clear();
			for(GroupToResMap groupToResMap:res){
				resLs.add(groupToResMap.getResource());
			}
		}
		catch(Exception e){
			logger.error(e);
		}
	}
	
	/**
	 * called when change user group
	 * @param userId
	 * 增减组员
	 */
	@Override
	public void updateGroupUserByMapIds(List<Long> mapIds){
		try{
			List<String> accountLs = groupToUserMapService.getAccountLsByUserMapIds(mapIds);
			for(String account:accountLs){
				accountToGroupMapCache.remove(account);
			}
		}
		catch(Exception e){
			logger.error(e);
		}
	}
	
	/**
	 * 增减组
	 * @param groupId
	 */
	@Override
	public void updateGroup(String[] groupIds,String flag){//add , delete
		try {
			List<Group> groups = groupService.getGroupsByIds(groupIds);
			List<GroupDto> groupDtos = CommonUtil.copyListProperties(groups,GroupDto.class);
			if(flag.equals("add")){
				for(GroupDto tmp:groupDtos){
					if(tmp.getParentGroupId()!=null){
						GroupDto parentGroup = groupMap.get(tmp.getParentGroupId());
						if(parentGroup!=null){
							List<GroupDto> childs = parentGroup.getChilds();
							if(childs==null){
								childs = new ArrayList<GroupDto>();
								parentGroup.setChilds(childs);
							}
							childs.add(tmp);
							tmp.setParentNode(parentGroup);
						}
					}
					groupMap.put(tmp.getId(), tmp);
				}
			}
			else{
				for(GroupDto tmp:groupDtos){
					removeGroup(groupMap.get(tmp.getId()));
				}
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	private void removeGroup(GroupDto currentGroup){
		GroupDto parentGroup = currentGroup.getParentNode();
		if(parentGroup!=null){
			List<GroupDto> childs = parentGroup.getChilds();
			int index = 0;
			for(GroupDto groupDto:childs){
				if(groupDto.getId().equals(currentGroup.getId())){
					childs.remove(index);
					break;
				}
				index++;
			}
		}
		groupMap.remove(currentGroup.getId());
		
		List<GroupDto> childs = currentGroup.getChilds();
		if(childs!=null){
			for(GroupDto child:childs){
				removeGroup(child);
			}
		}
	}
	
	@AfterAutowired
	public void init() {
		groupMap  = getGroupWithResource();
		
		//update public resources
		for(Entry<Long,GroupDto> entry :groupMap.entrySet()){
			GroupDto tmpDto = entry.getValue();
			if(tmpDto.getGroupNm().equals("public")){
				Set<String> resLs = tmpDto.getResLs();
				authDataStorage.getPublicRes().addAll(resLs);
				break;
			}
		}
	}
	
	/**
	 * get name of groups set owned by this account
	 * @param account
	 * @return
	 */
	@Override
	public Set<String> getAccessableGroupsByAccount(String account){
		Set<String> groupNameSet = new HashSet<String>();
		List<GroupToUserMapDto> groupToUserMapDtoLs = getGroupsByAccount(account);
		for(GroupToUserMapDto groupToUserMapDto:groupToUserMapDtoLs){
			GroupDto groupDto = groupMap.get(groupToUserMapDto.getGroupId());
			groupNameSet.add(groupDto.getGroupNm());
		}
		return groupNameSet;
	}
	
	@Override
	public UserToDoAction checkAccess(String account, String reqURI, HttpServletRequest request,HttpServletResponse response) {
		if(isPublicRes(reqURI)){
			return UserToDoAction.VALID_ACCESS;
		}
		Set<String> alowSet = getAccessableResourcesByAccount(account);
		if(alowSet.contains(reqURI)){
			return UserToDoAction.VALID_ACCESS;
		}
		else{
			return UserToDoAction.ACCESS_FORBIDDEN;
		}
	}
//----------------------------------------------------------------------------------------------------------	
	private boolean isPublicRes(String uri){	
		return authDataStorage.getPublicRes().contains(uri);
	}
	
	/*
	 * get resource set owned by this account
	 */
	private Set<String> getAccessableResourcesByAccount(String account){
		Set<String> resourceSet = new HashSet<String>();
		List<GroupToUserMapDto> groupToUserMapDtoLs = getGroupsByAccount(account);
		for(GroupToUserMapDto groupToUserMapDto:groupToUserMapDtoLs){
			GroupDto groupDto = groupMap.get(groupToUserMapDto.getGroupId());
			if(groupDto==null)continue;
			resolveResource(groupDto,resourceSet);
		}
		return resourceSet;
	}
	
	private void resolveResource(GroupDto groupDto,Set<String> resourceSet){
		resourceSet.addAll(groupDto.getResLs());
		GroupDto parentGroupDto= groupDto.getParentNode();
		if(parentGroupDto!=null){
			resolveResource(parentGroupDto,resourceSet);
		}
	}
	
	
	/**
	 * @param account
	 * @return
	 */
	private List<GroupToUserMapDto> getGroupsByAccount(String account){
		try{
			List<GroupToUserMapDto> groupToUserMapDtoLs = accountToGroupMapCache.get(account);
			if(groupToUserMapDtoLs==null){
				List<GroupToUserMap> groupToUserMapLs= groupToUserMapService.getGroupsByAccount(account);
				groupToUserMapDtoLs = CommonUtil.copyListProperties(groupToUserMapLs,GroupToUserMapDto.class);
				accountToGroupMapCache.put(account, groupToUserMapDtoLs);
			}
			 
			return groupToUserMapDtoLs;
		}
		catch(Exception e){
			logger.error(e, "getGroupsByAccount");
			return new ArrayList<GroupToUserMapDto>();
		}
	}
	
	/*
	 * groupid - treegroup
	 */

	private Map<Long,GroupDto> getGroupWithResource() {
		try{
			List<GroupToResMapDto> groupToResMapDtoLs= CommonUtil.copyListProperties(groupToResMapService.findAll(),GroupToResMapDto.class);
			
			List<GroupDto> groupDtos = CommonUtil.copyListProperties(groupService.findAll(),GroupDto.class);
			Map<Long,GroupDto> treeGroupMap = TreeUtil.resolvePlainLsAsTreeMap(groupDtos,"id","parentGroupId","childs","parentNode");
			
			for(GroupToResMapDto groupToResMapDto:groupToResMapDtoLs){
				GroupDto groupDto = treeGroupMap.get(groupToResMapDto.getGroupId());
				if(groupDto!=null)groupDto.addResource(groupToResMapDto.getResource());
			}
			
			
			Map<Long,GroupDto> result = new ConcurrentHashMap<Long,GroupDto>();
			result.putAll(treeGroupMap);
			treeGroupMap.clear();
			return result;
		}
		catch(Exception e){
			logger.error(e, "getGroupWithResource");
			return null;
		}
	}

}
