package answer.medicheck.oop.sorting;

import answer.medicheck.oop.map.Mapper;

import quiz.medicheck.MedicalCheck;

public class FieldComparing<V extends Comparable<V>> implements Comparing<V, MedicalCheck>{

	@Override
	public int compare(MedicalCheck o1, MedicalCheck o2,
			Mapper<V, MedicalCheck> mapper) {
		V f1 = mapper.map(o1);
		V f2 = mapper.map(o2);
		return f1.compareTo(f2);
	}	
}
