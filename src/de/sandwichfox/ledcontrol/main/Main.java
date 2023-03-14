package de.sandwichfox.ledcontrol.main;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getLogger().info("LEDController has been enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getLogger().info("LEDController has been disabled!");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock().getType().equals(Material.STONE_BUTTON))
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                getServer().getLogger().info("Stone button was pressed!");
                executePythonScript("/home/sabelpi/minecraft_server/change_led.py");
            }
        if (event.getClickedBlock().getType().equals(Material.POLISHED_BLACKSTONE_BUTTON))
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                getServer().getLogger().info("Blackstone button was pressed!");
                executePythonScript("/home/sabelpi/minecraft_server/change_led.py");
            }
        if (event.getClickedBlock().getType().equals(Material.OAK_BUTTON))
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                 getServer().getLogger().info("Oak button was pressed!");
                 ;
            }
    }

    private void executePythonScript(String scriptPath) {
        try {
            Runtime.getRuntime().exec("python3 " + scriptPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


