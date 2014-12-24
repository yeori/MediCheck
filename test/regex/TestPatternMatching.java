package regex;

import static org.junit.Assert.*;

import java.util.Scanner;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPatternMatching {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Scanner scanner = new Scanner("d.d.c=java.lang.Utils\na.b.c=java.a.DD");
		scanner.useDelimiter(Pattern.compile("[=\\s]"));
		assertEquals ( "d.d.c", scanner.next());
		assertEquals ( "java.lang.Utils", scanner.next());
		assertEquals ( "a.b.c", scanner.next());
		assertEquals ( "java.a.DD", scanner.next());
		scanner.close();
	}

}
