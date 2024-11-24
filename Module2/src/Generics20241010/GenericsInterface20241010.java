package Generics20241010;

interface Pair<K, V> {
	/*
	泛型介面:
	
		類似於泛型類，可以在介面定義中使用類型參數。
	 */
	
	
	
	K getKey();

	V getValue();
}

class OrderedPair<K, V> implements Pair<K, V> {
	private K key;
	private V value;

	public OrderedPair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}
}

public class GenericsInterface20241010 {
	public static void main(String[] args) {
        Pair<String, Integer> pair = new OrderedPair<>("Age", 30);
        System.out.println(pair.getKey() + ": " + pair.getValue());
    }
}
