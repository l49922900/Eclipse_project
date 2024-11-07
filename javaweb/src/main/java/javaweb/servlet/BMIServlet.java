package javaweb.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bmi")

/**
 * 執行網址: http://localhost:8080/javaweb/bmi?h=170&w=60
 * 得到 bmi 的資料
 * */

public class BMIServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		// 取得網址?後的參數
		String h0 = req.getParameter("h");
		String w0 = req.getParameter("w");
		
		
		// 檢查參數
		if(h0 == null || w0 == null) {
			req.setAttribute("message", "請輸入");
			req.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(req, resp);
			return;
		}
		
		// 計算 bmi
		double h = Double.parseDouble(h0);
		double w = Double.parseDouble(w0);	
		double bmi = w/Math.pow(h/100, 2);
		
		// 將資料傳給 jsp
		req.setAttribute("height",h);
		req.setAttribute("weight", w);
		req.setAttribute("bmi", bmi);
		
		
		// 透過 jsp 印出資料
		req.getRequestDispatcher("/WEB-INF/view/bmi.jsp").forward(req, resp);
		
		
	}
	
}
