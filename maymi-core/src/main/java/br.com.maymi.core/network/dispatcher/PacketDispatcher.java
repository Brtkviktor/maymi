package br.com.maymi.core.network.dispatcher;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.exception.UnknownPacketException;
import br.com.maymi.common.network.packet.ChatPacket;
import br.com.maymi.common.network.packet.DeathPacket;
import br.com.maymi.common.network.packet.ListResponsePacket;
import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.common.network.packet.PlayerQuitPacket;
import br.com.maymi.common.network.packet.RamResponsePacket;
import br.com.maymi.common.network.packet.TimeResponsePacket;
import br.com.maymi.common.network.packet.TpsResponsePacket;
import br.com.maymi.core.discord.DiscordService;
import br.com.maymi.core.network.handler.impl.ChatHandler;
import br.com.maymi.core.network.handler.impl.ListResponseHandler;
import br.com.maymi.core.network.handler.impl.PlayerDeathHandler;
import br.com.maymi.core.network.handler.impl.PlayerJoinHandler;
import br.com.maymi.core.network.handler.impl.PlayerQuitHandler;
import br.com.maymi.core.network.handler.impl.RamResponseHandler;
import br.com.maymi.core.network.handler.impl.TimeResponseHandler;
import br.com.maymi.core.network.handler.impl.TpsResponseHandler;

public class PacketDispatcher {

    private final PlayerJoinHandler playerJoinHandler;

    private final ChatHandler chatHandler;

    private final PlayerQuitHandler playerQuitHandler;

    private final PlayerDeathHandler playerDeathHandler;

    private final ListResponseHandler listResponseHandler;

    private final TpsResponseHandler tpsResponseHandler;

    private final RamResponseHandler ramResponseHandler;

    private final TimeResponseHandler timeResponseHandler;


    // =====================================================
    // CONSTRUTOR PRINCIPAL DA APLICAÇÃO
    // =====================================================

    public PacketDispatcher(
            DiscordService discordService
    ) {

        this.playerJoinHandler =
                new PlayerJoinHandler(
                        discordService
                );

        this.chatHandler =
                new ChatHandler(
                        discordService
                );

        this.playerQuitHandler =
                new PlayerQuitHandler(
                        discordService
                );

        this.playerDeathHandler =
                new PlayerDeathHandler(
                        discordService
                );

        this.listResponseHandler =
                new ListResponseHandler(
                        discordService
                );

        this.tpsResponseHandler =
                new TpsResponseHandler(
                        discordService
                );

        this.ramResponseHandler =
                new RamResponseHandler(
                        discordService
                );

        this.timeResponseHandler =
                new TimeResponseHandler(
                        discordService
                );

    }


    // =====================================================
    // CONSTRUTOR PARA TESTES / INJEÇÃO DE DEPENDÊNCIAS
    // =====================================================

    public PacketDispatcher(

            PlayerJoinHandler playerJoinHandler,

            ChatHandler chatHandler,

            PlayerQuitHandler playerQuitHandler,

            PlayerDeathHandler playerDeathHandler,

            ListResponseHandler listResponseHandler,

            TpsResponseHandler tpsResponseHandler,

            RamResponseHandler ramResponseHandler,

            TimeResponseHandler timeResponseHandler

    ) {

        this.playerJoinHandler =
                playerJoinHandler;

        this.chatHandler =
                chatHandler;

        this.playerQuitHandler =
                playerQuitHandler;

        this.playerDeathHandler =
                playerDeathHandler;

        this.listResponseHandler =
                listResponseHandler;

        this.tpsResponseHandler =
                tpsResponseHandler;

        this.ramResponseHandler =
                ramResponseHandler;

        this.timeResponseHandler =
                timeResponseHandler;

    }


    // =====================================================
    // DISPATCH
    // =====================================================

    public void dispatch(Packet packet) {

        if (packet instanceof PlayerJoinPacket joinPacket) {

            playerJoinHandler.handle(
                    joinPacket
            );

        } else if (packet instanceof ChatPacket chatPacket) {

            chatHandler.handle(
                    chatPacket
            );

        } else if (packet instanceof PlayerQuitPacket quitPacket) {

            playerQuitHandler.handle(
                    quitPacket
            );

        } else if (packet instanceof DeathPacket deathPacket) {

            playerDeathHandler.handle(
                    deathPacket
            );

        } else if (packet instanceof ListResponsePacket responsePacket) {

            listResponseHandler.handle(
                    responsePacket
            );

        } else if (packet instanceof TpsResponsePacket tpsPacket) {

            tpsResponseHandler.handle(
                    tpsPacket
            );

        } else if (packet instanceof RamResponsePacket ramPacket) {

            ramResponseHandler.handle(
                    ramPacket
            );

        } else if (packet instanceof TimeResponsePacket timePacket) {

            timeResponseHandler.handle(
                    timePacket
            );

        } else {

            throw new UnknownPacketException(
                    "Nenhum handler encontrado para o pacote: "
                            + packet.getType()
            );

        }

    }

}