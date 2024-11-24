package Lambda20241017;

import java.util.function.Function;

public class FunctionalInterface20241018 {
	/*
	Functional Interface（函數式介面）:
	
		是一種只包含一個抽象方法的介面，這個抽象方法用來定義該介面的主要功能。
		Java 已經預先設計了許多常用的函數式介面（Functional Interface），這些介面大多數位於 java.util.function 包裡。
		這些介面大幅簡化了開發過程，讓開發者不必自行定義大多數常見的操作
		Java 的函數式介面允許我們使用 lambda 表達式 來實現具體的方法
		
	 函數式介面的特點：
	 
		1.只能有一個抽象方法，但可以有多個預設方法（default）或靜態方法（static）。
		2.通常會加上 @FunctionalInterface 註解來強制該介面必須遵循函數式介面的規範，但這並不是必須的。
		3.可以與 lambda 表達式 和 方法引用（method reference） 一起使用，使得程式更加簡潔。	
		4.可以有其他繼承自Object類別的方法:
			(1)booleanequals(Object obj)
			(2)int hashcode()
			(3)String toString()
		
	常見的函數式介面：
	***Functional Interface內部有多種方法可以運用，具體有哪些問欸愛
	
		Function<T, R>:
		
			接收一個參數並返回一個結果。
			T 是輸入參數的類型，R 是返回值的類型。
			常用於轉換操作，如類型轉換或值處理。	
			
			
		 Predicate<T>:
		 
			接收一個參數並返回一個 boolean 值，通常用於條件判斷。
			T 是輸入參數的類型。
			
			
		 Consumer<T>:
		 
			接收一個參數但不返回任何結果，通常用於執行某些操作。
			T 是輸入參數的類型。
			
			
		 Supplier<T>:
		 
			不接收參數但會返回一個結果。
			T 是返回值的類型，常用於懶加載或提供某些資源。
			
		
		
		 BiFunction<T, U, R>:
		 
			接收兩個參數並返回一個結果。
			T 和 U 是輸入參數的類型，R 是返回值的類型。
			
			
			
		UnaryOperator<T> 和 BinaryOperator<T>:
		
			UnaryOperator<T> 是 Function<T, T> 的特化版本，接受一個參數並返回相同類型的結果。
			BinaryOperator<T> 是 BiFunction<T, T, T> 的特化版本，接受兩個相同類型的參數並返回相同類型的結果。
	 */
	
	////////////////////////////////////////////////////////////////////
	
	/*
	 原始類型介面（Primitive Type Interfaces）:
	 
		Java 內建的函數式介面主要是針對 Object 類型，不過在處理基本資料型態時，為了避免頻繁的封箱（Boxing）與拆箱（Unboxing），Java 提供了專門的原始類型函數式介面。
		這些介面針對特定的原始類型，如 int、long、double 等
		
		1.IntFunction<R>: 接受 int 作為輸入，並回傳一個任意類型的結果 R。
		2.DoubleFunction<R>: 接受 double 作為輸入，並回傳一個任意類型的結果 R。
		3.ToIntFunction<T>: 接受一個對象作為輸入，並回傳 int。
		
		
		
	二元操作介面（Binary Type Interfaces）:
	
		Binary 類型介面是接受兩個參數進行操作的介面，適合用來實現像是加法、比較等操作。	
		
		1.BiFunction<T, U, R>: 接受兩個參數 T 和 U，回傳一個結果 R。
		2.BinaryOperator<T>: 是 BiFunction 的特例，兩個參數的類型和結果的類型相同，適合用於數值操作，如加法、乘法等。
		
		
	一元操作介面（Unary Type Interfaces）:
	
		Unary 類型介面接受一個參數，並回傳同類型的結果，常用於進行對單一參數的轉換或運算。	
		
		1.UnaryOperator<T>: 接受一個參數，並回傳與輸入參數類型相同的結果。
		2.Function<T, R>: 接受一個參數，並回傳另一個類型的結果。這是最通用的一元函數式介面。
	 */
	
	
	
	
	//////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		
	        Function<Integer, String> intToString = (num) -> "Number is " + num;
	        //Function<Integer, String> 是一個預定義的函數式介面，它接受一個整數並返回一個字串。
	        
	        // 呼叫apply方法
	        System.out.println(intToString.apply(5));  // 輸出：Number is 5
	    
	}
}
