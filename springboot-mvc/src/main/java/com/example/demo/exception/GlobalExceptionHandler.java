/*
@ControllerAdvice 是 Spring Boot 中用於
全局異常處理（Global Exception Handling）、全局資料綁定（Global Data Binding）和模型屬性設定（Model Attribute Setting）的註解。
這個註解可以用來定義一個全局的控制器建議，讓所有控制器在處理請求時共用某些處理邏輯。


全局異常處理（Global Exception Handling）：利用 @ExceptionHandler 搭配 @ControllerAdvice，可以將異常處理集中管理，
不必在每個控制器內單獨處理。例如，當應用程式內發生特定異常時，可以自動返回自訂的錯誤頁面或 JSON 錯誤訊息給使用者，提升異常處理的統一性。


全局資料綁定（Global Data Binding）：可以透過 @InitBinder 方法在全域範圍內為控制器中的所有請求資料進行自動轉換或格式化，
例如日期格式的轉換，或針對特殊參數進行綁定。


模型屬性設定（Model Attribute Setting）：可以透過 @ModelAttribute 設定一些共用的資料，
讓所有控制器或控制器中的特定方法都可以直接存取，減少在每個控制器中重複設定的需求。
 */

package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.response.ApiResponse;

// 利用 @ControllerAdvice 的特性來處理全局錯誤
@ControllerAdvice
public class GlobalExceptionHandler {
	
	// 當系統發生 NumberFormatException 或 HttpStatus.BAD_REQUEST 時的解決方法
	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponse<Object>> handleNumberFormatException(NumberFormatException e) {
		ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "無效的數據格式, " + e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
	}
	
	// 當系統發生 RuntimeException
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse<Object>> handleRuntimeNumberFormatException(RuntimeException e) {
		ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.FORBIDDEN.value(), "執行時期錯誤, " + e);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
	}
	
	// 當系統發生 Exception 或 HttpStatus.INTERNAL_SERVER_ERROR 時的解決方法
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
		ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "伺服器內部錯誤, " + e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
	}
	
}