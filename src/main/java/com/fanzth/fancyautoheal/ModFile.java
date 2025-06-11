package com.fanzth.fancyautoheal;

import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fanzth.fancyautoheal.listener.BattleEndListener;

@Mod(ModFile.MOD_ID)
@Mod.EventBusSubscriber(modid = ModFile.MOD_ID)
public class ModFile {

    public static final String MOD_ID = "fancyautoheal";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    private static ModFile instance;

    public ModFile() {
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event) {
        Pixelmon.EVENT_BUS.register(new BattleEndListener());
    }

    public static ModFile getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
