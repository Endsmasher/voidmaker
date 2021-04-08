package de.endsmasher.voidmaker;

import de.endsmasher.voidmaker.commands.DeleteWorld;
import de.endsmasher.voidmaker.commands.GenerateWorld;
import de.endsmasher.voidmaker.commands.TeleportWorld;
import de.endsmasher.voidmaker.commands.UnloadWorld;
import org.bukkit.plugin.java.JavaPlugin;

public final class VoidMaker extends JavaPlugin {

    public static final String PREFIX = "§7[§bVoidMaker§7] §r";

    @Override
    public void onEnable() {
        loadCommands();
    }

    private void loadCommands() {
        new GenerateWorld(this);
        new TeleportWorld(this);
        new UnloadWorld(this);
        new DeleteWorld(this);
    }
}
