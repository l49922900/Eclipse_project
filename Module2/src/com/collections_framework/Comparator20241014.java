package com.collections_framework;

import java.util.Comparator;

public class Comparator20241014 {
	/*
	Comparator 介面:
	
		Comparator 是另一個泛型介面，用來定義「自訂排序」(custom ordering)。
		與 Comparable 不同，Comparator 可以定義多種不同的排序方式，且不需要修改物件的類別。
		//從 Java 8 開始，可以使用匿名類別或 Lambda 表達式簡化 Comparator 的實作。具體怎麼做問GPT
		
	方法:	
	
		public interface Comparator<T> {
	    int compare(T o1, T o2);
	    }
	    
		compare(T o1, T o2) 方法是用來比較兩個物件的大小。
		返回負數：表示 o1 比 o2 小。
		返回零：表示兩者相等。
		返回正數：表示 o1 比 o2 大。
		
		
		Comparable 特性:
	 * 
			1.定義位置：在需要排序的類別中實作。
			2.方法數量：只有一個 compareTo 方法。
			3.是否需修改類別：需要修改類別的程式碼，並實作 Comparable 介面。
			4.排序方式：定義物件的自然排序（通常是一種唯一的排序方式）。
			5.用途：當類別有明確的自然排序需求時使用。
			
			
	   Comparator 特性:
	   
			1.定義位置：作為獨立的類別或匿名類別來實作。
			2.方法數量：至少需要實作一個 compare 方法。
			3.是否需修改類別：不需要修改類別的程式碼，可以在外部定義排序邏輯。
			4.排序方式：可以定義多種排序方式（具有較高的靈活性）。
			5.用途：當需要多種排序方式或無法修改類別時使用。
			
			
			
		選擇使用 Comparable：

			1.當類別有明確且唯一的排序需求時（例如：字母順序、數字大小等）。
			2.希望在類別內部實作排序邏輯，使排序方法一致。



		選擇使用 Comparator：

			1.當需要多種排序方式時。
			2.無法修改類別的程式碼時（例如：第三方庫的類別）。
			3.排序邏輯可能隨著需求變動。

	 */
}

class StudentNameComparator implements Comparator<Student> {
    
	@Override
    public int compare(Student s1, Student s2) {
        return s1.getName().compareTo(s2.getName()); // 根據名字字母順序排序
    }
	
	
	/*
	public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Alice", 23),
            new Student("Bob", 20),
            new Student("Charlie", 22)
        );

        Collections.sort(students, new StudentNameComparator()); // 使用 Comparator 排序

        for (Student s : students) {
            System.out.println(s);
        }
    }
	 */
}
