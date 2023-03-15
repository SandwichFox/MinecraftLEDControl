package de.sandwichfox.ledcontrol.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetColor implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                String color = args[1];
                sendColorToPythonScript("localhost", 12345, color);
                player.sendMessage("Farbe wurde zu " + color + " geändert!");
            }
            else {
                player.sendMessage("/SetColor <color>");
            }
        }
        return true;
    }

    public void sendColorToPythonScript(String host, int port, String color) {
        try (Socket socket = new Socket(host, port)) {
            OutputStream output = socket.getOutputStream();
            output.write(color.getBytes());

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = input.readLine();

            System.out.println("Python script responded with: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}