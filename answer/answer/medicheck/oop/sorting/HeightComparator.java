package answer.medicheck.oop.sorting;

import java.util.Comparator;

import quiz.medicheck.MedicalCheck;

public class HeightComparator implements Comparator<MedicalCheck> {

	/*
	 * asc 가 true이면 오름차순으로 정렬합니다.
	 */
	private boolean asc = true;
	
	public HeightComparator() {
		this ( true );
	}
	
	public HeightComparator(boolean asc) {
		this.asc = asc;
	}
	
	@Override
	public int compare(MedicalCheck o1, MedicalCheck o2) {
		double h1 = o1.getHeight();
		double h2 = o2.getHeight();
		
		int dir = asc ? 1 : -1 ;
		
		if ( h1 == h2) {
			return 0;
		} else if ( h1 > h2 ) {
			return dir * 1;
		} else {
			return dir * -1;
		}
	}

}
