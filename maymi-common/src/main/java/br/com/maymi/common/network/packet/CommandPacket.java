package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class CommandPacket extends AbstractPacket {

    private String command;
    private String requestId;

    public CommandPacket() {
        super(PacketType.COMMAND);
    }

    public CommandPacket(String command) {
        this(command, null);
    }

    public CommandPacket(
            String command,
            String requestId
    ) {

        super(PacketType.COMMAND);

        this.command = command;
        this.requestId = requestId;
    }

    public String getCommand() {
        return command;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return """
                ==============================
                COMMAND PACKET
                ------------------------------
                Comando: %s
                Request ID: %s
                Tipo: %s
                ==============================
                """.formatted(
                command,
                requestId,
                getType()
        );
    }
}