/*
伺服器渲染（Server-Side Rendering, SSR）和客戶端渲染（Client-Side Rendering, CSR）是兩種將資料渲染到網頁的方式，
各自適合不同的應用場景。
1.SSR 適合靜態內容多、注重首次載入速度和 SEO 的應用。
2.CSR 適合需要動態互動、高頻更新頁面內容的應用。


@Controller 的使用時機
適用於 SSR 架構：當我們使用 @Controller 時，通常會將資料透過 JSP 或 Thymeleaf 等模板引擎進行伺服器端渲染（SSR），將渲染好的 HTML 回傳給瀏覽器。
適合傳統的 MVC 應用：使用 @Controller 時，控制器會根據路由來處理請求，並將資料傳遞至視圖層。視圖層在伺服器端進行 HTML 渲染，然後將完整的頁面送到用戶端顯示。
整體渲染的適用性：在需求中需要整體頁面渲染的情況下適用，例如單純的網頁展示、表單提交等。



@RestController 的使用時機
適用於 CSR 架構：@RestController 通常用於 RESTful API，會將資料以 JSON 格式回傳，適合用在客戶端渲染（CSR）的場景中，透過前端 JavaScript 框架（例如 React、Vue）渲染資料到 HTML。
包含 @ResponseBody：@RestController 相當於 @Controller 加上 @ResponseBody，會自動將回傳物件序列化為 JSON，這樣前端就可以直接接收 JSON 資料進行處理。
適合單頁應用（SPA）和前後端分離專案：若是要設計前後端分離的系統，且前端負責渲染頁面內容，這時候 @RestController 更為適合。


混合模式：@Controller + @ResponseBody
部分 Ajax 使用場景：若是希望使用 SSR 為主，但在部分功能上需要使用 Ajax 技術進行非同步更新，可以在 @Controller 方法上加上 @ResponseBody，將資料以 JSON 格式回傳給前端，讓前端在特定情況下進行非同步渲染。
適用於傳統的 MVC 項目中增加一些非同步功能：例如希望透過 Ajax 部分更新頁面而不是刷新整個頁面，可以選擇這種混合方式來實現。
 */

package com.example.demo.controller;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.bean.Book;
import com.example.demo.response.ApiResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// 了解各種不同 URL 與參數的傳遞接收
@RestController // 免去撰寫 @ResponseBody, 但若有要回傳 jsp 則不可使用
@RequestMapping("/api") // 統一 URL 前綴
public class ApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	/** 
	 * 1.歡迎頁 
	 * 路徑: /welcome
	 * 路徑: /home
	 * 網址: http://localhost:8080/api/welcome
	 * 網址: http://localhost:8080/api/home
	 */
	@GetMapping(value = {"/welcome", "/home"})
	public String welcome() {
		// 確認是否有執行到此方法
		logger.info("這是一條日誌訊息");
		return "Welcome";
	}
	
	/**
	 * 2. ?帶參數
	 * 路徑: /greet?name=John&age=18
	 * 路徑: /greet?name=Mary
	 * 網址: http://localhost:8080/api/greet?name=John&age=18
	 * 結果: Hi John, 18 (成年)
	 * 網址: http://localhost:8080/api/greet?name=Mary
	 * 結果: Hi Mary, 0 (未成年)
	 * 限制: name 參數一定要加, age 參數可不加(若沒有加 age 參數則會給初始值 0)
	 * */
	@GetMapping("/greet")
	public String greet(@RequestParam(value = "name", required = true) String username,
						@RequestParam(value = "age", required = false, defaultValue = "0") Integer userage) {
		// 觀察參數
		logger.info("username = " + username + ", userage = " + userage);
		return String.format("Hi %s, %d (%s)", username, userage, userage >= 18?"成年":"未成年");
		
	}
	
	// 3. 上述 2 的精簡寫法
	@GetMapping("/greet2")
	public String greet2(@RequestParam String name,
						 @RequestParam(defaultValue = "0") Integer age) {
		
		return String.format("Hi %s, %d (%s)", name, age, age >= 18?"成年":"未成年");
		
	}
	
	/**
	 * 4. Lab 練習 I
	 * 路徑: /bmi?h=170&w=60
	 * 網址: http://localhost:8080/api/bmi?h=170&w=60
	 * 執行結果: bmi = 20.76
	 * */
	@GetMapping("/bmi")
	public String bmi(@RequestParam Double h, @RequestParam Double w) {
		double bmi = w / Math.pow(h/100, 2);
		//return String.format("bmi = %.2f", bmi);
		return """
				{
				 "bmi": %.2f
				}
				""".formatted(bmi); // 手動拼寫 json 格式
	}
	
	/**
	 * 5. 同名多筆的資料
	 * 路徑: /age?age=17&age=21&age=20
	 * 網址: http://localhost:8080/api/age?age=17&age=21&age=20
	 * 計算出平均年齡
	 */
	//@GetMapping("/age")
	@GetMapping(value = "/age", produces = "application/json;charset=utf-8")
	public ResponseEntity<ApiResponse<Object>> getAverageOfAge(@RequestParam("age") List<String> ages) {
		double avgOfAge = ages.stream().mapToInt(Integer::parseInt).average().getAsDouble();
		Object data = Map.of("平均年齡", String.format("%.1f", avgOfAge));
		//return ResponseEntity.status(200).body(ApiResponse.success("查詢成功", data));
		return ResponseEntity.ok(ApiResponse.success("查詢成功", data));
	}
	
	/*
	 * 6. Lab 練習: 得到多筆 score 資料
	 * 路徑: "/exam?score=80&score=100&score=50&score=70&score=30"
	 * 網址: http://localhost:8080/api/exam?score=80&score=100&score=50&score=70&score=30
	 * 請自行設計一個方法，此方法可以
	 * 印出: 最高分=?、最低分=?、平均=?、總分=?、及格分數列出=?、不及格分數列出=?
	 * (支援中文字印出) 
	 * 提示: IntSummaryStatistics, Collectors.partitioningBy
	 * */
	@GetMapping(value = "/exam", produces = "application/json;charset=utf-8")
	public ResponseEntity<ApiResponse<Object>> getExamInfo(@RequestParam("score") List<String> scores) {
		// 統計資料
		IntSummaryStatistics stat = scores.stream().mapToInt(Integer::parseInt).summaryStatistics();
		// 利用 Collectors.partitioningBy 分組
		// key=true 及格分數, key=false 不及格分數
		Map<Boolean, List<String>> resultMap = scores.stream()
											.collect(Collectors.partitioningBy(score -> Integer.parseInt(score) >= 60)); 
		
		Object data = Map.of(
				"最高分", stat.getMax(), 
				"最低分", stat.getMin(),
				"平均", stat.getAverage(),
				"總分", stat.getSum(),
				"及格分數", resultMap.get(true),
				"不及格分數", resultMap.get(false)
				);
		
		return ResponseEntity.ok(ApiResponse.success("查詢成功", data));
		
	}
	
	/**
	 * 7. 多筆參數轉 Map
	 * name 書名(String), price 價格(Double), amount 數量(Integer), pub 出刊/停刊(Boolean)
	 * 路徑: /book?name=Math&price=12.5&amount=10&pub=true
	 * 路徑: /book?name=English&price=10.5&amount=20&pub=false
	 * 網址: http://localhost:8080/api/book?name=Math&price=12.5&amount=10&pub=true
	 * 網址: http://localhost:8080/api/book?name=English&price=10.5&amount=20&pub=false
	 * 自動會轉為 Map 集合
	 * */
	//@GetMapping("/book")
	public ResponseEntity<ApiResponse<Object>> getBookInfo(@RequestParam Map<String, Object> bookMap) {
		return ResponseEntity.ok(ApiResponse.success("查詢成功", bookMap));
	}
	
	/**
	 * 8. 多筆參數轉指定 Bean 物件
	 */
	@GetMapping("/book")
	public ResponseEntity<ApiResponse<Book>> getBookInfo(Book book) {
		return ResponseEntity.ok(ApiResponse.success("查詢成功", book));
	}
	
	/**
	 * 9. 路徑參數
	 * 路徑: /book/1
	 * 路徑: /book/3
	 * 網址: http://localhost:8080/api/book/1
	 * 網址: http://localhost:8080/api/book/3
	 */
	@GetMapping("/book/{id}")
	public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Integer id) {
		List<Book> books = List.of(
				new Book(1, "Math1", 12.5, 20, true),
				new Book(2, "Math2", 13.5, 21, false),
				new Book(3, "Math3", 14.5, 22, true),
				new Book(4, "Math4", 15.5, 23, false),
				new Book(5, "Math5", 16.5, 24, true));
		
		Optional<Book> optBook = books.stream().filter(b -> b.getId().equals(id)).findAny();
		
		if(optBook.isEmpty()) {
			throw new RuntimeException("查無此書");
		}
		
		return ResponseEntity.ok(ApiResponse.success("查詢成功", optBook.get()));
		
	}
	
	/**
	 * Lab: 請列出書本價格介於 13~17 之間且目前仍在版的書名
	 * 如何設計 GET API ?
	 * http://localhost:8080/api/book/pub/true?min=13&max=17
	 * http://localhost:8080/api/book/pub/false?min=13&max=17
	 * */
	@GetMapping("/book/pub/{pub}")
	public ResponseEntity<ApiResponse<List<String>>> queryBook(@PathVariable Boolean pub,
			@RequestParam Double min, @RequestParam Double max) {
		
		List<Book> books = List.of(
				new Book(1, "Math1", 12.5, 20, true),
				new Book(2, "Math2", 13.5, 21, false),
				new Book(3, "Math3", 14.5, 22, true),
				new Book(4, "Math4", 15.5, 23, false),
				new Book(5, "Math5", 16.5, 24, true));
		
		List<String> bookNames = books.stream().filter(b -> b.getPub())
											   .filter(b -> b.getPrice() >= min && b.getPrice() <= max)
											   .map(b -> b.getName())
											   .collect(Collectors.toList());
		
		if(bookNames.size() == 0) {
			throw new RuntimeException("此範圍查無任何書籍");
		}
		
		return ResponseEntity.ok(ApiResponse.success("查詢成功", bookNames));
	}
	
}

















