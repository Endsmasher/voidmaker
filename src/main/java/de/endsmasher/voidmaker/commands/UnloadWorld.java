package de.endsmasher.voidmaker.commands;

import de.endsmasher.voidmaker.VoidMaker;
import de.endsmasher.voidmaker.utils.BasicCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnloadWorld extends BasicCommand {

    public UnloadWorld(VoidMaker main) {
        super(main, "unload");
    }

    /**
     * unload a world
     */

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (!sender.hasPermission("voidmaker.unload")) return false;
        if (args.length != 1) {
            sender.sendMessage(VoidMaker.PREFIX + "Please do /unload <name>");
            return false;
        }
        var player = (Player) sender;
        var name = args[0];
        var world = Bukkit.getWorld(name);

        if (world == null) {
            player.sendMessage(VoidMaker.PREFIX + "World is already unloaded or does not exist!");
            return false;
        }
        if (!world.getPlayers().isEmpty()) {
            player.sendMessage(VoidMaker.PREFIX + "You can't unload a world which contains players");
            return false;
        }

        player.sendMessage(VoidMaker.PREFIX + "World is unloading this might take a while " + name);
        Bukkit.unloadWorld(world, true);
        player.sendMessage(VoidMaker.PREFIX + "World has been saved and unloaded " + name);
        return false;
    }
}
