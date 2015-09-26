
public class Weights {

	public int i,j,k;
	
	public double w[][][];
	public Weights(int i,int j, int k){
	this.i=i;
	this.j=j;
	this.k=k;
	w= new double[i][j][k];
	}
	public void addWeight(int i,int j,int k, double value){
		
		this.w[i][j][k]=value; 
	}
	public double getWeight(int i,int j,int k){
	return this.w[i][j][k];
	}
}