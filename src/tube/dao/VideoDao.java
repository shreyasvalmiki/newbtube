package tube.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import tube.db.ConnectDB;
import com.mysql.jdbc.*;
import tube.model.Video;
import tube.lib.*;

public class VideoDao {

	public int getAllCount(){
		int count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			
			Statement st = con.createStatement();
			String query = "SELECT COUNT(*) cnt FROM Video";
			ResultSet rs = st.executeQuery(query);
			rs.next();
			count = rs.getInt("cnt");
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<Video> findAllByDate(int start){
		ArrayList<Video> videos = new ArrayList<Video>();
		start = start*tube.lib.Constants.NUM_OF_VIDS_IN_PAGE;
		int end = start+tube.lib.Constants.NUM_OF_VIDS_IN_PAGE;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			
			Statement st = con.createStatement();
			String query = "SELECT * FROM Video ORDER BY id DESC LIMIT "+start+","+end;
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				videos.add(processRow(rs));
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return videos;
	}

	public ArrayList<Video> findAllByRatingDesc(int start){
		ArrayList<Video> videos = new ArrayList<Video>();
		start = start*tube.lib.Constants.NUM_OF_VIDS_IN_PAGE;
		int end = start+tube.lib.Constants.NUM_OF_VIDS_IN_PAGE;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			
			Statement st = con.createStatement();
			String query = "SELECT * FROM Video ORDER BY (ratings/possRatings) DESC LIMIT "+start+","+end;
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				videos.add(processRow(rs));
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return videos;
	}

	public ArrayList<Video> findAllByRatingAsc(int start){
		ArrayList<Video> videos = new ArrayList<Video>();
		start = start*tube.lib.Constants.NUM_OF_VIDS_IN_PAGE;
		int end = start+tube.lib.Constants.NUM_OF_VIDS_IN_PAGE;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			
			Statement st = con.createStatement();
			String query = "SELECT * FROM Video ORDER BY (ratings/possRatings) LIMIT "+start+","+end;
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				videos.add(processRow(rs));
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return videos;
	}

	public ArrayList<Video> findAllByHitsDesc(int start){
		ArrayList<Video> videos = new ArrayList<Video>();
		start = start*tube.lib.Constants.NUM_OF_VIDS_IN_PAGE;
		int end = start+tube.lib.Constants.NUM_OF_VIDS_IN_PAGE;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			
			Statement st = con.createStatement();
			String query = "SELECT * FROM Video ORDER BY hits DESC LIMIT "+start+","+end;
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				videos.add(processRow(rs));
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return videos;
	}

	public ArrayList<Video> findAllByHitsAsc(int start){
		ArrayList<Video> videos = new ArrayList<Video>();
		start = start*tube.lib.Constants.NUM_OF_VIDS_IN_PAGE;
		int end = start+tube.lib.Constants.NUM_OF_VIDS_IN_PAGE;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			
			Statement st = con.createStatement();
			String query = "SELECT * FROM Video ORDER BY hits LIMIT "+start+","+end;
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				videos.add(processRow(rs));
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return videos;
	}


	public ArrayList<Video> findById(int id){
		ArrayList<Video> videos = new ArrayList<Video>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			
			Statement st = con.createStatement();
			String query = "SELECT * FROM Video WHERE id = "+id;
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				videos.add(processRow(rs));
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return videos;
	}

	public ArrayList<Video> findByName(String name){
		ArrayList<Video> videos = new ArrayList<Video>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			
			Statement st = con.createStatement();
			String query = "SELECT * FROM Video WHERE name = "+name;
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				videos.add(processRow(rs));
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return videos;
	}


	public int insertVid(Video video, boolean isThumbSet){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			String query = "INSERT INTO Video (ip,name,descr,hits,ratings,possRatings) VALUES(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, video.getIp());
			ps.setString(2, video.getName());
			ps.setString(3, video.getDescr());
			ps.setInt(4, video.getHits());
			ps.setInt(5, video.getRatings());
			ps.setInt(6, video.getPossRatings());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next())
				video.setId(rs.getInt(1));

			video.setPath(tube.lib.Constants.S3_ADDRESS_VIDEO_ROOT+video.getId()+".mp4");
			if(isThumbSet){
				video.setThumbPath(tube.lib.Constants.S3_ADDRESS_VIDEO_ROOT+"th"+video.getId());
			}
			else
				video.setThumbPath(tube.lib.Constants.DEFAULT_THUMBNAIL_PATH);
			System.out.println("Path: "+video.getPath());

			updatePath(video);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return video.getId();
	}

	public void updatePath(Video video){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			
			String query = "UPDATE Video SET path = ?,thumbPath = ? WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, video.getPath());
			ps.setString(2, video.getThumbPath());
			ps.setInt(3, video.getId());

			ps.executeUpdate();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void hit(int id){
		try {
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			
			String query = "UPDATE Video SET hits = hits + 1 WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Video getVideo(int id){
		Video video = new Video();
		//video.setId(id);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			
			Statement st = con.createStatement();
			String query = "SELECT * FROM Video WHERE id = "+id;
			ResultSet rs = st.executeQuery(query);
			if(rs.next()){
				/*video.setIp(rs.getString("ip"));
				video.setPath(rs.getString("path"));
				video.setName(rs.getString("name"));
				video.setHits(rs.getInt("hits"));
				video.setDescr(rs.getString("descr"));
				video.setRatings(rs.getInt("ratings"));
				video.setPossRatings(rs.getInt("possRatings"));*/
				video = processRow(rs);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return video;
	}
	
	public void rate(int id, int ratings){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			
			String query = "UPDATE Video SET ratings = ratings + ?, possRatings = possRatings + 5 WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, ratings);
			ps.setInt(2, id);
			ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(int id){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(tube.lib.Constants.DB_CONN_STRING,tube.lib.Constants.DB_USERNAME,tube.lib.Constants.DB_PASSWORD);
			
			String query = "DELETE FROM Video WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);

			ps.executeUpdate();

			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	private Video processRow(ResultSet rs){
		Video vid = new Video();

		try {
			vid.setDescr(rs.getString("descr"));
			vid.setHits(rs.getInt("hits"));
			vid.setId(rs.getInt("id"));
			vid.setIp(rs.getString("ip"));
			vid.setName(rs.getString("name"));
			vid.setPath(rs.getString("path"));
			vid.setThumbPath(rs.getString("thumbPath"));
			vid.setRatings(rs.getInt("ratings"));
			vid.setPossRatings(rs.getInt("possRatings"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return vid;
	}
}
