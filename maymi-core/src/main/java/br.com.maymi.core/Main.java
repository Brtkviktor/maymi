package br.com.maymi.core;

import br.com.maymi.core.persistence.database.DatabaseInitializer;
import br.com.maymi.core.startup.MaymiApplication;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {

        try {
            DatabaseInitializer.initialize();
            MaymiApplication.start();

        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }
}