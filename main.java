package musicClustering;

import java.util.ArrayList;

public class main {

	public static void main(String[] args) {

		// List of artist to be clustered
		String[] musicList={
				"50 Cent",
				"Antonio Vivaldi",
				"Charlie Parker",
				"Coldplay",
				"Deep Purple",
				"Duke Ellington",
				"Eminem",
				"Fugees",
				"Genesis",
				"Good Charlotte",
				"Green Day",
				"Iron Maiden",
				"Jay-Z",
				"Johann Sebastian Bach",
				"Ludwig van Beethoven",
				"Marilyn Manson",
				"Miles Davis",
				"Muse",
				"Pink Floyd",
				"Rammstein",
				"Supertramp",
				"System of a Down",
				"The Who",
				"U2",
				"Wolfgang Amadeus Mozart",
				"Wu-Tang Clan"
		};

		Artist[] artists=new Artist[musicList.length];
		for(int i=0;i<musicList.length;i++){
			artists[i]=new Artist(musicList[i]);
		}

		// Get a database 
		String username="";
		String password="";
		DatabaseConnector dc=new DatabaseConnector();
		dc.setConnection("jdbc:mysql://localhost:3306/lastfm",username, password);
		ArrayList<Artist>[] clusters = null;

		int mode=1;
		switch(mode){
		case 0:
			// Compute bad of words coefficients
			BagOfWords.computeCoefficients(artists, dc,20);
			// Cluster the artists
			clusters =Kmeans.kmeans(artists, 6);	
			break;
		case 1:

			// Compute the Weighted Jacquard Distance matrix
			double[][] distanceMatrix=WeightedJacquard.computeDistance(artists, dc);
			// WeightedJacquard.printMatrix(distanceMatrix);

			// Cluster the artists
			clusters=Kmedoids.kmeans(artists, distanceMatrix, 3);
			break;

		}

		int p=1;

		for(ArrayList<Artist> arrayArtist:clusters){
			System.out.println("Playlist "+p+" :");
			for(Artist a:arrayArtist){
				System.out.println(a.name);
			}
			System.out.println("");
			p++;
		}

	}

}
