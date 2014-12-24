package answer.medicheck.oop.context;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMediCheckContext {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_init() {
		String path = "answer/medicheck/oop/config.txt";
		MediCheckContext ctx = new MediCheckContext(path);
//		ctx.initModules(path);
		
		assertNotNull( ctx.getFilter("boy"));
		assertNotNull( ctx.getFilter("girl"));
		
		assertNotNull ( ctx.getMapper("bmi"));
		assertNotNull ( ctx.getMapper("height"));
		assertNotNull ( ctx.getMapper("weight"));
		
		assertNotNull ( ctx.getComparator("height"));
		assertNotNull ( ctx.getComparator("weight"));
		assertNotNull ( ctx.getComparator("height.desc"));
		assertNotNull ( ctx.getComparator("weight.desc"));
	}

}
