package de.java2enterprise.onlineshop.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.java2enterprise.onlineshop.model.Valuation;

public class ValuationToJsonConverter {

	public static String convert(List<Valuation> valList) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{ \"items\": [ ");
		for (int i = 0; i < valList.size(); i++) {

			Valuation valuation = valList.get(i);
			buffer.append("{\n");
			buffer.append(jsonLabel("id"));
			buffer.append(valuation.getId());
			buffer.append(",\n");
			buffer.append(jsonLabel("productId"));
			buffer.append(valuation.getProductId());
			buffer.append(",\n");
			buffer.append(jsonLabel("stars"));
			buffer.append(valuation.getStars());
			buffer.append(",\n");

			Date timeSubmitted = valuation.getTimeSubmitted();
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
			String formatedTime = dt1.format(timeSubmitted);

			buffer.append(jsonLabel("time"));
			buffer.append("\"" + formatedTime + "\"");
			buffer.append(",\n");
			buffer.append(jsonLabel("comment"));
			String valuationcomment = valuation.getValuationcomment();
			if (valuationcomment != null) {
				buffer.append("\"" + valuationcomment.replaceAll("\n", "<br>") + "\"");
			} else {
				buffer.append("\"\"");
			}
			buffer.append(",\n");
			buffer.append(jsonLabel("userName"));
			buffer.append("\"" + valuation.getUserEmail() + "\"");

			buffer.append(valList.size() == i + 1 ? "}" : "},");
		}
		buffer.append("] }");
		String gson = buffer.toString();
		return gson;
	}

	private static String jsonLabel(String label) {
		return "\"" + label + "\" : ";
	}
}
