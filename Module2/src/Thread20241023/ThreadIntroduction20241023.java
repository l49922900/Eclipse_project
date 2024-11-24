package Thread20241023;

public class ThreadIntroduction20241023 {
	/*
	 *** 在 Java 的開發實務中，通常會更常使用 ExecutorService 而非直接使用 Thread
	 
	 
	 進程(Process)和執行緒(Thread)的區別:
	 
		進程(Process)：
			啟動一個LOL.exe就叫一個行程。 接著又啟動一個DOTA.exe，這叫兩個進程。
		
		線程(Thread)：
			線程是在進程內部同時做的事情
			比如在LOL裡，有很多事情要同時做，比如"蓋倫” 擊殺“提莫”，同時“賞金獵人”又在擊殺“盲僧” ，這就是由多線程來實現的。
			
			
			
			
		執行緒的使用時機:
		
			單執行緒應用程式:
				大部分的 Java 應用程式，特別是簡單的應用程式或工具程式，通常只在單一主執行緒（main thread）中運行。這意味著所有的程式碼按順序執行，不需要多執行緒。
				例如，大多數簡單的控制台應用程式、批處理程式都不需要額外的執行緒。
			
			多執行緒應用程式:
				當需要同時執行多個任務或需要對應用程式進行並行處理時，會使用執行緒。
				例如，伺服器應用程式、網路應用程式、遊戲開發等場景經常需要處理並行請求或任務。
			
				異步任務：當任務可以在不阻塞主執行緒的情況下進行，例如文件讀寫、網路請求、計時器等。
				伺服器：伺服器應用通常會為每個用戶請求分配一個新的執行緒來處理，這樣伺服器可以同時處理多個請求。
				圖形用戶界面（GUI）應用程式：GUI 框架（如 Swing 或 JavaFX）中的主執行緒是事件派發執行緒，額外的計算或 I/O 可能會需要在其他執行緒中運行，以避免阻塞 UI 響應。
		
		不應濫用多執行緒:
		
			多執行緒可以提高應用程式的效能和反應速度，但它也會引入複雜性，如資料同步、競爭條件和死鎖問題。
			
			
		三種建立Thread的方法:
			
			1.類別繼承java.lang.Thread類別(Thread類別實作Runnable介面)
				步驟:
					(1)寫一個新類別，繼承Thread類別
					(2)覆寫public void run()方法
					(3)建構一個新類別物件
					(4)執行其start()方法(繼承自Thread類別)
					
					例子:
					
					class MyThread extends Thread {
					    public void run() {
					        System.out.println("Thread is running...");
					    }
					}
					
					public class Main {
					    public static void main(String[] args) {
					        MyThread thread = new MyThread();
					        thread.start();  // 啟動執行緒
					    }
					}
					

			2.類別實作java.lang.Runnable介面(Runnable介面宣告public void run()方法)
				步驟:
					1.寫一個新類別，實作Runnable介面
					3.實作 run() 方法(啟動執行緒時，執行 run() 方法)
					4.建構一個Thread物件，以新類別物件作傳入參數
					5.執行Thread物件之start()方法
					***推薦使用此方法
					
					例子:
					
					class MyRunnable implements Runnable {
					    public void run() {
					        System.out.println("Thread is running using Runnable...");
					    }
					}
					
					public class Main {
					    public static void main(String[] args) {
					        MyRunnable runnable = new MyRunnable();
					        Thread thread = new Thread(runnable);
					        thread.start();  // 啟動執行緒
					    }
					}
				
				3. 使用 Lambda 表達式
				
				public static void main(String[] args) {
				        Thread thread = new Thread(() -> {
				            System.out.println("Thread is running using lambda expression...");
				        });
				        thread.start();  // 啟動執行緒
				    }
				    
			//////////////////////////////////////////////
			
			
			共用資料（記憶體中的共用資料）

				這些資料是多執行緒中會共用的，因此可能需要處理同步問題。這些資料類型包括：
					
					1.類別屬性：靜態變數或類別級別的資料。
					2.物件屬性：物件中的成員變數。
					3.多執行緒執行時可能有同步問題（Thread Unsafe）：這些資料在多執行緒執行時，如果不處理同步問題，可能會發生資料競爭或不一致的情況。
			
			
			非共用資料
			
				這些資料不會在多執行緒間共用，因此不會有同步問題，稱為「Thread Safe」。包括：
					
					1.區域變數：方法內部的變數。
					2.方法傳入參數：傳遞給方法的參數，通常只在方法範圍內使用。
					3.例外物件：處理例外情況時建立的物件。
					4.多執行緒執行時無同步問題（Thread Safe）：這些資料在多執行緒下不會引發同步問題，因為每個執行緒都會有自己獨立的副本。
			
			
			
			
				
			///////////////////////////////////////////////
				
				
			常見方法:
			
				start():
				
					功能: 啟動執行緒，讓執行緒開始執行。會觸發 run() 方法。
					使用情境: 當你需要啟動一個新的執行緒來執行某些任務時。
			
					thread thread = new Thread(() -> {
					    System.out.println("執行緒已啟動");
					});
					thread.start();
					
				 run():
				 
					功能: 定義執行緒要執行的邏輯，一般不直接呼叫，而是透過 start() 自動執行。			
					使用情境: 定義執行緒的行為，常見於實現 Runnable 介面或覆寫 Thread 類別的情況。
					
					class MyThread extends Thread {
					    @Override
					    public void run() {
					        System.out.println("自定義執行緒運行中...");
					    }
					}
					
					
					sleep(long millis):
					
						功能: 讓當前執行緒休眠指定的時間（毫秒），以暫時停止執行。
						使用情境: 當你需要讓執行緒暫時停止，模擬等待時間或控制執行緒的執行頻率時。
			
						try {
						    Thread.sleep(1000); // 執行緒休眠 1 秒
						} catch (InterruptedException e) {
						    e.printStackTrace();
						}
						
						
					join():
					
						功能: 等待另一個執行緒執行完畢，再繼續執行當前的執行緒。						
						使用情境: 當需要確保某個執行緒結束後，才執行後續操作時。
						
						Thread thread = new Thread(() -> {
						    System.out.println("執行緒執行中...");
						});
						thread.start();
						try {
						    thread.join(); // 等待執行緒結束
						} catch (InterruptedException e) {
						    e.printStackTrace();
						}
						System.out.println("主執行緒繼續執行");
						
						
				interrupt():
				
					功能: 中斷執行緒，通常用於「提醒」執行緒停止執行。中斷不會強制執行緒結束，但會改變它的中斷狀態。
					
							***中斷不會強制執行緒結束：
							
								interrupt() 不會直接停止正在運行的執行緒。
								執行緒需要自行檢查自己的中斷狀態，並根據情況自行決定是否要結束。
								這意味著執行緒可能會在中斷後繼續執行某些任務，直到它檢查到自己被中斷並做出反應為止。
					
							會改變它的中斷狀態：
							
								當執行緒被中斷時，它的中斷標誌（interrupted flag）會被設置為 true。
								執行緒可以通過方法 isInterrupted() 或 interrupted() 來檢查它是否被中斷，並根據這個狀態來決定接下來要怎麼做。
							
							***執行緒能夠自己決定是否要停止以及何時停止:
								
								java採用了自我管理的中斷機制，這種設計要求執行緒自行檢查是否應該停止
								這樣的設計是為了確保程式的安全性和穩定性
								
							
					使用情境: 當你想要停止或中斷正在執行的執行緒，特別是在執行緒長時間運行時。
						
						
						Thread thread = new Thread(() -> {
						    while (!Thread.currentThread().isInterrupted()) {
						        System.out.println("執行緒運行中...");
						    }
						});
						thread.start();
						thread.interrupt(); // 嘗試中斷執行緒
						
						
			isAlive():
					
					功能: 檢查執行緒是否還在運行（尚未結束）。						
					使用情境: 需要確認某個執行緒是否仍在執行中時。
						
						
						Thread thread = new Thread(() -> {
						    System.out.println("執行緒啟動");
						});
						thread.start();
						System.out.println(thread.isAlive()); // 檢查執行緒狀態
						
						
						
			setPriority(int priority) 和 getPriority():
			
					功能: 設定和取得執行緒的優先級。優先級數值範圍為 1 (Thread.MIN_PRIORITY) 到 10 (Thread.MAX_PRIORITY)。					
					使用情境: 當需要調整執行緒的優先順序時，但這通常是由作業系統調度器決定的，並不能完全保證效果。
					
					
					Thread thread = new Thread(() -> {
					    System.out.println("執行緒運行中...");
					});
					thread.setPriority(Thread.MAX_PRIORITY); // 設定為最高優先級
					thread.start();
					
					
			setName(String name) 和 getName()
			
				功能: 設定和取得執行緒的名稱。				
				使用情境: 當你希望給執行緒賦予有意義的名稱，便於日後進行除錯或管理時。

				Thread thread = new Thread(() -> {
				    System.out.println("執行緒名稱：" + Thread.currentThread().getName());
				});
				thread.setName("MyThread");
				thread.start();
				
			yield()
			
				功能: 提示當前執行緒願意讓出 CPU 資源，讓其他相同優先級的執行緒有機會執行。但這只是建議，作業系統不一定會遵守。				
				使用情境: 當你希望當前執行緒暫時讓出 CPU 給其他執行緒時。
				
				Thread.yield(); // 讓出 CPU 給其他執行緒
				
			
	 */	
	public static void main(String[] args) {
		
	}
				
}
