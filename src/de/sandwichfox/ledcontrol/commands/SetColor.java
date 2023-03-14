package de.sandwichfox.ledcontrol.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import

public class SetColor implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                String color = args[0];
                executePythonScript("change_led.py", color);
                player.sendMessage("Farbe wurde zu " + color + " geändert!");
            }
            else {
                player.sendMessage("/SetColor <color>");
            }
        }
        return true;
    }

    public void executePythonScript(String host, int port, String color) {
        try {
            Socket socket = new Socket(host, port);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(color.getBytes());
            outputStream.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}