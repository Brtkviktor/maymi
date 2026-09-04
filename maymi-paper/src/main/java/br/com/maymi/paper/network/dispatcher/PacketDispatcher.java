package br.com.maymi.paper.network.dispatcher;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.packet.CommandPacket;
import br.com.maymi.common.network.packet.DiscordChatPacket;
import br.com.maymi.common.network.packet.PlayerInfoRequestPacket;
import br.com.maymi.paper.network.handler.CommandHandler;
import br.com.maymi.paper.network.handler.DiscordChatHandler;
import br.com.maymi.paper.network.handler.PlayerInfoRequestHandler;
import br.com.maymi.paper.player.session.PlayerSessionManager;
import br.com.maymi.paper.socket.SocketClient;

public class PacketDispatcher {

    private final DiscordChatHandler discordChatHandler;
    private final CommandHandler commandHandler;
    private final PlayerInfoRequestHandler playerInfoRequestHandler;

    public PacketDispatcher(
            SocketClient socketClient,
            PlayerSessionManager playerSessionManager
    ) {

        this.discordChatHandler =
                new DiscordChatHandler();

        this.commandHandler =
                new CommandHandler(
                        socketClient
                );

        this.playerInfoRequestHandler =
                new PlayerInfoRequestHandler(
                        socketClient,
                        playerSessionManager
                );
    }

    public void dispatch(
            Packet packet
    ) {

        if (packet instanceof DiscordChatPacket chatPacket) {

            discordChatHandler.handle(
                    chatPacket
            );

            return;
        }

        if (packet instanceof CommandPacket commandPacket) {

            commandHandler.handle(
                    commandPacket
            );

            return;
        }

        if (packet instanceof PlayerInfoRequestPacket playerInfoPacket) {

            playerInfoRequestHandler.handle(
                    playerInfoPacket
            );

            return;
        }

        System.out.println(
                "Pacote desconhecido: "
                        + packet.getType()
        );
    }
}