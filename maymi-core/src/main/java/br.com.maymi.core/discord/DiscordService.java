package br.com.maymi.core.discord;

import br.com.maymi.common.network.packet.ChatPacket;
import br.com.maymi.common.network.packet.DeathPacket;
import br.com.maymi.common.network.packet.ListResponsePacket;
import br.com.maymi.common.network.packet.PlayerJoinPacket;
import br.com.maymi.common.network.packet.PlayerQuitPacket;
import br.com.maymi.common.network.packet.RamResponsePacket;
import br.com.maymi.common.network.packet.TimeResponsePacket;
import br.com.maymi.common.network.packet.TpsResponsePacket;
import br.com.maymi.core.configuration.ConfigurationManager;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import br.com.maymi.core.discord.DiscordService;


public class DiscordService {

    public void sendPlayerJoin(PlayerJoinPacket packet) {

        TextChannel channel =
                DiscordManager.getJda()
                        .getTextChannelById("1523470172420309102");

        if (channel == null) {

            System.out.println("Canal do Discord não encontrado.");
            return;

        }

        channel.sendMessage(
                "🟢 " + packet.getPlayerName() + " entrou no servidor!"
        ).queue();
    }

    public void sendChat(ChatPacket packet) {

        TextChannel channel =
                DiscordManager.getJda()
                        .getTextChannelById("1525925443860168817");

        if (channel == null) {
            return;
        }

        channel.sendMessage(
                "💬 **" + packet.getPlayerName() + "**: " +
                        packet.getMessage()
        ).queue();

    }

    public void sendPlayerQuit(PlayerQuitPacket packet) {

        TextChannel channel =
                DiscordManager.getJda()
                        .getTextChannelById("1523470172420309102");

        if (channel == null) {
            return;
        }

        channel.sendMessage(
                "🔴 " + packet.getPlayerName() + " saiu do servidor!"
        ).queue();

    }

    public void sendDeath(DeathPacket packet) {

        TextChannel channel =
                DiscordManager.getJda()
                        .getTextChannelById("1526065241962844160");

        if (channel == null) {
            return;
        }

        channel.sendMessage(
                "☠️ " + packet.getDeathMessage()
        ).queue();

    }

    public void sendPlayerList(ListResponsePacket packet) {

        TextChannel channel =
                DiscordManager.getJda()
                        .getTextChannelById("1526066012183593033");

        if (channel == null) {
            return;
        }

        StringBuilder message = new StringBuilder();

        message.append("👥 **Jogadores Online (")
                .append(packet.getPlayers().size())
                .append(")**\n\n");

        for (String player : packet.getPlayers()) {

            message.append("• ")
                    .append(player)
                    .append("\n");

        }

        channel.sendMessage(message.toString()).queue();

    }

    public void sendTps(TpsResponsePacket packet) {

        TextChannel channel =
                DiscordManager.getJda()
                        .getTextChannelById("1526066012183593033");

        if (channel == null) {
            return;
        }

        channel.sendMessage("""
            📊 **TPS DO SERVIDOR**

            TPS: %.2f
            """.formatted(packet.getTps()))
                .queue();

    }

    public void sendRam(RamResponsePacket packet) {

        TextChannel channel =
                DiscordManager.getJda()
                        .getTextChannelById("1526066012183593033");

        if (channel == null) {
            return;
        }

        channel.sendMessage("""
            🖥 **MEMÓRIA DO SERVIDOR**

            Usada : %.2f GB
            Livre : %.2f GB
            Máxima: %.2f GB
            """.formatted(
                packet.getUsedMemory(),
                packet.getFreeMemory(),
                packet.getMaxMemory()
        )).queue();

    }

    public void sendTime(TimeResponsePacket packet) {

        TextChannel channel =
                DiscordManager.getJda()
                        .getTextChannelById(
                                "1526066012183593033"
                        );

        if (channel == null) {
            return;
        }

        long time = packet.getTime();

        long hours =
                ((time / 1000) + 6) % 24;

        long minutes =
                (time % 1000) * 60 / 1000;

        channel.sendMessage("""
            🌍 **INFORMAÇÕES DO MUNDO**

            Mundo: **%s**
            Dia: **%d**
            Horário: **%02d:%02d**
            """.formatted(
                packet.getWorldName(),
                packet.getDay(),
                hours,
                minutes
        )).queue();

    }


}
