package xyz.qntum.TNTWhitelist;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.qntum.TNTWhitelist.commands.AddToWhitelistCommand;
import xyz.qntum.TNTWhitelist.commands.ListWhitelistCommand;
import xyz.qntum.TNTWhitelist.commands.RemoveFromWhitelistCommand;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

public final class TNTWhitelist extends JavaPlugin {

    // Usernames and UUIDs only
    public HashSet<String> whitelist;
    private File whitelistFile;

    @Override
    public void onEnable() {
        getLogger().info("Loading TNTWhitelist");

        saveResource("config.yml", false);

        // Create the whitelist file if it doesn't exist
        whitelistFile = new File(getDataFolder(), "whitelist.txt");

        if (!whitelistFile.exists()) {
            whitelistFile.getParentFile().mkdir();
            saveResource("whitelist.txt", false);
        }

        whitelist = new HashSet<>();

        // Load the whitelist into the list
        if (!whitelistFile.canRead() || !whitelistFile.isFile()) {
            // Crash the plugin
            getLogger().severe("Could not read whitelist file! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
        }

        try {
            Scanner fileReader = new Scanner(whitelistFile);

            while (fileReader.hasNextLine()) {
                String uuid = fileReader.nextLine();

                if (uuid.length() == 36) {
                    whitelist.add(uuid);
                } else {
                    getLogger().warning("Invalid UUID in whitelist file line: " + uuid);
                }

            }

            saveWhitelist();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Register events
        getServer().getPluginManager().registerEvents(new TNTEventsHandler(this), this);

        // Register commands
        this.getCommand("tntwl").setExecutor(new ListWhitelistCommand(this));
        this.getCommand("tntwladd").setExecutor(new AddToWhitelistCommand(this));
        this.getCommand("tntwlremove").setExecutor(new RemoveFromWhitelistCommand(this));


        getLogger().info("Loaded TNTWhitelist");
    }

    @Override
    public void onDisable() {
        getLogger().info("Stopping TNTWhitelist");

        saveWhitelist();

        getLogger().info("Saved username whitelist");
    }

    public void saveWhitelist() {
        if (whitelistFile.canWrite()) {
            try (FileWriter writer = new FileWriter(whitelistFile)) {
                whitelist
                        .forEach(u -> {
                            try {
                                writer.write(u + "\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (IOException e) {
                this.getLogger().severe("Cannot write to whitelist file!");
                e.printStackTrace();
            }
        }
    }
}
