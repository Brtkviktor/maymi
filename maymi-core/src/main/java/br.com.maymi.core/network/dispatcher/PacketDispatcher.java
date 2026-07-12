package br.com.maymi.core.network.dispatcher;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.core.network.handler.impl.PlayerJoinHandler;

public class PacketDispatcher {

    private final PlayerJoinHandler playerJoinHandler =
            new PlayerJoinHandler();

    public void dispatch(Packet packet) {

        if (packet instanceof PlayerJoinPacket joinPacket) {

            playerJoinHandler.handle(joinPacket);

        } else {

            System.out.println(
                    "Nenhum handler encontrado para: "
                            + packet.getType()
            );

        }

    }

}