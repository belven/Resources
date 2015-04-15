package belven.resources;

import org.bukkit.inventory.ItemStack;

public class ClassDrop {
	public ItemStack is;
	public int maxAmount = 1;
	public int wildernessAmount = 0;
	public boolean isArmor;
	public boolean isWilderness = false;

	public ClassDrop(ItemStack item, int max) {
		is = item;
		isArmor = MaterialFunctions.isArmor(is.getType());
		maxAmount = max;
	}

	public ClassDrop(ItemStack item, int max, int wildernessQuantity) {
		this(item, max);
		wildernessAmount = wildernessQuantity;
		isWilderness = true;
	}
}