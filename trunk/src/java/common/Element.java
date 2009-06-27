package common;

public class Element<T extends Comparable<T>> implements Comparable<Element<T>> {

	private T value;
	private int id;

	public Element(T value, int id) {
		this.value = value;
		this.id = id;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int compareTo(Element<T> element) {
		return this.value.compareTo(element.value);
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
