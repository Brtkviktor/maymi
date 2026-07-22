package br.com.maymi.core.discord.listener;

import br.com.maymi.common.network.packet.CommandPacket;
import br.com.maymi.common.network.packet.DiscordChatPacket;
import br.com.maymi.core.socket.SocketClient;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordMessageListener extends ListenerAdapter {

    private final SocketClient socketClient = new SocketClient();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) {
            return;
        }

        String author = event.getAuthor().getName();
        String message = event.getMessage().getContentDisplay();

        if (message.startsWith("/")) {

            socketClient.send(
                    new CommandPacket(message)
            );

            return;
        }

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