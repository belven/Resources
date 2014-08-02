package resources;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class Functions{

    public static boolean debuffs(PotionEffectType pet)
    {
        switch (pet.toString())
        {
        case "HUNGER":
        case "BLINDNESS":
        case "CONFUSION":
        case "POISON":
        case "SLOW":
        case "SLOW_DIGGING":
        case "WEAKNESS":
        case "WITHER":
            return true;

        default:
            return false;
        }
    }
    
    // NEEDS TO BE MOVED
    public static int abilityCap(double maxAmount, double currentLevel)
    {
        int tempCap = (int) (maxAmount * (currentLevel / getBaseMaxLevel()));

        if (tempCap > maxAmount)
        {
            tempCap = (int) maxAmount;
        }

        return tempCap;
    }

    // NEEDS TO BE MOVED
    public static int averagePlayerLevel()
    {
        @SuppressWarnings("deprecation")
        Player[] tempPlayers = Bukkit.getServer().getOnlinePlayers();

        int AverageLevel = 0;
        int totalLevel = 0;

        for (Player p : tempPlayers)
        {
            totalLevel += p.getLevel();
        }

        AverageLevel = (totalLevel / tempPlayers.length);

        return AverageLevel;
    }

    // NEEDS TO BE MOVED
    public static int maxPlayerLevel()
    {
        @SuppressWarnings("deprecation")
        Player[] tempPlayers = Bukkit.getServer().getOnlinePlayers();

        int maxLevel = 0;

        for (Player p : tempPlayers)
        {
            if (maxLevel < p.getLevel())
            {
                maxLevel = p.getLevel();
            }
        }

        return maxLevel;
    }

    // NEEDS TO BE MOVED
    public static int getBaseMaxLevel()
    {
        int tempBase = (int) Math.round(averagePlayerLevel() * 1.2);

        return tempBase < getMinMaxLevel() ? getMinMaxLevel() : tempBase;
    }

    public static int getMinMaxLevel()
    {
        return 60;
    }
    
    // NEEDS TO BE MOVED
    public static double ScaleDamage(int Level, double damageDone,
            int levelDivider)
    {
        return damageDone + Level / levelDivider;
    }

    // NEEDS TO BE MOVED
    public static double damageToDo(double damageDone, double currentHealth, double maxHealth){
        return damageDone
                + (damageDone * (1 - EntityFunctions
                        .entityCurrentHealthPercent(currentHealth, maxHealth)));
    }

    @SuppressWarnings("deprecation")
    public static void AddToInventory(Player p, ItemStack is)
    {
        int maxAmount = is.getMaxStackSize() - is.getAmount();

        if (is.getMaxStackSize() == 1
                && !p.getInventory().contains(is.getType(), 1))
        {
            p.getInventory().addItem(is);
        }
        else if (!p.getInventory().contains(is.getType(), maxAmount))
        {
            p.getInventory().addItem(is);
        }

        p.updateInventory();
    }

    public static Location offsetLocation(Location l, double x, double y,
            double z)
    {
        return new Location(l.getWorld(), l.getX() + x, l.getY() + y, l.getZ()
                + z);
    }

    public static boolean deosPlayersInventoryContainAtLeast(Player p,
            Material m, int amountInI)
    {
        return p.getInventory().containsAtLeast(new ItemStack(m), amountInI);
    }

    public static boolean deosPlayersInventoryContainAtLeast(Player p,
            PotionType pt, int amountInI, int level)
    {
        return p.getInventory().containsAtLeast(
                new ItemStack(new Potion(pt, level).toItemStack(1)), amountInI);
    }

    public static boolean numberBetween(double number, double start, double end){
        return (number >= start) && (number < end);
    }

    public static List<Block> getBlocksBetweenPoints(Location min, Location max)
    {
        World w = min.getWorld();
        List<Block> tempList = new ArrayList<Block>();

        for (int x = min.getBlockX(); x <= max.getBlockX(); x = x + 1)
        {
            for (int y = min.getBlockY(); y <= max.getBlockY(); y = y + 1)
            {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z = z + 1)
                {
                    tempList.add(w.getBlockAt(x, y, z));
                }
            }
        }
        return tempList;
    }

    public static int SecondsToTicks(int seconds)
    {
        return (seconds * 20);
    }

}
