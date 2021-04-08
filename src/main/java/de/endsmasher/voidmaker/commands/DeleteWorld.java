package de.endsmasher.voidmaker.commands;

import de.endsmasher.voidmaker.VoidMaker;
import de.endsmasher.voidmaker.utils.BasicCommand;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.IOException;

public class DeleteWorld extends BasicCommand {

    public DeleteWorld(VoidMaker main) {
        super(main, "delete");
    }

    /**
     * delete a world
     */

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (!sender.hasPermission("voidmaker.delete")) return false;

        if (args.length != 1) {
            sender.sendMessage(VoidMaker.PREFIX + "Please do /delete <name>");
            return false;
        }
        var name = args[0];
        var world = Bukkit.getWorld(name);

        if (world != null) {
            if (!world.getPlayers().isEmpty()) {
                sender.sendMessage(VoidMaker.PREFIX + "You can't delete a world which contains players");
                return false;
            }
        }
        sender.sendMessage(VoidMaker.PREFIX + "World is deleting this might take a while");
        try {
            delete(name, sender);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sender.sendMessage(VoidMaker.PREFIX + "World is now deleted");
        return false;
    }

    private void delete(String name, CommandSender sender) throws IOException {
        File file = new File(name);
        FileUtils.touch(file);

        if (!file.exists()) {
            sender.sendMessage(VoidMaker.PREFIX + "World does not exist");
            return;
        }
        FileUtils.deleteQuietly(file);
    }
}
