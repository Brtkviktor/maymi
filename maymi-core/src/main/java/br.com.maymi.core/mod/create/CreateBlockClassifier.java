package br.com.maymi.core.mod.create;

import java.util.Locale;
import java.util.Objects;

public final class CreateBlockClassifier {

    public CreateBlockCategory classify(
            String path
    ) {

        Objects.requireNonNull(
                path,
                "Path do bloco não pode ser nulo."
        );

        String normalized =
                path.trim()
                        .toLowerCase(Locale.ROOT);

        if (normalized.isBlank()) {
            return CreateBlockCategory.UNKNOWN;
        }

        if (
                normalized.contains("casing")
                        || normalized.contains("scoria")
                        || normalized.contains("limestone")
        ) {

            return CreateBlockCategory.STRUCTURE;
        }

        if (
                normalized.contains("cogwheel")
                        || normalized.contains("shaft")
                        || normalized.contains("gearbox")
                        || normalized.contains("clutch")
                        || normalized.contains("gearshift")
        ) {

            return CreateBlockCategory.MECHANICAL;
        }

        if (
                normalized.contains("mechanical_press")
                        || normalized.contains("mechanical_mixer")
                        || normalized.contains("deployer")
                        || normalized.contains("millstone")
        ) {

            return CreateBlockCategory.MACHINE;
        }

        if (
                normalized.contains("crushing_wheel")
                        || normalized.contains("encased_fan")
                        || normalized.contains("basin")
        ) {

            return CreateBlockCategory.PROCESSING;
        }

        if (
                normalized.contains("depot")
                        || normalized.contains("chute")
                        || normalized.contains("funnel")
                        || normalized.contains("belt")
                        || normalized.contains("portable_storage")
        ) {

            return CreateBlockCategory.LOGISTICS;
        }

        if (
                normalized.contains("water_wheel")
                        || normalized.contains("windmill")
                        || normalized.contains("steam_engine")
        ) {

            return CreateBlockCategory.POWER;
        }

        return CreateBlockCategory.UNKNOWN;
    }
}