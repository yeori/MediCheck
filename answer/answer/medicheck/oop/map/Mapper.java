package answer.medicheck.oop.map;

import quiz.medicheck.MedicalCheck;

public interface Mapper<V, T extends MedicalCheck> {
	
	public V map( T type) ;
}
