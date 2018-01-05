package com.cnautosoft.h2o.admin.resource;

import java.util.List;

import com.cnautosoft.h2o.admin.dto.MethodInfoDto;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;

public interface MethodInfoResource {
	ResourceResponse<MethodInfoDto> create(MethodInfoDto dto, String operator);

	ResourceResponse<MethodInfoDto> getByID(Long primaryKey);
 
	ResourceResponse<MethodInfoDto> update(MethodInfoDto methodInfoTO, String operator);

	ResourceResponse<?> deleteByID(Long primaryKey, String operator);

	ResourceResponse<?> batchDelete(String[] deleteIds, String operator);

	ResourceResponse<List<MethodInfoDto>> findAll();

	ResourceResponse<?> deleteAll(String operator);

	ResourceResponse<PaginationTO> getList(ResourceRequest<MethodInfoDto> listRequest);

	ResourceResponse<List<MethodInfoDto>> getConfigtList(ResourceRequest<MethodInfoDto> listRequest);

}
