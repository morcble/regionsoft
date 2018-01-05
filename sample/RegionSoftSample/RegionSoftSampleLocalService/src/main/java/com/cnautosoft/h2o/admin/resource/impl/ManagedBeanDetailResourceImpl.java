package com.cnautosoft.h2o.admin.resource.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.cnautosoft.h2o.admin.dto.ManagedBeanDetailDto;
import com.cnautosoft.h2o.admin.entity.ManagedBeanDetail;
import com.cnautosoft.h2o.admin.resource.ManagedBeanDetailResource;
import com.cnautosoft.h2o.admin.service.ManagedBeanDetailService;
import com.cnautosoft.h2o.admin.service.impl.ManagedBeanDetailServiceImpl;
import com.cnautosoft.h2o.annotation.Resource;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.H2OResourceImpl;
import com.cnautosoft.h2o.core.contextinfo.SystemContextDetailDetector;
import com.cnautosoft.h2o.core.exception.ValidationException;
import com.cnautosoft.h2o.core.CommonUtil;
import com.cnautosoft.h2o.web.core.ResourceRequest;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.core.RespCode;
import com.cnautosoft.h2o.web.wrapper.PaginationTO;
import com.cnautosoft.h2o.web.wrapper.ResourceResWrapper;


@Resource
public class ManagedBeanDetailResourceImpl extends H2OResourceImpl implements ManagedBeanDetailResource{
	private static final Logger logger = Logger.getLogger(ManagedBeanDetailResourceImpl.class);
	
	@Autowired(targetClass = ManagedBeanDetailServiceImpl.class)
	private ManagedBeanDetailService managedBeanDetailService;
	
	private boolean enableFastCopy = true;
	
	/**
	 * backend common validation 
	 * @param dto
	 * @throws Exception
	 */
	private void valid(ManagedBeanDetailDto dto) throws Exception{
		    if(CommonUtil.isEmpty(dto.getSystemId()))throw new ValidationException(getMessage("morcble.managedbeandetail.systemId.isnull"));
		    if(CommonUtil.isEmpty(dto.getContextName()))throw new ValidationException(getMessage("morcble.managedbeandetail.contextName.isnull"));
		    if(CommonUtil.isEmpty(dto.getName()))throw new ValidationException(getMessage("morcble.managedbeandetail.name.isnull"));
		    if(CommonUtil.isEmpty(dto.getPackageName()))throw new ValidationException(getMessage("morcble.managedbeandetail.packageName.isnull"));
		    if(CommonUtil.isEmpty(dto.getBeanType()))throw new ValidationException(getMessage("morcble.managedbeandetail.beanType.isnull"));
		    if(CommonUtil.isEmpty(dto.getSvcType()))throw new ValidationException(getMessage("morcble.managedbeandetail.svcType.isnull"));
	}
	

	/**
	 * copy utils
	 */
	private void copyDtoToEntity(ManagedBeanDetailDto dto,ManagedBeanDetail managedBeanDetail){
		if(enableFastCopy){
			managedBeanDetail.setSystemId(dto.getSystemId());		
			managedBeanDetail.setContextName(dto.getContextName());		
			managedBeanDetail.setName(dto.getName());		
			managedBeanDetail.setPackageName(dto.getPackageName());		
			managedBeanDetail.setBeanType(dto.getBeanType());		
			managedBeanDetail.setSvcType(dto.getSvcType());		
			managedBeanDetail.setId(dto.getId());		
			managedBeanDetail.setCreateBy(dto.getCreateBy());
			managedBeanDetail.setCreateDt(dto.getCreateDt());
			managedBeanDetail.setUpdateBy(dto.getUpdateBy());
			managedBeanDetail.setUpdateDt(dto.getUpdateDt());
			managedBeanDetail.setVersion(dto.getVersion());
			managedBeanDetail.setSoftDelete(dto.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(dto,managedBeanDetail);
		}
	}
	
	
	private void copyEntityToDto(ManagedBeanDetail managedBeanDetail,ManagedBeanDetailDto dto){
		if(enableFastCopy){
			dto.setSystemId(managedBeanDetail.getSystemId());	
			dto.setContextName(managedBeanDetail.getContextName());	
			dto.setName(managedBeanDetail.getName());	
			dto.setPackageName(managedBeanDetail.getPackageName());	
			dto.setBeanType(managedBeanDetail.getBeanType());	
			dto.setSvcType(managedBeanDetail.getSvcType());	
			dto.setId(managedBeanDetail.getId());		
			dto.setCreateBy(managedBeanDetail.getCreateBy());
			dto.setCreateDt(managedBeanDetail.getCreateDt());
			dto.setUpdateBy(managedBeanDetail.getUpdateBy());
			dto.setUpdateDt(managedBeanDetail.getUpdateDt());
			dto.setVersion(managedBeanDetail.getVersion());
			dto.setSoftDelete(managedBeanDetail.getSoftDelete());
		}
		else{
			CommonUtil.copyProperties(managedBeanDetail, dto);
		}
	}
	
	private List<ManagedBeanDetailDto> copyListPropertiesFromEntityToDto(List<ManagedBeanDetail> src){
		if(src==null)return null;
		if(enableFastCopy){
			List<ManagedBeanDetailDto> ls = new ArrayList<ManagedBeanDetailDto>();
			for(ManagedBeanDetail entity:src){
				ManagedBeanDetailDto dto = new ManagedBeanDetailDto();
				copyEntityToDto(entity,dto);
				ls.add(dto);
			}
			return ls;
		}
		else{
			return CommonUtil.copyListProperties(src,ManagedBeanDetailDto.class);
		}
	}
	
	
	@Override
	public ResourceResponse<ManagedBeanDetailDto> create(ManagedBeanDetailDto dto,String operator){
		try{
			valid(dto);
			ManagedBeanDetail managedBeanDetail = new ManagedBeanDetail();
			copyDtoToEntity(dto, managedBeanDetail);
			managedBeanDetail = managedBeanDetailService.create(managedBeanDetail, operator);
			ManagedBeanDetailDto result = new ManagedBeanDetailDto();
			copyEntityToDto(managedBeanDetail, result);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), result);
		}
		catch(ValidationException e){
			logger.warn(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), dto, RespCode._700);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), dto, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<ManagedBeanDetailDto> getByID(Long primaryKey) {
		try{
			ManagedBeanDetail managedBeanDetail = managedBeanDetailService.getByID(primaryKey);
			if(managedBeanDetail==null)return ResourceResWrapper.successResult("Record not found, primaryKey:"+primaryKey,null,RespCode._500);
			ManagedBeanDetailDto result = new ManagedBeanDetailDto();
			copyEntityToDto(managedBeanDetail, result);
			return ResourceResWrapper.successResult(null, result);
		} 
		catch(Exception e){
			logger.error(e, "getByID");
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<ManagedBeanDetailDto> update(ManagedBeanDetailDto managedBeanDetailTO,String operator){
		try{
			valid(managedBeanDetailTO);
			ManagedBeanDetail managedBeanDetail = new ManagedBeanDetail();
			copyDtoToEntity(managedBeanDetailTO, managedBeanDetail);
			managedBeanDetail = managedBeanDetailService.update(managedBeanDetail, operator);
			if(managedBeanDetail==null)return null;
			ManagedBeanDetailDto result = new ManagedBeanDetailDto();
			copyEntityToDto(managedBeanDetail, result);
			
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), result);
		}
		catch(ValidationException e){
			logger.warn(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), managedBeanDetailTO, RespCode._700);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+": "+e.getMessage(), managedBeanDetailTO, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<?> deleteByID(Long primaryKey,String operator){
		try{
			managedBeanDetailService.deleteByID(primaryKey, operator);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), null);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<?> batchDelete(String[] deleteIds,String operator){
		try{
			managedBeanDetailService.batchDelete(deleteIds, operator);
			return ResourceResWrapper.successResult(getMessage("operation.successfully"), null);
		}
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(getMessage("operation.failed")+"  :  "+e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<List<ManagedBeanDetailDto>> findAll(){
		try{
			List<ManagedBeanDetail> managedBeanDetails = managedBeanDetailService.findAll();
			List<ManagedBeanDetailDto> result = copyListPropertiesFromEntityToDto(managedBeanDetails);
			return ResourceResWrapper.successResult(null, result);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	@Override
	public ResourceResponse<?> deleteAll(String operator){
		try{
			managedBeanDetailService.deleteAll(operator);
			return ResourceResWrapper.successResult(null, null);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(), null, RespCode._500);
		}
	}
	
	
	@Override
	public ResourceResponse<PaginationTO> getList(ResourceRequest<ManagedBeanDetailDto> listRequest){
		try{
			ManagedBeanDetailDto dto = listRequest.getData();
			Long amount = managedBeanDetailService.getAmount(PaginationTO.NOT_DELETED 
						,dto.getSystemId()
						,dto.getContextName()
						,dto.getName()
						,dto.getPackageName()
						,dto.getBeanType()
						,dto.getSvcType()
			);
			
			PaginationTO paginationTO = listRequest.getPaginationTO();
	    	String orderBy = paginationTO.getOrderBy();
	    	if(CommonUtil.isEmpty(orderBy)){
	    		orderBy = "createDt desc";
	    		paginationTO.setOrderBy(orderBy);
	    	}
			int totalAmount = 0;
	    	int totalPageAmount = 0;
	    	int currentPageNo = paginationTO.getCurrentPageNo();
			paginationTO.setTotalAmount(amount.intValue());
			totalAmount = paginationTO.getTotalAmount();
			if(totalAmount==0){
				paginationTO.setCurrentPageNo(0);
				paginationTO.setTotalPageAmount(0);
			}
			else{
				totalPageAmount = totalAmount / paginationTO.getPageSize();
				int temp = totalAmount % paginationTO.getPageSize();
				if (temp > 0)
					totalPageAmount++;

				if (currentPageNo < 1)
					currentPageNo = 1;
				if (currentPageNo > totalPageAmount)
					currentPageNo = totalPageAmount;
				paginationTO.setCurrentPageNo(currentPageNo);
				paginationTO.setTotalPageAmount(totalPageAmount);
				
				List<ManagedBeanDetail> managedBeanDetails = managedBeanDetailService.getList(paginationTO.getCurrentPageNo(),paginationTO.getPageSize(),paginationTO.getOrderBy(), PaginationTO.NOT_DELETED
							,dto.getSystemId()
							,dto.getContextName()
							,dto.getName()
							,dto.getPackageName()
							,dto.getBeanType()
							,dto.getSvcType()
						);
				List<ManagedBeanDetailDto> result = copyListPropertiesFromEntityToDto(managedBeanDetails);
				paginationTO.setList(result);
			}
			return ResourceResWrapper.successResult(null, paginationTO);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(),null, RespCode._500);
		}
	}


	/**
	 * systemId reserved for cluster 
	 */
	@Override
	public ResourceResponse<?> scanManagedBeans(String systemId) {
		try{
			SystemContextDetailDetector.refreshSystemContextDetail(true);
			return ResourceResWrapper.successResult(null, null);
		} 
		catch(Exception e){
			logger.error(e);
			return ResourceResWrapper.failResult(e.getMessage(),null, RespCode._500);
		}
	}

}
