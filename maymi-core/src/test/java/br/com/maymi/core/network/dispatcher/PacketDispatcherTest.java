package br.com.maymi.core.network.dispatcher;

import br.com.maymi.common.network.packet.ChatPacket;
import br.com.maymi.common.network.packet.DashboardResponsePacket;
import br.com.maymi.common.network.packet.DeathPacket;
import br.com.maymi.common.network.packet.ListResponsePacket;
import br.com.maymi.common.network.packet.MobKillPacket;
import br.com.maymi.common.network.packet.PlayerInfoResponsePacket;
import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.common.network.packet.PlayerQuitPacket;
import br.com.maymi.common.network.packet.RamResponsePacket;
import br.com.maymi.common.network.packet.TimeResponsePacket;
import br.com.maymi.common.network.packet.TpsResponsePacket;

import br.com.maymi.core.network.handler.impl.BlockBreakHandler;
import br.com.maymi.core.network.handler.impl.BlockPlaceHandler;
import br.com.maymi.core.network.handler.impl.ChatHandler;
import br.com.maymi.core.network.handler.impl.DashboardResponseHandler;
import br.com.maymi.core.network.handler.impl.ListResponseHandler;
import br.com.maymi.core.network.handler.impl.MobKillHandler;
import br.com.maymi.core.network.handler.impl.PlayerDeathHandler;
import br.com.maymi.core.network.handler.impl.PlayerInfoResponseHandler;
import br.com.maymi.core.network.handler.impl.PlayerJoinHandler;
import br.com.maymi.core.network.handler.impl.PlayerQuitHandler;
import br.com.maymi.core.network.handler.impl.RamResponseHandler;
import br.com.maymi.core.network.handler.impl.TimeResponseHandler;
import br.com.maymi.core.network.handler.impl.TpsResponseHandler;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

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

        DashboardResponseHandler dashboardResponseHandler =
                mock(DashboardResponseHandler.class);

        PlayerInfoResponseHandler playerInfoResponseHandler =
                mock(PlayerInfoResponseHandler.class);

        MobKillHandler mobKillHandler =
                mock(MobKillHandler.class);

        PacketDispatcher dispatcher =
                criarDispatcher(
                        playerJoinHandler,
                        chatHandler,
                        playerQuitHandler,
                        playerDeathHandler,
                        listResponseHandler,
                        tpsResponseHandler,
                        ramResponseHandler,
                        timeResponseHandler,
                        dashboardResponseHandler,
                        playerInfoResponseHandler,
                        mock(MobKillHandler.class),
                        mock(BlockPlaceHandler.class),
                        mock(BlockBreakHandler.class)
                );

        PlayerJoinPacket packet =
                new PlayerJoinPacket(
                        UUID.randomUUID().toString(),
                        "BRtkViktor"
                );

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
                timeResponseHandler,
                dashboardResponseHandler,
                playerInfoResponseHandler,
                mobKillHandler
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
                        mock(TimeResponseHandler.class),
                        mock(DashboardResponseHandler.class),
                        mock(PlayerInfoResponseHandler.class),
                        mock(MobKillHandler.class),
                        mock(BlockPlaceHandler.class),
                        mock(BlockBreakHandler.class)
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
                        mock(TimeResponseHandler.class),
                        mock(DashboardResponseHandler.class),
                        mock(PlayerInfoResponseHandler.class),
                        mock(MobKillHandler.class),
                        mock(BlockPlaceHandler.class),
                        mock(BlockBreakHandler.class)
                );

        PlayerQuitPacket packet =
                new PlayerQuitPacket(
                        UUID.randomUUID().toString(),
                        "BRtkViktor"
                );

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
                        mock(TimeResponseHandler.class),
                        mock(DashboardResponseHandler.class),
                        mock(PlayerInfoResponseHandler.class),
                        mock(MobKillHandler.class),
                        mock(BlockPlaceHandler.class),
                        mock(BlockBreakHandler.class)
                );

        DeathPacket packet =
                new DeathPacket(
                        UUID.randomUUID().toString(),
                        "BRtkViktor",
                        "foi morto por um zumbi",
                        "ENTITY_ATTACK",
                        "ZOMBIE"
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
                        mock(TimeResponseHandler.class),
                        mock(DashboardResponseHandler.class),
                        mock(PlayerInfoResponseHandler.class),
                        mock(MobKillHandler.class),
                        mock(BlockPlaceHandler.class),
                        mock(BlockBreakHandler.class)
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
                        mock(TimeResponseHandler.class),
                        mock(DashboardResponseHandler.class),
                        mock(PlayerInfoResponseHandler.class),
                        mock(MobKillHandler.class),
                        mock(BlockPlaceHandler.class),
                        mock(BlockBreakHandler.class)
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
                        mock(TimeResponseHandler.class),
                        mock(DashboardResponseHandler.class),
                        mock(PlayerInfoResponseHandler.class),
                        mock(MobKillHandler.class),
                        mock(BlockPlaceHandler.class),
                        mock(BlockBreakHandler.class)
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
                        timeResponseHandler,
                        mock(DashboardResponseHandler.class),
                        mock(PlayerInfoResponseHandler.class),
                        mock(MobKillHandler.class),
                        mock(BlockPlaceHandler.class),
                        mock(BlockBreakHandler.class)
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

    @Test
    void deveEncaminharDashboardResponseParaHandlerCorreto() {

        DashboardResponseHandler dashboardResponseHandler =
                mock(DashboardResponseHandler.class);

        PacketDispatcher dispatcher =
                criarDispatcher(
                        mock(PlayerJoinHandler.class),
                        mock(ChatHandler.class),
                        mock(PlayerQuitHandler.class),
                        mock(PlayerDeathHandler.class),
                        mock(ListResponseHandler.class),
                        mock(TpsResponseHandler.class),
                        mock(RamResponseHandler.class),
                        mock(TimeResponseHandler.class),
                        dashboardResponseHandler,
                        mock(PlayerInfoResponseHandler.class),
                        mock(MobKillHandler.class),
                        mock(BlockPlaceHandler.class),
                        mock(BlockBreakHandler.class)
                );

        DashboardResponsePacket packet =
                new DashboardResponsePacket();

        dispatcher.dispatch(packet);

        verify(dashboardResponseHandler)
                .handle(packet);
    }

    @Test
    void deveEncaminharPlayerInfoResponseParaHandlerCorreto() {

        PlayerInfoResponseHandler playerInfoResponseHandler =
                mock(PlayerInfoResponseHandler.class);

        PacketDispatcher dispatcher =
                criarDispatcher(
                        mock(PlayerJoinHandler.class),
                        mock(ChatHandler.class),
                        mock(PlayerQuitHandler.class),
                        mock(PlayerDeathHandler.class),
                        mock(ListResponseHandler.class),
                        mock(TpsResponseHandler.class),
                        mock(RamResponseHandler.class),
                        mock(TimeResponseHandler.class),
                        mock(DashboardResponseHandler.class),
                        playerInfoResponseHandler,
                        mock(MobKillHandler.class),
                        mock(BlockPlaceHandler.class),
                        mock(BlockBreakHandler.class)
                );

        PlayerInfoResponsePacket packet =
                new PlayerInfoResponsePacket();

        dispatcher.dispatch(packet);

        verify(playerInfoResponseHandler)
                .handle(packet);
    }

    @Test
    void deveEncaminharMobKillParaHandlerCorreto() {

        MobKillHandler mobKillHandler =
                mock(MobKillHandler.class);

        PacketDispatcher dispatcher =
                criarDispatcher(
                        mock(PlayerJoinHandler.class),
                        mock(ChatHandler.class),
                        mock(PlayerQuitHandler.class),
                        mock(PlayerDeathHandler.class),
                        mock(ListResponseHandler.class),
                        mock(TpsResponseHandler.class),
                        mock(RamResponseHandler.class),
                        mock(TimeResponseHandler.class),
                        mock(DashboardResponseHandler.class),
                        mock(PlayerInfoResponseHandler.class),
                        mobKillHandler,
                        mock(BlockPlaceHandler.class),
                        mock(BlockBreakHandler.class)
                );

        MobKillPacket packet =
                new MobKillPacket(
                        UUID.randomUUID().toString(),
                        "BRtkViktor",
                        "ZOMBIE",
                        "Zombie"
                );

        dispatcher.dispatch(packet);

        verify(mobKillHandler)
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
            TimeResponseHandler timeResponseHandler,
            DashboardResponseHandler dashboardResponseHandler,
            PlayerInfoResponseHandler playerInfoResponseHandler,
            MobKillHandler mobKillHandler,
            BlockPlaceHandler blockPlaceHandler,
            BlockBreakHandler blockBreakHandler
    ) {

        return new PacketDispatcher(
                playerJoinHandler,
                chatHandler,
                playerQuitHandler,
                playerDeathHandler,
                listResponseHandler,
                tpsResponseHandler,
                ramResponseHandler,
                timeResponseHandler,
                dashboardResponseHandler,
                playerInfoResponseHandler,
                mobKillHandler,
                blockPlaceHandler,
                blockBreakHandler
        );
    }
}