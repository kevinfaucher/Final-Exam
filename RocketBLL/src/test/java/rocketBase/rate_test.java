package rocketBase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import exceptions.RateException;

public class rate_test {

	@Test
	public void test() {
		assert (1 == 1);
	}

	@Test
	public void getRatetest() throws RateException {
		assertEquals(RateBLL.getRate(700), 4, 1);
	}

	@Test(expected = RateException.class)
	public void getratetest2() throws RateException {
		RateBLL.getRate(200);

	}

}