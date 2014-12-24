package answer.medicheck.oop.filter;

import quiz.medicheck.MedicalCheck;

public interface Filter<T extends MedicalCheck> {

	public boolean isAcceptable( T type) ;
	
}
