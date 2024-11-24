package com.collections_framework;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Set20241007 {
	
	public static void main(String[] args) {
		
	
	/*
	Set :
	
	是 Java 集合框架（Java Collections Framework）中的一個介面，位於 java.util 套件下。
	它代表一個不包含重複元素的集合。換句話說，Set 保證其中的每個元素都是唯一的
	 
	
	Set 的特性:
	
		(1)無順序：大多數 Set 的實作類別不保證元素的排列順序。也就是說，元素的儲存順序可能與插入順序不同。
		(2)唯一性：Set 不允許包含重複的元素。每個元素在集合中只能出現一次。
		(3)高效查詢：Set 提供高效的元素查詢、插入和刪除操作，尤其是基於雜湊表（Hash table）的實作類別。
	 	(4)實作時Set的元素擺放位置是根據元素之hashCode()決定
	 
	 
	 
	Set 適用於以下幾種情況：

		1.需要存儲唯一元素：當需要確保集合中沒有重複元素時，使用 Set 是理想的選擇。
		2.快速查找：如果需要頻繁地檢查某個元素是否存在於集合中，Set 提供了高效的查找性能。
		3.數據去重：當需要對數據進行去重操作時，可以利用 Set 的特性輕鬆實現。
	 
	 
	 Set的實現類別:HashSet、LinkedHashSet、TreeSet(TreeSet同時是Set與SortedSet的實現類)
	 
	 ***HashSet、LinkedHashSet 和 TreeSet 都很常用
	 */
	
	
	/*
	HashSet
	
		1.特性：基於雜湊表（Hash table）實作，元素是無順序的且不保證元素的排列順序。
		2.效能：提供常數時間的基本操作（新增、刪除、查詢）。
		3.適用場景：需要高效存取且不關心元素順序時。
	 */
		//例子
		Set<String> hashSet = new HashSet<>();
		hashSet.add("Apple");
		hashSet.add("Banana");
		hashSet.add("Apple"); // 重複元素，不會被新增
		System.out.println(hashSet); // 輸出是 [Apple, Banana]
	
	
	////////////////////////////////////////////
		
	/*
	LinkedHashSet
	
		1.特性：基於鏈接哈希表實現，保留了元素的「插入順序」。
		2.性能：略低於 HashSet，但依然提供高效的操作。
		3.適用場景：需要保持元素插入順序且不允許重複時。	
	 */
		
		Set<String> linkedHashSet = new LinkedHashSet<>();
		linkedHashSet.add("Apple");
		linkedHashSet.add("Banana");
		linkedHashSet.add("Apple"); // 重複元素，不會被添加
		System.out.println(linkedHashSet); // 輸出 [Apple, Banana]	
		
	//////////////////////////////////////////////////
		
		/*
		TreeSet:

			TreeSet 是 SortedSet 與Set的主要實現類，它基於紅黑樹（Red-Black Tree）結構(一種自平衡的二元搜尋樹（Binary Search Tree, BST）)，
			保證元素的有序性和操作的時間效率（如插入、刪除和查找的時間複雜度為 O(log n)）。


		特點：

			1.有序性：元素按照自然順序或指定的比較器排序。
			2.性能：高效的查找、插入和刪除操作。
			3.不允許 null 元素：除非使用自定義的比較器，否則不允許插入 null。
			4.不允許插入重複元素
		 */
		
		
		
	}
}
