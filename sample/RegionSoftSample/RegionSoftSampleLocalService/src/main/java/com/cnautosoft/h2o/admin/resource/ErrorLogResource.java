package com.cnautosoft.h2o.admin.resource;

import java.util.List;

import com.cnautosoft.h2o.admin.dto.ErrorLogDto;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;

public interface ErrorLogResource {
	ResourceResponse<ErrorLogDto> create(ErrorLogDto dto, String operator);

	ResourceResponse<ErrorLogDto> getByID(Long primaryKey);
 
	ResourceResponse<ErrorLogDto> update(ErrorLogDto errorLogTO, String operator);

	ResourceResponse<?> deleteByID(Long primaryKey, String operator);

	ResourceResponse<?> batchDelete(String[] deleteIds, String operator);

	ResourceResponse<List<ErrorLogDto>> findAll();

	ResourceResponse<?> deleteAll(String operator);

	ResourceResponse<PaginationTO> getList(ResourceRequest<ErrorLogDto> listRequest);

}
