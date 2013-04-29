package tube.resources;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import tube.dao.VideoDao;
import tube.lib.Infrastructure;
import tube.model.Video;

@Path("videos")
public class VideoResource {
	VideoDao dao = new VideoDao();
	Video curVideo = new Video();
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount(){
		return(String.valueOf(dao.getAllCount()));
	}
	
	
	@GET
	@Path("findbyhitsdesc/{start}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<Video> findByHitsDesc(@PathParam("start") int start){
		System.out.println("Find by hits in descending order");
		return(dao.findAllByHitsDesc(start));
	}
	
	@GET
	@Path("findbyratingdesc/{start}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<Video> findByRatingDesc(@PathParam("start") int start){
		System.out.println("Find by rating in descending order");
		return(dao.findAllByRatingDesc(start));
	}
	
	
	@GET
	@Path("findbyhitsasc/{start}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<Video> findByHitsAsc(@PathParam("start") int start){
		System.out.println("Find by hits in ascending order");
		return(dao.findAllByHitsAsc(start));
	}
	
	@GET
	@Path("findbyratingasc/{start}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<Video> findByRatingAsc(@PathParam("start") int start){
		System.out.println("Find by rating in ascending order");
		return(dao.findAllByRatingAsc(start));
	}
	
	
	@GET
	@Path("findbydate/{start}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Video> findByDate(@PathParam("start") int start){
		System.out.println("Find by date");
		return(dao.findAllByDate(start));
	}
	
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Video getVideo(@PathParam("id") int id){
		System.out.println("Get Video");
		return(dao.getVideo(id));
	}
	
	@POST
	@Path("hit/{id}")
	public void hit(@PathParam("id") int id){
		System.out.println("Adding 1 hit");
		dao.hit(id);
	}
	
	@POST
	@Path("rate")
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	public void rate(@FormParam("id") int id, @FormParam("score") int score){
		System.out.println("Rating Video");
		dao.rate(id,score);
	}
	
	
	@POST
	@Path("insertvid")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.TEXT_PLAIN})
	public String insertVid(@FormDataParam("name") String name,
			@FormDataParam("descr") String descr,
			@FormDataParam("file") File file, 
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("thumbFile") File thumbFile,
			@Context HttpServletRequest req){
		Video vid = new Video();
		vid.setDescr(descr);
		vid.setName(name);
		vid.setHits(0);
		vid.setIp(req.getRemoteAddr());
		vid.setRatings(0);
		vid.setPossRatings(0);
		System.out.println("Insert details of a video to the database");
		
		if(thumbFile.length()==0)
			dao.insertVid(vid,false);
		else
			dao.insertVid(vid,true);
		
		
		curVideo = vid;
		
		Infrastructure inf = new Infrastructure();
		inf.uploadVideo(String.valueOf(curVideo.getId()), file, fileDetail.getSize());
		//inf.upload(String.valueOf(858934), file, fileDetail.getSize());
		
		System.out.println("Video uploaded");
		
		if(thumbFile.length()!=0){
			inf.uploadThumbnail("th"+String.valueOf(curVideo.getId()), thumbFile);
			System.out.println("Thumbnail Uploaded");
		}
		
		//ThumbnailExtractor thumbExt = new ThumbnailExtractor(String.valueOf(curVideo.getId()));
		
		//File thFile = thumbExt.getThumbnail(5, "x");
		//inf.uploadThumbnail("th"+String.valueOf(curVideo.getId()), thFile);
		
		return("File uploaded");
		
		//return(vid);
	}
	
	@DELETE
	@Path("delete/{id}")
	public void delete(@PathParam("id") int id){
		System.out.println("Deleting video "+id);
		dao.delete(id);
		
		Infrastructure inf = new Infrastructure();
		inf.deleteFile(String.valueOf(id));
		inf.deleteFile("th"+String.valueOf(id));
	}
	
	
	
	@POST
	@Path("updatepath")
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public void updatePath(Video vid){
		System.out.println("Insert details of a video to the database");
		dao.updatePath(vid);
		curVideo = vid;
	}
	
	
	
	@GET
	@Path("/dummy")
	@Produces({MediaType.TEXT_PLAIN})
	public String Dummy(){
		
		System.out.println("file uploaded");
		return("This is a dummy file");
		//Infrastructure inf = new Infrastructure();
		//inf.upload(String.valueOf(curVideo.getId()), iStream, fileDetail.getSize());
		//System.out.println("file uploaded");
	}
	
	
	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.TEXT_PLAIN})
	public String upload(@FormDataParam("file") File file, 
			@FormDataParam("file") FormDataContentDisposition fileDetail){
		
		System.out.println("file uploaded");
		
		Infrastructure inf = new Infrastructure();
		inf.uploadVideo(String.valueOf(curVideo.getId()), file, fileDetail.getSize());
		//inf.upload(String.valueOf(858934), file, fileDetail.getSize());
		System.out.println("file uploaded");
		return("File uploaded");
	}
	
	
	@GET
	@Path("createbucket")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON,MediaType.TEXT_HTML})
	public String createBucket(){
		Infrastructure inf = new Infrastructure();
		return(inf.createS3Bucket());
	}
	
}
