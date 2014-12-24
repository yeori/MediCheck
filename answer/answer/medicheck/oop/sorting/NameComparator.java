package answer.medicheck.oop.sorting;

import java.util.Comparator;

import quiz.medicheck.MedicalCheck;

public class NameComparator implements Comparator<MedicalCheck> {

	@Override
	public int compare(MedicalCheck o1, MedicalCheck o2) {
		String name1 = o1.getName();
		String name2 = o2.getName();
		return name1.compareTo(name2);
	}

}
