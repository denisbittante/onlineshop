package de.java2enterprise.onlineshop;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Valuation;

@WebServlet(urlPatterns = "/review", asyncSupported = true)
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Long prod_id = Long.valueOf(req.getParameter("product_id"));
		String comment = req.getParameter("comment");
		HttpSession session = req.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		Long user_id = customer.getId();

		Integer stars = 0;
		if (!req.getParameter("rate").equals("undefined")) {
			stars = Integer.valueOf(req.getParameter("rate"));

		}
		Valuation valuation = new Valuation();
		valuation.setProductId(prod_id);
		valuation.setStars(stars);
		valuation.setUserId(user_id);
		valuation.setTimeSubmitted(new Date());
		valuation.setValuationcomment(comment);

		try {
			persist(valuation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException(e.getMessage());

		}

	}

	public void persist(Valuation valuation) throws Exception {
		String[] autogeneratedKeys = new String[] { "id" };
		Connection con = ((DataSource) InitialContext.doLookup("jdbc/jndiOnlineshop")).getConnection();
		PreparedStatement stmt = con.prepareStatement(
				"INSERT INTO onlineshop.valuation ( time_submitted, stars, product_id, user_id, valuationcomment )"
						+ " VALUES ( SYSTIMESTAMP  , ?, ?, ?, ?) ",
				autogeneratedKeys);
		stmt.setInt(1, valuation.getStars());
		stmt.setLong(2, valuation.getProductId());

		stmt.setLong(3, valuation.getUserId());
		stmt.setString(4, valuation.getValuationcomment());
		stmt.executeUpdate();

		ResultSet rs = stmt.getGeneratedKeys();
		Long id = null;
		while (rs.next()) {
			id = rs.getLong(1);
			valuation.setId(id);
		}
		con.close();
	}

}
