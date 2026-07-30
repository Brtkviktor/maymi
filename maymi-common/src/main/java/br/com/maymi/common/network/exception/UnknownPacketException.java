package br.com.maymi.common.network.exception;

public class UnknownPacketException
        extends RuntimeException {

    public UnknownPacketException(
            String message
    ) {

        super(message);

    }

}