package de.sandwichfox.ledcontrol.main;

import de.sandwichfox.ledcontrol.commands.SetColor;
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
       logger.info("Enabled!");
        this.getCommand("SetColor").setExecutor(new SetColor(this));
    }

    @Override
    public void onDisable() {
        logger.info("Disabled!");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            switch (block.getType().toString()) {
                case "OAK_BUTTON" -> {
                    sendCommandToPythonScript("blue");
                   logger.info("Oak button was pressed!");
                }
                case "STONE_BUTTON" -> {
                    sendCommandToPythonScript("red");
                   logger.info("Stone button was pressed!");
                }
                case "ACACIA_BUTTON" -> {
                    sendCommandToPythonScript("rainbow");
                   logger.info("Acacia button was pressed!");
                }
                case "POLISHED_BLACKSTONE_BUTTON" -> {
                    sendCommandToPythonScript("green");
                   logger.info("Polished Blackstone button was pressed!");
                }
                default -> {
                    return;
                }
            }
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