package com.collections_framework;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class List20241008 {
	/*
	 List 介面:
	 
	 	是一個有序的集合，允許存儲重複的元素。它擴展自 Collection 介面，並增加了一些針對有序集合的特定操作，如按索引存取元素。
	 
	 
	List 的主要特性:
	
		1.有序性：List 中的元素是有序的，每個元素都有其在列表中的特定位置（索引）。
		2.可重複：List 允許存儲重複的元素，即可以有多個相同的元素存在於列表中。
		3.索引存取：可以通過索引來存取、插入、刪除元素，這使得 List 在需要頻繁隨機存取元素時非常高效。
		4.動態大小：List 的大小是可變的，可以根據需要動態增減元素。
	 

	常見實現類:
		
		1.ArrayList
		2.LinkedList(LinkedList同時是List、Deque、Queue的實現類)
		
		
	***ArrayList 和 LinkedList在開發中是常用的	
	 */
	
	///////////////////////////////////
	
	
	/*
	ArrayList:
	
		特點：
		
			1.底層使用array資料結構實現。
			2.支持快速隨機存取（通過索引）。
			3.插入和刪除元素（非末尾）可能較慢，因為需要移動元素。

		適用場景：頻繁進行隨機讀取操作，較少進行插入和刪除操作。
	 */
	
	//例子:
	
	
	public static void main(String[] args) {
		List<String> arrayList = new ArrayList<>();
		arrayList.add("Apple");
		arrayList.add("Banana");
		arrayList.add("Cherry");
		
		
		//////////////////////////////////////////////////
		
		
		/*
		Vector
		
		特點：
		
			1.與 ArrayList 類似，但它是同步的，線程安全。
			2.性能通常較 ArrayList 略低，因為同步機制帶來的開銷。
			3.適用場景：需要線程安全的情況下使用，否則一般使用 ArrayList。
		 */
		
		List<String> vector = new Vector<>();
		vector.add("Grapes");
		vector.add("Honeydew");
		vector.add("Iced Tea");
		

		////////////////////////////////////////////////////
		
		
		/*
		LinkedList
			
			特點：
			
				1.底層使用linked list實現。
				2.插入和刪除元素（特別是在列表開頭或中間）較快。
				3.隨機存取元素較慢，因為需要遍歷鏈表。
		
		適用場景：頻繁進行插入和刪除操作，較少進行隨機讀取操作。
		 */
		
		List<String> linkedList = new LinkedList<>();
		linkedList.add("Dog");
		linkedList.add("Elephant");
		linkedList.add("Frog");
	}
	

}
