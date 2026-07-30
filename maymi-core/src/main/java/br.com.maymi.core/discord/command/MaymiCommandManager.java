package br.com.maymi.core.discord.command;

import br.com.maymi.common.network.packet.CommandPacket;
import br.com.maymi.core.discord.command.impl.HelpCommand;
import br.com.maymi.core.discord.command.impl.ListCommand;
import br.com.maymi.core.discord.command.impl.PlayersCommand;
import br.com.maymi.core.discord.command.impl.RamCommand;
import br.com.maymi.core.discord.command.impl.ServerCommand;
import br.com.maymi.core.discord.command.impl.TimeCommand;
import br.com.maymi.core.discord.command.impl.TpsCommand;
import br.com.maymi.core.socket.SocketClient;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.EnumMap;
import java.util.Map;

public class MaymiCommandManager {

    private final SocketClient socketClient;

    private final Map<CommandType, MaymiCommand> commands =
            new EnumMap<>(CommandType.class);

    public MaymiCommandManager(
            SocketClient socketClient
    ) {

        this.socketClient = socketClient;

        commands.put(
                CommandType.HELP,
                new HelpCommand()
        );

        commands.put(
                CommandType.LIST,
                new ListCommand(socketClient)
        );

        commands.put(
                CommandType.PLAYERS,
                new PlayersCommand(socketClient)
        );

        commands.put(
                CommandType.TPS,
                new TpsCommand(socketClient)
        );

        commands.put(
                CommandType.RAM,
                new RamCommand(socketClient)
        );

        commands.put(
                CommandType.TIME,
                new TimeCommand(socketClient)
        );

        commands.put(
                CommandType.SERVER,
                new ServerCommand()
        );

    }

    public void handle(
            String command,
            MessageReceivedEvent event
    ) {

        CommandType type =
                parse(command);

        MaymiCommand maymiCommand =
                commands.get(type);

        if (maymiCommand != null) {

            maymiCommand.execute(event);

            return;

        }

        socketClient.send(
                new CommandPacket(command)
        );

    }

    private CommandType parse(
            String command
    ) {

        if (command == null) {
            return CommandType.UNKNOWN;
        }

        String cmd =
                command.trim().toLowerCase();

        return switch (cmd) {

            case "/maymihelp",
                 "/maymi help" -> CommandType.HELP;

            case "/list",
                 "/maymi list" -> CommandType.LIST;

            case "/players",
                 "/maymi players" -> CommandType.PLAYERS;

            case "/tps",
                 "/maymi tps" -> CommandType.TPS;

            case "/ram",
                 "/maymi ram" -> CommandType.RAM;

            case "/time",
                 "/maymi time" -> CommandType.TIME;

            case "/server",
                 "/maymi server" -> CommandType.SERVER;

            default -> CommandType.UNKNOWN;

        };

    }

}