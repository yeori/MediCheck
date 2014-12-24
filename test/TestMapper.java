import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import quiz.medicheck.MedicalCheck;
import answer.medicheck.oop.map.Mapper;
import answer.medicheck.oop.map.WeightMapper;


public class TestMapper {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Mapper<Double, MedicalCheck> weightMapper = new WeightMapper();
		
		MedicalCheck mc = new MedicalCheck("333", "ddd"	, "F", 188, 45);
		
		assertEquals ( 45, weightMapper.map(mc).doubleValue(), 0.1);
		
	}
	
	public static class HeightMapper implements Mapper<Double, MedicalCheck> {

		@Override
		public Double map(MedicalCheck type) {
			return type.getHeight();
		}
		
	}

}
