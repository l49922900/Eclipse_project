package Generics20241010;

class Box<T> {
	//Box 類使用了類型參數 T，在使用時可以指定具體的類型，如 String 或 Integer。
	//<T>告訴編譯器，我們定義此類別為泛型類別，T 必須在類別定義時指定，如此一來T就可以是各種引用類型
	
	private T content;

	public void set(T content) {
		this.content = content;
	}

	public T get() {
		return content;
	}
}

public class GenericsClass20241010 {
	public static void main(String[] args) {
        Box<String> stringBox = new Box<>();
        stringBox.set("Hello Generics");
        String str = stringBox.get();
        System.out.println(str);

        Box<Integer> intBox = new Box<>();
        intBox.set(123);
        Integer num = intBox.get();
        System.out.println(num);
    }
}
