package br.com.maymi.core.discord.command.impl;

import br.com.maymi.core.discord.command.MaymiCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand
        implements MaymiCommand {

    @Override
    public void execute(
            MessageReceivedEvent event
    ) {

        event.getChannel().sendMessage("""
                🌸 **MAYMI COMMANDS**

                `/maymi help`
                `/maymi list`
                `/maymi players`
                `/maymi tps`
                `/maymi ram`
                `/maymi time`
                `/maymi server`
                """).queue();

    }

}