package Lambda20241017;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class lambdaIntroduction20241017 {
	/*
	 Lambda 表示式（Lambda Expression）:
	 
	 	是一種簡潔的語法，可以用來表示匿名函數（Anonymous Function）。
	 	Lambda 表示式允許你把行為（function）當作方法參數，或者用來簡化單一抽象方法的介面（像是 Functional Interface）實作。
	 	
	 	
	 Lambda 表示式的語法：
	 	
	 	(參數列表) -> { 方法體 }
	 	
	 	1.參數列表：參數類似於方法的參數，可以是零個或多個。如果只有一個參數且不需要類型聲明，可以省略括號。
		2.箭頭符號（->）：用來分隔參數列表和方法體。
		3.方法體：可以是單一行或多行程式碼。如果是單一行，無需大括號 {}。
		4.方法可宣告區域變數，但不可與傳入參數同名
		5.方法不帶參數以()表示
	 	
	 Lambda 表示式的特性：
	 
		1.無需明確聲明參數類型：Java 編譯器會自動推斷參數的類型，這稱為類型推斷（Type Inference）。
		2.簡化代碼：不需要顯式的匿名類別，程式碼變得更加簡潔可讀。
		3.與 Functional Interface 搭配使用：Functional Interface 是僅有一個抽象方法的介面，像是 Runnable、Callable 或自訂的介面。
		4.lambda表達式在慣例上通常只包含一個運算式，比如這樣:
		
			(a, b) -> a + b  
			(因為很多使用lambda的場景(如流操作)只需要簡單的轉換或計算。)
	 	
	 	 
	 	
	 	
	 */
	//////////////////////////////////////////////////////////////
	
	
	//基本例子:
	@FunctionalInterface
	interface MyFunctionalInterface {
	    void doSomething(String message);
	}
	public static void main(String[] args) {
		// 使用Lambda表示式
        MyFunctionalInterface func = (message) -> {
            System.out.println("Message: " + message);
        };

        // 呼叫方法
        func.doSomething("Hello, Lambda!");
        
        /////////////////////////////////////////////////////////////////
        
        
        //使用 Lambda 表示式處理集合
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        // 使用Lambda表示式來篩選和遍歷
        names.stream()
             .filter(name -> name.startsWith("A")) // 篩選出以"A"開頭的字串
             .forEach(name -> System.out.println(name)); // 遍歷並印出結果
        
        
        ////////////////////////////////////////////////////////////////
        
        
        /*
        Java 提供了一些內建的 Functional Interface，
        例如 java.util.function.Predicate<T>（用來進行邏輯判斷，回傳 boolean），Function<T, R>（進行轉換，回傳結果），等等。
         */
        
        
        
        Predicate<Integer> isEven = (n) -> n % 2 == 0;
        /*
        Predicate 是 Java 內建的一個功能介面（Functional Interface），來自 java.util.function，
        它的唯一抽象方法是 test(T t)，這個方法接受一個參數並返回 boolean 值。
         */
        
        

        System.out.println(isEven.test(4));  // true
        System.out.println(isEven.test(3));  // false
	}
	
	
	
	
	
	
	
}
