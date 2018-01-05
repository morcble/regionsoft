package com.cnautosoft.h2o.admin.resource;

import java.util.List;

import com.cnautosoft.h2o.admin.dto.GroupToUserMapDto;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;

public interface GroupToUserMapResource {
	ResourceResponse<GroupToUserMapDto> create(GroupToUserMapDto dto, String operator);

	ResourceResponse<GroupToUserMapDto> getByID(Long primaryKey);
 
	ResourceResponse<GroupToUserMapDto> update(GroupToUserMapDto groupToUserMapTO, String operator);

	ResourceResponse<?> deleteByID(Long primaryKey, String operator);

	ResourceResponse<?> batchDelete(String[] deleteIds, String operator);

	ResourceResponse<List<GroupToUserMapDto>> findAll();

	ResourceResponse<?> deleteAll(String operator);

	ResourceResponse<PaginationTO> getList(ResourceRequest<GroupToUserMapDto> listRequest);

	ResourceResponse<List<Long>> addUsersToGroup(String[] chosenIds,String systemId, String groupId,String operator);

}
