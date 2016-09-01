package com.hxyc.util.verifycode;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

public class VerifyResult extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String validateC = ((String) request.getSession().getAttribute("rand"))
				.toLowerCase();//session中的验证码		
		String veryCode = (request.getParameter("c")).toLowerCase();//用户输入的验证码，这里接收参数的时候注意中文的乱码问题
		PrintWriter out = response.getWriter();
		if (veryCode != null) {
			if (validateC.equals(veryCode)) {
				out.println("验证码正确");
			} else {
				out.println("验证码错误");
			}
		} else {
			out.println("验证码为空");
		}
		out.flush();
		out.close();
	}

}