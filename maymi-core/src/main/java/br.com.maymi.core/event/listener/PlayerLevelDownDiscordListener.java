package br.com.maymi.core.event.listener;

import br.com.maymi.core.discord.DiscordChannelManager;
import br.com.maymi.core.discord.embed.LevelDownEmbedFactory;
import br.com.maymi.core.event.GameEventListener;
import br.com.maymi.core.event.player.MaymiLevelDownEvent;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.Objects;

public final class PlayerLevelDownDiscordListener
        implements GameEventListener<MaymiLevelDownEvent> {

    private final DiscordChannelManager channelManager;

    public PlayerLevelDownDiscordListener(
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
            MaymiLevelDownEvent event
    ) {

        TextChannel channel =
                channelManager.getLevelLogsChannel();

        if (channel == null) {
            return;
        }

        channel.sendMessageEmbeds(
                LevelDownEmbedFactory.create(
                        event.playerName(),
                        event.previousLevel(),
                        event.currentLevel(),
                        event.currentXp()
                )
        ).queue();
    }
}