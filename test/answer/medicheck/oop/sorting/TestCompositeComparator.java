package answer.medicheck.oop.sorting;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import quiz.medicheck.MedicalCheck;

public class TestCompositeComparator {

	@Before
	public void setUp() throws Exception {
	}

	/** 도전 과제 : 다중정렬
	 * 
	 * input.txt에는 키가 같은 학생이 세 명 있습니다.
	 * 
	 *  2 2006  박경혁 180.3cm
	 *  3 2008  장혁수 180.3cm
	 *  4 2009  임근형 180.3cm
	 * 
	 * 키의 내림차순으로 출력할 때 키가 같은 경우 이름의 오름차순으로
	 * 출력해달라는 요구사항이 추가되었습니다.(즉 아래처럼...)
	 * 
	 *  2 2006  박경혁 180.3cm
	 *  3 2009  임근형 180.3cm
	 *  4 2008  장혁수 180.3cm
	 * 
	 * 위와 같이 출력될 수 있도록 Comparator구현을 만들어보세요.
	 * 
	 */
	@Test
	public void test() {
		List<MedicalCheck> mChecks = Arrays.asList(
				new MedicalCheck("6", "박경혁", "M", 180.3, 78), // 2nd
				new MedicalCheck("8", "장혁수", "M", 180.3, 78), // 4th
				new MedicalCheck("9", "임근형", "M", 180.3, 78), // 3rd
				new MedicalCheck("2", "김상호", "M", 190.8, 78)  // 1st
				);
		// 1. 키의 내림차순
		// 2. (키가 같을경우) 이름의 오름차순
		CompositeComparator ccomp = new CompositeComparator(
				new HeightComparator(false), 
				new NameComparator());
		
		Collections.sort(mChecks, ccomp);
		
		assertEquals ( "박경혁", mChecks.get(1).getName());
		assertEquals ( "임근형", mChecks.get(2).getName());
		assertEquals ( "장혁수", mChecks.get(3).getName());
		
	}

}
