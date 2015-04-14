package belven.resources;

@SuppressWarnings("hiding")
public class Ratio<T, Double> {

	private T key;

	private Double value;

	public Ratio(T key, Double value) {
		this.key = key;
		this.value = value;
	}

	public T getKey() {
		return key;
	}

	public Double getValue() {
		return value;
	}

}
