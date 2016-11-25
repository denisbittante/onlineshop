package de.java2enterprise.onlineshop.converter;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import de.java2enterprise.onlineshop.model.Valuation;
import junit.framework.Assert;

@SuppressWarnings("deprecation")

public class ValuationToJsonConverterTest {

	@SuppressWarnings("static-access")
	@Test
	public void test() {
		Date timeSubmitted = new Date(Long.valueOf("1480041257521"));

		ValuationToJsonConverter testee = new ValuationToJsonConverter();

		ArrayList<Valuation> arrayList = new ArrayList<>();
		Valuation e = new Valuation();
		e.setId(Long.valueOf(10));
		e.setProductId(Long.valueOf(2));
		e.setStars(2);
		e.setTimeSubmitted(timeSubmitted);
		e.setUserEmail("a@b.ch");
		e.setUserId(Long.valueOf("1"));
		e.setValuationcomment("ist ja toll");
		arrayList.add(e);
		String convert = testee.convert(arrayList);

		String[] split = convert.split("\n");
		Assert.assertEquals("{ \"items\": [ {", split[0]);
		Assert.assertEquals("\"id\" : 10,", split[1]);
		Assert.assertEquals("\"productId\" : 2,", split[2]);
		Assert.assertEquals("\"stars\" : 2,", split[3]);
		Assert.assertEquals("\"time\" : \"2016-11-25 03:34:521\",", split[4]);
		Assert.assertEquals("\"comment\" : \"ist ja toll\",", split[5]);
		Assert.assertEquals("\"userName\" : a@b.ch}] }", split[5]);

	}

}
