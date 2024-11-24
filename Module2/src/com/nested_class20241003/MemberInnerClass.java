package com.nested_class20241003;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberInnerClass extends JFrame {
	//MyFrame 類別繼承自 JFrame，表示這個類別是一個視窗框架。
	
	
	
	/*這裡介紹的是成員內部類別（Member Inner Class）
	 
	 * 內部類別（Inner Class）
	 * 內部類別是不使用 static 修飾符定義的類別，與外部類別的實例緊密相關。內部類別可以訪問外部類別的所有成員(因為他是實例的)，包括私有成員。
	 		
	 		分為以下幾種:
	 			成員內部類別（Member Inner Class）
				方法內部類別（局部內部類別，Local Inner Class）
				匿名內部類別（Anonymous Inner Class）
				
				這裡介紹的是成員內部類別（Member Inner Class）
		特性:
			
			(1)靜態嵌套類別與內部類別的主要區別在於是否與外部類別的實例相關聯。靜態嵌套類別不需要外部類別的實例即可使用，而內部類別則需要。
	 		(2)訪問控制： 內部類別可以訪問外部類別的所有成員，包括私有成員。
			(3)實例化依賴性： 內部類別的實例化依賴於外部類別的實例(因為他是實例的)。
	 */
	
	
	
	
	//例子:使用 Java Swing 庫來建立一個簡單的圖形使用者介面（GUI），包含一個按鈕。
	//當使用者點擊按鈕時，會彈出一個對話框顯示訊息。
	private JButton button;
	//private JButton button;：宣告一個按鈕元件。
	
	

    public MemberInnerClass() {
        button = new JButton("點擊我");
        button.addActionListener(new ButtonClickListener());
        //為按鈕添加事件監聽器，當按鈕被點擊時會觸發 ButtonClickListener 內部類別中的 actionPerformed 方法。
        
        this.add(button);
        //將按鈕添加到 JFrame 中。
        
        this.setSize(200, 200);
        //設置視窗的大小為 200x200 像素。
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //設置關閉視窗時的行為為退出程式。
        
        this.setVisible(true);
        //使視窗可見
    }

    // 內部類別實現事件監聽器
    private class ButtonClickListener implements ActionListener {
    	//內部類別，用於處理按鈕的點擊事件。
    	
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	//當按鈕被點擊時，此方法會被呼叫。
        	
        	
            JOptionPane.showMessageDialog(MemberInnerClass.this, "按鈕被點擊了！");
            //出一個訊息對話框，顯示「按鈕被點擊了！」的訊息。MyFrame.this 作為對話框的父元件，確保對話框相對於主視窗顯示。
        }
    }

    public static void main(String[] args) {
        new MemberInnerClass();
    }
    
    
    /*
     * 優點:
     		
     		1.如果內部類別需要被多個外部方法使用，將其作為成員內部類別可以提高重用性，而不需要在每個方法中重複創建匿名內部類別。
     		2.需要保護內部類別的實現細節，增強封裝性。
     */
}
