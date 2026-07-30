package br.com.maymi.common.network.exception;

public class PacketDeserializationException
        extends RuntimeException {

    public PacketDeserializationException(
            String message
    ) {

        super(message);

    }

    public PacketDeserializationException(
            String message,
            Throwable cause
    ) {

        super(message, cause);

    }

}