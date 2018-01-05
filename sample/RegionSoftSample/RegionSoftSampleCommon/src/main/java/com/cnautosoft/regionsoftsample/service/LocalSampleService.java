package com.cnautosoft.regionsoftsample.service;

import java.util.List;
import com.cnautosoft.regionsoftsample.entity.LocalSample;
import java.util.Date;

public interface LocalSampleService {
	LocalSample create(LocalSample instance,String operator) throws Exception;
	LocalSample getByID(Long primaryKey) throws Exception;
	LocalSample update(LocalSample instance,String operator) throws Exception;
	void deleteByID(Long primaryKey, String operator) throws Exception;
	void deleteAll(String operator) throws Exception;
	void batchDelete(String[] deleteIds, String operator) throws Exception;
	List<LocalSample> getList(Integer pageNo, Integer pageSize, String orderBy, Integer softDelete,String name) throws Exception;
	List<LocalSample> findAll() throws Exception;
	Long getAmount(Integer softDelete,String name) throws Exception;
}
