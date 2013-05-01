package tube.lib;
/*
 * Static class to maintain constants.
 * 
 * 
 * Created by Shreyas Valmiki 
 * Email: shreyasvalmiki[at]gmail[dot]com
 * 
 * 
 */
public class Constants {
	public static final String TCP = "tcp";
	public static final String SERURITY_GROUP_NAME = "user_group";
	public static final String SECURITY_GROUP_DESCRIPTION = "Security group for Virtual IT";
	public static final String KEY_FILE_NAME = "virtualit.pem";
	public static final String ALL_ACCESS_IP_RANGE = "0.0.0.0/0";
	public static final String KEY_PAIR_NAME = "virtualit";
	public static final String EC2_USER_NAME = "ec2-user";
	public static final String INSTANCE_TYPE_MICRO = "t1.micro";
	public static final String IMAGE_ID_AMAZON_LINUX = "ami-76f0061f";
	public static final String AVAILABILITY_ZONE_EAST = "us-east-1c";
	public static final String ENDPOINT_US_EAST = "ec2.us-east-1.amazonaws.com";
	public static final String COMMAND_AVG_CPU_USAGE = "cat /proc/loadavg";
	public static final String DB_CONN_STRING = "jdbc:mysql://newbtube.cousga5z2q3c.us-east-1.rds.amazonaws.com/newbtube";
	public static final String DB_USERNAME = "root";
	public static final String DB_PASSWORD = "roottoor";
	public static final int START_HOUR = 9;
	public static final int END_HOUR = 19;
	public static final String BUCKET_NAME = "newbtube"; 
	public static final int NUM_OF_VIDS_IN_PAGE = 5;
	public static final String S3_ADDRESS_VIDEO_ROOT = "https://s3.amazonaws.com/newbtube/";
	public static final String S3_ADDRESS_THUMBNAIL_ROOT = "https://s3.amazonaws.com/newbtube/";
	public static final String DEFAULT_THUMBNAIL_PATH = "img/thumb.jpg";
}
