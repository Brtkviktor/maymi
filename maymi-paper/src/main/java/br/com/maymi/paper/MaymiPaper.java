package br.com.maymi.paper;

import br.com.maymi.paper.listeners.BlockActivityListener;
import br.com.maymi.paper.listeners.MobKillListener;
import br.com.maymi.paper.listeners.PlayerChatListener;
import br.com.maymi.paper.listeners.PlayerConnectionListener;
import br.com.maymi.paper.listeners.PlayerDeathListener;
import br.com.maymi.paper.listeners.PlayerQuitListener;
import br.com.maymi.paper.player.session.PlayerSessionManager;
import br.com.maymi.paper.service.MinecraftEventService;
import br.com.maymi.paper.socket.SocketServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MaymiPaper extends JavaPlugin {

    private PlayerSessionManager playerSessionManager;
    private MinecraftEventService minecraftEventService;
    private SocketServer socketServer;

    @Override
    public void onEnable() {

        getLogger().info("==========================");
        getLogger().info("Maymi Paper iniciado!");
        getLogger().info(
                "Versão: " + getPluginMeta().getVersion()
        );
        getLogger().info("==========================");

        this.playerSessionManager =
                new PlayerSessionManager();

        this.minecraftEventService =
                new MinecraftEventService();

        Bukkit.getOnlinePlayers().forEach(
                player -> playerSessionManager.startSession(
                        player.getUniqueId()
                )
        );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new PlayerConnectionListener(
                                minecraftEventService,
                                playerSessionManager
                        ),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new PlayerChatListener(),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new MobKillListener(),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new PlayerQuitListener(
                                minecraftEventService
                        ),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new PlayerDeathListener(),
                        this
                );

        this.socketServer =
                new SocketServer(
                        playerSessionManager
                );

        Thread socketThread =
                new Thread(
                        socketServer::start,
                        "maymi-socket-server"
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new BlockActivityListener(),
                        this
                );


        socketThread.start();
    }

    @Override
    public void onDisable() {

        getLogger().info(
                "Maymi Paper desligado."
        );
    }
}