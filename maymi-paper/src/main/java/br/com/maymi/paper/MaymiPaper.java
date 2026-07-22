package br.com.maymi.paper;

import br.com.maymi.paper.listeners.PlayerDeathListener;
import br.com.maymi.paper.listeners.PlayerQuitListener;
import br.com.maymi.paper.socket.SocketServer;
import org.bukkit.plugin.java.JavaPlugin;
import br.com.maymi.paper.listeners.PlayerConnectionListener;
import br.com.maymi.paper.listeners.PlayerChatListener;

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

        getServer().getPluginManager().registerEvents(
                new PlayerChatListener(),
                this
        );

        getServer().getPluginManager().registerEvents(
                new PlayerQuitListener(),
                this
        );

        getServer().getPluginManager().registerEvents(
                new PlayerDeathListener(),
                this
        );

        SocketServer socketServer =
                new SocketServer();

        new Thread(socketServer::start).start();


    }

    @Override
    public void onDisable() {

        getLogger().info("Maymi Paper desligado.");

    }

}