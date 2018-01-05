package com.cnautosoft.regionsoftsample.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.cnautosoft.h2o.annotation.Controller;
import com.cnautosoft.h2o.annotation.tag.HeaderInfo;
import com.cnautosoft.h2o.annotation.tag.Parameter;
import com.cnautosoft.h2o.annotation.tag.RequestMapping;
import com.cnautosoft.h2o.annotation.tag.ResponseBody;
import com.cnautosoft.h2o.common.JsonUtil;
import com.cnautosoft.h2o.common.Logger;
import com.cnautosoft.h2o.core.CommonUtil;
import com.cnautosoft.h2o.enums.RequestMethod;
import com.cnautosoft.h2o.utils.SystemAttrs;
import com.cnautosoft.h2o.web.core.ResourceResponse;
import com.cnautosoft.h2o.web.core.RespCode;
import com.cnautosoft.h2o.web.wrapper.ResourceResWrapper;

@Controller
@RequestMapping("/common/file")
public class FileController {
	public static Logger logger = Logger.getLogger(FileController.class);

	//file upload----------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/upload" ,method ={RequestMethod.POST,RequestMethod.GET},responseHeader= {"Content-Type == application/json;charset=UTF-8"})
    public @ResponseBody String upload(HttpServletRequest request, HttpServletResponse response,@HeaderInfo Map<String,String> headerInfo,	@Parameter(value = "requestStr") String requestStr) {
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        File cacheFolder = new File(SystemAttrs.FILE_SERVER_ROOT+SystemAttrs.SYSTEM_SEPERATOR+"tmp");
        factory.setRepository(cacheFolder);
        ResourceResponse<?> result = null;
        ServletFileUpload upload = new ServletFileUpload(factory);
        String errorMsg = null;
        try {
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			List<FileItem> files = new ArrayList<FileItem>();
			
			Map<String,String> reqMap = new HashMap<String,String>();
			while (iter.hasNext()) {
			    FileItem item = iter.next();
			    if (item.isFormField()) {
			    	reqMap.put(item.getFieldName(), item.getString());
			    } else {
			    	files.add(item);
			    }
			}
			
			String relativePath = null;
			String paras = reqMap.get("paras");
			if(!CommonUtil.isEmpty(paras)){
				String[] parasArray = paras.split(";");
				Map<String,String> parasMap = new HashMap<String,String>();
				for(String tmp:parasArray){
					String[] tmpArray = tmp.split("=");
					parasMap.put(tmpArray[0].trim(),tmpArray[1].trim());
				}
				relativePath = parasMap.get("relativepath");
			}

			for(FileItem file:files){
				processUploadedFile(file,relativePath);
			}
			result = ResourceResWrapper.successResult("operation.successfully", result);
		} catch (Exception e) {
			logger.debug(e);
			errorMsg = e.getMessage();
		}
        if (errorMsg != null) {
			result = ResourceResWrapper.failResult(errorMsg, null, RespCode._508);
		}
		return JsonUtil.objectToJson(result);
	}
	

	private void processUploadedFile(FileItem item,String relativePath) throws Exception {
		String fieldName = item.getFieldName();
	    String contentType = item.getContentType();
	    long sizeInBytes = item.getSize();
		String fileName = item.getName();
		StringBuilder storePath = new StringBuilder(SystemAttrs.FILE_SERVER_ROOT);
		
		File uploadedFile = null;
		if(!CommonUtil.isEmpty(relativePath)){
			String[] strArray= relativePath.split("\\.");
			for(String tmp:strArray){
				storePath.append(SystemAttrs.SYSTEM_SEPERATOR);
				storePath.append(tmp);
			}
			storePath.append(SystemAttrs.SYSTEM_SEPERATOR);
			
			File folder = new File(storePath.toString());
			if(!folder.exists())folder.mkdirs();
			
			storePath.append(fileName);
			uploadedFile = new File(storePath.toString());
		}
		else{
			storePath.append(SystemAttrs.SYSTEM_SEPERATOR);
			storePath.append(fileName);
			uploadedFile = new File(storePath.toString());
		}
	    try {
			item.write(uploadedFile);
		} catch (Exception e) {
			throw e;
		}
	}
	
	//file download----------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/download",method ={RequestMethod.POST,RequestMethod.GET})
	public void download(HttpServletRequest request, HttpServletResponse response,@HeaderInfo Map<String,String> headerInfo,
			@Parameter(value = "requestStr") String requestStr,@Parameter(value = "fileName") String fileName) {
		subDownload(request, response, fileName,false);
	}

	@RequestMapping(value = "/view",method ={RequestMethod.POST,RequestMethod.GET})
	public void view(HttpServletRequest request, HttpServletResponse response,@HeaderInfo Map<String,String> headerInfo,
			@Parameter(value = "requestStr") String requestStr,@Parameter(value = "fileName") String fileName) {
		subDownload(request, response,fileName,true);
	}

	private void subDownload(HttpServletRequest request, HttpServletResponse response, String fileName, boolean viewMode) {
		StringBuilder downLoadPath = new StringBuilder(SystemAttrs.FILE_SERVER_ROOT);
		downLoadPath.append(SystemAttrs.SYSTEM_SEPERATOR);
		String[] tmpArray = fileName.split("/");
		String realFilePath = "";
		for(int i = 0 ; i<tmpArray.length-1;i++){
			realFilePath+=tmpArray[i]+SystemAttrs.SYSTEM_SEPERATOR;
		}
		String realFileName = tmpArray[tmpArray.length-1];
		realFilePath+=tmpArray[tmpArray.length-1];
		
		downLoadPath.append(realFilePath);
		response.reset();
		logger.debug("try to download file:" + downLoadPath);
		File file = new File(downLoadPath.toString());
		
		if (!file.exists()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String contentType = new MimetypesFileTypeMap().getContentType(file);
		response.setContentType(contentType);
		if (viewMode) {
			response.setHeader("Content-Disposition", "inline; filename=" + realFileName);
		} else {
			try {
				response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(realFileName,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				try {
					response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(realFileName,"UTF-16"));
				} catch (UnsupportedEncodingException e1) {
					logger.error(e1);
				}
			}
		}

		FileInputStream fileInputStream = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			bufferedInputStream = new BufferedInputStream(fileInputStream);
			int size = 0;
			byte[] b = new byte[4096];
			while ((size = bufferedInputStream.read(b)) != -1) {
				response.getOutputStream().write(b, 0, size);
			}
		} catch (Exception e) {
			logger.warn(e);
		} finally {
			try {
				response.getOutputStream().flush();
			} catch (IOException e) {
				logger.error(e);
			}
			CommonUtil.closeQuietly(bufferedInputStream);
			CommonUtil.closeQuietly(fileInputStream);
		}

	}
}
