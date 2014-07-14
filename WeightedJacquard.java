package musicClustering;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/* Papers :
 * http://static.googleusercontent.com/media/research.google.com/en/us/pubs/archive/36928.pdf
 * http://theory.stanford.edu/~sergei/papers/soda10-jaccard.pdf
 * 
 */
public class WeightedJacquard {
	public static double[][] computeDistance(Artist[] artists,DatabaseConnector db){
		int na=artists.length;
		double[][] dist=new double[na][na];
		double[][] var=new double[na][na];
		String stringArtists="";
		int p=0;
		HashMap<String,Integer> artistMap=new HashMap<String,Integer>();
		for(Artist a:artists){
			if(p>0){stringArtists+=",";}
			stringArtists+="\""+a.name+"\"";
			artistMap.put(a.name, p);
			p++;

		}

		String query="select t1.artist,t2.artist,t1.var+t2.var from artistcount t1 cross join artistcount t2\n";
		query+="on t1.artist in ("+stringArtists+") and\n";
		query+="t2.artist in ("+stringArtists+")\n";
		query+="and t1.artist<t2.artist\n";
		query+="order by t1.artist,t2.artist\n";

		try {
			ResultSet s=db.statement.executeQuery(query);
			s.first();

			for(int i=0;i<(na-1);i++){
				for(int j=i+1;j<na;j++){
					var[i][j]=s.getDouble(3);
					var[j][i]=s.getDouble(3);
					s.next();

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		query="select a1,a2,sum(g) as g,sum(l) as l,\n";
		query+="sum(score1) as s1,sum(score2) as s2 from(\n";
		query+="select t1.tag,t1.artist as a1,t2.artist as a2,\n";
		query+="greatest(t1.count/t3.count,t2.count/t4.count) as g,\n";
		query+="least(t1.count/t3.count,t2.count/t4.count) as l,\n";
		query+="t1.count/t3.count as score1,\n";
		query+="t2.count/t4.count as score2\n";
		query+="from (select * from artisttags \n";
		query+="where artist in ("+stringArtists+") and count>200\n";
		query+=") as t1 join\n";
		query+="(select * from artisttags where artist in ("+stringArtists+") and count>200) t2\n";
		query+="on t1.tag=t2.tag and t1.artist<t2.artist\n";
		query+="join artistcount t3 on t1.artist=t3.artist\n";
		query+="join artistcount t4 on t2.artist=t4.artist) t\n";
		query+="group by a1,a2\n";
	;
		try {
			ResultSet s=db.statement.executeQuery(query);
			s.first();
			int i=0;
			
			while(!s.isAfterLast()){
				int a1=artistMap.get(s.getString(1));
				int a2=artistMap.get(s.getString(2));
				double x=s.getDouble(3)-s.getDouble(5)-s.getDouble(6);
				double m=s.getDouble(4);
				dist[a1][a2]=m/(2+x);
				dist[a2][a1]=m/(2+x);
				s.next();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<na;i++){
			for(int j=0;j<na;j++){
				dist[i][j]=1-dist[i][j];
			}	
		}
	return dist;

}
static void printMatrix(double[][] M){
	int na=M.length;
	for(int i=0;i<na;i++){
		for(int j=0;j<na;j++){
			System.out.print(M[i][j]+",");
		}
		System.out.println("");
	}
}
}
