package Generics20241010;

import java.util.List;

class Util {
	
	/*
	  泛型方法:
	 
	 	是在方法聲明中引入一個或多個類型參數。這些類型參數可以獨立於類的泛型類型參數。
	 	
	 	
	 語法:
	 
	 	[修飾符] <T, U, ...> 返回類型 方法名(參數列表) {
    			// 方法體
			}

	 */
    public static <T> void printArray(T[] array) {
        for(T element : array){
            System.out.print(element + " ");
        }
        System.out.println();
    }
}


public class GenericsMethod20241010 {
	public static void main(String[] args) {
        Integer[] intArray = {1, 2, 3, 4, 5};
        String[] strArray = {"A", "B", "C"};

        Util.printArray(intArray);
        Util.printArray(strArray);
    }
	
	/////////////////////////////////////////////////////
	
	 /*
	 泛型方法與通配符的不同:
	 
	 	1.通配符 <?> 的目的則是提供更廣泛的通用性，但代價是放棄了對插入或修改操作的支持。只能進行「讀取」
	 	2.泛型方法的關鍵在於它允許在方法調用時確定具體的類型，從而保證插入操作的類型安全。
	 	3.***具體的泛型參數:在泛型方法中，<T> 是一個佔位符。當泛型方法被調用時，具體的類型會在方法調用時被確定
	 	4.***而通配符 <?> 始終表示未知類型，無法在運行時確定具體的類型。
	 
	 		
	 		
	 具體應用場景的區別:
	 
	 	1.通配符 <?>：如果你只需要讀取集合中的數據，並且不需要進行插入或修改操作
	 	2.泛型方法：當你需要一個方法能夠處理多種具體類型，並且能夠在方法內部對這些類型進行讀寫操作時
	  */
	
	
	public static void printList(List<?> list) {
		//printList 方法使用了通配符 <?>，這意味著可以接受任意類型的 List
		//只讀取列表中的元素，但不會對列表進行寫操作
		
	    for (Object elem : list) {
	        System.out.print(elem + " ");
	    }
	    System.out.println();
	}
	
	
	
	public static <T> void printArray(T[] array) {
		//能夠接受任何類型的數組（T[] array）
		
	    for (T element : array) {
	        System.out.print(element + " ");
	    }
	    System.out.println();
	}

}
