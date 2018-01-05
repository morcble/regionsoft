package com.cnautosoft.regionsoftsample.resource;

import java.util.List;
import com.cnautosoft.regionsoftsample.dto.RemoteSampleDto;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;

public interface RemoteSampleResource {
	ResourceResponse<RemoteSampleDto> create(RemoteSampleDto dto, String operator);

	ResourceResponse<RemoteSampleDto> getByID(Long primaryKey);
 
	ResourceResponse<RemoteSampleDto> update(RemoteSampleDto remoteSampleTO, String operator);

	ResourceResponse<?> deleteByID(Long primaryKey, String operator);

	ResourceResponse<?> batchDelete(String[] deleteIds, String operator);

	ResourceResponse<List<RemoteSampleDto>> findAll();

	ResourceResponse<?> deleteAll(String operator);

	ResourceResponse<PaginationTO> getList(ResourceRequest<RemoteSampleDto> listRequest);

}
