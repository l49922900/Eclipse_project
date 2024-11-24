package Lambda20241017;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionalProgramming20241018 {
	/*
	Java 的指令式程式設計（Imperative Programming）與函數式程式設計（Functional Programming）是兩種不同的程式設計範式（programming paradigms）
	指令式程式設計（Imperative Programming）側重於「如何完成」一個操作，強調具體的步驟與狀態的變更。
	函數式程式設計（Functional Programming）則著重於「做什麼」，關注於描述資料之間的轉換與處理。
	
	////////////////////////////////////////////////////
	 */
	
	public static void main(String[] args) {
	/*
	指令式程式設計（Imperative Programming）:
	
		指令式程式設計是一種以逐步執行指令為主的編程方式。
		這種方法著重於描述程式應該「如何做」，也就是透過具體的步驟和指令，讓電腦按照規定的順序執行
		
		
	 特徵:
	 
	 	1.改變狀態（State Changes）：變數的狀態可以隨著程式執行的過程中被修改，例如賦值操作（x = x + 1）。
		2.流程控制（Control Flow）：使用 for 迴圈、while 迴圈、if-else 條件判斷等控制結構來控制執行的順序。
		3.命令序列（Sequences of Commands）：指令是按照一定的順序來執行的，每個指令都影響後續指令的結果。
		
	指令式設計適合的場景：如需要精確控制程式執行順序、或需要頻繁更新變數狀態的場景。	
	 */
		
//		例子:
		int sum = 0;
		for (int i = 1; i <= 10; i++) {
		    sum += i;
		}
		System.out.println(sum);
		
		///////////////////////////////////////////////////////
		
		
		/*
		函數式程式設計（Functional Programming）:
		
			函數式程式設計是一種以數學函數（Mathematical Functions）為核心的編程方式。
			這種範式強調描述程式應該「做什麼」而非「如何做」，它著重於函數的組合與不變性（Immutability）。
			
			1.不變性（Immutability）：資料不會被修改，使用不可變物件（immutable objects）來確保程式的狀態不會變化。
			
				(1)這是一種避免資料競爭、狀態變異（mutation）帶來錯誤的方式
				(2)為物件或變數加上final或private
				
			2.無副作用（No Side Effects）：函數式程式設計中的函數盡可能沒有副作用（side effects），也就是不會影響函數外的狀態（例如修改全域變數）。
			3.高階函數（Higher-Order Functions）：函數可以作為參數傳遞或作為回傳值。
			
				(1)指能夠接收其他函數作為參數，或返回一個函數的函數。 
			
			4.函數組合（Function Composition）：將小型的函數組合成更大、更複雜的函數來完成複雜的操作。
			5.純函數（pure functions）:
			
				(1)輸入決定輸出：只要輸入相同，函數的結果永遠不會改變。
				
		
		
		函數式設計適合的場景：如資料流處理、大量資料轉換、或不需要修改狀態的演算法等。
		
		 */
//		例子:
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		List<Integer> doubled = numbers.stream()
		                               .map(n -> n * 2) // 將每個數字乘以2
		                               .collect(Collectors.toList()); 
		/*
		比如在這段程式中，我們沒有逐步說明每一個數字應該如何倍增，或者如何遍歷這個列表。
		相反地，我們只描述了資料應該如何轉換：將每個數字乘以 2，然後將結果收集為列表。這正是「關注於資料轉換」的意思。
		 */
	}
}
