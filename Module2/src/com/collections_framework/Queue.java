package com.collections_framework;

public class Queue {

	public static void main(String[] args) {
		
		/*
		Queue(佇列)介面:
		
			Queue(佇列)是一種遵循先進先出（FIFO）的集合，通常用於排隊或任務調度。
			「Deque 是 Queue 介面的子介面」。
			
		
		Queue 的主要特性:
		
			1.FIFO（先進先出）: 元素按插入的順序處理。
			2.無界和有界: 某些實現（如 LinkedList）無界，而其他實現（如 ArrayBlockingQueue）有固定容量。
			3.可選的容量限制: 可以根據需要設置最大容量。
			4.線程安全: 默認的 Queue 實現（如 LinkedList）不是線程安全的。如果需要線程安全的隊列，可以使用 ConcurrentLinkedQueue 或其他並發隊列實現。
		 
		 
		 Queue常用方法:
		 
		 	1. offer(E e):在Queue中加入指定元素。成功傳回true，失敗傳回false。傳回值為boolean
		 	2. peak():從Queue中取得起始指定元素內容，但不移除。若Queue是空的則傳回null，傳回值為E
		 	3. poll():從Queue中取得起始指定元素內容，並移除該元素。若Queue是空的則傳回null，傳回值為E


		 
		 實現類:LinkedList(LinkedList同時是List、Deque、Queue的實現類)
		 */
	}

}
