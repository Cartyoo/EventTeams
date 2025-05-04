package xyz.herberto.eventTeams.teams;

import org.bukkit.Bukkit;
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
                    Bukkit.getLogger().info("member: + " + configMember);
                    members.add(UUID.fromString(configMember));
                }

                return new Team(name, members);
            }
        }

        return null;

    }


    public static Team getTeam(UUID uuid) {

        for(String teamName : getAllTeamNames()) {
            Team team = getTeam(teamName);
            if(team.getMembers().contains(uuid)) {
                return team;
            }
        }

        return null;
    }

    public static List<String> getAllTeamNames() {
        ConfigurationSection root = EventTeams.getInstance().getConfig().getConfigurationSection("teams");
        return new ArrayList<>(root.getKeys(false));
    }

    public static List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<>();

        ConfigurationSection root = EventTeams.getInstance().getConfig().getConfigurationSection("teams");

        for(String key : root.getKeys(false)) {
            ConfigurationSection section = root.getConfigurationSection(key);
            teams.add(getTeam(key));
        }

        return teams;

    }

}
