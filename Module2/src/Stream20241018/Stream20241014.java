package Stream20241018;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Stream20241014 {
	public static void main(String[] args) {
	
	/*
	Stream :
	
		是自Java 8引入的處理資料的抽象層(指將複雜系統的具體細節隱藏起來，並提供簡化的介面或工具，讓使用者能以較簡單的方式進行操作)
		提供了一種高效、簡潔且具表現力的方式來處理「集合」（如 List、Set 等）或其他資料來源（如陣列、I/O 通道等）的資料。
		它允許開發者以宣告式的方式（類似於SQL語句）來進行資料的過濾、轉換、聚合等操作，而不需要顯式地迭代資料結構。
	
	Stream 的主要特性:
	
		1.無狀態（Stateless）：Stream 的操作不會改變原始資料來源，而是生成新的資料流。
		2.鏈式操作（Chaining）：Stream 支援方法鏈接，可以串連多個操作來處理資料。
			
			例子:list.stream().filter(n->n%2==0).forEach(n->System.out.println(" "+n));
			
		3.內部迭代（Internal Iteration）：Stream 由框架本身管理迭代過程，而不是由開發者手動迭代。
		4.延遲執行（Lazy Evaluation）：中間操作（如 filter、map）是延遲執行的，只有在終端操作（如 collect、forEach）被調用時才會執行。
		5.可並行處理（Parallel Processing）：Stream 可以輕鬆地進行並行處理，以提高效能。
	 	6.Pipeline   Operations:指的是使用 Java Stream API 進行資料處理時所組成的流水線操作。
	 	
	 		(1)Source (資料來源)：資料來源可以是集合 (如 List、Set) 或是陣列等。Stream 是從這些資料來源建立的。
	 		(2)Intermediate Operations (中間操作)：這些操作不會立即執行，它們是惰性 (lazy) 的，只在需要最終結果時才會被執行。
	 			每個中間操作都會返回一個新的 Stream，允許進一步的操作。常見的中間操作有：
	 			
	 			filter(Predicate)：根據條件過濾資料。
				map(Function)：將元素轉換為另一種形式。
				sorted(Comparator)：對元素進行排序。
				
			(3)Terminal Operations (終端操作)：這是結束 Stream 並觸發所有中間操作執行的操作。終端操作會返回一個具體的結果，而不是 Stream。常見的終端操作有：
	 
	 			forEach(Consumer)：對每個元素執行操作。
				collect(Collector)：將 Stream 的結果收集成另一種形式 (如 List)。
				count()：計算 Stream 中元素的數量。
	 
	 Stream 的基本組成:
	 
			來源（Source）：
				資料的來源，可以是集合、陣列、I/O 通道等。

			中間操作（Intermediate Operations）：
				轉換 Stream 的操作，如 filter、map、sorted 等。
				這些操作是懶執行的，並返回一個新的 Stream。

			終端操作（Terminal Operations）：
			
				產生結果或副作用的操作，如 collect、forEach、reduce 等。
				終端操作會觸發所有的中間操作並開始處理資料。
				
				
	注意事項
	
		1.不可重複使用：一旦對 Stream 執行過終端操作，就無法再次使用該 Stream。
		2.無序性：除非特別指定（如 sorted），否則 Stream 可能不保證元素的順序。
		3.不可修改來源：Stream 的操作不會改變原始資料來源，保持資料的不可變性。
		
		////////////////////////////////////////////////////////////
		 
	 /*
	 Collection.stream() 方法
	 
	 	 介紹:
	 	 
	 	 	Collection.stream() 是 Collection 介面的一個具體方法，這個方法的唯一作用是將集合轉換成 Stream。
	 	 	在 List、Set、Queue 等集合類別中都可以使用這個方法來生成一個 Stream 物件。
	 	 	
	 	 用途：

	 	 	stream() 是資料處理的入口點，透過它我們可以將資料容器轉換為串流，並進行後續的資料操作。它本身不處理資料，只是提供了 Stream 的起始點。
	
		stream()：專門用於從集合類型（如 List, Set）中創建 Stream。
		Stream.of()：可以從任意單個物件或多個物件直接創建 Stream，不限於集合類型。
	  */
		
		//Collection.stream() 方法例子:
		List<String> list2 = Arrays.asList("apple", "banana", "orange");
		Stream<String> stream2 = list2.stream();  // 將 List<String> 轉換為 Stream<String>
		//這裡 stream() 僅僅是將集合轉換為 Stream，後續的操作由 Stream 介面的方法來實現。
	 
		
		
		
		
	/*
	Stream.of()：
	
		Stream.of() 是一個靜態方法，適用於將單個或多個元素轉換為 Stream。不一定需要是集合，也可以是單個物件或一組物件。
		
		
		stream()：專門用於從集合類型（如 List, Set）中創建 Stream。
		Stream.of()：可以從任意單個物件或多個物件直接創建 Stream，不限於集合類型。
	 */
		
		Stream<String> stream = Stream.of("a", "b", "c");
		
		
		////////////////////////////////////////////////////////////////////
		
		
		
		/*
		Stream 介面:
		
			介紹：
			
				Stream 是一個新的 Java API，它是一個資料處理的抽象層，讓開發者能夠使用函數式編程的風格來進行資料的過濾、轉換和聚合等操作。
				Stream 介面包含了許多操作資料流的方法，像是 filter()、map()、forEach() 等，這些方法允許我們對資料流進行操作。
				
				
			用途：Stream 介面是設計來處理資料流的，它提供了大量的資料處理方法（中間操作與終端操作）。一旦資料被轉換成 Stream，我們就可以使用這些方法來處理資料流，而不會影響到原始集合。	
		 */
		//Stream 介面例子:
		Stream<String> stream3 = list2.stream();
		stream3.filter(s -> s.startsWith("a"))   // 使用 Stream 的操作過濾資料
		      .forEach(System.out::println);    // 終端操作
		//filter() 和 forEach() 都是 Stream 介面提供的方法，用於對資料流進行處理。
		
		
		
		
		/*
		兩者的關係與比較：

			1.Collection.stream() 是如何進入 Stream 資料流處理的起點，它將集合轉換為 Stream。
			2.Stream 介面則是這個資料流中進行具體操作的核心部分。所有對資料的操作都是在 Stream 介面上完成的，而 stream() 方法本身不包含這些操作。
		 */
		
		//////////////////////////////////////////////////////////////////
		
		//創建 Stream
		
		
			List<String> list = Arrays.asList("apple", "banana", "cherry");
			Stream<String> stream5 = list.stream();
		
		
//		中間操作:
		
	//		filter(Predicate<? super T> predicate)
	//		返回值：Stream<T>
	//		作用：根據指定的條件過濾元素，保留符合條件的元素。
			
			
			
	//		map(Function<? super T, ? extends R> mapper)
	//		返回值：Stream<R>
	//		作用：將每個元素轉換為另一個類型。
			
			
			
	//		flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)
	//		返回值：Stream<R>
	//		作用：將每個元素轉換為 Stream，然後將所有的子 Stream 合併為一個 Stream。
	//		
			
			
	//		distinct()
	//		返回值：Stream<T>
	//		作用：去除 Stream 中的重複元素。
	//		
			
			
	//		sorted()
	//		返回值：Stream<T>
	//		作用：根據自然順序對元素進行排序。
	//		
			
	//		sorted(Comparator<? super T> comparator)
	//		返回值：Stream<T>
	//		作用：根據提供的比較器對元素進行排序。
	//		
			
			
	//		peek(Consumer<? super T> action)
	//		返回值：Stream<T>
	//		作用：對每個元素執行操作，但不改變 Stream 的元素。
	//		
			
			
	//		limit(long maxSize)
	//		返回值：Stream<T>
	//		作用：返回一個包含最多不超過指定數量元素的 Stream。
	//		
			
			
	//		skip(long n)
	//		返回值：Stream<T>
	//		作用：跳過前 n 個元素，返回剩下的元素。
		
		
//		終端操作

	//		collect：收集結果到集合或其他資料結構
			List<String> result = stream.collect(Collectors.toList());
			
	//		forEach：對每個元素執行操作
			stream.forEach(System.out::println);
			
	//		reduce：歸約操作，將 Stream 元素合併為一個結果
			Optional<String> concatenated = stream.reduce((s1, s2) -> s1 + "," + s2);
	}
}
