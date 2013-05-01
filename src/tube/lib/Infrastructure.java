package tube.lib;

/*
 * Class interfaces with Amazon AWS to manage S3 Bucket
 * 
 * 
 * Created by Shreyas Valmiki 
 * Email: shreyasvalmiki[at]gmail[dot]com
 * 
 * 
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;


public class Infrastructure {
	static AmazonS3Client s3;
	/*
	 * Constructor
	 */
	public Infrastructure(){
		AWSCredentials credentials = null;
		try {
			credentials = new PropertiesCredentials(
					Infrastructure.class
					.getResourceAsStream("/AwsCredentials.properties"));
		} catch (IOException e1) {
			System.out.println("Error opening credentials file");
			e1.printStackTrace();
		}

		s3 = new AmazonS3Client(credentials);
	}


	
	public String createS3Bucket(){
		List<Bucket> buckets = s3.listBuckets();
		boolean isBucketPresent = false;
		for(Bucket buck:buckets){
			if(buck.getName().equals(Constants.BUCKET_NAME)){
				isBucketPresent = true;
				break;
			}
		}
		if(!isBucketPresent){
			CreateBucketRequest req = new CreateBucketRequest(Constants.BUCKET_NAME);
			req.setCannedAcl(CannedAccessControlList.PublicReadWrite);
			
			s3.createBucket(Constants.BUCKET_NAME);
			System.out.println("Created S3 Bucket: "+Constants.BUCKET_NAME);
			return("Created bucket: "+Constants.BUCKET_NAME);
		}

		else{
			System.out.println("S3 Bucket: "+Constants.BUCKET_NAME+" already available to use.");
			return("Bucket exists: "+Constants.BUCKET_NAME);
		}
	}

	public void deleteS3Bucket(){
		s3.deleteBucket(Constants.BUCKET_NAME);
		System.out.println("Deleted S3 Bucket: "+Constants.BUCKET_NAME);
	}

	
	public void uploadVideo(String key,File file,long fileLength){
		key = key+".mp4";
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentType("video/mp4");
		meta.setContentDisposition("inline");
		//File file = new File(filePath);
		createS3Bucket();
		PutObjectRequest putObjReq = new PutObjectRequest(Constants.BUCKET_NAME, key, file);
		putObjReq.setMetadata(meta);
		putObjReq.setCannedAcl(CannedAccessControlList.PublicReadWrite);
		s3.putObject(putObjReq);
	}
	
	
	public void uploadThumbnail(String key,File file){
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentType("image/*");
		//ObjectMetadata meta = new ObjectMetadata();
		//meta.setContentLength(fileLength);
		//File file = new File(filePath);
		//createS3Bucket();
		PutObjectRequest putObjReq = new PutObjectRequest(Constants.BUCKET_NAME, key, file);
		putObjReq.setMetadata(meta);
		putObjReq.setCannedAcl(CannedAccessControlList.PublicReadWrite);
		s3.putObject(putObjReq);
	}
	
	
	public void deleteFile(String key){
		
		
		if(isFilePut(key)){
			DeleteObjectRequest req = new DeleteObjectRequest(Constants.BUCKET_NAME, key);
			s3.deleteObject(req);
		}
		
	}
	
	public boolean isFilePut(String key){
		
		ObjectListing objs = s3.listObjects(Constants.BUCKET_NAME);
		for(S3ObjectSummary summ: objs.getObjectSummaries()){
			if(summ.getKey().equals(key))
				return true;
		}
		return false;
		
		/*try{
			GetObjectRequest req = new GetObjectRequest(Constants.BUCKET_NAME, key);
			S3Object obj = s3.getObject(req);
		}
		catch(AmazonServiceException e){
			 String errorCode = e.getErrorCode();
			    if (!errorCode.equals("NoSuchKey")) {
			        return false;
			    }
		}
		return true;*/
	}
	
	public static void main(String[] args) throws Exception {
		Infrastructure infra = new Infrastructure();
		
		//Creates bucket
		infra.createS3Bucket();
	}
	
	
	
}

