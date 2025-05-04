package xyz.herberto.eventTeams;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.herberto.eventTeams.commands.TeamAdminCommand;
import xyz.herberto.eventTeams.papi.TeamPlaceholders;

import java.util.Arrays;

public final class EventTeams extends JavaPlugin {

    @Getter
    public static EventTeams instance;


    @Override
    public void onEnable() {

        instance = this;

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.enableUnstableAPI("help");

        Arrays.asList(
                new TeamAdminCommand()
        ).forEach(manager::registerCommand);

        saveDefaultConfig();

        new TeamPlaceholders().register();

    }

    @Override
    public void onDisable() {

    }
}
