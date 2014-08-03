package resources;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Gear
{
    public ItemStack h;
    public ItemStack c;
    public ItemStack l;
    public ItemStack b;
    public ItemStack w;

    public Gear()
    {

    }

    public Gear(Player p)
    {
        PlayerInventory inv = p.getInventory();
        h = inv.getHelmet();
        c = inv.getChestplate();
        l = inv.getLeggings();
        b = inv.getBoots();
        w = inv.getItemInHand();
    }

    public Gear(ItemStack helmet, ItemStack chest, ItemStack legs,
            ItemStack boots, ItemStack weapon)
    {
        h = helmet;
        c = chest;
        l = legs;
        b = boots;
        w = weapon;
    }

    public void Clear()
    {
        h = null;
        c = null;
        l = null;
        b = null;
        w = null;
    }

}
