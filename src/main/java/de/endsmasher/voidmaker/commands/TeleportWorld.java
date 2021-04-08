package de.endsmasher.voidmaker.commands;

import de.endsmasher.voidmaker.VoidMaker;
import de.endsmasher.voidmaker.utils.BasicCommand;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class TeleportWorld extends BasicCommand {

    public TeleportWorld(VoidMaker main) {
        super(main, "visit");
    }

    /**
     * teleport to specific world
     */

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (!sender.hasPermission("voidmaker.visit")) return false;
        if (!(sender instanceof Player)) {
            sender.sendMessage(VoidMaker.PREFIX + "You are not a player");
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage(VoidMaker.PREFIX + "Please do /visit <name>");
            return false;
        }
        var player = (Player) sender;
        var name = args[0];
        var world = Bukkit.getWorld(name);

        if (world == null) {
             if (!doesExist(sender, name)) {
                 return false;
             }
             sender.sendMessage(VoidMaker.PREFIX + "Recast the command to teleport to the world " + name);
             return true;
        }
        Bukkit.getWorlds().add(world);
        player.teleport(world.getSpawnLocation());
        player.sendMessage(VoidMaker.PREFIX + "You have been summoned to the world " + name);

        return false;
    }

    /**
     * checks if world files do exist
     */

    private boolean doesExist(CommandSender sender, String name) {
        File world = new File(name);

        if(!world.exists()) {
            sender.sendMessage(VoidMaker.PREFIX + "The world " + name + " does not exists");
            return false;
        }
        File uid = new File(name + "/uid.dat");
        if(uid.exists()) {
            uid.delete();
        }
        sender.sendMessage(VoidMaker.PREFIX + "World is loading this might take a while " + name);
        WorldCreator worldCreator = new WorldCreator(name);
        worldCreator.generator();
        sender.sendMessage(VoidMaker.PREFIX + "World loaded");
        return true;
    }
}
