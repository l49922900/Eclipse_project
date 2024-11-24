package com.factory_design_pattern;

public class FactoryMethodPattern {
	// TODO Auto-generated method stub
	/*
	 * 工廠設計模式（Factory Design Pattern）:
	 * 
	 * 是創建型設計模式（Creational Design Patterns）之一，旨在提供一種創建對象的接口，而不暴露創建邏輯的具體實現。
	 * 
	 * 
	 * 在Java中，工廠設計模式主要有以下幾種類型：
	 * 
	 * 1.簡單工廠模式（Simple Factory Pattern） 2.工廠方法模式（Factory Method Pattern）
	 * 3.抽象工廠模式（Abstract Factory Pattern）
	 * 
	 * 
	 * 
	 * 這裡介紹的是工廠方法模式（Factory Method Pattern）
	 * 
	 * 
	 * 優點：
	 * 
	 * 遵循開閉原則，新增產品類型時不需修改現有代碼，只需添加新的工廠類。 每個工廠類專責創建一種產品，提高了單一職責。
	 * 
	 * 缺點：
	 * 
	 * 當產品種類較多時，會增加工廠類的數量，導致代碼複雜。
	 * 
	 */
}
////////////////////////////////////////////

// 例子
// Shape 接口和幾個實現類:
interface ShapeFactory2 {
	Shape createShape();
}

// 圓形工廠
class CircleFactory implements ShapeFactory2 {
	@Override
	public Shape createShape() {
		return new Circle();
	}
}

// 矩形工廠
class RectangleFactory implements ShapeFactory2 {
	@Override
	public Shape createShape() {
		return new Rectangle();
	}
}

// 三角形工廠
class TriangleFactory implements ShapeFactory2 {
	@Override
	public Shape createShape() {
		return new Triangle();
	}
}

class FactoryMethodPatternDemo {
	public static void main(String[] args) {
		ShapeFactory2 circleFactory = new CircleFactory();
		Shape circle = circleFactory.createShape();
		circle.draw();

		ShapeFactory2 rectangleFactory = new RectangleFactory();
		Shape rectangle = rectangleFactory.createShape();
		rectangle.draw();

		ShapeFactory2 triangleFactory = new TriangleFactory();
		Shape triangle = triangleFactory.createShape();
		triangle.draw();
	}
}
