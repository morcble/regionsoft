package com.cnautosoft.h2o.admin.resource;

import java.util.List;

import com.cnautosoft.h2o.admin.dto.GroupDto;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;

public interface GroupResource {
	ResourceResponse<GroupDto> create(GroupDto dto, String operator);

	ResourceResponse<GroupDto> getByID(Long primaryKey);
 
	ResourceResponse<GroupDto> update(GroupDto groupTO, String operator);

	ResourceResponse<String[]> deleteByID(Long primaryKey, String operator);

	ResourceResponse<String[]> batchDelete(String[] deleteIds, String operator);

	ResourceResponse<List<GroupDto>> findAll();

	ResourceResponse<?> deleteAll(String operator);

	ResourceResponse<PaginationTO> getList(ResourceRequest<GroupDto> listRequest);

}
