package br.com.maymi.core.network.dispatcher;

import br.com.maymi.common.network.Packet;
import br.com.maymi.common.network.exception.UnknownPacketException;
import br.com.maymi.common.network.packet.BlockBreakPacket;
import br.com.maymi.common.network.packet.BlockPlacePacket;
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

import br.com.maymi.core.discord.DiscordService;
import br.com.maymi.core.discord.interaction.InteractionResponseService;
import br.com.maymi.core.event.MaymiEventBus;
import br.com.maymi.core.mod.ModDetectionService;

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

import br.com.maymi.core.persistence.service.PlayerPersistenceService;

import java.util.Objects;

public final class PacketDispatcher {

    private final PlayerJoinHandler playerJoinHandler;
    private final ChatHandler chatHandler;
    private final PlayerQuitHandler playerQuitHandler;
    private final PlayerDeathHandler playerDeathHandler;
    private final ListResponseHandler listResponseHandler;
    private final TpsResponseHandler tpsResponseHandler;
    private final RamResponseHandler ramResponseHandler;
    private final TimeResponseHandler timeResponseHandler;
    private final DashboardResponseHandler dashboardResponseHandler;
    private final PlayerInfoResponseHandler playerInfoResponseHandler;
    private final MobKillHandler mobKillHandler;
    private final BlockPlaceHandler blockPlaceHandler;
    private final BlockBreakHandler blockBreakHandler;

    public PacketDispatcher(
            DiscordService discordService,
            PlayerPersistenceService playerPersistenceService,
            MaymiEventBus eventBus,
            ModDetectionService modDetectionService
    ) {

        Objects.requireNonNull(
                discordService,
                "DiscordService não pode ser nulo."
        );

        Objects.requireNonNull(
                playerPersistenceService,
                "PlayerPersistenceService não pode ser nulo."
        );

        Objects.requireNonNull(
                eventBus,
                "MaymiEventBus não pode ser nulo."
        );

        Objects.requireNonNull(
                modDetectionService,
                "ModDetectionService não pode ser nulo."
        );

        InteractionResponseService interactionResponseService =
                new InteractionResponseService();

        this.playerJoinHandler =
                new PlayerJoinHandler(
                        discordService,
                        playerPersistenceService
                );

        this.chatHandler =
                new ChatHandler(
                        discordService
                );

        this.playerQuitHandler =
                new PlayerQuitHandler(
                        discordService,
                        playerPersistenceService
                );

        this.playerDeathHandler =
                new PlayerDeathHandler(
                        discordService,
                        eventBus
                );

        this.listResponseHandler =
                new ListResponseHandler(
                        discordService,
                        interactionResponseService
                );

        this.tpsResponseHandler =
                new TpsResponseHandler(
                        discordService,
                        interactionResponseService
                );

        this.ramResponseHandler =
                new RamResponseHandler(
                        discordService,
                        interactionResponseService
                );

        this.timeResponseHandler =
                new TimeResponseHandler(
                        discordService,
                        interactionResponseService
                );

        this.dashboardResponseHandler =
                new DashboardResponseHandler(
                        interactionResponseService
                );

        this.playerInfoResponseHandler =
                new PlayerInfoResponseHandler(
                        interactionResponseService
                );

        this.mobKillHandler =
                new MobKillHandler(
                        eventBus
                );

        this.blockBreakHandler =
                new BlockBreakHandler(
                        eventBus,
                        modDetectionService
                );

        this.blockPlaceHandler =
                new BlockPlaceHandler(
                        eventBus,
                        modDetectionService
                );
    }

    /*
     * Construtor útil para testes.
     * Como os handlers já chegam prontos, não precisamos
     * montar dependências aqui.
     */
    public PacketDispatcher(
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

        this.playerJoinHandler =
                Objects.requireNonNull(
                        playerJoinHandler
                );

        this.chatHandler =
                Objects.requireNonNull(
                        chatHandler
                );

        this.playerQuitHandler =
                Objects.requireNonNull(
                        playerQuitHandler
                );

        this.playerDeathHandler =
                Objects.requireNonNull(
                        playerDeathHandler
                );

        this.listResponseHandler =
                Objects.requireNonNull(
                        listResponseHandler
                );

        this.tpsResponseHandler =
                Objects.requireNonNull(
                        tpsResponseHandler
                );

        this.ramResponseHandler =
                Objects.requireNonNull(
                        ramResponseHandler
                );

        this.timeResponseHandler =
                Objects.requireNonNull(
                        timeResponseHandler
                );

        this.dashboardResponseHandler =
                Objects.requireNonNull(
                        dashboardResponseHandler
                );

        this.playerInfoResponseHandler =
                Objects.requireNonNull(
                        playerInfoResponseHandler
                );

        this.mobKillHandler =
                Objects.requireNonNull(
                        mobKillHandler
                );

        this.blockPlaceHandler =
                Objects.requireNonNull(
                        blockPlaceHandler
                );

        this.blockBreakHandler =
                Objects.requireNonNull(
                        blockBreakHandler
                );
    }

    public void dispatch(
            Packet packet
    ) {

        Objects.requireNonNull(
                packet,
                "O pacote recebido não pode ser nulo."
        );

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

        } else if (
                packet instanceof DashboardResponsePacket dashboardPacket
        ) {

            dashboardResponseHandler.handle(
                    dashboardPacket
            );

        } else if (
                packet instanceof PlayerInfoResponsePacket playerInfoPacket
        ) {

            playerInfoResponseHandler.handle(
                    playerInfoPacket
            );

        } else if (
                packet instanceof MobKillPacket mobKillPacket
        ) {

            mobKillHandler.handle(
                    mobKillPacket
            );

        } else if (
                packet instanceof BlockPlacePacket blockPlacePacket
        ) {

            blockPlaceHandler.handle(
                    blockPlacePacket
            );

        } else if (
                packet instanceof BlockBreakPacket blockBreakPacket
        ) {

            blockBreakHandler.handle(
                    blockBreakPacket
            );

        } else {

            throw new UnknownPacketException(
                    "Nenhum handler encontrado para o pacote: "
                            + packet.getType()
            );
        }
    }
}