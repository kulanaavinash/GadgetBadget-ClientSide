package com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logindao.Login;

@WebServlet("/Login")
public class LoginApi extends HttpServlet {
	private Login login = new Login();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uName = request.getParameter("uname");
		String uPassword = request.getParameter("pass");

		if (login.authentication(uName, uPassword)) {
			HttpSession session = request.getSession();
			session.setAttribute("username", uName);
			response.sendRedirect("Index.jsp");
		} else {
			response.sendRedirect("Login.jsp");
		}
	}

}