package resources;

import org.bukkit.plugin.java.JavaPlugin;


public class ResourcesManager extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        
    }

    @Override
    public void onDisable()
    {
        getLogger().info("Goodbye world!");
        this.saveConfig();
    }

}
