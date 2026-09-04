package br.com.maymi.neoforge;

import br.com.maymi.neoforge.event.BlockBreakListener;
import br.com.maymi.neoforge.event.MobKillListener;
import br.com.maymi.neoforge.event.PlayerConnectionListener;
import br.com.maymi.neoforge.event.PlayerDeathListener;
import br.com.maymi.neoforge.event.BlockPlaceListener;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(MaymiNeoForge.MOD_ID)
public final class MaymiNeoForge {

    public static final String MOD_ID =
            "maymi";

    private static final Logger LOGGER =
            LogUtils.getLogger();

    public MaymiNeoForge() {

        NeoForge.EVENT_BUS.register(
                PlayerConnectionListener.class
        );

        NeoForge.EVENT_BUS.register(
                PlayerDeathListener.class
        );

        NeoForge.EVENT_BUS.register(
                MobKillListener.class
        );

        NeoForge.EVENT_BUS.register(
                BlockBreakListener.class
        );

        NeoForge.EVENT_BUS.register(
                BlockPlaceListener.class
        );

        LOGGER.info(
                "================================="
        );

        LOGGER.info(
                "Maymi NeoForge iniciado!"
        );

        LOGGER.info(
                "Mod ID: {}",
                MOD_ID
        );

        LOGGER.info(
                "Listeners NeoForge registrados."
        );

        LOGGER.info(
                "================================="
        );
    }
}