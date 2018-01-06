		var Config = {
				skin : "skin1",
				LOCALE:"",//_cn  _en
				REGION_DEBUG_MODE : false,
				lazyLoadImg:true,
				releaseVersion : 1,
				combineRequests:false,
				combineRequestsTimeGap:100,
				detachMode:true,
				devMode:true,
				frontendPath :  "http://localhost:8080/RegionSoftSampleFrontend",
				backendPath  : "http://localhost:8080/RegionSoftSampleLocalService/region",
				compressReq:false
		}
		
		var jsDependencies = [
		               Config.frontendPath+"/core/common/jquery-1.12.2.min.js",
		               Config.frontendPath+"/core/common/regionbundle-0.5.1.1.release.js",
		               Config.frontendPath+"/core/common/pako.min.js",
		               Config.frontendPath+"/messages/global_msg.js",
		               Config.frontendPath+"/core/datals.js",
		               Config.frontendPath+"/datepicker/js/moment.min.js",
		               Config.frontendPath+"/datepicker/js/bootstrap-datepicker-uxsolutions.min.js",
		               Config.frontendPath+"/js/locales/bootstrap-datetimepicker.zh-CN.js",
		               Config.frontendPath+"/core/common/jquery.lazyload.1.9.3.js",
		              
		               ];

		var cssDependencies = [
						Config.frontendPath+"/css/"+Config.skin+"/common/font-awesome-4.7.0/css/font-awesome.min.css",
						Config.frontendPath+"/css/"+Config.skin+"/common/bootstrap.min.css",
						Config.frontendPath+"/css/"+Config.skin+"/common/regionframe.css",
						
						//date begin
						Config.frontendPath+"/datepicker/css/bootstrap-datepicker3.standalone.min.css"
						//date end
		                ];
					
								
							
						



for(var i = 0 ; i <cssDependencies.length ; i++){
	document.write("<link href=\""+cssDependencies[i]+"?version="+Config.releaseVersion+"\" rel=\"stylesheet\"></link>");
}

for(var i = 0 ; i <jsDependencies.length ; i++){
	document.write("<script src=\""+jsDependencies[i]+"?version="+Config.releaseVersion+"\"><\/script>");
}




