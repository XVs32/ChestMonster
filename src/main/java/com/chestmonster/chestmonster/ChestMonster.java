package com.chestmonster.chestmonster;


import com.chestmonster.chestmonster.channel.ChannelSetup;
import com.chestmonster.chestmonster.events.ChestSorter;
import com.chestmonster.chestmonster.events.CustomKeybind;
import com.chestmonster.chestmonster.events.InventoryGrappler;
import com.chestmonster.chestmonster.events.RightClickChest;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod("cm")
public class ChestMonster {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "cm";

    public ChestMonster() throws Exception {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event){

        MinecraftForge.EVENT_BUS.register(InventoryGrappler.class);
        MinecraftForge.EVENT_BUS.register(ChestSorter.class);

        ChannelSetup.register();

    }

    private void doClientStuff(final FMLClientSetupEvent event){
        try {
            CustomKeybind.init();
        } catch (Exception e) {
            System.out.println("Exception");
        }

        MinecraftForge.EVENT_BUS.register(CustomKeybind.class);
        MinecraftForge.EVENT_BUS.register(RightClickChest.class);

    }


}