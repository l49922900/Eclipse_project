package Lambda20241017;

interface Greeting {
	void sayHello();
}

class JFrame {
	public void setVisible(boolean b) {
	}

	public JFrame(String string) {
	}
}

public class AnonymousClass20241017 {
	/*
	 * 匿名類別（Anonymous Class）:
	 * 
		 * 是一種「沒有名稱」的內部類別。
		 * 它允許你在創建類的同時立即對其進行實例化，特別適用於需要臨時使用某個類的情況，如實現介面或擴展現有類的功能，而不需要為此額外定義一個命名類別。
	 * 
	 * 特點與用途:
	 * 
		 * 1.無名稱：匿名類別沒有顯式的類名，通常在聲明的同時被實例化。 
		 * 2.一次性使用：適合於一次性使用的場景，不需要重複使用該類別。
		 * 3.簡化代碼：減少額外類別的定義，使代碼更為簡潔。
	 * 
	 匿名類別通常用於實現介面或繼承某個類，基本語法如下：
	 
		 new 介面或父類() { 
		 
		 // 實現方法或覆蓋方法 
		 	};
		 	
		 優點
		 
			1.簡潔性：減少了額外類別的定義，讓代碼更加緊湊。
			2.封裝性：將特定功能的實現封裝在使用它的地方，提高代碼的內聚性。
			3.方便性：適合用於事件處理、回調等場景，提升開發效率。
		
		缺點
		
			1.可讀性：過度使用匿名類別可能使代碼變得難以閱讀和維護，尤其是在類別內容較為複雜時。
			2.調試困難：由於沒有名稱，調試和錯誤追蹤可能較為困難。
			3.無法重用：匿名類別通常是一次性使用，無法在多處重用相同的實現。
	 */
	public static void main(String[] args) {

		//////////////////////////////////////
		
		// 例子 實現介面的匿名類別：:
		Greeting greeting = new Greeting() {
			@Override
			public void sayHello() {
				System.out.println("你好！");
			}
		};

		// 調用匿名類別的方法
		greeting.sayHello();

		///////////////////////////////////////////////////////

		//例子 繼承類的匿名類別：
		JFrame frame = new JFrame("My Frame") {
		/*
		這裡使用匿名類別創建 JFrame 的子類實例:
			 
			 這個匿名子類繼承自 JFrame 並覆蓋了 setVisible 方法
			 ***跟一般創建物件不同的是，後面多了{}中的內容
			 ***使用 new JFrame("My Frame") { ... } 的語法，您創建了一個沒有名字的子類，這個子類繼承自 JFrame。
			 ***frame是變數名，
		*/
			
			@Override
			public void setVisible(boolean b) {
				super.setVisible(b);
				System.out.println("Frame is now visible: " + b);
			}
		};
		frame.setVisible(true);
		
		/////////////////////////////////////////////////
		
		//例子: 如果我們想要將某個行為（例如：一段邏輯處理或運算）傳遞給方法，我們通常會使用匿名類別（Anonymous Class）來實現
		//去看AnonymousClassUseExample20241017
	}

}
