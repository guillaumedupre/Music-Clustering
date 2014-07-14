package musicClustering;

public class Artist {
String name;
double[] coefficients; 
public Artist(String s){
	name=s;
}
double distanceTo(double[] c){
	double sum=0;
	for(int i=0;i<coefficients.length;i++){
		sum+=Math.pow(c[i]-coefficients[i], 2);
	}
	return sum;
}

}
