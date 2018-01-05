package com.cnautosoft.h2o.admin.resource;

import java.util.List;

import com.cnautosoft.h2o.admin.dto.UserDto;
import com.cnautosoft.h2o.rpc.common.to.StringIDDto;
import com.cnautosoft.h2o.rpc.common.to.LongIDDto;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;

public interface UserResource {
	ResourceResponse<UserDto> create(UserDto dto, String operator);

	ResourceResponse<UserDto> getByID(Long primaryKey);
 
	ResourceResponse<UserDto> update(UserDto userTO, String operator);

	ResourceResponse<?> deleteByID(Long primaryKey, String operator);

	ResourceResponse<?> batchDelete(String[] deleteIds, String operator);

	ResourceResponse<List<UserDto>> findAll();

	ResourceResponse<?> deleteAll(String operator);

	ResourceResponse<PaginationTO> getList(ResourceRequest<UserDto> listRequest);

	ResourceResponse<?> lockUser(LongIDDto idDto, String operator);

	ResourceResponse<?> unlockUser(LongIDDto idDto, String operator);

	ResourceResponse<Long> getAmountNotInGroup(ResourceRequest<UserDto> listRequest);

	ResourceResponse<List<UserDto>> getListNotInGroup(ResourceRequest<UserDto> listRequest);

}
