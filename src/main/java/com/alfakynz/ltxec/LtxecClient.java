package com.alfakynz.ltxec;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = Ltxec.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Ltxec.MODID, value = Dist.CLIENT)
public class LtxecClient {

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {

    }
}
