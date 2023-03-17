package de.sandwichfox.ledcontrol.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.sandwichfox.ledcontrol.main.Main;

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
                //Main.sendCommandToPythonScript(color);
                player.sendMessage("Farbe wurde zu " + color + " geändert!");
            } else {
                player.sendMessage("/SetColor <color>");
            }
        }
        return true;
    }
}
