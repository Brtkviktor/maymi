package br.com.maymi.core.discord.listener;

import br.com.maymi.common.network.packet.CommandPacket;
import br.com.maymi.core.socket.SocketClient;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import br.com.maymi.core.discord.interaction.PendingInteractionManager;
import br.com.maymi.common.network.packet.PlayerInfoRequestPacket;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import br.com.maymi.core.discord.profile.PlayerProfileEmbedFactory;
import br.com.maymi.core.discord.profile.PlayerProfileRepository;
import br.com.maymi.core.discord.profile.PlayerRankingEmbedFactory;
import br.com.maymi.core.discord.embed.PlayerStatsEmbedFactory;
import br.com.maymi.core.achievement.PlayerAchievementsService;
import br.com.maymi.core.discord.embed.PlayerAchievementsEmbedFactory;

import java.util.Objects;
import java.util.UUID;

import java.awt.Color;
import java.time.Instant;

public class SlashCommandListener extends ListenerAdapter {

    private static final Color MAYMI_COLOR =
            new Color(255, 105, 180);

    private final SocketClient socketClient;

    private final PlayerProfileRepository playerProfileRepository;
    private final PlayerAchievementsService playerAchievementsService;

    public SlashCommandListener(
            SocketClient socketClient,
            PlayerProfileRepository playerProfileRepository,
            PlayerAchievementsService playerAchievementsService
    ) {

        this.socketClient =
                Objects.requireNonNull(
                        socketClient
                );

        this.playerProfileRepository =
                Objects.requireNonNull(
                        playerProfileRepository
                );

        this.playerAchievementsService =
                Objects.requireNonNull(
                        playerAchievementsService
                );
    }

    @Override
    public void onSlashCommandInteraction(
            SlashCommandInteractionEvent event
    ) {

        if (!event.getName().equals("maymi")) {
            return;
        }

        String subcommand =
                event.getSubcommandName();

        if (subcommand == null) {

            event.reply(
                            "Subcomando inválido."
                    )
                    .setEphemeral(true)
                    .queue();

            return;
        }

        switch (subcommand) {

            case "help" ->
                    sendHelpEmbed(event);


            case "server" ->
                    sendServerEmbed(event);

            case "list",
                 "players",
                 "tps",
                 "ram",
                 "time",
                 "dashboard" ->
                    sendMinecraftCommand(
                            event,
                            subcommand
                    );

            case "player" ->
                    sendPlayerInfoRequest(event);

            case "profile" ->
                    sendPlayerProfile(event);

            case "rank" ->
                    sendRanking(event);

            case "stats" ->
                    sendPlayerStats(event);

            case "achievements" ->
                    sendPlayerAchievements(
                            event
                    );

            default ->
                    event.reply(
                                    "Comando não reconhecido."
                            )
                            .setEphemeral(true)
                            .queue();
        }
    }

    private void sendHelpEmbed(
            SlashCommandInteractionEvent event
    ) {


        EmbedBuilder embed =
                new EmbedBuilder();

        embed.setColor(MAYMI_COLOR);

        embed.setTitle(
                "🌸 Maymi Commands"
        );

        embed.setDescription(
                "Comandos disponíveis para consultar e gerenciar o servidor Minecraft."
        );

        embed.addField(
                "📖 Ajuda",
                "`/maymi help`\nMostra todos os comandos.",
                false
        );

        embed.addField(
                "👥 Jogadores",
                """
                `/maymi list`
                `/maymi players`
                Mostra os jogadores online.
                """,
                false
        );

        embed.addField(
                "🧑 Jogador",
                """
                `/maymi player jogador:<nick>`
                Mostra informações detalhadas de um jogador.
                """,
                false
        );

        embed.addField(
                "📊 Desempenho",
                """
                `/maymi tps`
                Mostra o TPS do servidor.

                `/maymi ram`
                Mostra o consumo de memória.
                """,
                false
        );

        embed.addField(
                "🌍 Mundo",
                """
                `/maymi time`
                Mostra o dia e o horário do mundo.
                """,
                false
        );

        embed.addField(
                "🖥️ Servidor",
                """
                `/maymi server`
                Mostra o status geral do servidor.
                """,
                false
        );

        embed.addField(
                "📋 Dashboard",
                """
                `/maymi dashboard`
                Mostra uma visão geral do servidor em tempo real.
                """,
                false
        );

        embed.addField(
                "📊 Estatísticas",
                """
                `/maymi stats jogador:<nick>`
                Mostra combate, construção, atividade e histórico.
                """,
                false
        );

        embed.setFooter(
                "Maymi • Minecraft Discord Companion"
        );

        embed.setTimestamp(
                Instant.now()
        );

        event.replyEmbeds(
                        embed.build()
                )
                .queue();
    }

    private void sendServerEmbed(
            SlashCommandInteractionEvent event
    ) {

        EmbedBuilder embed =
                new EmbedBuilder();

        embed.setColor(MAYMI_COLOR);

        embed.setTitle(
                "🌸 Maymi Server"
        );

        embed.setDescription(
                "Informações gerais da conexão com o servidor Minecraft."
        );

        embed.addField(
                "Status",
                "🟢 Online",
                true
        );

        embed.addField(
                "Conexão",
                "Discord ↔ Minecraft",
                true
        );

        embed.addField(
                "Comandos úteis",
                """
                `/maymi dashboard`
                `/maymi tps`
                `/maymi ram`
                `/maymi time`
                `/maymi players`
                """,
                false
        );

        embed.setFooter(
                "Maymi • Servidor conectado"
        );

        embed.addField(
                "🌸 Perfil",
                """
                `/maymi profile jogador:<nick>`
                Mostra nível, XP, ranking e estatísticas permanentes.
                """,
                false
        );

        embed.setTimestamp(
                Instant.now()
        );

        event.replyEmbeds(
                        embed.build()
                )
                .queue();
    }

    private void sendMinecraftCommand(
            SlashCommandInteractionEvent event,
            String subcommand
    ) {

        String requestId =
                UUID.randomUUID().toString();

        event.deferReply().queue(
                hook -> {

                    PendingInteractionManager.register(
                            requestId,
                            hook
                    );

                    socketClient.send(
                            new CommandPacket(
                                    "/" + subcommand,
                                    requestId
                            )
                    );

                    hook.editOriginal(
                                    "🌸 Consultando o servidor Minecraft..."
                            )
                            .queue();

                },

                error -> {

                    System.err.println(
                            "Erro ao iniciar Slash Command: "
                                    + error.getMessage()
                    );

                }
        );

    }

    private void sendPlayerInfoRequest(
            SlashCommandInteractionEvent event
    ) {

        OptionMapping playerOption =
                event.getOption("jogador");

        if (playerOption == null) {

            event.reply(
                            "Informe o nome do jogador."
                    )
                    .setEphemeral(true)
                    .queue();

            return;
        }

        String playerName =
                playerOption
                        .getAsString()
                        .trim();

        if (playerName.isBlank()) {

            event.reply(
                            "O nome do jogador não pode estar vazio."
                    )
                    .setEphemeral(true)
                    .queue();

            return;
        }

        String requestId =
                UUID.randomUUID().toString();

        event.deferReply().queue(

                hook -> {

                    PendingInteractionManager.register(
                            requestId,
                            hook
                    );

                    socketClient.send(
                            new PlayerInfoRequestPacket(
                                    playerName,
                                    requestId
                            )
                    );

                    hook.editOriginal(
                                    "🌸 Procurando informações de `"
                                            + playerName
                                            + "`..."
                            )
                            .queue();

                },

                error -> {

                    System.err.println(
                            "Erro ao consultar jogador: "
                                    + error.getMessage()
                    );

                }
        );
    }
    private void sendPlayerProfile(
            SlashCommandInteractionEvent event
    ) {

        OptionMapping playerOption =
                event.getOption("jogador");

        if (playerOption == null) {

            event.reply(
                            "Informe o nome do jogador."
                    )
                    .setEphemeral(true)
                    .queue();

            return;
        }

        String playerName =
                playerOption
                        .getAsString()
                        .trim();

        if (playerName.isBlank()) {

            event.reply(
                            "O nome do jogador não pode estar vazio."
                    )
                    .setEphemeral(true)
                    .queue();

            return;
        }

        event.deferReply().queue(
                hook -> {

                    try {

                        var profile =
                                playerProfileRepository
                                        .findByNickname(
                                                playerName
                                        );

                        if (profile.isEmpty()) {

                            hook.editOriginal(
                                            "🔎 Nenhum jogador chamado `"
                                                    + playerName
                                                    + "` foi encontrado."
                                    )
                                    .queue();

                            return;
                        }

                        hook.editOriginalEmbeds(
                                        PlayerProfileEmbedFactory.create(
                                                profile.get()
                                        )
                                )
                                .queue();

                    } catch (Exception exception) {

                        exception.printStackTrace();

                        hook.editOriginal(
                                        "❌ Não foi possível consultar o perfil agora."
                                )
                                .queue();
                    }
                },

                error -> System.err.println(
                        "Erro ao iniciar consulta de perfil: "
                                + error.getMessage()
                )
        );
    }

    private void sendRanking(
            SlashCommandInteractionEvent event
    ) {

        event.deferReply().queue(
                hook -> {

                    try {

                        var ranking =
                                playerProfileRepository
                                        .findTopRanking(
                                                10
                                        );

                        hook.editOriginalEmbeds(
                                        PlayerRankingEmbedFactory.create(
                                                ranking
                                        )
                                )
                                .queue();

                    } catch (Exception exception) {

                        exception.printStackTrace();

                        hook.editOriginal(
                                        "❌ Não foi possível consultar o ranking agora."
                                )
                                .queue();
                    }
                }
        );
    }

    private void sendPlayerStats(
            SlashCommandInteractionEvent event
    ) {

        OptionMapping playerOption =
                event.getOption(
                        "jogador"
                );

        if (playerOption == null) {

            event.reply(
                            "Informe o nome do jogador."
                    )
                    .setEphemeral(true)
                    .queue();

            return;
        }

        String playerName =
                playerOption
                        .getAsString()
                        .trim();

        if (playerName.isBlank()) {

            event.reply(
                            "O nome do jogador não pode estar vazio."
                    )
                    .setEphemeral(true)
                    .queue();

            return;
        }

        event.deferReply().queue(
                hook -> {

                    try {

                        var profile =
                                playerProfileRepository
                                        .findByNickname(
                                                playerName
                                        );

                        if (profile.isEmpty()) {

                            hook.editOriginal(
                                            "🔎 Nenhum jogador chamado `"
                                                    + playerName
                                                    + "` foi encontrado."
                                    )
                                    .queue();

                            return;
                        }

                        hook.editOriginalEmbeds(
                                        PlayerStatsEmbedFactory.create(
                                                profile.get()
                                        )
                                )
                                .queue();

                    } catch (Exception exception) {

                        exception.printStackTrace();

                        hook.editOriginal(
                                        "❌ Não foi possível consultar as estatísticas agora."
                                )
                                .queue();
                    }
                }
        );
    }

    private void sendPlayerAchievements(
            SlashCommandInteractionEvent event
    ) {

        OptionMapping playerOption =
                event.getOption(
                        "jogador"
                );

        if (playerOption == null) {

            event.reply(
                            "Informe o nome do jogador."
                    )
                    .setEphemeral(true)
                    .queue();

            return;
        }

        String playerName =
                playerOption
                        .getAsString()
                        .trim();

        event.deferReply().queue(
                hook -> {

                    try {

                        var achievements =
                                playerAchievementsService
                                        .findByNickname(
                                                playerName
                                        );

                        hook.editOriginalEmbeds(
                                        PlayerAchievementsEmbedFactory.create(
                                                playerName,
                                                achievements
                                        )
                                )
                                .queue();

                    } catch (IllegalArgumentException exception) {

                        hook.editOriginal(
                                        "🔎 "
                                                + exception.getMessage()
                                )
                                .queue();

                    } catch (Exception exception) {

                        exception.printStackTrace();

                        hook.editOriginal(
                                        "❌ Não foi possível consultar as conquistas."
                                )
                                .queue();
                    }
                }
        );
    }
}
