package br.com.maymi.common.network.packet;

import br.com.maymi.common.network.AbstractPacket;
import br.com.maymi.common.network.PacketType;

public class DiscordChatPacket extends AbstractPacket {

    private String author;
    private String message;

    public DiscordChatPacket() {
        super(PacketType.DISCORD_CHAT);
    }

    public DiscordChatPacket(String author, String message) {
        super(PacketType.DISCORD_CHAT);
        this.author = author;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return """
                ==============================
                DISCORD CHAT PACKET
                ------------------------------
                Autor: %s
                Mensagem: %s
                Tipo: %s
                ==============================
                """.formatted(
                author,
                message,
                getType()
        );
    }
}