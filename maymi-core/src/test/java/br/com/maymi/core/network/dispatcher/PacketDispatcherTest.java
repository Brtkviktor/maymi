package br.com.maymi.core.network.dispatcher;

import br.com.maymi.common.network.packet.ChatPacket;
import br.com.maymi.common.network.packet.DeathPacket;
import br.com.maymi.common.network.packet.ListResponsePacket;
import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.common.network.packet.PlayerQuitPacket;
import br.com.maymi.common.network.packet.RamResponsePacket;
import br.com.maymi.common.network.packet.TimeResponsePacket;
import br.com.maymi.common.network.packet.TpsResponsePacket;
import br.com.maymi.core.network.handler.impl.ChatHandler;
import br.com.maymi.core.network.handler.impl.ListResponseHandler;
import br.com.maymi.core.network.handler.impl.PlayerDeathHandler;
import br.com.maymi.core.network.handler.impl.PlayerJoinHandler;
import br.com.maymi.core.network.handler.impl.PlayerQuitHandler;
import br.com.maymi.core.network.handler.impl.RamResponseHandler;
import br.com.maymi.core.network.handler.impl.TimeResponseHandler;
import br.com.maymi.core.network.handler.impl.TpsResponseHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class PacketDispatcherTest {

    @Test
    void deveEncaminharPlayerJoinParaHandlerCorreto() {

        PlayerJoinHandler playerJoinHandler =
                mock(PlayerJoinHandler.class);

        ChatHandler chatHandler =
                mock(ChatHandler.class);

        PlayerQuitHandler playerQuitHandler =
                mock(PlayerQuitHandler.class);

        PlayerDeathHandler playerDeathHandler =
                mock(PlayerDeathHandler.class);

        ListResponseHandler listResponseHandler =
                mock(ListResponseHandler.class);

        TpsResponseHandler tpsResponseHandler =
                mock(TpsResponseHandler.class);

        RamResponseHandler ramResponseHandler =
                mock(RamResponseHandler.class);

        TimeResponseHandler timeResponseHandler =
                mock(TimeResponseHandler.class);

        PacketDispatcher dispatcher =
                criarDispatcher(
                        playerJoinHandler,
                        chatHandler,
                        playerQuitHandler,
                        playerDeathHandler,
                        listResponseHandler,
                        tpsResponseHandler,
                        ramResponseHandler,
                        timeResponseHandler
                );

        PlayerJoinPacket packet =
                new PlayerJoinPacket("BRtkViktor");

        dispatcher.dispatch(packet);

        verify(playerJoinHandler)
                .handle(packet);

        verifyNoInteractions(
                chatHandler,
                playerQuitHandler,
                playerDeathHandler,
                listResponseHandler,
                tpsResponseHandler,
                ramResponseHandler,
                timeResponseHandler
        );
    }


    @Test
    void deveEncaminharChatParaHandlerCorreto() {

        ChatHandler chatHandler =
                mock(ChatHandler.class);

        PacketDispatcher dispatcher =
                criarDispatcher(
                        mock(PlayerJoinHandler.class),
                        chatHandler,
                        mock(PlayerQuitHandler.class),
                        mock(PlayerDeathHandler.class),
                        mock(ListResponseHandler.class),
                        mock(TpsResponseHandler.class),
                        mock(RamResponseHandler.class),
                        mock(TimeResponseHandler.class)
                );

        ChatPacket packet =
                new ChatPacket(
                        "BRtkViktor",
                        "Olá, Maymi!"
                );

        dispatcher.dispatch(packet);

        verify(chatHandler)
                .handle(packet);
    }


    @Test
    void deveEncaminharPlayerQuitParaHandlerCorreto() {

        PlayerQuitHandler playerQuitHandler =
                mock(PlayerQuitHandler.class);

        PacketDispatcher dispatcher =
                criarDispatcher(
                        mock(PlayerJoinHandler.class),
                        mock(ChatHandler.class),
                        playerQuitHandler,
                        mock(PlayerDeathHandler.class),
                        mock(ListResponseHandler.class),
                        mock(TpsResponseHandler.class),
                        mock(RamResponseHandler.class),
                        mock(TimeResponseHandler.class)
                );

        PlayerQuitPacket packet =
                new PlayerQuitPacket("BRtkViktor");

        dispatcher.dispatch(packet);

        verify(playerQuitHandler)
                .handle(packet);
    }


    @Test
    void deveEncaminharDeathParaHandlerCorreto() {

        PlayerDeathHandler playerDeathHandler =
                mock(PlayerDeathHandler.class);

        PacketDispatcher dispatcher =
                criarDispatcher(
                        mock(PlayerJoinHandler.class),
                        mock(ChatHandler.class),
                        mock(PlayerQuitHandler.class),
                        playerDeathHandler,
                        mock(ListResponseHandler.class),
                        mock(TpsResponseHandler.class),
                        mock(RamResponseHandler.class),
                        mock(TimeResponseHandler.class)
                );

        DeathPacket packet =
                new DeathPacket(
                        "BRtkViktor",
                        "caiu de um lugar alto"
                );

        dispatcher.dispatch(packet);

        verify(playerDeathHandler)
                .handle(packet);
    }


    @Test
    void deveEncaminharListResponseParaHandlerCorreto() {

        ListResponseHandler listResponseHandler =
                mock(ListResponseHandler.class);

        PacketDispatcher dispatcher =
                criarDispatcher(
                        mock(PlayerJoinHandler.class),
                        mock(ChatHandler.class),
                        mock(PlayerQuitHandler.class),
                        mock(PlayerDeathHandler.class),
                        listResponseHandler,
                        mock(TpsResponseHandler.class),
                        mock(RamResponseHandler.class),
                        mock(TimeResponseHandler.class)
                );

        ListResponsePacket packet =
                new ListResponsePacket(
                        List.of(
                                "BRtkViktor",
                                "MaymiPlayer"
                        )
                );

        dispatcher.dispatch(packet);

        verify(listResponseHandler)
                .handle(packet);
    }


    @Test
    void deveEncaminharTpsResponseParaHandlerCorreto() {

        TpsResponseHandler tpsResponseHandler =
                mock(TpsResponseHandler.class);

        PacketDispatcher dispatcher =
                criarDispatcher(
                        mock(PlayerJoinHandler.class),
                        mock(ChatHandler.class),
                        mock(PlayerQuitHandler.class),
                        mock(PlayerDeathHandler.class),
                        mock(ListResponseHandler.class),
                        tpsResponseHandler,
                        mock(RamResponseHandler.class),
                        mock(TimeResponseHandler.class)
                );

        TpsResponsePacket packet =
                new TpsResponsePacket(20.0);

        dispatcher.dispatch(packet);

        verify(tpsResponseHandler)
                .handle(packet);
    }


    @Test
    void deveEncaminharRamResponseParaHandlerCorreto() {

        RamResponseHandler ramResponseHandler =
                mock(RamResponseHandler.class);

        PacketDispatcher dispatcher =
                criarDispatcher(
                        mock(PlayerJoinHandler.class),
                        mock(ChatHandler.class),
                        mock(PlayerQuitHandler.class),
                        mock(PlayerDeathHandler.class),
                        mock(ListResponseHandler.class),
                        mock(TpsResponseHandler.class),
                        ramResponseHandler,
                        mock(TimeResponseHandler.class)
                );

        RamResponsePacket packet =
                new RamResponsePacket(
                        2.0,
                        6.0,
                        8.0
                );

        dispatcher.dispatch(packet);

        verify(ramResponseHandler)
                .handle(packet);
    }


    @Test
    void deveEncaminharTimeResponseParaHandlerCorreto() {

        TimeResponseHandler timeResponseHandler =
                mock(TimeResponseHandler.class);

        PacketDispatcher dispatcher =
                criarDispatcher(
                        mock(PlayerJoinHandler.class),
                        mock(ChatHandler.class),
                        mock(PlayerQuitHandler.class),
                        mock(PlayerDeathHandler.class),
                        mock(ListResponseHandler.class),
                        mock(TpsResponseHandler.class),
                        mock(RamResponseHandler.class),
                        timeResponseHandler
                );

        TimeResponsePacket packet =
                new TimeResponsePacket(
                        "world",
                        34,
                        19277
                );

        dispatcher.dispatch(packet);

        verify(timeResponseHandler)
                .handle(packet);
    }


    private PacketDispatcher criarDispatcher(
            PlayerJoinHandler playerJoinHandler,
            ChatHandler chatHandler,
            PlayerQuitHandler playerQuitHandler,
            PlayerDeathHandler playerDeathHandler,
            ListResponseHandler listResponseHandler,
            TpsResponseHandler tpsResponseHandler,
            RamResponseHandler ramResponseHandler,
            TimeResponseHandler timeResponseHandler
    ) {

        return new PacketDispatcher(
                playerJoinHandler,
                chatHandler,
                playerQuitHandler,
                playerDeathHandler,
                listResponseHandler,
                tpsResponseHandler,
                ramResponseHandler,
                timeResponseHandler
        );
    }

}