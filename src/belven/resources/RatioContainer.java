package belven.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;

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

	public List<Ratio<T, Double>> getRatios() {
		return ratios;
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
		Bukkit.getServer().getLogger().info("total Ratio: " + String.valueOf(totalRatio));

		List<Ratio<T, Double>> tempRatios = new ArrayList<>();

		for (Ratio<T, Double> ratio : ratios) {
			double value = 0.0;
			if (index == -1) {
				value = ratio.getValue() / totalRatio * 100;
			} else {
				double tempValue = ratio.getValue() / totalRatio * 100;
				value = tempValue + tempRatios.get(index).getValue();
			}
			Bukkit.getServer().getLogger().info("value: " + String.valueOf(value));

			tempRatios.add(new Ratio<T, Double>(ratio.getKey(), value));
			index++;
		}
		return tempRatios;
	}

	public T getRandomKey() {
		List<Ratio<T, Double>> tempRatios = getListActualValues();
		int rand = random.nextInt(100);

		for (Ratio<T, Double> ratio : tempRatios) {
			if (rand < ratio.getValue()) {
				return ratio.getKey();
			}
		}
		return null;
	}
}
