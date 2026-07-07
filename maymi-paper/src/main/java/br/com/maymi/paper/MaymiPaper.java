package br.com.maymi.paper;

import org.bukkit.plugin.java.JavaPlugin;

public class MaymiPaper extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("======================================");
        getLogger().info("Maymi Paper iniciado!");
        getLogger().info("Versão: " + getPluginMeta().getVersion());
        getLogger().info("======================================");

    }

    @Override
    public void onDisable() {

        getLogger().info("Maymi Paper desligado.");

    }

}