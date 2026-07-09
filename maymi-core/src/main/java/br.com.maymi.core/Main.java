package br.com.maymi.core;

import br.com.maymi.core.socket.SocketServer;
import br.com.maymi.core.startup.MaymiApplication;

public class Main {

    public static void main(String[] args) {

        MaymiApplication.start();
        SocketServer socketServer = new SocketServer();

        new Thread(socketServer::start).start();

    }

}