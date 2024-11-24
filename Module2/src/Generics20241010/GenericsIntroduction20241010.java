package Generics20241010;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericsIntroduction20241010 {
	/*
	泛型（Generics）:
	
		是 Java 5 引入的一項重要「語言特性」，旨在提供類、介面和方法的「類型參數化能力」。
		使用泛型可以實現類型安全的集合操作，減少類型轉換的需要
		當我們在定義類、介面或方法時，使用了泛型參數（如 T），這些參數在定義時並沒有具體的類型，它們只是占位符。
		但是，在實際使用這些類或方法時，我們可以指定具體的類型，讓這些泛型參數具體化為特定的數據類型。
		
		
		優點：

			1.類型安全：在編譯時進行類型檢查，避免運行時的類型轉換異常。
			2.代碼重用：同一個類、介面或方法可以操作多種不同的數據類型。
			3.提高可讀性：明確指定操作的數據類型，讓代碼更易理解。
			
		泛型的限制:
		
			1.無法使用基本資料型別作為型別參數：只能使用參考型別，如 Integer 代替 int。
			2.無法在執行時取得具體的泛型型別：由於「型別擦除」，下面會解釋
			3.無法建立泛型陣列：例如，new T[10] 是不允許的。
			4.禁止泛型類直接繼承 Throwable 類（Exception 和 Error 都是 Throwable 的子類）。下面會解釋 
			5.也是因為「型別擦除」，方法的覆載或多載不考慮不同的型別參數
			6.Java泛型的多型中，基底型別可作多型自動轉型，型別參數不可作多型自動轉型。這被稱為泛型的不變性（invariance）。下面會解釋       
			7.不變性（Invariance）:沒有共變性（Covariance）與逆變性（Contravariance），下面會解釋。
				沒有共變性:即使類型A是類型B的子類型,Generic<A>也不是Generic<B>的子類型。這意味著在使用泛型時,不能像普通類型那樣進行多型轉換。   
				沒有逆變性:如果 Animal 是 Dog 的父類別，那麼 List<Animal> 「不可」被視為 List<? super Dog> 的一種形式。
				(但透過通配符和泛型方法可以達到類似的效果)
		
		
			
		「型別擦除（Type Erasure）」:
			
			在編譯階段，程式碼中的泛型資訊（即型別參數）會被移除，並且在執行時，程式中不再保留這些具體的泛型型別資訊。
			
		Java 的型別擦除機制會將泛型型別參數替換為以下兩種情況之一：

			1.替換為其邊界類型（Bounded Type）：如果泛型有明確的邊界，例如 T extends Number，那麼在編譯後，泛型型別 T 將被替換為其邊界類型 Number。
			2.替換為 Object：如果泛型沒有明確的邊界（例如 T 沒有指定上界），那麼在編譯後，泛型型別 T 將被替換為 Object。	
			3.造成的限制:由於型別在編譯後被擦除，無法在執行時取得具體的泛型型別資訊	
			
			
			////////////////////////////////////////


			禁止泛型類直接繼承 Throwable 類的原因:
			
				如果允許泛型異常，那麼在運行時由於類型擦除，JVM將無法區分不同參數化類型的異常，這會導致異常處理機制失效。	
	 */
	
	//例子:
	public static void main(String[] args) {
//		if (list instanceof List<String>) { 
		    // 編譯時就會報錯，因為無法在執行時檢測到具體的泛型型別
//		}
		
		//////////////////////////////////////////////////
		
		
		/*
		基底型別可作多型自動轉型，型別參數不可作多型自動轉型:
		
			基底型別的多型:在Java中，基底型別（或稱原始類型）確實支持多型自動轉型。這是Java面向對象編程的一個基本特性。
		 */
		//例子:
		List<String> stringList = new ArrayList<String>();
		Collection<String> collection = stringList; // 這是允許的，ArrayList是List的子類，List是Collection的子介面
	
		/*
		型別參數的多型：
			
			然而，對於泛型的型別參數，Java不支持自動轉型。這被稱為泛型的不變性（invariance）
			這種限制是為了保證型別安全。如果允許這種轉換，可能會導致運行時錯誤。
		 */
		//例子:
		List<String> stringList2 = new ArrayList<String>();
//		List<Object> objectList3 = stringList2; // 這是不允許的，儘管String是Object的子類
	
		/*
		值得注意的是，Java確實提供了一些方法來實現型別參數的有限度"多型"，如通配符（wildcards）和限定型別（bounded types）。
		但這些並不是自動轉型，而是需要顯式聲明的。
		 */
		
		
		///////////////////////////////////////////////////////
		

		/*
		不變性（Invariance）:沒有共變性（Covariance）與逆變性（Contravariance），下面會解釋。
			
		沒有共變性:
			
			1.即使類型A是類型B的子類型,Generic<A>也不是Generic<B>的子類型。這意味著在使用泛型時,不能像普通類型那樣進行多型轉換。   
			2.但透過使用通配符 ? extends T，你可以讓泛型類型接受 T 或其任何子類型。
		 
		例子:
			List<? extends 動物> animals = new ArrayList<狗>();
			//在這個例子中，animals 可以指向 動物 的任何子類別的列表，例如 狗 或 貓。這特別適合用在只讀取資料而不需要修改列表的情境。
			//注意： 當使用 ? extends T 時，你無法向列表中新增元素，因為編譯器無法保證所新增的元素類型是安全的。
		 
		 沒有逆變性:
		 
		 	1.如果 Animal 是 Dog 的父類別，那麼 List<Animal> 「不可」被視為 List<? super Dog> 的一種形式。
		 	2.透過使用通配符 ? super T，你可以讓泛型類型接受 T 或其任何父類型。
		 
		 例子:
		 	List<? super 狗> dogs = new ArrayList<動物>();
			//在這個例子中，dogs 可以指向 狗 的任何父類別的列表，例如 動物 或 Object。這適合用在只需要新增元素但不需要讀取特定型別資料的情境。				
			//注意： 當使用 ? super T 時，你可以安全地向列表中新增 T 型別的物件，但在讀取時只能保證返回的是 Object 型別。
		 	
		 */
	
	}
}
