package com.collections_framework;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

class Map20241009 {
	/*
	 Map interface:
	 
	 	用於存儲鍵值對（key-value pairs）。Map 不允許鍵（key）重複，每個鍵對應一個值（value）。
	 	***它不繼承自 Collection 介面，但它是集合框架的一部分。Map 用於存儲和操作鍵值對數據結構，常見於需要快速查找、插入和刪除操作的場景。
	 
	 
	 常見用途:
	 
		1.儲存配置信息（如鍵值對形式）
		2.數據庫查詢結果的存儲
		3.快速查找和索引
		
		
	Map 介面的主要特性:
	
		1.***鍵唯一：每個鍵在 Map 中是唯一的，不能重複。
		2.允許值重複：不同的鍵可以對應相同的值。
		3.無序或有序：具體是否有序取決於具體的實現類。例如，HashMap 是無序的，而 TreeMap 是有序的(***Treemap既是map的實現類也是SortedMap的)。
		4.允許 null 值：部分實現類允許鍵或值為 null，如 HashMap 允許一個 null 鍵和多個 null 值；Hashtable 不允許 null 鍵或值。
	 
	Map 介面常用方法及其傳回值:
	
	
		V put(K key, V value)
		
			說明：將指定的鍵和值加入 Map，若鍵已存在則更新其值。
			傳回值：傳回與該鍵對應的舊值，如果該鍵之前沒有對應的值，則傳回 null。
		
		
		
		V get(Object key)
		
			說明：根據鍵取得對應的值，若鍵不存在則返回 null。
			傳回值：傳回與指定鍵對應的值，若鍵不存在則傳回 null。
		
		
		
		V remove(Object key)
		說明：移除指定的鍵和值，並返回被移除的值。
		傳回值：傳回被移除的值，若鍵不存在則傳回 null。
		
		
		
		boolean containsKey(Object key)
		
			說明：檢查 Map 是否包含指定的鍵。
			傳回值：傳回 true 如果鍵存在，否則傳回 false。
		
		
		
		boolean containsValue(Object value)
		
			說明：檢查 Map 是否包含指定的值。
			傳回值：傳回 true 如果值存在，否則傳回 false。
		
		
		Set<K> keySet()
		
			說明：返回所有鍵的 Set 集合。
			傳回值：傳回 Set，其中包含所有鍵。
		
		
		Collection<V> values()
		
			說明：返回所有值的集合。
			傳回值：傳回 Collection，其中包含所有值。
		
		
		Set<Map.Entry<K, V>> entrySet()
		
			說明：返回所有鍵值對的 Set 集合。
			傳回值：傳回 Set，其中每個元素是一個 Map.Entry，包含鍵和值。
		
		
		
		int size()
		
			說明：返回 Map 中鍵值對的數量。
			傳回值：傳回鍵值對的數量。
		
		
		
		void clear()
		
			說明：清空 Map 中所有的鍵值對。
			傳回值：無傳回值。
	 */
	
	/////////////////////////////////////////
	
	public static void main(String[] args) {
		/*
		HashMap
		
			特性：基於哈希表實現，允許一個 null 鍵和多個 null 值，鍵值對無順序。
			適用情境：快速查找、插入和刪除資料。
		 */
		
		//例子
		Map<String, Integer> hashMap = new HashMap<>();

        // 添加元素
        hashMap.put("蘋果", 3);
        hashMap.put("香蕉", 2);
        hashMap.put("櫻桃", 5);

        // 取得元素
        System.out.println("蘋果的數量: " + hashMap.get("蘋果"));

        // 遍歷 Map
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());}
		
        
        ///////////////////////////////////////////
        
        /*
        TreeMap
        
        
        	1.***TreeMap既是 Map 介面的實現類，也是 SortedMap 介面的實現類，要實現哪個看你需要哪種特性
			2.有序性：TreeMap 中的鍵是有序的，默認按照鍵的自然順序排序，也可以通過構造函數提供自定義的 Comparator。
			3.性能：基本操作（如 get、put、remove）的時間複雜度為 O(log n)。
			4.不允許 null 鍵：TreeMap 不允許鍵為 null，但允許值為 null
			5.線程安全性：TreeMap 不是線程安全的。如果多個線程同時訪問和修改 TreeMap，需要進行同步處理。
			6.比較器的一致性：如果使用自定義的 Comparator，確保其與鍵的 equals 方法一致，以避免不一致的行為。
        
        	***TreeMap既是 Map 介面的實現類，也是 SortedMap 介面的實現類
        	如何選擇Map、SortedMap、TreeMap作為宣告類型:
        		
        		1.如果只需要基本的Map功能,並希望保持最大的靈活性,使用Map。
				2.如果不需要TreeMap的特殊方法(如進階的範圍查詢),使用SortedMap。
				3.如果需要使用TreeMap的全部功能,直接宣告為TreeMap。
        		
        	
        	
        	
			特性：
			
				1.基於紅黑樹實現，鍵按自然順序或自定義順序排序，不允許 null 鍵。
			
			適用情境：需要依鍵順序存取資料。
         */
        
        //例子:作為 Map 的實現類
        Map<String, Integer> treeMap = new TreeMap<>();

        // 添加元素
        treeMap.put("香蕉", 2);
        treeMap.put("蘋果", 3);
        treeMap.put("櫻桃", 5);
        
        
        //作為 SortedMap 的實現類
        SortedMap<String, Integer> treeMap2 = new TreeMap<>();
        treeMap2.put("Apple", 50);
        treeMap2.put("Banana", 30);
        treeMap2.put("Cherry", 20);
        
        
        
        //////////////////////////////////////////
        
        /*
        LinkedHashMap
        
			特性：基於哈希表和鏈結串列，保持鍵值對的插入順序或訪問順序，允許 null 鍵與值。
			適用情境：需要保持插入順序的場景。
         */
        
        //例子
        Map<String, Integer> linkedHashMap = new LinkedHashMap<>();

        linkedHashMap.put("蘋果", 3);
        linkedHashMap.put("香蕉", 2);
        linkedHashMap.put("櫻桃", 5);
	}
}
