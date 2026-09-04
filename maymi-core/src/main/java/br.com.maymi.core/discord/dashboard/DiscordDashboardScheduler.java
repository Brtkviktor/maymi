package br.com.maymi.core.discord.dashboard;

import br.com.maymi.core.configuration.ConfigurationManager;
import br.com.maymi.core.discord.profile.PlayerProfileRepository;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class DiscordDashboardScheduler {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    DiscordDashboardScheduler.class
            );

    private final JDA jda;
    private final PlayerProfileRepository repository;

    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor(
                    runnable -> {

                        Thread thread =
                                new Thread(
                                        runnable,
                                        "maymi-dashboard-scheduler"
                                );

                        thread.setDaemon(true);

                        return thread;
                    }
            );

    private volatile String dashboardMessageId;

    public DiscordDashboardScheduler(
            JDA jda,
            PlayerProfileRepository repository
    ) {

        this.jda =
                Objects.requireNonNull(
                        jda,
                        "JDA não pode ser nulo."
                );

        this.repository =
                Objects.requireNonNull(
                        repository,
                        "PlayerProfileRepository não pode ser nulo."
                );

        this.dashboardMessageId =
                ConfigurationManager
                        .getDashboardMessageId();
    }

    public void start() {

        long intervalSeconds =
                Math.max(
                        10,
                        ConfigurationManager
                                .getDashboardUpdateSeconds()
                );

        LOGGER.info(
                "Dashboard agendado para atualizar a cada {} segundo(s).",
                intervalSeconds
        );

        scheduler.scheduleAtFixedRate(
                this::updateSafely,
                0,
                intervalSeconds,
                TimeUnit.SECONDS
        );
    }

    public void stop() {
        scheduler.shutdownNow();
    }

    private void updateSafely() {

        try {
            updateDashboard();

        } catch (Exception exception) {

            LOGGER.error(
                    "Erro ao atualizar o dashboard do Discord.",
                    exception
            );
        }
    }

    private void updateDashboard() {

        String channelId =
                ConfigurationManager
                        .getDashboardChannel();

        if (channelId == null || channelId.isBlank()) {

            LOGGER.warn(
                    "DISCORD_CHANNEL_DASHBOARD não foi configurado."
            );

            return;
        }

        TextChannel channel =
                jda.getTextChannelById(
                        channelId
                );

        if (channel == null) {

            LOGGER.warn(
                    "Canal do dashboard não encontrado: {}",
                    channelId
            );

            return;
        }

        var ranking =
                repository.findTopRanking(
                        5
                );

        var statistics =
                repository.findGlobalStatistics();

        var embed =
                GlobalDashboardEmbedFactory.create(
                        ranking,
                        statistics
                );

        if (
                dashboardMessageId == null
                        || dashboardMessageId.isBlank()
        ) {

            createDashboardMessage(
                    channel,
                    embed
            );

            return;
        }

        channel
                .retrieveMessageById(
                        dashboardMessageId
                )
                .queue(
                        message ->
                                message
                                        .editMessageEmbeds(
                                                embed
                                        )
                                        .queue(
                                                ignored ->
                                                        LOGGER.debug(
                                                                "Dashboard atualizado."
                                                        ),

                                                error -> {

                                                    LOGGER.warn(
                                                            "Não foi possível editar a mensagem {}. "
                                                                    + "Uma nova mensagem será criada.",
                                                            dashboardMessageId
                                                    );

                                                    dashboardMessageId = "";

                                                    createDashboardMessage(
                                                            channel,
                                                            embed
                                                    );
                                                }
                                        ),

                        error -> {

                            LOGGER.warn(
                                    "Mensagem do dashboard não encontrada. "
                                            + "Uma nova será criada."
                            );

                            dashboardMessageId = "";

                            createDashboardMessage(
                                    channel,
                                    embed
                            );
                        }
                );
    }

    private void createDashboardMessage(
            TextChannel channel,
            net.dv8tion.jda.api.entities.MessageEmbed embed
    ) {

        channel
                .sendMessageEmbeds(
                        embed
                )
                .queue(
                        message -> {

                            dashboardMessageId =
                                    message.getId();

                            LOGGER.info(
                                    "Dashboard criado com sucesso."
                            );

                            LOGGER.info(
                                    "Adicione ao .env: "
                                            + "DISCORD_DASHBOARD_MESSAGE_ID={}",
                                    dashboardMessageId
                            );
                        },

                        error ->
                                LOGGER.error(
                                        "Não foi possível criar a mensagem do dashboard.",
                                        error
                                )
                );
    }
}