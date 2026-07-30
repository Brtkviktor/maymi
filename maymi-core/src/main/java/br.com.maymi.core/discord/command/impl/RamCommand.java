package br.com.maymi.core.discord.command.impl;

import br.com.maymi.common.network.packet.CommandPacket;
import br.com.maymi.core.discord.command.MaymiCommand;
import br.com.maymi.core.socket.SocketClient;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RamCommand
        implements MaymiCommand {

    private final SocketClient socketClient;

    public RamCommand(
            SocketClient socketClient
    ) {

        this.socketClient = socketClient;

    }

    @Override
    public void execute(
            MessageReceivedEvent event
    ) {

        socketClient.send(
                new CommandPacket("/ram")
        );

    }

}