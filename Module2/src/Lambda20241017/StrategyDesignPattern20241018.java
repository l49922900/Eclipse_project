package Lambda20241017;

public class StrategyDesignPattern20241018 {
	/*
	策略設計模式（Strategy Design Pattern）是一種行為型設計模式，
	主要用來定義一系列算法，並將每個算法封裝起來，使得它們可以相互替換，而不影響客戶端使用的方式。
	此模式允許我們在運行時根據具體需求來選擇不同的策略或算法，從而達到靈活擴展和修改系統行為的目的。

	策略設計模式的三個主要角色：
	
		1.策略（Strategy）介面：定義所有具體策略類的共同行為（例如一個通用的方法 doOperation()）。
		2.具體策略（ConcreteStrategy）類：實現策略介面，提供具體的算法實現。
		3.上下文（Context）類：維護對策略物件的引用，並根據具體情況來調用相應的策略。
		
	Lambda 表達式與策略設計模式的應用:

		在傳統策略模式中，我們需要定義一個介面並且建立多個具體的策略類。
		然而，使用 Lambda 表達式後，我們可以直接將算法（行為）作為參數來傳遞，而不需要顯式地建立具體策略類，從而使代碼更加簡潔。
	 */
	
	////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		Order order = new Order();

        // 使用正常價格策略
        order.setPriceStrategy(price -> price);
        System.out.println("正常價格策略: " + order.calculateFinalPrice(150));

        // 使用滿額減策略
        order.setPriceStrategy(price -> {
            if (price > 100) {
                return price - 20;
            }
            return price;
        });
        System.out.println("滿額減策略: " + order.calculateFinalPrice(150));

        // 使用百分比折扣策略
        order.setPriceStrategy(price -> price * 0.9);
        System.out.println("百分比折扣策略: " + order.calculateFinalPrice(150));
	}
	
}

@FunctionalInterface
interface PriceStrategy {

	/*
	範例：電子商務中的價格計算策略

	需求:
	
		在一個電子商務系統中，當用戶下訂單時，需要計算最終價格。
		不同的情況下會有不同的價格計算策略，
		
	具體包括：

		正常價格策略：不適用任何折扣，原價銷售。
		滿額減策略：如果購買金額超過某個門檻，則減去一定金額。
		百分比折扣策略：根據一定的百分比提供折扣。
	 */
	
	
	
	//定義一個通用的 PriceStrategy 介面，所有的價格計算策略都要實現這個介面的方法。
	
    double calculatePrice(double price);
}

class StrategyPatternECommerce {
	//可以通過 Lambda 表達式來實現不同的價格計算策略。
	
    public static void main(String[] args) {
        // 正常價格策略：無折扣，返回原價
        PriceStrategy normalPriceStrategy = price -> price;

        // 滿額減策略：假設購物金額超過100元，減去20元
        PriceStrategy fullReductionStrategy = price -> {
            if (price > 100) {
                return price - 20;
            }
            return price;
        };

        // 百分比折扣策略：給予10%的折扣
        PriceStrategy percentageDiscountStrategy = price -> price * 0.9;

        // 測試使用不同的價格策略
        System.out.println("正常價格策略: " + calculateFinalPrice(150, normalPriceStrategy));
        System.out.println("滿額減策略: " + calculateFinalPrice(150, fullReductionStrategy));
        System.out.println("百分比折扣策略: " + calculateFinalPrice(150, percentageDiscountStrategy));
    }

    // 計算最終價格的方法，接收價格和策略
    public static double calculateFinalPrice(double price, PriceStrategy strategy) {
        return strategy.calculatePrice(price);
    }
}

class Order {
	/*
	優化上下文類（Context Class）:
	在實際應用中，經常會有一個上下文類來管理策略選擇及其執行，
	我們可以擴展一個上下文類來根據條件選擇不同的策略。
	 */
	
    private PriceStrategy priceStrategy;

    // 設定價格策略
    public void setPriceStrategy(PriceStrategy priceStrategy) {
        this.priceStrategy = priceStrategy;
    }

    // 計算最終價格
    public double calculateFinalPrice(double price) {
        return priceStrategy.calculatePrice(price);
    }
}
