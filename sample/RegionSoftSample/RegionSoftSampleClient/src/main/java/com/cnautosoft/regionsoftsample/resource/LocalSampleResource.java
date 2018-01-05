package com.cnautosoft.regionsoftsample.resource;

import java.util.List;
import com.cnautosoft.regionsoftsample.dto.LocalSampleDto;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;

public interface LocalSampleResource {
	ResourceResponse<LocalSampleDto> create(LocalSampleDto dto, String operator);

	ResourceResponse<LocalSampleDto> getByID(Long primaryKey);
 
	ResourceResponse<LocalSampleDto> update(LocalSampleDto localSampleTO, String operator);

	ResourceResponse<?> deleteByID(Long primaryKey, String operator);

	ResourceResponse<?> batchDelete(String[] deleteIds, String operator);

	ResourceResponse<List<LocalSampleDto>> findAll();

	ResourceResponse<?> deleteAll(String operator);

	ResourceResponse<PaginationTO> getList(ResourceRequest<LocalSampleDto> listRequest);

}
