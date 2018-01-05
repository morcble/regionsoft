package com.cnautosoft.regionsoftsample.batch;

import java.util.List;
import com.cnautosoft.h2o.annotation.Batch;
import com.cnautosoft.h2o.annotation.tag.Autowired;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.SYSEnvSetup;
import com.cnautosoft.h2o.core.SystemContext;

@Batch
public class BatchSample {
	private static final Logger logger = Logger.getLogger(BatchSample.class);
	
	/*@Autowired(targetClass = SampleServiceImpl.class)
	private SampleService sampleService;*/
	
	public void test(){
		try {
			
		} catch (Exception e) {
			logger.debug(e);
		}
	}
	
	public static void main(String[] args){
		try {
			SystemContext systemContext = SYSEnvSetup.setUp();
			BatchSample batch = (BatchSample) systemContext.getManagedBean(BatchSample.class);
			batch.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			SYSEnvSetup.releaseResource();
		}
	}
}