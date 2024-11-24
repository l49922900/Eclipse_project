package Generics20241010;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class DiamondOperator20241011 {
	/*
	鑽石運算符的基本概念：

		1.鑽石運算符是一對空的尖括號 <>。
		2.它允許編譯器自動推斷泛型類型參數。


	使用場景：

		1.主要用於創建泛型類的實例時。
		2.可以用在變量聲明、方法調用等多種上下文中。
		
	 */
	public static void main(String[] args) {
		//a. 標準集合類：
		Map<String, List<String>> myMap2 = new HashMap<>();
		//編譯器從左側的聲明推斷出 HashMap 的類型參數
		
		
		//b. 自定義泛型類：
//		Pair<String, Integer> pair = new Pair<>("Age", 30);
		//編譯器從聲明和構造函數參數推斷出 Pair 的類型參數。
		
		//c. 方法調用：
		List<String> names = Collections.synchronizedList(new ArrayList<>());
		//鑽石運算符也可以在方法調用中使用。
		
	}
	
}
