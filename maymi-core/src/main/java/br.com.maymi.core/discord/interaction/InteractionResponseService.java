package br.com.maymi.core.discord.interaction;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.Color;
import java.time.Instant;
import java.util.List;

public class InteractionResponseService {

    private static final Color MAYMI_COLOR =
            new Color(255, 105, 180);

    public boolean replyTps(
            String requestId,
            double tps
    ) {

        String status;

        if (tps >= 19.0) {
            status = "🟢 Excelente";
        } else if (tps >= 15.0) {
            status = "🟡 Atenção";
        } else {
            status = "🔴 Crítico";
        }

        EmbedBuilder embed =
                createBaseEmbed(
                        "📊 TPS do Servidor",
                        "Informações de desempenho do servidor Minecraft."
                );

        embed.addField(
                "TPS",
                String.format("%.2f", tps),
                true
        );

        embed.addField(
                "Status",
                status,
                true
        );

        return editInteraction(
                requestId,
                embed
        );

    }

    public boolean replyRam(
            String requestId,
            double usedMemory,
            double freeMemory,
            double maxMemory
    ) {

        double percentage =
                maxMemory <= 0
                        ? 0
                        : usedMemory / maxMemory * 100;

        String status;

        if (percentage < 70) {
            status = "🟢 Normal";
        } else if (percentage < 90) {
            status = "🟡 Atenção";
        } else {
            status = "🔴 Crítico";
        }

        EmbedBuilder embed =
                createBaseEmbed(
                        "🧠 Memória do Servidor",
                        "Uso atual da memória Java do servidor."
                );

        embed.addField(
                "Usada",
                String.format("%.2f GB", usedMemory),
                true
        );

        embed.addField(
                "Livre",
                String.format("%.2f GB", freeMemory),
                true
        );

        embed.addField(
                "Máxima",
                String.format("%.2f GB", maxMemory),
                true
        );

        embed.addField(
                "Uso",
                String.format("%.1f%%", percentage),
                true
        );

        embed.addField(
                "Status",
                status,
                true
        );

        return editInteraction(
                requestId,
                embed
        );

    }

    public boolean replyPlayers(
            String requestId,
            List<String> players
    ) {

        List<String> safePlayers =
                players == null
                        ? List.of()
                        : players;

        String description =
                safePlayers.isEmpty()
                        ? "Nenhum jogador está online."
                        : safePlayers.stream()
                        .map(player -> "• " + player)
                        .reduce(
                                (first, second) ->
                                first + "\n" + second
                        )
                        .orElse(
                                "Nenhum jogador está online."
                        );

        EmbedBuilder embed =
                createBaseEmbed(
                        "👥 Jogadores Online",
                        description
                );

        embed.addField(
                "Total",
                String.valueOf(safePlayers.size()),
                true
        );

        return editInteraction(
                requestId,
                embed
        );

    }

    public boolean replyTime(
            String requestId,
            String worldName,
            long day,
            long time
    ) {

        String formattedTime =
                formatMinecraftTime(time);

        EmbedBuilder embed =
                createBaseEmbed(
                        "🌍 Informações do Mundo",
                        "Informações atuais do mundo principal."
                );

        embed.addField(
                "Mundo",
                worldName == null
                        ? "Desconhecido"
                        : worldName,
                true
        );

        embed.addField(
                "Dia",
                String.valueOf(day),
                true
        );

        embed.addField(
                "Horário",
                formattedTime,
                true
        );

        return editInteraction(
                requestId,
                embed
        );

    }


    public boolean replyDashboard(
            String requestId,
            double tps,
            double mspt,
            double usedMemory,
            double maxMemory,
            int onlinePlayers,
            int maxPlayers,
            String worldName,
            long day,
            long time,
            long uptime
    ) {

        double memoryPercentage =
                maxMemory <= 0
                        ? 0
                        : usedMemory / maxMemory * 100;

        String serverStatus;

        if (tps >= 19.0 && mspt <= 50.0) {
            serverStatus = "🟢 Online e saudável";
        } else if (tps >= 15.0) {
            serverStatus = "🟡 Online com atenção";
        } else {
            serverStatus = "🔴 Desempenho crítico";
        }

        String formattedMinecraftTime =
                formatMinecraftTime(time);

        String formattedUptime =
                formatUptime(uptime);

        String safeWorldName =
                worldName == null || worldName.isBlank()
                        ? "Desconhecido"
                        : worldName;

        EmbedBuilder embed =
                createBaseEmbed(
                        "🌸 Maymi Dashboard",
                        serverStatus
                );

        embed.addField(
                "⚡ TPS",
                String.format("%.2f", tps),
                true
        );

        embed.addField(
                "⏱ MSPT",
                String.format("%.2f ms", mspt),
                true
        );

        embed.addField(
                "👥 Jogadores",
                onlinePlayers + "/" + maxPlayers,
                true
        );

        embed.addField(
                "🧠 Memória",
                String.format(
                        "%.2f / %.2f GB",
                        usedMemory,
                        maxMemory
                ),
                true
        );

        embed.addField(
                "📊 Uso da memória",
                String.format(
                        "%.1f%%",
                        memoryPercentage
                ),
                true
        );

        embed.addField(
                "🌍 Mundo",
                safeWorldName,
                true
        );

        embed.addField(
                "📅 Dia",
                String.valueOf(day),
                true
        );

        embed.addField(
                "🕒 Horário",
                formattedMinecraftTime,
                true
        );

        embed.addField(
                "⏳ Uptime",
                formattedUptime,
                true
        );

        return editInteraction(
                requestId,
                embed
        );
    }

    public boolean replyPlayerInfo(
            String requestId,
            boolean found,
            boolean online,
            String playerName,
            String uuid,
            double health,
            double maxHealth,
            int foodLevel,
            int experienceLevel,
            String worldName,
            double x,
            double y,
            double z,
            String gameMode,
            long sessionTime
    ) {

        String safePlayerName =
                playerName == null || playerName.isBlank()
                        ? "Desconhecido"
                        : playerName;

        if (!found) {

            EmbedBuilder embed =
                    createBaseEmbed(
                            "🔎 Jogador não encontrado",
                            "Nenhum jogador chamado `"
                                    + safePlayerName
                                    + "` foi encontrado no servidor."
                    );

            return editInteraction(
                    requestId,
                    embed
            );
        }

        String status =
                online
                        ? "🟢 Online"
                        : "⚫ Offline";

        EmbedBuilder embed =
                createBaseEmbed(
                        "🧑 Informações do jogador",
                        "**" + safePlayerName + "**\n" + status
                );

        embed.addField(
                "UUID",
                uuid == null || uuid.isBlank()
                        ? "Desconhecido"
                        : "`" + uuid + "`",
                false
        );

        if (online) {

            String safeWorldName =
                    worldName == null || worldName.isBlank()
                            ? "Desconhecido"
                            : worldName;

            String safeGameMode =
                    gameMode == null || gameMode.isBlank()
                            ? "Desconhecido"
                            : gameMode;

            embed.addField(
                    "❤️ Vida",
                    String.format(
                            "%.1f / %.1f",
                            health,
                            maxHealth
                    ),
                    true
            );

            embed.addField(
                    "🍗 Fome",
                    foodLevel + " / 20",
                    true
            );

            embed.addField(
                    "✨ Nível",
                    String.valueOf(
                            experienceLevel
                    ),
                    true
            );

            embed.addField(
                    "🌍 Mundo",
                    safeWorldName,
                    true
            );

            embed.addField(
                    "🎮 Modo de jogo",
                    safeGameMode,
                    true
            );

            embed.addField(
                    "📍 Posição",
                    String.format(
                            "X: %.1f\nY: %.1f\nZ: %.1f",
                            x,
                            y,
                            z
                    ),
                    true
            );

            embed.addField(
                    "⏳ Sessão atual",
                    formatUptime(sessionTime),
                    true
            );

        } else {

            embed.addField(
                    "Status",
                    "O jogador já entrou no servidor, "
                            + "mas não está online no momento.",
                    false
            );
        }

        return editInteraction(
                requestId,
                embed
        );
    }

    private boolean editInteraction(
            String requestId,
            EmbedBuilder embed
    ) {

        InteractionHook hook =
                PendingInteractionManager.remove(
                        requestId
                );

        if (hook == null) {
            return false;
        }

        hook.editOriginalEmbeds(
                        embed.build()
                )
                .queue(
                        success -> {
                        },

                        error -> System.err.println(
                                "Erro ao editar interação: "
                                        + error.getMessage()
                        )
                );

        return true;

    }

    private EmbedBuilder createBaseEmbed(
            String title,
            String description
    ) {

        EmbedBuilder embed =
                new EmbedBuilder();

        embed.setColor(MAYMI_COLOR);
        embed.setTitle(title);
        embed.setDescription(description);

        embed.setFooter(
                "Maymi • Minecraft Discord Companion"
        );

        embed.setTimestamp(
                Instant.now()
        );

        return embed;

    }

    private String formatUptime(
            long uptimeMilliseconds
    ) {

        if (uptimeMilliseconds < 0) {
            return "Desconhecido";
        }

        long totalSeconds =
                uptimeMilliseconds / 1000;

        long days =
                totalSeconds / 86400;

        long hours =
                totalSeconds % 86400 / 3600;

        long minutes =
                totalSeconds % 3600 / 60;

        long seconds =
                totalSeconds % 60;

        if (days > 0) {

            return String.format(
                    "%dd %02dh %02dm %02ds",
                    days,
                    hours,
                    minutes,
                    seconds
            );
        }

        if (hours > 0) {

            return String.format(
                    "%02dh %02dm %02ds",
                    hours,
                    minutes,
                    seconds
            );
        }

        return String.format(
                "%02dm %02ds",
                minutes,
                seconds
        );
    }

    private String formatMinecraftTime(
            long minecraftTime
    ) {

        long normalizedTime =
                Math.floorMod(
                        minecraftTime,
                        24000
                );

        long totalMinutes =
                ((normalizedTime + 6000) % 24000)
                        * 60
                        / 1000;

        long hours =
                totalMinutes / 60;

        long minutes =
                totalMinutes % 60;

        return String.format(
                "%02d:%02d",
                hours,
                minutes
        );

    }

}