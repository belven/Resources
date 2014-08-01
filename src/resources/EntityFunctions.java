package resources;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EntityFunctions
{

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

}
