package Stream20241018;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelStream20241021 {
	public static void main(String[] args) {
		
	
	/*
	並行操作（Parallel Stream）:
	
		是指透過將資料切分成多個部分，並且每個部分由不同的執行緒來處理，這樣可以提升效能。
		Java 提供的 parallelStream() 讓我們可以簡單地實現並行處理。
		
	串行和並行處理的差別:
	
		1.串行處理：stream() 方法會使用單一執行緒逐一處理資料。
		2.並行處理：parallelStream() 方法會將資料分割，使用多個執行緒同時處理。
	*/
		
		
	/*
	方法使用:
		
		parallelStream() 方法:
		
			1.定義：parallelStream() 是 Collection 介面的默認方法，主要用來將一個「集合」轉換成並行流（Parallel Stream）。
			2.使用場景：當你想要從集合（例如 List、Set 等）中建立並行流時，會直接使用 parallelStream()。
			3.實現方式：調用該方法後，資料集會自動分割，並透過 Fork/Join 框架來實現多執行緒處理。
	*/		
		//例子：
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		numbers.parallelStream().forEach(System.out::println);
		
		
		
	/*
	parallel() 方法
	
		1.定義：parallel() 是 Stream 介面的方法，適用於將「已經存在的串行流」（Sequential Stream）轉換成並行流。
		2.使用場景：當你已經有一個串行流並想要在後續操作中轉換為並行流時，會使用 parallel() 方法。
		3.實現方式：它不會創建新的流，而是將現有的串行流轉換為並行流。
	 */
		//例子：
		Stream<Integer> stream = numbers.stream();
		stream.parallel().forEach(System.out::println);
		
		
	/*	
	注意事項:
		1.效能問題：雖然並行處理可以提高效能，但並不是所有情況下都適合使用，尤其是在小規模資料處理或是 CPU 核心數量不多的情況下，並行可能反而會降低效能。
		2.非同步問題：並行操作涉及多執行緒，如果在處理過程中存在可變狀態，可能會導致非同步問題。務必要注意在並行流中，對共享資源的操作應是無狀態的或是使用適當的同步機制。
		3.如果你想測試串行和並行處理的效能差異，可以使用 System.currentTimeMillis() 來計算兩者的運行時間	
		4.並行處理不適用於處理有執行緒同步問題的物件
	 */
	
	
	//並行處理範例
		 List<Integer> numbers5 = IntStream.range(1, 100).boxed().collect(Collectors.toList());
	        
	        List<Integer> squareNumbers = numbers.parallelStream()
	            .map(n -> n * n)
	            .collect(Collectors.toList());
	        
	        System.out.println(squareNumbers);
	}
}
