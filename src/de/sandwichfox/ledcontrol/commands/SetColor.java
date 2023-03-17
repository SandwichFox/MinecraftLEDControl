package de.sandwichfox.ledcontrol.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.sandwichfox.ledcontrol.main.Main;

import java.util.ArrayList;
import java.util.List;

public class SetColor implements CommandExecutor {

    private final Main plugin;
    private final List colorlist = new ArrayList();

    public SetColor(Main plugin) {
        colorlist.add("red");
        colorlist.add("green");
        colorlist.add("blue");
        colorlist.add("rainbow");
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                String color = args[0];
                if (color.startsWith("#")) {
                    if (color.length() == 7) {
                        plugin.sendCommandToPythonScript("color");
                        player.sendMessage("§bFarbe wurde zu " + color + " geändert!");
                    } else
                        player.sendMessage("§cDies ist kein Hexcode!");
                } else {
                    color = color.toLowerCase();
                    if (colorlist.contains(color)) {
                        plugin.sendCommandToPythonScript("color");
                        player.sendMessage("§bFarbe wurde zu " + color + " geändert!");
                    } else
                        player.sendMessage("§cDies ist keine verfügbare Farbe!");
                }
                return true;

            }
        }
        return false;

    }
}


