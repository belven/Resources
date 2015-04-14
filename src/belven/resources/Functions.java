package belven.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.google.common.base.Function;

public class Functions {
	private final static List<PotionEffectType> negativeEffects = Arrays.asList(PotionEffectType.HUNGER,
			PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.POISON, PotionEffectType.SLOW,
			PotionEffectType.SLOW_DIGGING, PotionEffectType.WEAKNESS, PotionEffectType.WITHER);

	public static boolean debuffs(PotionEffectType pet) {
		return negativeEffects.indexOf(pet) != -1;
	}

	public synchronized static void callDamageEvent(LivingEntity damager, LivingEntity damagee, double damage) {
		if (damager == null || damagee == null) {
			return;
		}

		HashMap<DamageModifier, Double> damageMap = new HashMap<>();
		damageMap.put(DamageModifier.BASE, damage);

		Map<DamageModifier, Function<? super Double, Double>> functions = new HashMap<>();
		functions.put(DamageModifier.BASE, com.google.common.base.Functions.constant(damage));

		EntityDamageByEntityEvent ede = new EntityDamageByEntityEvent(damager, damagee, DamageCause.CUSTOM, damageMap,
				functions);

		Bukkit.getPluginManager().callEvent(ede);
		// return ede;
	}

	// NEEDS TO BE MOVED
	public static int abilityCap(double maxAmount, double currentLevel) {
		int tempCap = (int) (maxAmount * (currentLevel / getBaseMaxLevel()));

		if (tempCap > maxAmount) {
			tempCap = (int) maxAmount;
		}

		return tempCap;
	}

	// NEEDS TO BE MOVED
	public static int averagePlayerLevel() {
		Collection<? extends Player> tempPlayers = Bukkit.getServer().getOnlinePlayers();

		int AverageLevel = 0;
		int totalLevel = 0;

		for (Player p : tempPlayers) {
			totalLevel += p.getLevel();
		}

		AverageLevel = totalLevel / tempPlayers.size();

		return AverageLevel;
	}

	public static int getRandomIndex(Object[] array) {
		int ran = new Random().nextInt(array.length);

		if (ran == array.length) {
			ran--;
		}

		return ran;
	}

	public static int getRandomIndex(List<?> array) {
		return getRandomIndex(array.toArray());
	}

	// NEEDS TO BE MOVED
	public static int maxPlayerLevel() {
		Collection<? extends Player> tempPlayers = Bukkit.getServer().getOnlinePlayers();

		int maxLevel = 0;

		for (Player p : tempPlayers) {
			if (maxLevel < p.getLevel()) {
				maxLevel = p.getLevel();
			}
		}

		return maxLevel;
	}

	// NEEDS TO BE MOVED
	public static int getBaseMaxLevel() {
		int tempBase = (int) Math.round(averagePlayerLevel() * 1.2);

		return tempBase < getMinMaxLevel() ? getMinMaxLevel() : tempBase;
	}

	public static int getMinMaxLevel() {
		return 60;
	}

	// NEEDS TO BE MOVED
	public static double ScaleDamage(int Level, double damageDone, int levelDivider) {
		return damageDone + Level / levelDivider;
	}

	// NEEDS TO BE MOVED
	public static double damageToDo(double damageDone, double currentHealth, double maxHealth) {
		return damageDone + damageDone * (1 - EntityFunctions.entityCurrentHealthPercent(currentHealth, maxHealth));
	}

	public static void AddToInventory(Player p, ItemStack is, int max) {
		int maxAmount = max - is.getAmount() + 1;

		if (is.getMaxStackSize() == 1 && !p.getInventory().contains(is.getType(), 1)) {
			p.getInventory().addItem(is);
		} else if (!p.getInventory().contains(is.getType(), maxAmount)) {
			p.getInventory().addItem(is);
		}

		p.updateInventory();
	}

	public static void AddToInventory(Player p, ItemStack is) {
		AddToInventory(p, is, is.getMaxStackSize());
	}

	public static Location offsetLocation(Location l, double x, double y, double z) {
		return new Location(l.getWorld(), l.getX() + x, l.getY() + y, l.getZ() + z);
	}

	public static boolean deosPlayersInventoryContainAtLeast(Player p, Material m, int amountInI) {
		return p.getInventory().containsAtLeast(new ItemStack(m), amountInI);
	}

	public static boolean deosPlayersInventoryContainAtLeast(Player p, PotionType pt, int amountInI, int level) {
		return p.getInventory().containsAtLeast(new ItemStack(new Potion(pt, level).toItemStack(1)), amountInI);
	}

	public static boolean numberBetween(double number, double start, double end) {
		return number >= start && number < end;
	}

	public static List<Block> getBlocksBetweenPoints(Location min, Location max) {

		World w = min.getWorld();

		final int minX = min.getBlockX();
		final int maxX = max.getBlockX() + 1;

		final int minY = min.getBlockY();
		final int maxY = max.getBlockY() + 1;

		final int minZ = min.getBlockZ();
		final int maxZ = max.getBlockZ() + 1;

		final int size = (max.getBlockX() - min.getBlockX()) * (max.getBlockY() - min.getBlockY())
				* (max.getBlockZ() - max.getBlockZ());

		List<Block> lst = new ArrayList<Block>(size);

		for (int x = minX; x < maxX; ++x) {
			for (int y = minY; y < maxY; ++y) {
				for (int z = minZ; z < maxZ; ++z) {
					lst.add(w.getBlockAt(x, y, z));
				}
			}
		}

		return lst;

		/* World w = min.getWorld(); List<Block> tempList = new ArrayList<Block>();
		 * 
		 * for (int x = min.getBlockX(); x <= max.getBlockX(); x = x + 1) { for (int y = min.getBlockY(); y <= max.getBlockY(); y = y + 1)
		 * { for (int z = min.getBlockZ(); z <= max.getBlockZ(); z = z + 1) { tempList.add(w.getBlockAt(x, y, z)); } } } return tempList; */
	}

	public static int SecondsToTicks(int seconds) {
		return seconds * 20;
	}

}
