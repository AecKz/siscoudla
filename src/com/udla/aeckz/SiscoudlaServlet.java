package com.udla.aeckz;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class SiscoudlaServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Programando en Cloud :D");
	}
}
