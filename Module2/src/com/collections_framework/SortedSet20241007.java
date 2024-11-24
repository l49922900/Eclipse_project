package com.collections_framework;

import java.util.SortedSet;
import java.util.TreeSet;



public class SortedSet20241007 {

	public static void main(String[] args) {
		/*
		SortedSet:
		
			專門用於處理有序的集合。它擴展了 Set 介面，並提供了一些額外的方法來支持元素的有序操作。
			確保集合中的元素按照其自然順序或指定的比較器（Comparator）進行排序。這意味著，SortedSet 不僅保證元素的唯一性（由 Set 提供），還保證元素的有序性。 
		
		比較器（Comparator）:
			
			是一個用於定義自定義排序規則的介面。允許開發者在某些類型的集合（如 SortedSet、TreeSet、PriorityQueue 等）或排序方法中，根據特定的邏輯來比較兩個物件。
		
		特點:
		
			1.有序性：元素按照升序排列(依物件邏輯)，或者按照提供的比較器進行排序。
			2.唯一性：作為 Set 的子介面，SortedSet 保證元素的唯一性(equals()來判斷加入的物件是否重複中)。	
		 
		
		
		SortedSet 介面增加了一些專門的方法:
		
			Comparator<? super E> comparator():返回用於此集的比較器，若使用自然順序，則返回 null。
			
			E first():返回此集中的第一個（最小）元素。
			
			E last():返回此集中的最後一個（最大）元素。
			
			SortedSet<E> headSet(E toElement):返回此集的一部分，其元素小於 toElement。
			
			SortedSet<E> tailSet(E fromElement):返回此集的一部分，其元素大於或等於 fromElement。
			
			SortedSet<E> subSet(E fromElement, E toElement):返回此集的一部分，其元素大於或等於 fromElement 且小於 toElement。
			
			Iterator<E> iterator():返回按升序迭代此集的元素。
		 

		Java 提供了多個類來實現 SortedSet 介面，最常用的是 TreeSet(TreeSet同時是Set與SortedSet的實現類)
		 */
		
		////////////////////////////////////////
		
		/*
		TreeSet:

			TreeSet 是 SortedSet 的主要實現類，它基於紅黑樹（Red-Black Tree）結構(一種自平衡的二元搜尋樹（Binary Search Tree, BST）)，
			保證元素的有序性和操作的時間效率（如插入、刪除和查找的時間複雜度為 O(log n)）。


		特點：

			1.有序性：元素按照自然順序或指定的比較器排序。
			2.性能：高效的查找、插入和刪除操作。
			3.不允許 null 元素：除非使用自定義的比較器，否則不允許插入 null。
		 */
		
		//例子
		
		 SortedSet<Integer> sortedSet = new TreeSet<>();

	        sortedSet.add(5);
	        sortedSet.add(3);
	        sortedSet.add(8);
	        sortedSet.add(1);

	        System.out.println("SortedSet: " + sortedSet); // 輸出: [1, 3, 5, 8]
	        System.out.println("First Element: " + sortedSet.first()); // 輸出: 1
	        System.out.println("Last Element: " + sortedSet.last());   // 輸出: 8

	        SortedSet<Integer> headSet = sortedSet.headSet(5);
	        System.out.println("HeadSet (elements < 5): " + headSet); // 輸出: [1, 3]

	        SortedSet<Integer> tailSet = sortedSet.tailSet(5);
	        System.out.println("TailSet (elements >= 5): " + tailSet); // 輸出: [5, 8]

	        SortedSet<Integer> subSet = sortedSet.subSet(2, 7);
	        System.out.println("SubSet (2 <= elements < 7): " + subSet); // 輸出: [3, 5]

	}

}
