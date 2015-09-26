
public class MainApp {
	double range = (0.9 - -0.9) + 1;
	int i,j,k;
	public static void main(String[] args) {
		
		
		
		Weights first=new Weights(2,2,1);
		first.addWeight(1,1,0,0.2);
		
		System.out.println(first.getWeight(1,1,0));
	}

}
