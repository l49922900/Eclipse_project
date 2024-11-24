package Stream20241018;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class IntStream20241021 {
	public static void main(String[] args) {
		
	
	/*
	IntStream:
	
	 	是一個針對 int 基本型別的流。
	 	透過它，我們可以針對 int 型別的數據執行串流操作，像是篩選、轉換、計算等。
	 	
	 	
	 	主要方法：
	 	
	 		1.of(int... values):將指定int陣列元素依序轉換為Stream物件
	 		2.stream(int[] array):將指定int陣列元素依序轉換為IntStream物件
			3.range(int startInclusive, int endExclusive)：建立一個範圍 [startInclusive, endExclusive) 的 IntStream。
			4.filter(IntPredicate predicate)：依照條件過濾流中的元素。
			5.map(IntUnaryOperator mapper)：對成員套用函式，將傳入元素一對一轉換為另一元素
			6.sum()：對流中的所有元素進行加總。
	 */
		
		//例子:
		// 建立一個從 1 到 9 的範圍
        int sum = IntStream.range(1, 10)
                           .filter(n -> n % 2 == 0)  // 過濾偶數
                           .sum();  // 將結果加總
        System.out.println("偶數的總和: " + sum);	
        
        
        
        
        /*
        LongStream:
        
         	是一個針對 long 基本型別的流，適合處理較大的整數數據集。
         	
         	
        主要方法：
        
			range(long startInclusive, long endExclusive)：建立一個範圍 [startInclusive, endExclusive) 的 LongStream。
			filter(LongPredicate predicate)：依照條件過濾流中的元素。
			mapToObj(LongFunction<U> mapper)：將 long 值轉換為物件。
			average()：計算流中元素的平均值，返回 OptionalDouble。	
         */
        
     // 建立一個從 1 到 100 的範圍，並計算平均值
        double avg = LongStream.range(1, 101)
                               .filter(n -> n % 5 == 0)  // 過濾能被 5 整除的數字
                               .average()
                               .orElse(0.0);  // 如果結果為空，返回 0.0
        System.out.println("能被5整除的數字的平均值: " + avg);
        
        /*
        DoubleStream:
        
         	是一個針對 double 基本型別的流，主要用於處理浮點數運算。
         	
         主要方法：
         
			of(double... values)：建立包含多個 double 值的流。
			filter(DoublePredicate predicate)：依條件過濾流中的元素。
			map(DoubleUnaryOperator mapper)：對成員套用函式，將傳入元素一對一轉換為另一元素
			max()：計算流中元素的最大值，返回 OptionalDouble。
         */
        
     // 建立一個 double 值流，並找出最大值
        double max = DoubleStream.of(1.5, 2.3, 3.7, 0.6)
                                 .filter(n -> n > 1.0)  // 過濾大於 1.0 的數字
                                 .max()
                                 .orElse(Double.NaN);  // 如果結果為空，返回 NaN
        System.out.println("最大值: " + max);
	}
}
