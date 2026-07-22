package br.com.maymi.paper.network.dispatcher;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.packet.CommandPacket;
import br.com.maymi.common.network.packet.DiscordChatPacket;
import br.com.maymi.paper.network.handler.CommandHandler;
import br.com.maymi.paper.network.handler.DiscordChatHandler;

public class PacketDispatcher {

    private final DiscordChatHandler discordChatHandler =
            new DiscordChatHandler();

    private final CommandHandler commandHandler =
            new CommandHandler();

    public void dispatch(Packet packet) {

        if (packet instanceof DiscordChatPacket chatPacket) {

            discordChatHandler.handle(chatPacket);

        }

        if (packet instanceof CommandPacket commandPacket) {

            commandHandler.handle(commandPacket);

            return;
        } else {

            System.out.println(
                    "Pacote desconhecido: " + packet.getType()
            );

        }

    }
}