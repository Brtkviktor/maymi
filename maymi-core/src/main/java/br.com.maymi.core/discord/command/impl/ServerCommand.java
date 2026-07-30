package br.com.maymi.core.discord.command.impl;

import br.com.maymi.core.discord.command.MaymiCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ServerCommand
        implements MaymiCommand {

    @Override
    public void execute(
            MessageReceivedEvent event
    ) {

        event.getChannel().sendMessage("""
                🖥️ **MAYMI SERVER**

                Status: 🟢 Online

                Utilize:

                • /maymi tps
                • /maymi ram
                • /maymi time
                """).queue();

    }

}