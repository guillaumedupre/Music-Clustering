package musicClustering;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class DatabaseConnector {
	private Connection connect = null;
	Statement statement = null;
	
	public void setConnection(String a,String b,String c) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect= DriverManager.getConnection(a,b,c);
			
			statement = connect.createStatement();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void query(){
		try {
			ResultSet s=statement.executeQuery("select artist,sum(count) as c from artisttags group by artist order by c desc limit 10");
			
			s.first();
			while(!s.isAfterLast()){
			System.out.println(s.getString(1));
			s.next();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String[] getTopTags(String artists,int k){
	
		String[] topTags=new String[k];
		String query="select tag,sum(count) as c from artisttags where artist in  ("+artists+") group by tag order by c desc limit "+k;
		System.out.println(query);
		try {
			ResultSet s=statement.executeQuery(query);
			
			s.first();
			while(!s.isAfterLast()){
			System.out.println(s.getString(1));
			s.next();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return topTags;
	}
}
