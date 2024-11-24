package com.factory_design_pattern;

public class SimpleFactoryPattern {
	/*
	 工廠設計模式（Factory Design Pattern）:

	 是創建型設計模式（Creational Design Patterns）之一，旨在提供一種創建對象的接口，而不暴露創建邏輯的具體實現。
	
	
	 在Java中，工廠設計模式主要有以下幾種類型：
	 
	 1.簡單工廠模式（Simple Factory Pattern） 
	 2.工廠方法模式（Factory Method Pattern）
	 3.抽象工廠模式（Abstract Factory Pattern）
	 
	 
	 
	 這裡介紹的是簡單工廠模式（Simple Factory Pattern）
	 
	 優點：

		1.封裝了對象創建的過程，降低了客戶端的耦合度。
		2.易於擴展新的產品類型，只需修改工廠類即可。
	
	缺點：

		1.當產品種類增加時，工廠類會變得複雜且不易維護。
		2.不符合開閉原則（對擴展開放，對修改封閉）。
	 
	 */
}
/////////////////////////////////////

//例子:

//Shape 接口和幾個實現類:
//定義形狀接口
interface Shape {
	void draw();
}

//圓形實現
class Circle implements Shape {
	@Override
	public void draw() {
		System.out.println("畫一個圓形");
	}
}

//矩形實現
class Rectangle implements Shape {
	@Override
	public void draw() {
		System.out.println("畫一個矩形");
	}
}

//三角形實現
class Triangle implements Shape {
	@Override
	public void draw() {
		System.out.println("畫一個三角形");
	}
}

////////////////////////////////

class ShapeFactory {
	// 根據形狀類型創建對象
	public Shape getShape(String shapeType) {
		if (shapeType == null) {
			return null;
		}
		if (shapeType.equalsIgnoreCase("CIRCLE")) {
			// 如果傳入的參數是CIRCLE，則返回Circle物件
			return new Circle();
		} else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
			return new Rectangle();
		} else if (shapeType.equalsIgnoreCase("TRIANGLE")) {
			return new Triangle();
		}
		return null;
	}
}

class FactoryPatternDemo {
	public static void main(String[] args) {
		ShapeFactory shapeFactory = new ShapeFactory();

		// 獲取圓形並繪製
		Shape shape1 = shapeFactory.getShape("CIRCLE");
		shape1.draw();

		// 獲取矩形並繪製
		Shape shape2 = shapeFactory.getShape("RECTANGLE");
		shape2.draw();

		// 獲取三角形並繪製
		Shape shape3 = shapeFactory.getShape("TRIANGLE");
		shape3.draw();
	}
}