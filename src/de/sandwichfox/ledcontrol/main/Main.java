package de.sandwichfox.ledcontrol.main;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener {

    private static final String HOST = "localhost";
    private static final int PORT = 8000;
    private Logger logger = getLogger();


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
                sendCommandToPythonScript("red");
            }
        if (event.getClickedBlock().getType().equals(Material.POLISHED_BLACKSTONE_BUTTON))
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                getServer().getLogger().info("Blackstone button was pressed!");
                sendCommandToPythonScript("green");
            }
        if (event.getClickedBlock().getType().equals(Material.OAK_BUTTON))
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                getServer().getLogger().info("Oak button was pressed!");
                sendCommandToPythonScript("blue");
            }
        if (event.getClickedBlock().getType().equals(Material.ACACIA_BUTTON))
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                getServer().getLogger().info("Acacia button was pressed!");
                sendCommandToPythonScript("rainbow");
            }
        try {
        } catch (NullPointerException e) {
            // do nothing
        }
    }

    public void sendCommandToPythonScript(String command) {
        try (Socket socket = new Socket(HOST, PORT)) {
            socket.getOutputStream().write(command.getBytes());
            socket.getOutputStream().flush();
            byte[] response = new byte[2];
            socket.getInputStream().read(response);
            String responseString = new String(response);
            if (!responseString.equals("OK")) {
                logger.warning("Received invalid response from LEDControl server: " + responseString);
            }
        } catch (IOException e) {
            logger.warning("Error communicating with LEDControl server: " + e.getMessage());
        }
    }

}