package xyz.herberto.eventTeams.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import xyz.herberto.eventTeams.teams.Team;
import xyz.herberto.eventTeams.teams.TeamHandler;

public class TeamPlaceholders extends PlaceholderExpansion {

    public TeamPlaceholders() {
    }

    @Override
    public String getAuthor() {
        return "herberto";
    }

    @Override
    public String getIdentifier() {
        return "eventteams";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        Team team = TeamHandler.getTeam(player.getUniqueId());

        if(params.equalsIgnoreCase("team_name")){
            if(team != null) {
                return team.getName();
            } else {
                return "N/A";
            }
        } else if(params.equalsIgnoreCase("team_total_members")){
            return String.valueOf(team == null || team.getMembers() == null ? 0 : team.getMembers().size());

        } else if(params.equalsIgnoreCase("is_in_team")) {
            return String.valueOf(team != null);

        } else if(params.equalsIgnoreCase("is_in_team_formatted")) {
            return team != null ? "&aYes" : "&cNo";

        }

        return null;
    }
}