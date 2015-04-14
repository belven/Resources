package belven.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RatioContainer<T> {
	private T t;
	private Random random = new Random();
	private List<Ratio<T, Double>> ratios = new ArrayList<>();

	public void set(T t) {
		this.t = t;
	}

	public T get() {
		return t;
	}

	public void Add(Ratio<T, Double> ratio) {
		ratios.add(ratio);
	}

	public void Add(T key, Double value) {
		ratios.add(new Ratio<T, Double>(key, value));
	}

	public Ratio<T, Double> Get(T key) {
		for (Ratio<T, Double> ratio : ratios) {
			if (ratio.getKey() == key) {
				return ratio;
			}
		}
		return null;
	}

	public double getTotalRatio() {
		double total = 0;

		for (Ratio<T, Double> ratio : ratios) {
			total += ratio.getValue();
		}
		return total;
	}

	public List<Ratio<T, Double>> getListActualValues() {
		int index = -1;
		double totalRatio = getTotalRatio();
		List<Ratio<T, Double>> tempRatios = new ArrayList<>();

		for (Ratio<T, Double> ratio : ratios) {
			double value = index == -1 ? ratio.getValue() / totalRatio : tempRatios.get(index).getValue()
					+ (ratio.getValue() / totalRatio);
			tempRatios.add(new Ratio<T, Double>(ratio.getKey(), value * 100));
			index++;
		}
		return tempRatios;
	}

	public T getRandomKey() {
		List<Ratio<T, Double>> tempRatios = getListActualValues();
		int rand = random.nextInt(100);

		for (int i = 0; i < tempRatios.size(); i++) {
			if (rand < tempRatios.get(i).getValue()) {
				return tempRatios.get(i).getKey();
			}
		}
		return null;
	}
}
