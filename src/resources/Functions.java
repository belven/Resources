package resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

public class Functions
{
    public static Location move(Location loc, Vector offset)
    {
        return move(loc, offset.getX(), offset.getY(), offset.getZ());
    }

    public static Location move(Location loc, double dx, double dy, double dz)
    {
        Vector off = rotate(loc.getYaw(), loc.getPitch(), dx, dy, dz);
        double x = loc.getX() + off.getX();
        double y = loc.getY() + off.getY();
        double z = loc.getZ() + off.getZ();
        return new Location(loc.getWorld(), x, y, z, loc.getYaw(),
                loc.getPitch());
    }

    public static Vector rotate(float yaw, float pitch, double x, double y,
            double z)
    {
        float angle = yaw * 0.01745329F;
        double sinyaw = Math.sin(angle);
        double cosyaw = Math.cos(angle);

        angle = pitch * 0.01745329F;
        double sinpitch = Math.sin(angle);
        double cospitch = Math.cos(angle);

        double newx = 0.0D;
        double newy = 0.0D;
        double newz = 0.0D;
        newz -= x * cosyaw;
        newz -= y * sinyaw * sinpitch;
        newz -= z * sinyaw * cospitch;
        newx += x * sinyaw;
        newx -= y * cosyaw * sinpitch;
        newx -= z * cosyaw * cospitch;
        newy += y * cospitch;
        newy -= z * sinpitch;

        return new Vector(newx, newy, newz);
    }

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

    public static int abilityCap(double maxAmount, double currentLevel)
    {
        int tempCap = (int) (maxAmount * (currentLevel / getBaseMaxLevel()));

        if (tempCap > maxAmount)
        {
            tempCap = (int) maxAmount;
        }

        return tempCap;
    }

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

    public static int getBaseMaxLevel()
    {
        int tempBase = (int) Math.round(averagePlayerLevel() * 1.2);

        return tempBase < getMinMaxLevel() ? getMinMaxLevel() : tempBase;
    }

    public static int getMinMaxLevel()
    {
        return 60;
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

    public static Player[] getNearbyPlayers(Location l, int radius)
    {
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();

        for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++)
        {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++)
            {
                int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();

                for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z
                        + (chZ * 16)).getChunk().getEntities())
                {
                    if (e instanceof Player
                            && e.getLocation().distance(l) <= radius)
                    {
                        radiusEntities.add((Player) e);
                    }
                }
            }
        }

        return radiusEntities.toArray(new Player[radiusEntities.size()]);
    }

    public static LivingEntity GetDamager(EntityDamageByEntityEvent event)
    {
        Entity damagerEntity = event.getDamager();

        if (damagerEntity.getType() == EntityType.PLAYER)
        {
            return (LivingEntity) damagerEntity;
        }
        else if (damagerEntity.getType() == EntityType.ARROW)
        {
            // Arrow currentArrow = (Arrow) damagerEntity;
            // return currentArrow.getShooter();
            return null;
        }
        else if (damagerEntity.getType() == EntityType.FIREBALL)
        {
            return null;
        }
        else
        {
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public static Player[] getNearbyPlayersNew(Location l, int radius)
    {
        HashSet<Entity> radiusEntities = new HashSet<Entity>();

        for (Player p : Bukkit.getServer().getOnlinePlayers())
        {
            if (p.getLocation().getWorld() == l.getWorld()
                    && p.getLocation().distance(l) <= radius)
            {
                radiusEntities.add(p);
            }
        }

        return radiusEntities.toArray(new Player[radiusEntities.size()]);
    }

    public static Location offsetLocation(Location l, double x, double y,
            double z)
    {
        return new Location(l.getWorld(), l.getX() + x, l.getY() + y, l.getZ()
                + z);
    }

    public static double ScaleDamage(int Level, double damageDone,
            int levelDivider)
    {
        return damageDone + Level / levelDivider;
    }

    public static double damageToDo(double damageDone, double currentHealth,
            double maxHealth)
    {
        return damageDone
                + (damageDone * (1 - EntityFunctions
                        .entityCurrentHealthPercent(currentHealth, maxHealth)));
    }

    public static boolean isNumberBetween(int numberToCheck, int low, int high)
    {
        return (numberToCheck >= low && numberToCheck < high);
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

    public static boolean numberBetween(double number, double start, double end)
    {
        return (number >= start) && (number < end);
    }

    public static Location lookAt(Location loc, Location lookat)
    {
        loc = loc.clone();

        double dx = lookat.getX() - loc.getX();
        double dy = lookat.getY() - loc.getY();
        double dz = lookat.getZ() - loc.getZ();

        if (dx != 0)
        {
            if (dx < 0)
            {
                loc.setYaw((float) (1.5 * Math.PI));
            }
            else
            {
                loc.setYaw((float) (0.5 * Math.PI));
            }
            loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
        }
        else if (dz < 0)
        {
            loc.setYaw((float) Math.PI);
        }

        double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

        loc.setPitch((float) -Math.atan(dy / dxz));
        loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
        loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);

        return loc;
    }

    public static List<Block> getBlocksInRadius(Location l, int radius)
    {
    	// Test
        World w = l.getWorld();
        int xCoord = (int) l.getX();
        int zCoord = (int) l.getZ();
        int YCoord = (int) l.getY();

        List<Block> tempList = new ArrayList<Block>();

        for (int x = 0; x <= 2 * radius; x++)
        {
            for (int z = 0; z <= 2 * radius; z++)
            {
                for (int y = 0; y <= 2 * radius; y++)
                {
                    tempList.add(w.getBlockAt(xCoord + x, YCoord + y, zCoord
                            + z));
                }
            }
        }
        return tempList;
    }

    public static void Heal(LivingEntity entityToHeal, int amountToHeal)
    {
        Damageable dEntityToHeal = (Damageable) entityToHeal;
        double max = dEntityToHeal.getMaxHealth();
        double current = dEntityToHeal.getHealth();

        if (entityToHeal != null)
        {
            for (int i = amountToHeal; i != 0; i--)
            {
                if ((current + i) < max)
                {
                    entityToHeal.setHealth(current + i);
                }
            }
        }
    }

    public static void RestoreHunger(Player entityToRestore, int amountToRestore)
    {
        if (entityToRestore != null)
        {
            for (int i = amountToRestore; i != 0; i--)
            {
                if ((entityToRestore.getFoodLevel() + i) < 10)
                {
                    entityToRestore.setFoodLevel(entityToRestore.getFoodLevel()
                            + i);
                }
            }
        }
    }

    public static void RestoreSaturation(Player entityToRestore,
            int amountToRestore)
    {
        if (entityToRestore != null)
        {
            for (int i = amountToRestore; i != 0; i--)
            {
                if ((entityToRestore.getSaturation() + i) < 10)
                {
                    entityToRestore.setSaturation(entityToRestore
                            .getSaturation() + i);
                }
            }
        }
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

    private final static List<EntityType> mobs = Arrays.asList(
            EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CREEPER,
            EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.GHAST,
            EntityType.MAGMA_CUBE, EntityType.PIG_ZOMBIE, EntityType.SKELETON,
            EntityType.SPIDER, EntityType.SLIME, EntityType.WITCH,
            EntityType.WITHER, EntityType.ZOMBIE);

    public static boolean IsAMob(EntityType currentEntityType)
    {
        return mobs.contains(currentEntityType);
    }

    public static double MobMaxHealth(LivingEntity entity)
    {
        // -1 is for slime's

        final double[] maxHP =
        { 20.0, 12.0, 20.0, 200.0, 40.0, 10.0, -1, 20.0, 20.0, 16.0, -1, 26.0,
                300.0, 20.0 };

        final double[] slimeSize =
        { 1.0, 4.0, 16.0 };

        final double mhp = maxHP[mobs.indexOf(entity)];

        return (mhp != -1) ? mhp : slimeSize[(int) (Math.log(((Slime) entity)
                .getSize()) / Math.log(2))];
    }
}
