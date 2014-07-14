package musicClustering;

import java.util.ArrayList;

public class Kmedoids {
	static ArrayList<Artist>[]  kmeans(Artist[] artists,double[][] dist,int k){
		int N=dist.length;
		int[] centerPoints=new int[k];
		ArrayList<Integer>[] clusters=new ArrayList[k];
		for(int i=0;i<k;i++){
			clusters[i]=new ArrayList<Integer>();
			clusters[i].add(i);
		}
		for(int j=0;j<20;j++){
			// We compute the center points and empty the clusters
			for(int i=0;i<k;i++){
				centerPoints[i]=barycenter(clusters[i],dist);
				clusters[i]=new ArrayList<Integer>();
			}
			for(int i=0;i<N;i++){
				double minDist=dist[i][centerPoints[0]];
				double minDistCluster=0;
				for(int l=1;l<k;l++){
					double d=dist[i][centerPoints[l]];
					if(d<minDist){minDist=d;minDistCluster=l;}
				}
				clusters[(int) minDistCluster].add(i);
			}
		}
		ArrayList<Artist>[] clustersArtist=new ArrayList[k];	
		int i=0;
		for(ArrayList<Integer> c:clusters){
			clustersArtist[i]=new ArrayList<Artist>();
			for(int j:c){
				clustersArtist[i].add(artists[j]);
			}
			i++;
		}
	return clustersArtist;
	}

	private static int barycenter(ArrayList<Integer> cluster, double[][] dist) {
		// This method compute the new medoids
		int N=cluster.size();
		double minTotalDist=10000;
		int minTotalDistIndex=-1;
		for(int i:cluster){
			double totalDist=0;
			for(int j:cluster){
				totalDist+=dist[i][j];
			}
			if(totalDist<minTotalDist){minTotalDist=totalDist;minTotalDistIndex=i;}
		}
		return minTotalDistIndex;
	}
}
