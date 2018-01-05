package com.cnautosoft.h2o.admin.resource;

import java.util.List;

import com.cnautosoft.h2o.admin.dto.ManagedBeanDetailDto;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;

public interface ManagedBeanDetailResource {
	ResourceResponse<ManagedBeanDetailDto> create(ManagedBeanDetailDto dto, String operator);

	ResourceResponse<ManagedBeanDetailDto> getByID(Long primaryKey);
 
	ResourceResponse<ManagedBeanDetailDto> update(ManagedBeanDetailDto managedBeanDetailTO, String operator);

	ResourceResponse<?> deleteByID(Long primaryKey, String operator);

	ResourceResponse<?> batchDelete(String[] deleteIds, String operator);

	ResourceResponse<List<ManagedBeanDetailDto>> findAll();

	ResourceResponse<?> deleteAll(String operator);

	ResourceResponse<PaginationTO> getList(ResourceRequest<ManagedBeanDetailDto> listRequest);

	ResourceResponse<?> scanManagedBeans(String systemId);

}
