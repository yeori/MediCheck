package answer.medicheck.oop.sorting;

import java.util.Comparator;

import quiz.medicheck.MedicalCheck;

/**
 * ex)
 * new CompositeComparator( new GenderComparator(), new HeightComparator(false) , new WeightComparator(true))
 * @author Suhkyung
 *
 */
public class CompositeComparator implements Comparator<MedicalCheck> {
	private Comparator<MedicalCheck> [] compositeComps ;
	
	public CompositeComparator(Comparator<MedicalCheck> ... mComps) {
		// TODO Auto-generated constructor stub
		compositeComps = mComps;
		
	}
	@Override
	public int compare(MedicalCheck o1, MedicalCheck o2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
