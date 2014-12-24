import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import answer.medicheck.oop.map.BmiMapper;
import quiz.medicheck.MedicalCheck;


public class TestBmiMapper {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_bmi() {
		MedicalCheck mc = new MedicalCheck("ddd", "ddd", "F", 160, 55);
		// 키 : 160cm
		// 몸무게 : 55kg
		// bmi = 55 / (1.6*1.6)
		
		BmiMapper mapper = new BmiMapper();
		assertEquals ( 55/(1.6*1.6), mapper.map(mc).doubleValue(), 0.01);
	}

}
