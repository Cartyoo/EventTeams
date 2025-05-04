package xyz.herberto.eventTeams;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.herberto.eventTeams.commands.TeamAdminCommand;

import java.util.Arrays;

public final class EventTeams extends JavaPlugin {

    @Getter
    public static EventTeams instance;


    @Override
    public void onEnable() {

        instance = this;

        PaperCommandManager manager = new PaperCommandManager(this);

        Arrays.asList(
                new TeamAdminCommand()
        ).forEach(manager::registerCommand);

        saveDefaultConfig();

    }

    @Override
    public void onDisable() {

    }
}
