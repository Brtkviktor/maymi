package br.com.maymi.core.event.listener;

import br.com.maymi.core.discord.DiscordChannelManager;
import br.com.maymi.core.discord.embed.LevelUpEmbedFactory;
import br.com.maymi.core.event.GameEventListener;
import br.com.maymi.core.event.player.MaymiLevelUpEvent;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.Objects;

public final class PlayerLevelUpDiscordListener
        implements GameEventListener<MaymiLevelUpEvent> {

    private final DiscordChannelManager channelManager;

    public PlayerLevelUpDiscordListener(
            DiscordChannelManager channelManager
    ) {
        this.channelManager =
                Objects.requireNonNull(
                        channelManager,
                        "DiscordChannelManager não pode ser nulo."
                );
    }

    @Override
    public void onEvent(
            MaymiLevelUpEvent event
    ) {

        TextChannel channel =
                channelManager.getLevelLogsChannel();

        if (channel == null) {
            return;
        }

        channel.sendMessageEmbeds(
                LevelUpEmbedFactory.create(
                        event.playerName(),
                        event.previousLevel(),
                        event.currentLevel(),
                        event.currentXp()
                )
        ).queue();
    }
}