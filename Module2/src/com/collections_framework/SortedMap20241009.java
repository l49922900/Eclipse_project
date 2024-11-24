package com.collections_framework;

import java.util.TreeMap;

public class SortedMap20241009 {

	public static <V> void main(String[] args) {
		/*
		SortedMap:
		
		 	是一個擴展自「 Map 介面的子介面」，提供了對鍵（key）進行排序的功能或根據指定的比較器進行排序的能力。
		 	這對於需要有序存取元素的應用場景非常有用
		 
		 
		 SortedMap 的特性
		 
			1.有序性：鍵按照自然順序（如數字大小、字母順序）或自定義順序進行排序。
			2.範圍視圖：可以輕鬆地獲取鍵的子集，如小於某個鍵、大於某個鍵等。
			3.導航方法：提供了如 firstKey()、lastKey() 等方法來快速訪問有序鍵集中的特定元素。
		 
		 
		 SortedMap 的實現類：TreeMap
		 ***TreeMap既是 Map 介面的實現類，也是 SortedMap 介面的實現類，要實現哪個看你需要哪種特性
		 
		 
		 
		 
		 SortedMap 主要方法列表:
		 	
		 	Comparator<? super K> comparator():

				說明：返回用於對鍵進行排序的 Comparator，如果使用鍵的自然順序，則回傳 null。
				回傳值：Comparator<? super K> 或 null。
			
			
			K firstKey():
			
				說明：返回映射中第一個（排序最小的）鍵。
				回傳值：鍵的類型 K。
			
			
			K lastKey():
			
				說明：返回映射中最後一個（排序最大的）鍵。
				回傳值：鍵的類型 K。
				
			
			SortedMap<K, V> headMap(K toKey):
			
				說明：返回鍵小於 toKey 的部分視圖。
				回傳值：SortedMap<K, V>（包含鍵小於 toKey 的鍵值對）。
			
			
			
			SortedMap<K, V> tailMap(K fromKey):
			
				說明：返回鍵大於或等於 fromKey 的部分視圖。
				回傳值：SortedMap<K, V>（包含鍵大於或等於 fromKey 的鍵值對）。
			
			
			
			SortedMap<K, V> subMap(K fromKey, K toKey):
			
				說明：返回鍵在 fromKey（含）和 toKey（不含）之間的部分視圖。
				回傳值：SortedMap<K, V>（包含鍵在 fromKey 到 toKey 之間的鍵值對）。
		 */
		
		/////////////////////////////////////////
		
		/*
		實現類：TreeMap
			
			1.***TreeMap既是 Map 介面的實現類，也是 SortedMap 介面的實現類，要實現哪個看你需要哪種特性
			2.有序性：TreeMap 中的鍵是有序的，默認按照鍵的自然順序排序，也可以通過構造函數提供自定義的 Comparator。
			3.性能：基本操作（如 get、put、remove）的時間複雜度為 O(log n)。
			4.不允許 null 鍵：TreeMap 不允許鍵為 null，但允許值為 null
			5.線程安全性：TreeMap 不是線程安全的。如果多個線程同時訪問和修改 TreeMap，需要進行同步處理。
			6.比較器的一致性：如果使用自定義的 Comparator，確保其與鍵的 equals 方法一致，以避免不一致的行為。
		 */
		
		// 使用鍵的自然順序
		TreeMap<String, Integer> treeMap1 = new TreeMap<>();

	}

}
