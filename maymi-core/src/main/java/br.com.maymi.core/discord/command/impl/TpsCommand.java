package br.com.maymi.core.discord.command.impl;

import br.com.maymi.common.network.packet.CommandPacket;
import br.com.maymi.core.discord.command.MaymiCommand;
import br.com.maymi.core.socket.SocketClient;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TpsCommand
        implements MaymiCommand {

    private final SocketClient socketClient;

    public TpsCommand(
            SocketClient socketClient
    ) {

        this.socketClient = socketClient;

    }

    @Override
    public void execute(
            MessageReceivedEvent event
    ) {

        socketClient.send(
                new CommandPacket("/tps")
        );

    }

}