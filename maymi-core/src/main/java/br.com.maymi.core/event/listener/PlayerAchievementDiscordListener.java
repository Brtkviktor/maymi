package br.com.maymi.core.event.listener;

import br.com.maymi.core.discord.DiscordChannelManager;
import br.com.maymi.core.discord.embed.AchievementEmbedFactory;
import br.com.maymi.core.event.GameEventListener;
import br.com.maymi.core.event.player.MaymiAchievementUnlockEvent;

import java.util.Objects;

public final class PlayerAchievementDiscordListener
        implements GameEventListener<MaymiAchievementUnlockEvent> {

    private final DiscordChannelManager channelManager;

    public PlayerAchievementDiscordListener(
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
            MaymiAchievementUnlockEvent event
    ) {

        var channel =
                channelManager.getAchievementChannel();

        if (channel == null) {
            return;
        }

        channel.sendMessageEmbeds(
                AchievementEmbedFactory.create(
                        event.playerName(),
                        event.achievement()
                )
        ).queue();
    }
}