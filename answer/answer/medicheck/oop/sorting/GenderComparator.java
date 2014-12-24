package answer.medicheck.oop.sorting;

import java.util.Comparator;

import quiz.medicheck.MedicalCheck;

public class GenderComparator implements Comparator<MedicalCheck> {

	@Override
	public int compare(MedicalCheck o1, MedicalCheck o2) {
		String g1 = o1.getSex();
		String g2 = o2.getSex();
		return g1.compareTo(g2);
	}

}
