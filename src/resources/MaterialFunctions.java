package resources;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialFunctions
{
    public static boolean isArmor(Material material)
    {
        if (material.name().contains("CHESTPLATE"))
        {
            return true;
        }
        if (material.name().contains("LEGGINGS"))
        {
            return true;
        }
        if (material.name().contains("HELMET"))
        {
            return true;
        }
        if (material.name().contains("BOOTS"))
        {
            return true;
        }
        return false;
    }

    public static boolean isWeapon(Material material)
    {
        if (isAMeeleWeapon(material))
        {
            return true;
        }
        if (material.toString().contains("BOW"))
        {
            return true;
        }
        return false;
    }

    public static boolean isFood(Material material)
    {
        switch (material.toString())
        {
        case "MUSHROOM_SOUP":
        case "GOLDEN_APPLE":
        case "COOKED_CHICKEN":
        case "ROTTEN_FLESH":
        case "RAW_CHICKEN":
        case "PORK":
        case "GRILLED_PORK":
        case "BREAD":
        case "MELON":
        case "COOKED_BEEF":
        case "RAW_BEEF":
        case "GOLDEN_CARROT":
        case "CARROT":
        case "COOKIE":
            return true;
        default:
            return false;
        }
    }

    public static boolean isNotInteractiveBlock(Material material)
    {
        switch (material.toString())
        {
        case "CHEST":
        case "WORKBENCH":
        case "ANVIL":
        case "FURNACE":
        case "ENCHANTMENT_TABLE":
        case "ENDER_CHEST":
        case "BED":
        case "MINECART":
        case "SIGN":
        case "BUTTON":
        case "LEVER":
            return false;
        default:
            return true;
        }
    }

    public static ArrayList<ItemStack> getAllMeeleWeapons()
    {
        ArrayList<ItemStack> tempWeapons = new ArrayList<ItemStack>();
        tempWeapons.add(new ItemStack(Material.WOOD_SWORD));
        tempWeapons.add(new ItemStack(Material.STONE_SWORD));
        tempWeapons.add(new ItemStack(Material.IRON_SWORD));
        tempWeapons.add(new ItemStack(Material.GOLD_SWORD));
        tempWeapons.add(new ItemStack(Material.DIAMOND_SWORD));
        return tempWeapons;
    }

    public static boolean isAMeeleWeapon(Material material)
    {
        switch (material.toString())
        {
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

}
