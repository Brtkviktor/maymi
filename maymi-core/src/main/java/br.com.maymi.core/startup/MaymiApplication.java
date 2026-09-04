package br.com.maymi.core.startup;

import br.com.maymi.core.achievement.AchievementCatalogService;
import br.com.maymi.core.achievement.AchievementRepository;
import br.com.maymi.core.achievement.AchievementUnlockService;
import br.com.maymi.core.achievement.PlayerAchievementRepository;
import br.com.maymi.core.achievement.PlayerAchievementsService;

import br.com.maymi.core.discord.DiscordChannelManager;
import br.com.maymi.core.discord.DiscordService;
import br.com.maymi.core.discord.bot.DiscordBot;
import br.com.maymi.core.discord.profile.PlayerProfileRepository;

import br.com.maymi.core.event.MaymiEventBus;

import br.com.maymi.core.event.listener.PlayerAchievementDiscordListener;
import br.com.maymi.core.event.listener.PlayerAchievementHistoryListener;
import br.com.maymi.core.event.listener.PlayerBlockBreakStatisticsListener;
import br.com.maymi.core.event.listener.PlayerBlockPlaceStatisticsListener;
import br.com.maymi.core.event.listener.PlayerDeathHistoryListener;
import br.com.maymi.core.event.listener.PlayerDeathStatisticsListener;
import br.com.maymi.core.event.listener.PlayerLevelDownDiscordListener;
import br.com.maymi.core.event.listener.PlayerLevelDownHistoryListener;
import br.com.maymi.core.event.listener.PlayerLevelUpDiscordListener;
import br.com.maymi.core.event.listener.PlayerLevelUpHistoryListener;
import br.com.maymi.core.event.listener.PlayerMobKillStatisticsListener;

import br.com.maymi.core.event.player.MaymiAchievementUnlockEvent;
import br.com.maymi.core.event.player.MaymiBlockBreakEvent;
import br.com.maymi.core.event.player.MaymiBlockPlaceEvent;
import br.com.maymi.core.event.player.MaymiLevelDownEvent;
import br.com.maymi.core.event.player.MaymiLevelUpEvent;
import br.com.maymi.core.event.player.MaymiMobKillEvent;
import br.com.maymi.core.event.player.MaymiPlayerDeathEvent;

import br.com.maymi.core.history.PlayerHistoryRepository;
import br.com.maymi.core.history.PlayerHistoryService;

import br.com.maymi.core.event.listener.create.CreateStatisticsListener;
import br.com.maymi.core.event.mod.create.MaymiCreateBlockEvent;
import br.com.maymi.core.mod.ModDetectionService;
import br.com.maymi.core.mod.create.CreateBlockClassifier;
import br.com.maymi.core.mod.create.CreateIntegration;
import br.com.maymi.core.mod.create.persistence.CreateStatisticsRepository;

import br.com.maymi.core.network.dispatcher.PacketDispatcher;

import br.com.maymi.core.persistence.repository.PlayerRepository;
import br.com.maymi.core.persistence.repository.PlayerStatisticsRepository;
import br.com.maymi.core.persistence.service.PlayerPersistenceService;
import br.com.maymi.core.persistence.service.PlayerStatisticsService;

import br.com.maymi.core.progression.PlayerProgressionService;
import br.com.maymi.core.progression.PlayerXpService;
import br.com.maymi.core.progression.policy.DeathXpPenaltyPolicy;

import br.com.maymi.core.socket.SocketServer;

public final class MaymiApplication {

    private MaymiApplication() {
    }

    public static void start() {

        printBanner();

        // =====================================================
        // REPOSITORIES
        // =====================================================

        PlayerRepository playerRepository =
                new PlayerRepository();

        PlayerStatisticsRepository statisticsRepository =
                new PlayerStatisticsRepository();

        PlayerProfileRepository playerProfileRepository =
                new PlayerProfileRepository();

        AchievementRepository achievementRepository =
                new AchievementRepository();

        PlayerAchievementRepository playerAchievementRepository =
                new PlayerAchievementRepository();

        PlayerHistoryRepository playerHistoryRepository =
                new PlayerHistoryRepository();

        // =====================================================
        // ACHIEVEMENT CATALOG
        // =====================================================

        AchievementCatalogService achievementCatalogService =
                new AchievementCatalogService(
                        achievementRepository
                );

        achievementCatalogService.synchronizeRegistry();

        // =====================================================
        // HISTORY
        // =====================================================

        PlayerHistoryService playerHistoryService =
                new PlayerHistoryService(
                        playerHistoryRepository
                );

        // =====================================================
// EVENT BUS
// =====================================================

        MaymiEventBus eventBus =
                new MaymiEventBus();

        // =====================================================
// MOD INTEGRATIONS
// =====================================================

        CreateBlockClassifier createBlockClassifier =
                new CreateBlockClassifier();

        CreateIntegration createIntegration =
                new CreateIntegration(
                        createBlockClassifier,
                        eventBus
                );

// =====================================================
// MOD DETECTION
// =====================================================

        ModDetectionService modDetectionService =
                new ModDetectionService(
                        createIntegration
                );

// =====================================================
// CREATE LISTENERS
// =====================================================

        CreateStatisticsRepository createStatisticsRepository =
                new CreateStatisticsRepository();

        CreateStatisticsListener createStatisticsListener =
                new CreateStatisticsListener(
                        createStatisticsRepository
                );

        eventBus.register(
                MaymiCreateBlockEvent.class,
                createStatisticsListener::handle
        );

        // =====================================================
        // XP AND ACHIEVEMENTS
        // =====================================================

        PlayerXpService playerXpService =
                new PlayerXpService(
                        statisticsRepository
                );

        AchievementUnlockService achievementUnlockService =
                new AchievementUnlockService(
                        playerProfileRepository,
                        playerAchievementRepository,
                        playerXpService,
                        eventBus
                );

        PlayerAchievementsService playerAchievementsService =
                new PlayerAchievementsService(
                        playerProfileRepository,
                        playerAchievementRepository
                );

        // =====================================================
        // PERSISTENCE SERVICES
        // =====================================================

        PlayerStatisticsService playerStatisticsService =
                new PlayerStatisticsService(
                        statisticsRepository
                );

        PlayerPersistenceService playerPersistenceService =
                new PlayerPersistenceService(
                        playerRepository,
                        statisticsRepository,
                        playerStatisticsService,
                        achievementUnlockService,
                        playerHistoryService
                );

        // =====================================================
        // PROGRESSION
        // =====================================================

        DeathXpPenaltyPolicy deathXpPenaltyPolicy =
                new DeathXpPenaltyPolicy();

        PlayerProgressionService progressionService =
                new PlayerProgressionService(
                        playerXpService,
                        eventBus,
                        achievementUnlockService
                );

        // =====================================================
        // GAMEPLAY LISTENERS
        // =====================================================

        PlayerDeathStatisticsListener deathStatisticsListener =
                new PlayerDeathStatisticsListener(
                        playerStatisticsService,
                        progressionService,
                        deathXpPenaltyPolicy
                );

        PlayerMobKillStatisticsListener mobKillStatisticsListener =
                new PlayerMobKillStatisticsListener(
                        playerStatisticsService,
                        progressionService,
                        achievementUnlockService
                );

        PlayerBlockBreakStatisticsListener blockBreakListener =
                new PlayerBlockBreakStatisticsListener(
                        playerStatisticsService,
                        progressionService,
                        achievementUnlockService
                );

        PlayerBlockPlaceStatisticsListener blockPlaceStatisticsListener =
                new PlayerBlockPlaceStatisticsListener(
                        playerStatisticsService,
                        progressionService,
                        achievementUnlockService
                );

        eventBus.register(
                MaymiPlayerDeathEvent.class,
                deathStatisticsListener
        );

        eventBus.register(
                MaymiMobKillEvent.class,
                mobKillStatisticsListener
        );

        eventBus.register(
                MaymiBlockBreakEvent.class,
                blockBreakListener
        );

        eventBus.register(
                MaymiBlockPlaceEvent.class,
                blockPlaceStatisticsListener
        );

        // =====================================================
        // HISTORY LISTENERS
        // =====================================================

        PlayerLevelUpHistoryListener levelUpHistoryListener =
                new PlayerLevelUpHistoryListener(
                        playerHistoryService
                );

        PlayerLevelDownHistoryListener levelDownHistoryListener =
                new PlayerLevelDownHistoryListener(
                        playerHistoryService
                );

        PlayerAchievementHistoryListener achievementHistoryListener =
                new PlayerAchievementHistoryListener(
                        playerHistoryService
                );

        PlayerDeathHistoryListener deathHistoryListener =
                new PlayerDeathHistoryListener(
                        playerHistoryService
                );

        eventBus.register(
                MaymiLevelUpEvent.class,
                levelUpHistoryListener
        );

        eventBus.register(
                MaymiLevelDownEvent.class,
                levelDownHistoryListener
        );

        eventBus.register(
                MaymiAchievementUnlockEvent.class,
                achievementHistoryListener
        );

        eventBus.register(
                MaymiPlayerDeathEvent.class,
                deathHistoryListener
        );

        // =====================================================
        // DISCORD BOT
        // =====================================================

        DiscordBot bot =
                new DiscordBot(
                        playerProfileRepository,
                        playerAchievementsService
                );

        bot.start();

        // =====================================================
        // DISCORD SERVICES
        // =====================================================

        DiscordChannelManager channelManager =
                new DiscordChannelManager();

        DiscordService discordService =
                new DiscordService(
                        channelManager
                );

        // =====================================================
        // DISCORD EVENT LISTENERS
        // =====================================================

        PlayerLevelUpDiscordListener levelUpListener =
                new PlayerLevelUpDiscordListener(
                        channelManager
                );

        PlayerLevelDownDiscordListener levelDownListener =
                new PlayerLevelDownDiscordListener(
                        channelManager
                );

        PlayerAchievementDiscordListener achievementListener =
                new PlayerAchievementDiscordListener(
                        channelManager
                );

        eventBus.register(
                MaymiAchievementUnlockEvent.class,
                achievementListener
        );

        eventBus.register(
                MaymiLevelUpEvent.class,
                levelUpListener
        );

        eventBus.register(
                MaymiLevelDownEvent.class,
                levelDownListener
        );

        // =====================================================
        // PACKET DISPATCHER
        // =====================================================

        PacketDispatcher dispatcher =
                new PacketDispatcher(
                        discordService,
                        playerPersistenceService,
                        eventBus,
                        modDetectionService
                );

        // =====================================================
        // SOCKET SERVER
        // =====================================================

        SocketServer socketServer =
                new SocketServer(
                        dispatcher
                );

        Thread socketThread =
                new Thread(
                        socketServer::start,
                        "maymi-socket-server"
                );

        socketThread.start();
    }

    private static void printBanner() {

        System.out.println("""
                 __  __
                |  \\/  |
                | \\  / | __ _ _   _ _ __ ___  _ __
                | |\\/| |/ _` | | | | '_ ` _ \\| '_ \\
                | |  | | (_| | |_| | | | | | | |
                |_|  |_|\\__,_|\\__, |_| |_| |_|_| |_|
                               __/ |
                              |___/

                """);

        System.out.println(
                "================================="
        );

        System.out.println(
                "MAYMI v0.3.0"
        );

        System.out.println(
                "================================="
        );
    }
}