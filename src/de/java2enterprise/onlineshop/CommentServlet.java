package de.java2enterprise.onlineshop;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import de.java2enterprise.onlineshop.converter.ValuationToJsonConverter;
import de.java2enterprise.onlineshop.model.Valuation;

@WebServlet(value = "/comments")
public class CommentServlet extends HttpServlet {

	private static final long serialVersionUID = 5361978195631685305L;

	/**
	 * Liefert die Kommentare zu einem Produkt zurück.
	 * 
	 * @throws Exception
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Long productId = Long.valueOf(req.getParameter("productId").toString());
		List<Valuation> find;
		try {
			find = find(productId);

			String gson = ValuationToJsonConverter.convert(find);
			log(gson);

			resp.getWriter().write(gson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Valuation> find(Long productId) throws Exception {
		Connection con = ((DataSource) InitialContext.doLookup("jdbc/jndiOnlineshop")).getConnection();
		PreparedStatement stmt = con.prepareStatement(
				"select val.*, (cust.email) as email from  onlineshop.valuation val join onlineshop.customer cust on val.user_id = cust.id  "
				+ "where val.product_id =? and (length(val.valuationcomment)>0 or val.stars >  0) order by val.TIME_SUBMITTED");

		stmt.setLong(1, productId);
		ResultSet rs = stmt.executeQuery();

		List<Valuation> items = new ArrayList<Valuation>();

		while (rs.next()) {

			Valuation item = new Valuation();

			Timestamp ts = rs.getTimestamp("time_submitted");
			if (ts != null) {
				Date timeSubmitted = new Date(ts.getTime());
				item.setTimeSubmitted(timeSubmitted);
			}

			item.setId(Long.valueOf(rs.getLong("id")));

			item.setStars(Integer.valueOf(rs.getInt("stars")));

			String comment = rs.getString("valuationcomment");
			if (comment != null && !comment.isEmpty()) {
				item.setValuationcomment(comment);
			}
			item.setProductId(Long.valueOf(rs.getLong("product_id")));
			item.setUserEmail(rs.getString("email"));
			item.setUserId(Long.valueOf(rs.getLong("user_id")));

			items.add(item);
		}

		con.close();
		return items;
	}

}
