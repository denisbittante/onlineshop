package de.java2enterprise.onlineshop.converter;

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
			buffer.append(jsonLabel("time"));
			buffer.append("\"" + valuation.getTimeSubmitted() + "\"");
			buffer.append(",\n");
			buffer.append(jsonLabel("comment"));
			buffer.append("\"" + valuation.getValuationcomment() + "\"");
			buffer.append(",\n");
			buffer.append(jsonLabel("userId"));
			buffer.append(valuation.getUserId());

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
