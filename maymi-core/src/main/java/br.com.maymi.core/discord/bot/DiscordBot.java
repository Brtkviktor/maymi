package br.com.maymi.core.discord.bot;

import br.com.maymi.core.achievement.PlayerAchievementsService;
import br.com.maymi.core.configuration.ConfigurationManager;
import br.com.maymi.core.discord.DiscordManager;
import br.com.maymi.core.discord.dashboard.DiscordDashboardScheduler;
import br.com.maymi.core.discord.listener.DiscordMessageListener;
import br.com.maymi.core.discord.listener.SlashCommandListener;
import br.com.maymi.core.discord.profile.PlayerProfileRepository;
import br.com.maymi.core.shared.util.Console;
import br.com.maymi.core.socket.SocketClient;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot {

    private final PlayerProfileRepository playerProfileRepository;

    private DiscordDashboardScheduler dashboardScheduler;

    private final PlayerAchievementsService playerAchievementsService;


    private JDA jda;

    public DiscordBot(
            PlayerProfileRepository playerProfileRepository,
            PlayerAchievementsService playerAchievementsService
    ) {

        this.playerProfileRepository =
                playerProfileRepository;

        this.playerAchievementsService =
                playerAchievementsService;
    }

    public void start() {

        try {

            String token =
                    ConfigurationManager
                            .getDiscordToken();

            Console.info(
                    "Conectando ao Discord..."
            );

            // =====================================================
            // SOCKET CLIENT
            // =====================================================

            SocketClient socketClient =
                    new SocketClient();

            // =====================================================
            // DISCORD BOT
            // =====================================================

            jda =
                    JDABuilder
                            .createDefault(token)

                            .enableIntents(
                                    GatewayIntent.MESSAGE_CONTENT
                            )

                            .addEventListeners(
                                    new DiscordMessageListener(
                                            socketClient
                                    ),

                                    new SlashCommandListener(
                                            socketClient,
                                            playerProfileRepository,
                                            playerAchievementsService
                                    )
                            )

                            .build();

            // =====================================================
            // AGUARDA CONEXÃO
            // =====================================================

            jda.awaitReady();

            DiscordManager.setJda(
                    jda
            );

            // =====================================================
            // INFORMAÇÕES
            // =====================================================

            Console.success(
                    "Discord conectado!"
            );

            Console.info(
                    "Bot: "
                            + jda.getSelfUser()
                            .getAsTag()
            );

            Console.info(
                    "Servidores: "
                            + jda.getGuilds()
                            .size()
            );

            // =====================================================
            // DASHBOARD AUTOMÁTICO
            // =====================================================

            Console.info(
                    "Iniciando Dashboard automático..."
            );

            this.dashboardScheduler =
                    new DiscordDashboardScheduler(
                            jda,
                            playerProfileRepository
                    );

            this.dashboardScheduler.start();

            // =====================================================
            // REGISTRO DOS SLASH COMMANDS
            // =====================================================

            registerSlashCommands();

        } catch (InterruptedException exception) {

            Thread.currentThread().interrupt();

            Console.error(
                    "A inicialização do Discord foi interrompida."
            );

            exception.printStackTrace();

        } catch (Exception exception) {

            Console.error(
                    exception.getMessage()
            );

            exception.printStackTrace();
        }
    }

    private void registerSlashCommands() {

        var maymiCommand =
                Commands
                        .slash(
                                "maymi",
                                "Comandos de gerenciamento do servidor Minecraft"
                        )
                        .addSubcommands(

                                new SubcommandData(
                                        "help",
                                        "Mostra todos os comandos disponíveis"
                                ),

                                new SubcommandData(
                                        "list",
                                        "Mostra os jogadores online"
                                ),

                                new SubcommandData(
                                        "players",
                                        "Mostra os jogadores online"
                                ),

                                new SubcommandData(
                                        "tps",
                                        "Mostra o TPS do servidor"
                                ),

                                new SubcommandData(
                                        "ram",
                                        "Mostra o consumo de memória do servidor"
                                ),

                                new SubcommandData(
                                        "time",
                                        "Mostra o dia e o horário do mundo"
                                ),

                                new SubcommandData(
                                        "server",
                                        "Mostra o status do servidor"
                                ),

                                new SubcommandData(
                                        "dashboard",
                                        "Mostra o painel geral do servidor"
                                ),

                                new SubcommandData(
                                        "player",
                                        "Mostra informações detalhadas de um jogador"
                                )
                                        .addOption(
                                                OptionType.STRING,
                                                "jogador",
                                                "Nome do jogador no Minecraft",
                                                true
                                        ),

                                new SubcommandData(
                                        "profile",
                                        "Mostra o perfil, XP e estatísticas de um jogador"
                                )
                                        .addOption(
                                                OptionType.STRING,
                                                "jogador",
                                                "Nome do jogador no Minecraft",
                                                true
                                        ),

                                new SubcommandData(
                                        "stats",
                                        "Mostra as estatísticas detalhadas de um jogador"
                                )
                                        .addOption(
                                                OptionType.STRING,
                                                "jogador",
                                                "Nome do jogador no Minecraft",
                                                true
                                        ),

                                new SubcommandData(
                                        "achievements",
                                        "Mostra as conquistas de um jogador"
                                )
                                        .addOption(
                                                OptionType.STRING,
                                                "jogador",
                                                "Nome do jogador no Minecraft",
                                                true
                                        ),

                                new SubcommandData(
                                        "rank",
                                        "Mostra o ranking global de jogadores"
                                )
                        );

        if (jda.getGuilds().isEmpty()) {

            Console.error(
                    "A Maymi não está conectada a nenhum servidor do Discord."
            );

            return;
        }

        jda.getGuilds().forEach(
                guild -> guild
                        .updateCommands()
                        .addCommands(
                                maymiCommand
                        )
                        .queue(
                                commands -> Console.success(
                                        "Slash Commands registrados no servidor: "
                                                + guild.getName()
                                ),

                                error -> Console.error(
                                        "Erro ao registrar Slash Commands em "
                                                + guild.getName()
                                                + ": "
                                                + error.getMessage()
                                )
                        )
        );
    }
}