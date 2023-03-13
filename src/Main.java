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
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.STONE_BUTTON) {
            executePythonScript("/home/sabelpi/minecraft_server/change_led.py");
        }
    }

    public void executePythonScript(String scriptPath) {
        try {
            Runtime.getRuntime().exec("python " + scriptPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

