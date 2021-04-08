package de.endsmasher.voidmaker.commands;

import de.endsmasher.voidmaker.VoidMaker;
import de.endsmasher.voidmaker.backend.VoidGenerator;
import de.endsmasher.voidmaker.utils.BasicCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;


public class GenerateWorld extends BasicCommand {

    public GenerateWorld(VoidMaker main) {
        super(main, "generate");
    }

    /**
     * small list out of given biomes
     */

    private static final String[] biomes = {"THE_END", "THE_VOID"};

    private DeleteWorld deleteWorld;

    /**
     * generate a world with one of the given biomes
     */

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (!sender.hasPermission("voidmaker.generate"))
        if (!(sender instanceof Player)) {
            sender.sendMessage(VoidMaker.PREFIX + "You are not a player");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(VoidMaker.PREFIX + "Available biomes: §7" + Arrays.toString(biomes));
            return false;
        }
        if (args.length != 2) {
            sender.sendMessage(VoidMaker.PREFIX + "Please do /generate <type> <name>");
            return false;
        }
        var player = (Player) sender;
        var type = args[0].toUpperCase();
        var name = args[1];
        var worldCreator = new WorldCreator(name);
        var world = Bukkit.getWorld(name);
        VoidGenerator voidGenerator;

        if (world != null || exists(name)) {
            player.sendMessage(VoidMaker.PREFIX + "World does already exist");
            return false;
        }

        try {
            voidGenerator = new VoidGenerator(Biome.valueOf(type));
        } catch (Exception e) {
            player.sendMessage(VoidMaker.PREFIX + "Invalid biome " + type);
            return false;
        }
        var generator = worldCreator.generator(voidGenerator);

        player.sendMessage(VoidMaker.PREFIX + "World is generating this might take a while");
        generator.createWorld();
        world = Bukkit.getWorld(name);
        if (world == null) return false;

        var spawnLocation = world.getSpawnLocation();
        var x = spawnLocation.getBlockX();
        var y = spawnLocation.getBlockY();
        var z = spawnLocation.getBlockZ();
        var spawnBlock = world.getBlockAt(x, y-1, z);

        if (spawnBlock.isEmpty()) spawnBlock.setType(Material.STONE);
        player.teleport(world.getSpawnLocation());
        player.sendMessage(VoidMaker.PREFIX + "You have been teleported to " + name);
        return false;
    }

    private boolean exists(String name) {
        File file = new File(name);
        return file.exists();
    }
}
