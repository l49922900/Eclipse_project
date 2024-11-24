package com.collections_framework;

import java.util.ArrayDeque;
import java.util.Deque;

public class Deque20241009 {

	public static void main(String[] args) {
		/*
		Deque（雙端佇列）:
		
			是一種特殊的佇列，允許在兩端進行元素的插入和移除操作。
			「Deque 是 Queue 介面的子介面」，提供了更靈活的資料結構，既可以作為佇列（FIFO：先入先出），也可以作為堆疊（LIFO：後入先出）來使用。
			
		
		特性:
		
			1.允許在佇列的兩端進行插入和移除操作。這使得 Deque 能夠用作以下資料結構：
				佇列（Queue）：遵循先入先出（FIFO）原則。
				堆疊（Stack）：遵循後入先出（LIFO）原則。
			2.靈活性：既能作為佇列（Queue）使用，也能作為堆疊（Stack）使用。
			3.高效性：大多數操作的時間複雜度為常數時間。
			4.非同步：Deque 的標準實現（如 ArrayDeque 和 LinkedList）不是同步的。如果多線程訪問需要自行處理同步問題，或使用 ConcurrentLinkedDeque 等並發實現。
			5.空佇列操作：許多 Deque 方法在佇列為空時會拋出異常（如 removeFirst），使用相應的 poll 或 peek 方法可以避免異常，這些方法在失敗時會返回 null。
			
				
		 
		 
		 Deque 的實現類:
		 
		 	1.ArrayDeque：基於動態數組實現，沒有容量限制（除非內存不足），效率高。
			2.LinkedList：基於雙向鏈表實現，LinkedList同時是List、Deque、Queue的實現類
		 
		 
		 ArrayDeque:
		 	
			1.雙端佇列：可以在佇列的兩端進行插入和刪除操作。
			2.非執行緒安全：不支援同步(並發)操作。
			3.不允許 null 元素。
			4.比 LinkedList 更高效：通常作為堆疊或佇列的首選實現。
		 
		 
		 ArrayDeque 與 LinkedList 的比較:
		 
		 	特性				ArrayDeque	          LinkedList
			底層結構			 動態數組	           雙向鏈表
			性能	    更高（特別是對於大部分操作）	   相對較低
			記憶體使用	      更高效	          每個元素需要額外的指標
			支持的接口	       Deque   	         Deque, List,Queue
		 	隨機存取        不支援隨機存取           支援隨機存取
		 	null 元素      不允許 null 元素        允許 null 元素
		 	用途         適合用作堆疊或佇列    適合頻繁在中間位置插入或刪除元素的場景
		 	
		 	
		 	建議：
		 	
		 		1.如果需要一個高效的雙端佇列，並且不需要在中間插入元素，選擇 ArrayDeque。
				2.如果需要頻繁在列表中間插入或刪除元素，選擇 LinkedList。
				3.對於大多數用作堆疊或佇列的場景，ArrayDeque 是更好的選擇。
		 
		 
		 
		 
		 
		 
		 Deque vs Queue:
		 
			Queue：僅支持尾部插入和頭部移除操作，適用於典型的先入先出場景。
			Deque：支持雙端插入和移除，提供更大的靈活性，可以作為 Queue 或 Stack 使用
		 
		 
		 
		 
		 常用方法介紹:
		 
			 插入元素:
			 
				addFirst(E e)：在頭部插入元素，若佇列滿則拋出異常。
				offerFirst(E e)：在頭部插入元素，成功返回 true，否則返回 false。
				addLast(E e)：在尾部插入元素，若佇列滿則拋出異常。
				offerLast(E e)：在尾部插入元素，成功返回 true，否則返回 false。

			移除元素:
			
				removeFirst()：移除並返回頭部元素，若佇列為空則拋出異常。
				pollFirst()：移除並返回頭部元素，若佇列為空則返回 null。
				removeLast()：移除並返回尾部元素，若佇列為空則抛出異常。
				pollLast()：移除並返回尾部元素，若佇列為空則返回 null。

			查看元素:
			
				getFirst()：返回頭部元素，若佇列為空則抛出異常。
				peekFirst()：返回頭部元素，若佇列為空則返回 null。
				getLast()：返回尾部元素，若佇列為空則抛出異常。
				peekLast()：返回尾部元素，若佇列為空則返回 null。
		 	
		 */
		
		// 創建一個 ArrayDeque 實例
        Deque<String> deque = new ArrayDeque<>();

        // 在尾部插入元素
        deque.addLast("Java");
        deque.addLast("Python");
        deque.addLast("C++");
        System.out.println("Deque after adding elements at last: " + deque);

        // 在頭部插入元素
        deque.addFirst("JavaScript");
        System.out.println("Deque after adding element at first: " + deque);

        // 移除頭部元素
        String first = deque.removeFirst();
        System.out.println("Removed first element: " + first);
        System.out.println("Deque after removing first: " + deque);

        // 移除尾部元素
        String last = deque.removeLast();
        System.out.println("Removed last element: " + last);
        System.out.println("Deque after removing last: " + deque);

        // 使用堆疊操作
        deque.push("Ruby");
        System.out.println("Deque after push: " + deque);
        String top = deque.pop();
        System.out.println("Popped element: " + top);
        System.out.println("Deque after pop: " + deque);

        // 查看頭部和尾部元素
        String peekFirst = deque.peekFirst();
        String peekLast = deque.peekLast();
        System.out.println("First element: " + peekFirst);
        System.out.println("Last element: " + peekLast);
    

	}

}
