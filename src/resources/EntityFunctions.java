package resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class EntityFunctions
{

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

    @SuppressWarnings("deprecation")
    public static LivingEntity GetDamager(EntityDamageByEntityEvent event)
    {
        Entity damagerEntity = event.getDamager();

        if (damagerEntity instanceof LivingEntity)
        {
            return (LivingEntity) damagerEntity;
        }
        else if (damagerEntity.getType() == EntityType.ARROW)
        {
            Arrow currentArrow = (Arrow) damagerEntity;
            return currentArrow.getShooter();
        }
        else if (damagerEntity.getType() == EntityType.FIREBALL)
        {
            // Fireball currentFireball = (Fireball) damagerEntity;
            // return currentFireball.getShooter();
            return null;
        }
        else
        {
            return null;
        }
    }

    public static List<LivingEntity> findTargetEntityByType(Player origin,
            double radius, List<EntityType> types, int maxTargets)
    {
        int count = 0;
        Location originLocation = origin.getEyeLocation();
        Vector originDirection = originLocation.getDirection();
        Vector originVector = originLocation.toVector();

        List<LivingEntity> targets = new ArrayList<LivingEntity>();
        double minDotProduct = 4.9E-324D;
        for (Entity entity : origin.getNearbyEntities(radius, radius, radius))
        {
            if (count >= maxTargets)
            {
                break;
            }
            if ((!entity.equals(origin)) && ((entity instanceof LivingEntity)))
            {
                LivingEntity living = (LivingEntity) entity;

                Vector toTarget = living.getEyeLocation().toVector()
                        .subtract(originVector).normalize();

                double dotProduct = toTarget.dot(originDirection);
                if ((types.contains(living.getType())) && (dotProduct > 0.3D)
                        && (origin.hasLineOfSight(living))
                        && ((targets == null) || (dotProduct > minDotProduct)))
                {
                    count++;
                    targets.add(living);
                    minDotProduct = dotProduct;
                }
            }
        }
        return targets;
    }

    public static LivingEntity findTargetEntityByType(Player origin,
            double radius, List<EntityType> types)
    {
        Location originLocation = origin.getEyeLocation();
        Vector originDirection = originLocation.getDirection();
        Vector originVector = originLocation.toVector();

        LivingEntity target = null;
        double minDotProduct = 4.9E-324D;
        for (Entity entity : origin.getNearbyEntities(radius, radius, radius))
        {
            if ((!entity.equals(origin)) && ((entity instanceof LivingEntity)))
            {
                LivingEntity living = (LivingEntity) entity;
                Location newTargetLocation = living.getEyeLocation();

                Vector toTarget = newTargetLocation.toVector()
                        .subtract(originVector).normalize();
                double dotProduct = toTarget.dot(originDirection);
                if ((types.contains(living.getType())) && (dotProduct > 0.3D)
                        && (origin.hasLineOfSight(living))
                        && ((target == null) || (dotProduct > minDotProduct)))
                {
                    target = living;
                    minDotProduct = dotProduct;
                    break;
                }
            }
        }
        return target;
    }

    public static LivingEntity findTargetEntityByType(Player origin,
            double radius, EntityType type)
    {
        Location originLocation = origin.getEyeLocation();
        Vector originDirection = originLocation.getDirection();
        Vector originVector = originLocation.toVector();

        LivingEntity target = null;
        double minDotProduct = 4.9E-324D;
        for (Entity entity : origin.getNearbyEntities(radius, radius, radius))
        {
            if ((!entity.equals(origin)) && ((entity instanceof LivingEntity)))
            {
                LivingEntity living = (LivingEntity) entity;
                Location newTargetLocation = living.getEyeLocation();

                Vector toTarget = newTargetLocation.toVector()
                        .subtract(originVector).normalize();
                double dotProduct = toTarget.dot(originDirection);
                if ((living.getType() == type) && (dotProduct > 0.3D)
                        && (origin.hasLineOfSight(living))
                        && ((target == null) || (dotProduct > minDotProduct)))
                {
                    target = living;
                    minDotProduct = dotProduct;
                    break;
                }
            }
        }
        return target;
    }

    public static LivingEntity findTargetPlayer(Player origin, double radius)
    {

        Location originLocation = origin.getEyeLocation();
        Vector originDirection = originLocation.getDirection();
        Vector originVector = originLocation.toVector();

        LivingEntity target = null;
        double minDotProduct = 4.9E-324D;
        for (Entity entity : origin.getNearbyEntities(radius, radius, radius))
        {
            if (((entity instanceof Player)) && (!entity.equals(origin)))
            {
                LivingEntity living = (LivingEntity) entity;
                Location newTargetLocation = living.getEyeLocation();

                Vector toTarget = newTargetLocation.toVector()
                        .subtract(originVector).normalize();
                double dotProduct = toTarget.dot(originDirection);
                if ((dotProduct > 0.3D) && (origin.hasLineOfSight(living))
                        && ((target == null) || (dotProduct > minDotProduct)))
                {
                    target = living;
                    minDotProduct = dotProduct;
                }
            }
        }
        return target;
    }

    public static List<LivingEntity> getNearbyEntities(Location l, int radius)
    {

        int chunkRadius = radius < 16 ? 1 : (radius - radius % 16) / 16;
        List<LivingEntity> arrayOfLivingEntity = new ArrayList<LivingEntity>();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++)
        {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++)
            {
                int x = (int) l.getX();
                int y = (int) l.getY();
                int z = (int) l.getZ();

                Entity[] arrayOfEntity;

                int j = (arrayOfEntity = new Location(l.getWorld(), x + chX
                        * 16, y, z + chZ * 16).getChunk().getEntities()).length;

                for (int i = 0; i < j; i++)
                {
                    Entity e = arrayOfEntity[i];
                    if ((e.getLocation().distance(l) <= radius)
                            && e instanceof LivingEntity)
                    {
                        arrayOfLivingEntity.add((LivingEntity) e);
                    }
                }
            }
        }
        return arrayOfLivingEntity;
    }

    public static LivingEntity findTargetEntity(Player origin, double radius)
    {

        Location originLocation = origin.getEyeLocation();
        Vector originDirection = originLocation.getDirection();
        Vector originVector = originLocation.toVector();

        LivingEntity target = null;
        double minDotProduct = 4.9E-324D;
        for (Entity entity : origin.getNearbyEntities(radius, radius, radius))
        {
            if ((!entity.equals(origin)) && ((entity instanceof LivingEntity)))
            {
                LivingEntity living = (LivingEntity) entity;
                Location newTargetLocation = living.getEyeLocation();

                Vector toTarget = newTargetLocation.toVector()
                        .subtract(originVector).normalize();
                double dotProduct = toTarget.dot(originDirection);
                if ((dotProduct > 0.3D) && (origin.hasLineOfSight(living))
                        && ((target == null) || (dotProduct > minDotProduct)))
                {
                    target = living;
                    minDotProduct = dotProduct;
                }
            }
        }
        return target;
    }

    public static boolean isHealthLessThanOther(LivingEntity le1,
            LivingEntity le2)
    {

        Damageable dPlayer = le1;
        Damageable dOwner = le2;

        double otherHealth = entityCurrentHealthPercent(dPlayer.getHealth(),
                dPlayer.getMaxHealth());

        double selfHealth = entityCurrentHealthPercent(dOwner.getHealth(),
                dOwner.getMaxHealth());

        return otherHealth < selfHealth;
    }

    public static double entityCurrentHealthPercent(double currentHealth,
            double maxHealth)
    {
        return currentHealth / maxHealth;
    }

    // Mobs
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

    public static boolean IsUndeadMob(EntityType currentEntityType)
    {
        if (currentEntityType == EntityType.PIG_ZOMBIE
                || currentEntityType == EntityType.SKELETON
                || currentEntityType == EntityType.WITHER
                || currentEntityType == EntityType.ZOMBIE)
        {
            return true;
        }
        else
        {
            return false;
        }
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
}
