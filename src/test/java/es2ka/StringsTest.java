package es2ka;

import org.elasticsearch.common.Strings;
import org.junit.Test;

public class StringsTest {
	@Test
	public void tesUnderscoreCase() {
//		String toUnderscoreCase1 = Strings.toUnderscoreCase("cluster.name");
		String toUnderscoreCase = Strings.toUnderscoreCase("cluster.name");
		System.out.println(toUnderscoreCase);
	}

}
