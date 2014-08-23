package resources;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialFunctions {

	private static final List<String> armor = Collections
			.unmodifiableList(Arrays.asList("IRON_CHESTPLATE", "IRON_LEGGINGS",
					"IRON_HELMET", "IRON_BOOTS", "LEATHER_CHESTPLATE",
					"LEATHER_LEGGINGS", "LEATHER_HELMET", "LEATHER_BOOTS",
					"GOLD_CHESTPLATE", "GOLD_LEGGINGS", "GOLD_HELMET",
					"GOLD_BOOTS", "DIAMOND_CHESTPLATE", "DIAMOND_LEGGINGS",
					"DIAMOND_HELMET", "DIAMOND_BOOTS"));

	private static final List<String> food = Collections
			.unmodifiableList(Arrays.asList("MUSHROOM_SOUP", "GOLDEN_APPLE",
					"COOKED_CHICKEN", "ROTTEN_FLESH", "RAW_CHICKEN", "PORK",
					"GRILLED_PORK", "BREAD", "MELON", "COOKED_BEEF",
					"RAW_BEEF", "GOLDEN_CARROT", "CARROT", "COOKIE"));
	private static final List<String> interactiveBlock = Collections
			.unmodifiableList(Arrays.asList("CHEST", "WORKBENCH", "ANVIL",
					"FURNACE", "ENCHANTMENT_TABLE", "ENDER_CHEST", "BED",
					"MINECART", "SIGN", "BUTTON", "LEVER"));

	public static boolean isArmor(Material material) {
		return armor.indexOf(material.toString()) != -1;
	}

	public static boolean isFood(Material material) {
		return food.indexOf(material.toString()) != -1;
	}

	public static boolean isInteractiveBlock(Material material) {
		return interactiveBlock.indexOf(material.toString()) != -1;
	}

	public static ArrayList<ItemStack> getAllMeeleWeapons() {
		ArrayList<ItemStack> tempWeapons = new ArrayList<ItemStack>();
		tempWeapons.add(new ItemStack(Material.WOOD_SWORD));
		tempWeapons.add(new ItemStack(Material.STONE_SWORD));
		tempWeapons.add(new ItemStack(Material.IRON_SWORD));
		tempWeapons.add(new ItemStack(Material.GOLD_SWORD));
		tempWeapons.add(new ItemStack(Material.DIAMOND_SWORD));
		return tempWeapons;
	}

	public static boolean isWeapon(Material material) {
		return isAMeeleWeapon(material) || isARangedWeapon(material);
	}

	public static boolean isAMeeleWeapon(Material material) {
		switch (material.toString()) {
		case "WOOD_SWORD":
		case "STONE_SWORD":
		case "IRON_SWORD":
		case "GOLD_SWORD":
		case "DIAMOND_SWORD":
			return true;
		default:
			return false;
		}
	}

	public static boolean isARangedWeapon(Material material) {
		return material.toString().contains("BOW");
	}

}
