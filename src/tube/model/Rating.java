package tube.model;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Rating {
	private int id;
	private String ip;
	private int rating;
	
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
	
	public int getRating(){
		return this.rating;
	}
	public void setRating(int rating){
		this.rating = rating;
	}
}
