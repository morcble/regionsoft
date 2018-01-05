package com.cnautosoft.regionsoftsample.service;

import java.util.List;
import com.cnautosoft.regionsoftsample.entity.RemoteSample;
import java.util.Date;

public interface RemoteSampleService {
	RemoteSample create(RemoteSample instance,String operator) throws Exception;
	RemoteSample getByID(Long primaryKey) throws Exception;
	RemoteSample update(RemoteSample instance,String operator) throws Exception;
	void deleteByID(Long primaryKey, String operator) throws Exception;
	void deleteAll(String operator) throws Exception;
	void batchDelete(String[] deleteIds, String operator) throws Exception;
	List<RemoteSample> getList(Integer pageNo, Integer pageSize, String orderBy, Integer softDelete,String remoteName) throws Exception;
	List<RemoteSample> findAll() throws Exception;
	Long getAmount(Integer softDelete,String remoteName) throws Exception;
}
