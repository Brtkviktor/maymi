package br.com.maymi.paper;

import org.bukkit.plugin.java.JavaPlugin;
import br.com.maymi.paper.listeners.PlayerConnectionListener;

public class MaymiPaper extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("==========================");
        getLogger().info("Maymi Paper iniciado!");
        getLogger().info("Versão: " + getPluginMeta().getVersion());
        getLogger().info("==========================");

        getServer().getPluginManager().registerEvents(
                new PlayerConnectionListener(),
                this
        );

    }

    @Override
    public void onDisable() {

        getLogger().info("Maymi Paper desligado.");

    }

}