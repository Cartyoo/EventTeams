package xyz.herberto.eventTeams.teams;

import org.bukkit.configuration.ConfigurationSection;
import xyz.herberto.eventTeams.EventTeams;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamHandler {

    public static Team getTeam(String name) {

        ConfigurationSection root = EventTeams.getInstance().getConfig().getConfigurationSection("teams");

        for (String key : root.getKeys(false)) {
            if (key.equalsIgnoreCase(name)) {
                List<String> configMembers = root.getStringList(key);

                List<UUID> members = new ArrayList<>();

                for(String configMember : configMembers) {
                    members.add(UUID.fromString(configMember));
                }

                return new Team(name, members);
            }
        }

        return null;

    }

    public static Team getTeam(UUID uuid) {

        ConfigurationSection root = EventTeams.getInstance().getConfig().getConfigurationSection("teams");

        for (String key : root.getKeys(false)) {
            List<String> configMembers = root.getStringList(key);
            if(configMembers.contains(uuid.toString())) {
                return getTeam(key);
            }
        }

        return null;
    }

    public static List<String> getAllTeamNames() {
        ConfigurationSection root = EventTeams.getInstance().getConfig().getConfigurationSection("teams");
        return new ArrayList<>(root.getKeys(false));
    }

}
