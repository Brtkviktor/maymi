package br.com.maymi.core.discord.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface MaymiCommand {

    void execute(
            MessageReceivedEvent event
    );

}