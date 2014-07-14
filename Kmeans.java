package musicClustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

public class Kmeans{

	public static double distance(double c1[],double c2[]){
		double sum=0;
		for(int i=0;i<c1.length;i++){
			sum+=Math.pow(c1[i]-c2[i], 2);
		}
		return sum;
	}

	public static double[] barycenter(ArrayList<Artist> artists,int dimension){
	
		double[] b=new double[dimension];
		double count=0;
		for(Artist a:artists){

			for(int i=0;i<dimension;i++){
				b[i]+=a.coefficients[i];
			}
			count++;
		}
		for(int i=0;i<dimension;i++){
			b[i]/=count;
		}
		return b;
	}
	static ArrayList<Artist>[]  kmeans(Artist[] artists,int k){
		int dimension=artists[0].coefficients.length;
		double[][] centerPoints=new double[k][dimension];
		ArrayList<Artist>[] clusters=new ArrayList[k];
		for(int i=0;i<k;i++){
			clusters[i]=new ArrayList<Artist>();
			clusters[i].add(artists[i]);
		}
		for(int j=0;j<10;j++){
			// We compute the center points and empty the clusters
			for(int i=0;i<k;i++){
				centerPoints[i]=barycenter(clusters[i],dimension);
				clusters[i]=new ArrayList<Artist>();
			}
			// Put each artist in a cluster
			for(Artist a:artists){
				double minDist=a.distanceTo(centerPoints[0]);
				int minDistCluster=0;	
				for(int i=1;i<k;i++){
					double d=a.distanceTo(centerPoints[i]);
					if(d<minDist){
						minDist=d;
						minDistCluster=i;
					}
				}
				clusters[minDistCluster].add(a);
			}

		}	

		return clusters;

	}

}
