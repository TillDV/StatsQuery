package de.tilldv.statsquery;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Listener(), this);

        this.getCommand("tode").setExecutor(new CommandDeaths());

        MySQL mysql = new MySQL("localhost","bedwars","root","root");
        try {
            mysql.connect();
            PreparedStatement statement = mysql.createStatement("SELECT uuid, tode from bedwars ORDER BY tode DESC LIMIT 1");
            ResultSet result = statement.executeQuery();
            if(result.next()){
                String uuid = result.getString("uuid");

                getLogger().info("§a-----[Tl] " + Requests.getName(UUID.fromString(uuid.toString())) + " hat am meisten Tode...");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        getLogger().info("Bestenliste wurde geladen...");
    }

    public class CommandDeaths implements CommandExecutor {

        // This method is called, when somebody uses our command
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            UUID uuid;
            uuid = UUID.fromString("34bda3ec-4763-4272-96a9-538f278f6adb");
            if (sender instanceof Player) {
                Player player = (Player) sender;



                if (args.length == 0) {
                    uuid = player.getUniqueId();
                } else {
                    OfflinePlayer o = Bukkit.getOfflinePlayer(args[0]);
                    uuid = o.getUniqueId();
                }

                MySQL mysql = new MySQL("localhost","bedwars","root","root");
                try {


                    mysql.connect();
                    PreparedStatement statement = mysql.createStatement("SELECT uuid, tode from bedwars where uuid='"+ uuid + "'");
                    ResultSet result = statement.executeQuery();
                    if(result.next()){
                        String tode = result.getString("tode");

                        if (uuid == player.getUniqueId()) {
                            player.sendMessage("§aDu hast §b" + tode + "§a Tode!");
                        } else {
                            try {
                                player.sendMessage("§b" + Requests.getName(UUID.fromString(uuid.toString())) + "§a hat §b" + tode +"§a Tode!");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
                return true;

        }
    }



    public class Listener implements org.bukkit.event.Listener {}

}
