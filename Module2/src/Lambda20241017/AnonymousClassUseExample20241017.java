package Lambda20241017;

import java.util.Arrays;
import java.util.List;

public class AnonymousClassUseExample20241017 {
	public static void main(String[] args) {
        List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry");

        // 使用匿名類別來實現自定義的打印邏輯
        printList(fruits, new ListPrinter() {
            @Override
            public void print(String item) {
                System.out.println("Fruit: " + item.toUpperCase());
            }
        });
    }

    // 介面定義
    interface ListPrinter {
        void print(String item);
    }

    // 方法接受一個列表和一個打印器
    static void printList(List<String> list, ListPrinter printer) {
        for (String item : list) {
            printer.print(item);
        }
    }
}

