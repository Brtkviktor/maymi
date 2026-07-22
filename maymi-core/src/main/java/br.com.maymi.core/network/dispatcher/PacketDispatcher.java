package br.com.maymi.core.network.dispatcher;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.packet.ChatPacket;
import br.com.maymi.common.network.packet.DeathPacket;
import br.com.maymi.common.network.packet.ListResponsePacket;
import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.common.network.packet.PlayerQuitPacket;
import br.com.maymi.common.network.packet.RamResponsePacket;
import br.com.maymi.common.network.packet.TpsResponsePacket;
import br.com.maymi.core.network.handler.impl.ChatHandler;
import br.com.maymi.core.network.handler.impl.ListResponseHandler;
import br.com.maymi.core.network.handler.impl.PlayerDeathHandler;
import br.com.maymi.core.network.handler.impl.PlayerJoinHandler;
import br.com.maymi.core.network.handler.impl.PlayerQuitHandler;
import br.com.maymi.core.network.handler.impl.RamResponseHandler;
import br.com.maymi.core.network.handler.impl.TpsResponseHandler;
import br.com.maymi.common.network.packet.TimeResponsePacket;
import br.com.maymi.core.network.handler.impl.TimeResponseHandler;

public class PacketDispatcher {

    private final PlayerJoinHandler playerJoinHandler =
            new PlayerJoinHandler();

    private final ChatHandler chatHandler =
            new ChatHandler();

    private final PlayerQuitHandler playerQuitHandler =
            new PlayerQuitHandler();

    private final PlayerDeathHandler playerDeathHandler =
            new PlayerDeathHandler();

    private final ListResponseHandler listResponseHandler =
            new ListResponseHandler();

    private final TpsResponseHandler tpsResponseHandler =
            new TpsResponseHandler();

    private final RamResponseHandler ramResponseHandler =
            new RamResponseHandler();

    private final TimeResponseHandler timeResponseHandler =
            new TimeResponseHandler();

    public void dispatch(Packet packet) {

        if (packet instanceof PlayerJoinPacket joinPacket) {

            playerJoinHandler.handle(joinPacket);

        } else if (packet instanceof ChatPacket chatPacket) {

            chatHandler.handle(chatPacket);

        } else if (packet instanceof PlayerQuitPacket quitPacket) {

            playerQuitHandler.handle(quitPacket);

        } else if (packet instanceof DeathPacket deathPacket) {

            playerDeathHandler.handle(deathPacket);

        } else if (packet instanceof ListResponsePacket responsePacket) {

            listResponseHandler.handle(responsePacket);

            return;
        } else if (packet instanceof TpsResponsePacket tpsPacket) {

            tpsResponseHandler.handle(tpsPacket);

            return;

        } else if(packet instanceof RamResponsePacket ramPacket){

            ramResponseHandler.handle(ramPacket);

            return;

        }else if (packet instanceof TimeResponsePacket timePacket) {

            timeResponseHandler.handle(timePacket);

            return;

        }


        else {

            System.out.println(
                    "Nenhum handler encontrado para: "
                            + packet.getType()
            );

        }

    }

}