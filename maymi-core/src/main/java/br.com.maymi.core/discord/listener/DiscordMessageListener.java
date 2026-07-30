package br.com.maymi.core.discord.listener;

import br.com.maymi.common.network.packet.DiscordChatPacket;
import br.com.maymi.core.discord.command.MaymiCommandManager;
import br.com.maymi.core.socket.SocketClient;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordMessageListener extends ListenerAdapter {

    private final SocketClient socketClient;

    private final MaymiCommandManager commandManager;

    public DiscordMessageListener(SocketClient socketClient) {

        this.socketClient = socketClient;

        this.commandManager =
                new MaymiCommandManager(socketClient);

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) {
            return;
        }

        String author =
                event.getAuthor().getName();

        String message =
                event.getMessage().getContentDisplay();

        // =====================================================
        // COMANDOS
        // =====================================================

        if (message.startsWith("/")) {

            System.out.println("""
            ==================================
            NOVO COMANDO DO DISCORD
            ----------------------------------
            Autor: %s
            Comando: %s
            ==================================
            """.formatted(
                    author,
                    message
            ));

            commandManager.handle(
                    message,
                    event
            );

            return;

        }

        // =====================================================
        // CHAT DISCORD -> MINECRAFT
        // =====================================================

        System.out.println("""
                ==================================
                NOVA MENSAGEM DO DISCORD
                ----------------------------------
                Autor: %s
                Canal: %s
                Mensagem: %s
                ==================================
                """.formatted(
                author,
                event.getChannel().getName(),
                message
        ));

        DiscordChatPacket packet =
                new DiscordChatPacket(
                        author,
                        message
                );

        socketClient.send(packet);

    }
}