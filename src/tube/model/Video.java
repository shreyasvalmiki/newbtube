package tube.model;

import javax.xml.bind.annotation.XmlRootElement;;

@XmlRootElement
public class Video {
	private int id;
	private String ip;
	private String path;
	private String name;
	private String descr;
	private int hits;
	private int ratings;
	private int possRatings;
	private String thumbPath;
	
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	public String getIp(){
		return this.ip;
	}
	public void setIp(String ip){
		this.ip = ip;
	}
	
	public String getPath(){
		return this.path;
	}
	public void setPath(String path){
		this.path = path;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public String getDescr(){
		return this.descr;
	}
	public void setDescr(String descr){
		this.descr = descr;
	}
	
	public int getHits(){
		return this.hits;
	}
	public void setHits(int hits){
		this.hits = hits;
	}
	
	public int getRatings(){
		return this.ratings;
	}
	public void setRatings(int ratings){
		this.ratings = ratings;
	}
	
	public int getPossRatings(){
		return this.possRatings;
	}
	public void setPossRatings(int possRatings){
		this.possRatings = possRatings;
	}
	
	public String getThumbPath(){
		return this.thumbPath;
	}
	public void setThumbPath(String thumbPath){
		this.thumbPath = thumbPath;
	}
	
}
