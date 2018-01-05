package com.cnautosoft.h2o.admin.resource;

import java.util.List;

import com.cnautosoft.h2o.admin.dto.GroupToResMapDto;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;

public interface GroupToResMapResource {
	ResourceResponse<GroupToResMapDto> create(GroupToResMapDto dto, String operator);

	ResourceResponse<GroupToResMapDto> getByID(Long primaryKey);
 
	ResourceResponse<GroupToResMapDto> update(GroupToResMapDto resToGroupMapTO, String operator);

	ResourceResponse<?> deleteByID(Long primaryKey, String operator);

	ResourceResponse<?> batchDelete(String[] deleteIds, String operator);

	ResourceResponse<List<GroupToResMapDto>> findAll();

	ResourceResponse<?> deleteAll(String operator);

	ResourceResponse<PaginationTO> getList(ResourceRequest<GroupToResMapDto> listRequest);

	ResourceResponse<?> changeGroupRes(List<GroupToResMapDto> changeDtoLs,String systemId,String operator,Long controllerBeanId,Long groupId);

}
