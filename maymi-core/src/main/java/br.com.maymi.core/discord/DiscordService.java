package br.com.maymi.core.discord;

import br.com.maymi.common.network.packet.PlayerJoinPacket;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

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

}
