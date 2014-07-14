package musicClustering;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class BagOfWords {

	public static void computeCoefficients(Artist[] artists,DatabaseConnector db,int k){
		String stringArtists="";
		int i=0;
		for(Artist a:artists){
			if(i>0){stringArtists+=",";}
			stringArtists+="\""+a.name+"\"";
			i++;
		}
		String[] topTags=new String[k];
		String query="select tag,sum(count) as c from artisttags where artist in  ("+stringArtists+") group by tag order by c desc limit "+k;
		String stringCoeff="";
		try {
			ResultSet s=db.statement.executeQuery(query);
			s.first();
			i=0;
			while(!s.isAfterLast()){
				topTags[i]=s.getString(1);
				if(i>0){stringCoeff+=",";}
				stringCoeff+="sum(case when tag=\""+s.getString(1)+"\" then count else 0 end)/sum(count) ";
				
				s.next();
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		query="SELECT "+stringCoeff;
		query+="from artisttags where artist in ("+stringArtists+") group by artist order by artist";
		try {
			ResultSet s=db.statement.executeQuery(query);
			s.first();
			i=0;
			while(!s.isAfterLast()){
				//System.out.println(artists[i].name);
				artists[i].coefficients=new double[k];
				for(int j=1;j<=k;j++){
					artists[i].coefficients[j-1]=s.getDouble(j);
			
				}
			
				s.next();
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
