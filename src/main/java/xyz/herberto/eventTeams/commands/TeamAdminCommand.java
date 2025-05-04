package xyz.herberto.eventTeams.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import jdk.jfr.Event;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.herberto.eventTeams.EventTeams;
import xyz.herberto.eventTeams.teams.Team;
import xyz.herberto.eventTeams.teams.TeamHandler;

import java.util.UUID;

@CommandAlias("teamadmin")
@CommandPermission("eventteams.command.teamadmin")
public class TeamAdminCommand extends BaseCommand {

    @HelpCommand
    public void help(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("create")
    @Description("Create a team")
    public void create(CommandSender sender, @Name("team name") String name) {

        if(EventTeams.getInstance().getConfig().getConfigurationSection("teams." + name) == null) {

            Team team = new Team(name);
            team.save();
            sender.sendMessage("Created team " + name);

        } else {
            sender.sendMessage("Team " + name + " already exists! Delete it with /team delete " + name);
        }

    }

    @Subcommand("delete")
    @Description("Delete a team")
    public void delete(CommandSender sender, @Name("team name") String name) {

        if(EventTeams.getInstance().getConfig().getConfigurationSection("teams." + name) == null) {

            sender.sendMessage("Team " + name + " does not exist!");
            return;

        }

        Team team = new Team(name);
        team.delete();
        sender.sendMessage("Deleted team " + name);

    }

    @Subcommand("list")
    @Description("List all teams or members in a specific team")
    public void list(CommandSender sender, @Optional @Name("team name") String name) {

        if(name == null) {
            for(Team team : TeamHandler.getAllTeams()) {

                sender.sendMessage("Team:");
                sender.sendMessage("  | Name: " + team.getName());
                sender.sendMessage("  | Total Members: " + team.getMembers().size());
                sender.sendMessage("  | See members with /teamadmin list " + team.getName());
                sender.sendMessage(" ");

            }
        } else {

            Team team = TeamHandler.getTeam(name);

            if(team == null) {
                sender.sendMessage("Team " + name + " does not exist!");
                return;
            }

            sender.sendMessage("Team: ");
            sender.sendMessage("  | Name: " + team.getName());
            sender.sendMessage("  | Total Members: " + team.getMembers().size());
            sender.sendMessage("  | Members:");

            for(UUID member : team.getMembers()) {
                Player player = EventTeams.getInstance().getServer().getPlayer(member);
                sender.sendMessage("    | " + player.getName());
            }

        }

    }

    @Subcommand("add")
    @Description("Add a player to a team")
    @CommandCompletion("@players @nothing")
    public void add(CommandSender sender, @Name("player") OnlinePlayer target, @Name("team") String team) {

        if(EventTeams.getInstance().getConfig().getConfigurationSection("teams." + team) == null) {
            sender.sendMessage("Team " + team + " does not exist.");
            return;
        }

        Team inTeam = TeamHandler.getTeam(target.getPlayer().getUniqueId());

        if(inTeam != null) {
            sender.sendMessage("Player " + target.getPlayer().getName() + " is already in another team (" + inTeam.getName() + ")");
            return;
        }

        Team team1 = TeamHandler.getTeam(team);
        team1.getMembers().add(target.getPlayer().getUniqueId());
        team1.save();

        sender.sendMessage("Added " + target.getPlayer().getName() + " to the team " + team);

    }

    @Subcommand("remove")
    @Description("Remove a player from a team")
    @CommandCompletion("@players")
    public void remove(CommandSender sender, @Name("player") OnlinePlayer target) {

        Team team = TeamHandler.getTeam(target.getPlayer().getUniqueId());

        if(team == null) {
            sender.sendMessage("Player " + target.getPlayer().getName() + " is not in a team.");
            return;
        }

        team.getMembers().remove(target.getPlayer().getUniqueId());
        team.save();

        sender.sendMessage("Removed " + target.getPlayer().getName() + " from the team " + team.getName());
    }

    @Subcommand("tpall")
    @Description("Teleport all members of a team to you")
    public void teleportAll(Player player, String teamName) {

        Team team = TeamHandler.getTeam(teamName);

        if(team == null) {
            player.sendMessage("Team " + teamName + " does not exist.");
            return;
        }

        for(UUID member : team.getMembers()) {
            Player memberPlayer = EventTeams.getInstance().getServer().getPlayer(member);
            memberPlayer.teleport(player);
        }

        player.sendMessage("You have teleported all members of the team " + teamName + " to your location.");

    }

}
