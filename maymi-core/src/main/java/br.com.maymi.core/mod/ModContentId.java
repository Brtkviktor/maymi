package br.com.maymi.core.mod;

import java.util.Locale;
import java.util.Objects;

public record ModContentId(
        String namespace,
        String path
) {

    public ModContentId {

        Objects.requireNonNull(
                namespace,
                "Namespace não pode ser nulo."
        );

        Objects.requireNonNull(
                path,
                "Path não pode ser nulo."
        );

        namespace =
                namespace
                        .trim()
                        .toLowerCase(Locale.ROOT);

        path =
                path
                        .trim()
                        .toLowerCase(Locale.ROOT);

        if (namespace.isBlank()) {
            throw new IllegalArgumentException(
                    "Namespace não pode ser vazio."
            );
        }

        if (path.isBlank()) {
            throw new IllegalArgumentException(
                    "Path não pode ser vazio."
            );
        }
    }

    public static ModContentId parse(
            String registryId
    ) {

        Objects.requireNonNull(
                registryId,
                "Registry ID não pode ser nulo."
        );

        String value =
                registryId.trim();

        int separator =
                value.indexOf(':');

        if (
                separator <= 0
                        || separator == value.length() - 1
        ) {

            throw new IllegalArgumentException(
                    "Registry ID inválido: "
                            + registryId
            );
        }

        return new ModContentId(
                value.substring(
                        0,
                        separator
                ),
                value.substring(
                        separator + 1
                )
        );
    }

    public boolean isVanilla() {
        return namespace.equals(
                "minecraft"
        );
    }

    public boolean isModded() {
        return !isVanilla();
    }

    public String registryId() {
        return namespace
                + ":"
                + path;
    }
}