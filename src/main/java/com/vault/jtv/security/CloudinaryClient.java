package com.vault.jtv.security;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.vault.util.MessageConstants;



@Service
public class CloudinaryClient {
	 
	 protected Logger logger = LoggerFactory.getLogger(CloudinaryClient.class);
	 public static Cloudinary c=new Cloudinary(MessageConstants.mCloudinary+MessageConstants.mApiKey+":"+MessageConstants.mApiSecret+"@"+MessageConstants.mCloudName);
	
	/**
     * To get the list of all images 
     * @return
     */

   
    public List<String> getImageList()
    {        
        List<String> retval=new ArrayList<String>();
        try
        {
        	Map response=c.api().resource("", ObjectUtils.asMap(MessageConstants.TYPE, MessageConstants.UPLOAD,MessageConstants.MAX_RESULTS,MessageConstants.FIFTY)); //for all images list , default =10 results        	
            JSONObject json=new JSONObject(response);        
            JSONArray ja=json.getJSONArray(MessageConstants.RESOURCES);
            for(int i=0; i<ja.length(); i++)
            {
                JSONObject j=ja.getJSONObject(i);
                retval.add(j.getString(MessageConstants.URL));
            }

            return retval;
        }
        catch (Exception e)
        {
            return retval;
        }
    }
    
    
    /**
     * To get an image with the name (public_id)
     * @param aName
     * @return
     */
   
    public String getImage(String aName)
    {
    	String url="";
        try
        {          
        	Map response=c.api().resource(aName, ObjectUtils.asMap(MessageConstants.TYPE, MessageConstants.UPLOAD));
            JSONObject json=new JSONObject(response);
             url= json.getString(MessageConstants.URL);
             logger.info(url);
             return url;
        }
        catch (Exception e)
        {
            return url;
        }
    }

   
   
    /**
     * To delete an image with image name (public_id)
     * @param fileName
     * @param request
     * @return
     */
   
    public JSONObject deleteImage(String fileName)
    {
    	JSONObject json=null;
    	try
        {   		
            //delete image
    		//invalidate true as image will be cached for 30 days
    		 Map uploadResult = c.uploader().destroy(fileName,ObjectUtils.asMap(MessageConstants.INVALIDATE, true));
    		  if (uploadResult != null) {
    			  	json=new JSONObject(uploadResult);
              		String status=json.getString(MessageConstants.RESULT);
              		logger.info(status);
              		return json;
    		  }          
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            return json;
        }
    	return json;
    }
    
   
    
    public JSONObject imageUploadTrans(MultipartFile aFile)
    {
    	JSONObject json=null;
       try
        {  
    	   File f=Files.createTempFile(MessageConstants.TEMP, aFile.getOriginalFilename()).toFile();
        	aFile.transferTo(f);
            Map params = ObjectUtils.asMap(MessageConstants.TRANSFORMATION,new Transformation().quality(MessageConstants.AUTO)); 
            Map uploadResult = c.uploader().upload(f, params);
            if (uploadResult != null) {
            	 json=new JSONObject(uploadResult);
            	 String url=json.getString(MessageConstants.URL);
            	logger.info(MessageConstants.URL+url);
                return json;
            }
          }
        catch(Exception e)
        {
        	e.printStackTrace();
            return json;
        }
	return json;
		
    }

    /**
     * To upload a image
     * @param aFile
     * @param request
     * @return
     */

    public String postImage(MultipartFile aFile)
    {
    	String url="";
        try
        {
            File f=Files.createTempFile(MessageConstants.TEMP, aFile.getOriginalFilename()).toFile();
            aFile.transferTo(f);
            Map response=c.uploader().upload(f, ObjectUtils.emptyMap());
            JSONObject json=new JSONObject(response);
            url=json.getString(MessageConstants.URL);
            return url;
        }
        catch(Exception e)
        {
            return url;
        }
    }
    
    
    
    public JSONObject docUploadTrans(MultipartFile aFile)
    {
    	JSONObject json=null;
       try
        {  
    	   File f=Files.createTempFile(MessageConstants.TEMP, aFile.getOriginalFilename()).toFile();
        	aFile.transferTo(f);
        	Map params = ObjectUtils.asMap(MessageConstants.RESOURCE_TYPE,MessageConstants.RAW);	
            Map uploadResult = c.uploader().upload(f, params);
            if (uploadResult != null) {
            	 json=new JSONObject(uploadResult);
            	 String url=json.getString(MessageConstants.URL);
            	logger.info(MessageConstants.URL+url);
                return json;
            }
          }
        catch(Exception e)
        {
        	e.printStackTrace();
            return json;
        }
	return json;
		
    }
  
}
