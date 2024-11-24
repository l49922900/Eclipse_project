package Generics20241010;

import java.util.ArrayList;
import java.util.List;

class UpperBoundLowerBound20241010 {

	/*
	 泛型的邊界:
	 
	 	泛型邊界用於限制類型參數的範圍，確保類型參數符合特定的類型或其子類型。
	 */

	////////////////////////////////////////////////////////
	
	
	/*
	 通配符（Wildcard）:
	 
		1.通配符 ? 用於表示未知的類型。它可以與上邊界和下邊界結合使用。
		2.使用通配符是為了讓方法能夠接受某個類型範圍內的所有類型，但在方法內對這個類型進行的操作受到限制，特別是它只能進行讀取
		

		***通配符與泛型方法看似類似，時則有不同，不同之處去看GenericsMethod20241011
	 */
	
	
	public static void printList(List<?> list) {
		//無邊界通配符:表示任何類型的未知類型。
	}
	
	public static void processNumbers(List<? extends Number> list) {
		//上邊界通配符:表示類型必須是某個類型或其子類型。
	}
	
	public static void addIntegers(List<? super Integer> list) {
		//下邊界通配符:表示類型必須是某個類型或其超類型。
	}
	
	
	
	
	////////////////////////////////////////////////////////////////
	
	public <T extends Number> void printNumbers(List<T> list) {
		//在這個例子中,T必須是Number類或其子類(如Integer, Double等)。
	
		
		/*
		 上邊界（Upper Bounds）:
		 
		 	1.使用 extends 關鍵字來設置上邊界，表示類型參數必須是指定類型或其子類型。
		 	2.當你試圖向一個有上界限制的集合或列表中插入元素時，Java 編譯器會禁止插入任何東西，除了 null。
		 		(1)因為在有上界限制的情況下，編譯器無法確保你插入的元素類型與列表允許的類型完全匹配
		 		(2)當你宣告一個 List<? extends Animal> 時，這個列表可以持有任何類型的對象，這些對象的類型必須是 Animal 本身或它的子類型（例如 Dog、Cat）。
		 		   雖然從讀取的角度來看，你可以確定從這個列表中讀出的對象一定是 Animal 或其子類型之一，但對於插入元素來說，編譯器無法知道具體要插入的是哪個子類型。
		 		   具體解釋看例子2
		 		
		 		
		 */
			
		
//		例子:
//		List<? extends Animal> animals = new ArrayList<>();
//		animals.add(new Dog()); // 編譯錯誤
//		animals.add(null); // 可以編譯通過
		
		
//		例子2:
		
//		List<? extends Animal> animals = new ArrayList<Dog>();
//		在這個例子中，animals 其實是指向一個 Dog 的列表，但由於 <? extends Animal>，這個列表可能也指向一個 Cat 的列表。如果你嘗試插入一個 Cat 對象：
		
//		animals.add(new Cat()); // 編譯錯誤
//		這樣做會導致編譯錯誤，因為編譯器無法確定這個列表是否真的是一個 Cat 或 Dog 的列表。如果這個 animals 是一個 Dog 的列表，那麼插入 Cat 會是不合法的，因此編譯器會禁止插入任何具體的非 null 對象。
		
	}
	
	////////////////////////////////////////////////////////////////
	
	public void addNumbers(List<? super Number> list) {
		//Integer 是 Number 的子類型，可以安全插入
		//Double 也是 Number 的子類型，可以安全插入
	    
		
		/*
		下邊界（Lower Bounds）:
		
			1.使用 super 關鍵字來設置下邊界，表示類型參數必須是指定類型或其超類型。下邊界通常在通配符中使用。
				超類型:即 T 或 T 的父類型
				
			2.***由於容器的類型是 T 的某個超類型，這意味著這個容器可以接受 T 或任何「 T 的子類型的對象」。
			例如，如果我們有 List<? super Integer>，這個列表可以接受 Integer 或 Integer 的子類型（雖然 Integer 是最終類，沒有子類，但這只是一種舉例。)
			
			***為什麼容器可以接受 T 或任何 「T 的子類型的對象」:父類型可以接受或包含它的子類型的實例。換句話說，子類型的物件可以被賦值給父類型的變數。
			
			
			
		 */
		

	///////////////////////////////////////////////////////////////
	}
	
	/*
	生產者和消費者原則（Producer Extends, Consumer Super，PECS）：這是一個常用的泛型設計原則。

		1.如果某個方法只讀取泛型類型的數據，應該使用 <? extends T>，這樣可以讀取 T 的子類型數據。
		2.如果某個方法只寫入泛型類型的數據，應該使用 <? super T>，這樣可以保證你可以安全地寫入 T 或其子類型。
			為什麼說是安全的:使用 <? super T> 保證了方法在寫入數據時不會違反泛型類型的約束。因為你知道至少可以接受 T 類型，所以寫入 T 或其子類型是被允許的，
	 */
	
	// 只讀取資料，使用上邊界
	public void printNumbers2(List<? extends Number> list) {
	    for (Number num : list) {
	        System.out.println(num);
	    }
	}

	// 只寫入資料，使用下邊界
	public void addNumbers2(List<? super Integer> list) {
	    list.add(10);  // 可以安全插入 Integer
	}
}





