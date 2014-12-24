package answer.medicheck.oop.sorting;

import java.util.Comparator;

import quiz.medicheck.MedicalCheck;

/**
 * ex)
 * new CompositeComparator( new GenderComparator(), new HeightComparator(false) , new WeightComparator(true))
 * 
 *
 */
public class CompositeComparator implements Comparator<MedicalCheck> {
	private Comparator<MedicalCheck> [] compositeComps ;
	
	@SafeVarargs
	public CompositeComparator(Comparator<MedicalCheck> ... mComps) {
		compositeComps = mComps;
		
	}
	/**
	 * 배열로 주어진 comparator 구현체를 순서대로 호출한 후 결과값이 0이 아니면
	 * 그 값(1 또는 -1)을 반환합니다. 
	 * 
	 * 결과값이 0이면 다음 comparator에게 정렬 우선 순위를 결정하게 합니다.
	 * 
	 * 다른 예제들과 마찬가지로 comparator 배열에 담긴 실제 구현이 어떤 기준에 따라서 
	 * 
	 * 정렬 우선 순위를 결정하는지 알 필요가 없습니다.
	 * 
	 * CompositeComparator 입장에서는 전달받은 comparator 배열들이   
	 * 
	 */
	@Override
	public int compare(MedicalCheck o1, MedicalCheck o2) {
		int result = 0;
		for( int i = 0 ; i < compositeComps.length ; i++ ) {
			result = compositeComps[i].compare(o1, o2);
			if ( result != 0 ) {
				break;
			}
		}
		
		return result;
	}

}
