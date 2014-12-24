import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import quiz.medicheck.MedicalCheck;
import answer.medicheck.oop.sorting.HeightComparator;
import answer.medicheck.oop.sorting.WeightComparator;


public class TestHeightComparator {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_height_comparator() {
		HeightComparator hcomp = new HeightComparator(false); // 오름차순
		
		ArrayList<MedicalCheck> list = new ArrayList<MedicalCheck>();
		list.add( new MedicalCheck("a", "a", "F", 177, 56));
		list.add( new MedicalCheck("b", "b", "F", 140, 66));
		list.add( new MedicalCheck("c", "c", "F", 155, 66));
		
		Collections.sort(list, hcomp);
		
		assertEquals ( "a", list.get(0).getName());
		assertEquals ( "c", list.get(1).getName());
		assertEquals ( "b", list.get(2).getName());
	}
	
	@Test
	public void test_weight_comparator() {
		WeightComparator wcomp = new WeightComparator(); // 오름차순
		
		ArrayList<MedicalCheck> list = new ArrayList<MedicalCheck>();
		list.add( new MedicalCheck("a", "a", "F", 177, 56));
		list.add( new MedicalCheck("b", "b", "F", 140, 46));
		list.add( new MedicalCheck("c", "c", "F", 155, 66));
		
		Collections.sort(list, wcomp);
		
		assertEquals ( "b", list.get(0).getName());
		assertEquals ( "a", list.get(1).getName());
		assertEquals ( "c", list.get(2).getName());
		
		Collections.sort( list, new WeightComparator(false));
		assertEquals ( "b", list.get(2).getName());
		assertEquals ( "a", list.get(1).getName());
		assertEquals ( "c", list.get(0).getName());
		
		
	}

}
