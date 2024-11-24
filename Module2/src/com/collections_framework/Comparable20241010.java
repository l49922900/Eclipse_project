package com.collections_framework;

import java.lang.Comparable;

class Comparable20241010 {
	/*
	 * Comparable 介面:
	 * 
	 * Comparable 是一個泛型介面，讓類別可以自行定義「自然排序」(natural ordering)。 當一個類別實作了 Comparable
	 * 介面後，該類別的物件就可以使用像 Collections.sort() 或 Arrays.sort() 這類排序方法來排序。
	 * 
	 * 
	 * 「自然排序」（Natural Ordering）:
	 * 
	 * 是指物件在其類別中預先定義的一種排序方式，通常基於物件的某個主要屬性。
	 * 這種排序方式由物件本身決定，並且通常是最常見或最直觀的排序方法。例如，數字的自然排序是從小到大，字串的自然排序是按照字母順序排列。
	 * 
	 * 
	 * 方法:
	 * 
	 * public interface Comparable<T> { int compareTo(T o); }
	 * 
	 * compareTo(T o): 是用來比較當前物件與傳入物件的大小。 返回負數：表示當前物件比傳入物件小。 返回零：表示兩者相等。
	 * 返回正數：表示當前物件比傳入物件大。
	 * 
	 * 
	 * Comparable 特性:
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

class Student implements Comparable<Student> {
	// 例子
	// 假設有一個 Student 類，需要根據學生的年齡進行排序：

	private String name;
	private int age;

	// 建構子、getters 與 setters 略

	@Override
	public int compareTo(Student other) {
		return this.age - other.age; // 根據年齡升序排序
	}

	@Override
	public String toString() {
		return name + ": " + age;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	
	
	//實際執行
//	public static void main(String[] args) {
//		List<Student> students = Arrays.asList(new Student("Alice", 23), new Student("Bob", 20),
//				new Student("Charlie", 22));
//
//		Collections.sort(students); // 使用 compareTo 方法排序
//
//		for (Student s : students) {
//			System.out.println(s);
//		}
//	}
}
