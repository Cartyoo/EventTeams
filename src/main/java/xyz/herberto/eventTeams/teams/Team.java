package xyz.herberto.eventTeams.teams;

import lombok.Data;
import org.bukkit.configuration.ConfigurationSection;
import xyz.herberto.eventTeams.EventTeams;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class Team {

    private String name;
    private List<UUID> members;

    public Team(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    public Team(String name, List<UUID> members) {
        this.name = name;
        this.members = members;
    }

    public void save() {
        if (EventTeams.getInstance().getConfig().getConfigurationSection("teams." + name) == null) {
            EventTeams.getInstance().getConfig().createSection("teams." + name);
        }

        List<String> uuidList = members.stream()
                .map(UUID::toString)
                .collect(Collectors.toList());

        EventTeams.getInstance().getConfig().set("teams." + name + ".members", uuidList);
        EventTeams.getInstance().saveConfig();
    }


    public void delete() {
        EventTeams.getInstance().getConfig().set("teams." + name, null);
    }

}
