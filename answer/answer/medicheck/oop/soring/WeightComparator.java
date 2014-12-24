package answer.medicheck.oop.soring;

import java.util.Comparator;

import quiz.medicheck.MedicalCheck;

public class WeightComparator implements Comparator<MedicalCheck> {

	
	private boolean asc = true;
	
	public WeightComparator() {
		this ( true );
	}
	
	public WeightComparator(boolean asc) {
		this.asc = asc;
	}
	
	@Override
	 
	public int compare(MedicalCheck o1, MedicalCheck o2) {
		double weight1 = o1.getWeight();
		double weight2 = o2.getWeight();
		int dir = asc ? 1 : -1;
		
		if (weight1 == weight2) {
			return 0; 
		}else if (weight1 > weight2){
			return 1 * dir;
		}else {
			return -1 * dir;
		}
		
		
	}

}
