package com.collections_framework;

import java.util.ArrayList;
import java.util.Iterator;

public class Iterator20241007 {
	/*
	Iterator :
	用於遍歷集合（如 List、Set 等）中的元素。它提供了一種統一的方式來訪問集合中的元素，而不需要了解集合的內部結構
	
	
	
	主要包含以下三個方法：

	boolean hasNext()
	
		描述：檢查迭代器是否還有下一個元素可供訪問。
		返回值：如果有下一個元素，返回 true；否則返回 false。
		
		
	
	E next()
	
		描述：返回迭代器的下一個元素。
		返回值：集合中的下一個元素。
		注意：如果沒有更多元素，會拋出 NoSuchElementException。
		
	
	
	void remove()
	
		描述：從集合中移除迭代器返回的最後一個元素。
		注意：此方法是可選的，並非所有的迭代器都支持。如果不支持，會拋出 UnsupportedOperationException。
	 
	***增強型 for 迴圈（for-each）提供了一種更簡潔的方式來遍歷集合，內部實際上也是使用 Iterator 來實現
	 然而，當需要在遍歷過程中修改集合（如刪除元素）時，仍然建議使用 Iterator，因為增強型 for 迴圈不支持直接修改集合。
	 */
	
	
	//例子
	public static void main(String[] args) {
        // 創建一個 ArrayList 並添加元素
        ArrayList<String> list = new ArrayList<>();
        list.add("蘋果");
        list.add("香蕉");
        list.add("橙子");

        // 獲取迭代器
        Iterator<String> iterator = list.iterator();

        // 使用迭代器遍歷集合
        while (iterator.hasNext()) {
            String fruit = iterator.next();
            System.out.println(fruit);

            // 示例：移除特定元素
            if (fruit.equals("香蕉")) {
                iterator.remove();
            }
        }

        // 輸出移除後的集合
        System.out.println("移除後的集合: " + list);
    }
}
