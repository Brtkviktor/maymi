package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class CommandPacket extends AbstractPacket {

    private String command;

    public CommandPacket() {
        super(PacketType.COMMAND);
    }

    public CommandPacket(String command) {
        super(PacketType.COMMAND);
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return """
                ==============================
                COMMAND PACKET
                ------------------------------
                Comando: %s
                Tipo: %s
                ==============================
                """.formatted(
                command,
                getType()
        );
    }
}