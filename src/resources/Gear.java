package resources;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Gear {
	public ItemStack h;
	public ItemStack c;
	public ItemStack l;
	public ItemStack b;
	public ItemStack w;

	public Gear() {

	}

	public Gear(Player p) {
		PlayerInventory inv = p.getInventory();
		h = inv.getHelmet();
		c = inv.getChestplate();
		l = inv.getLeggings();
		b = inv.getBoots();
		w = inv.getItemInHand();
	}

	public void SetGear(LivingEntity le) {
		EntityEquipment inv = le.getEquipment();
		if (h != null)
			inv.setHelmet(h);

		if (c != null)
			inv.setChestplate(c);

		if (l != null)
			inv.setLeggings(l);

		if (b != null)
			inv.setBoots(b);

		if (w != null)
			inv.setItemInHand(w);
	}

	public Gear(ItemStack helmet, ItemStack chest, ItemStack legs,
			ItemStack boots, ItemStack weapon) {
		h = helmet;
		c = chest;
		l = legs;
		b = boots;
		w = weapon;
	}

	public void Clear() {
		h = null;
		c = null;
		l = null;
		b = null;
		w = null;
	}

}
