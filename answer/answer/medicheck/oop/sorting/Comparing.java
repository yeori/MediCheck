package answer.medicheck.oop.sorting;

import answer.medicheck.oop.map.Mapper;
import quiz.medicheck.MedicalCheck;
/**
 * 주어진 mapper를 이용해서 o1과 o2에서 특정 field 값을 얻어낸 후 비교합니다.
 * 
 * 현재 MedicalCheck 클래스는 키, 몸무게, 성별, 이름의 4가지 필드를 가지고 있기 때문에
 * 4가지 comparator 구현체만 있으면 충분합니다.
 * 
 * 하지만 MedicalCheck 클래스에 여러종류의 신체검사 수치가 추가되면
 * 각각의 필드에 대해서 comparator 구현체가 필요해질 수 있습니다.
 * (이로 인해 수많은 comparator구현체가 넘쳐나기 시작함)
 * 
 * WeighComparator, HeightComparator, GenderComparator와 같이 각각의 필드에 대응하는
 * 구현들을 만들기보다는 정렬에 사용되는 필드값 선택을 Mapper 구현체에게 위임함으로써
 * 추상화를 한단계 더 높일 수 있습니다.
 * 
 * 이러한 추상화는 애플리케이션의 규모에 맞춰서 단계적으로 높이는 것이 바람직합니다.
 * 
 * 추상화의 수준이 너무 낮으면 코드 중복이 발생하거나 애플리케이션의 유연성이 떨어지고,
 * 추상화의 수준이 너무 높으면 유연성은 확보되나 생산성이 떨어지게 됩니다.
 * 
 * @author chminseo
 * @see FieldComparing
 * @param <V> - 비교에 사용될 동일한 맥락의 값(키, 몸무게 등). mapper가 선택해줌.
 * @param <T> - MedicalCheck 타입과 그 하위타입들
 */
public interface Comparing<V extends Comparable<V>, T extends MedicalCheck> {
	/**
	 * o1과 o2를 비교 우선순위를 정수값(-1, 0, 1)로 반환합니다.
	 * 
	 * 이 메소드는 java collection framework에서 제공하는 Collections.sort() 와 호환되지 않기 때문에
	 * 어댑터 역할을 하는 별도의 Comarator 구현체가 제공되어야 합니다.
	 * 
	 * @param o1
	 * @param o2
	 * @param mapper - o1과 o2에서 특정 필드를 뽑아내는 역할을 합니다.
	 * @return  o1이 o2보다 우선순위가 높으면 1, o2가 o1보다 우선순위가 높으면 -1, 동일한 우선순위이면 0
	 */
	public int compare( T o1, T o2, Mapper<V, T> mapper ) ;
}
