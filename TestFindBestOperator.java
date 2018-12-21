package com.icss;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.icss.*;

public class TestFindBestOperator {

	@Test
	public void testBestOperator() {
		FindBestOperator  fOperator = new FindBestOperator();
		Map<String,Map<String,String>> map = fOperator.getopWisePrice();
		fOperator.findBestOperator(map, "4673212345");
		assertEquals("lyca", fOperator.getBestOperator());
		assertEquals(1.0, fOperator.getBestUnitPrice(),0.0f);
	}

}
