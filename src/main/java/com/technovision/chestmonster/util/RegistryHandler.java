package com.technovision.chestmonster.util;


import com.technovision.chestmonster.ChestMonster;
import com.technovision.chestmonster.events.InventoryGrappler;
import com.technovision.chestmonster.events.KeyboardEventHandler;
import com.technovision.chestmonster.items.ItemBase;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    //public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, ChestMonster.MOD_ID);
    public static DeferredRegister<Item> ITEMS;

    public static void init(){
        System.out.println("init");

        MinecraftForge.EVENT_BUS.register(KeyboardEventHandler.class);
        MinecraftForge.EVENT_BUS.register(InventoryGrappler.class);


        ITEMS = ITEMS.create(ForgeRegistries.ITEMS, ChestMonster.MOD_ID);
        RUBY = ITEMS.register("ruby", ItemBase::new);

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

    public static RegistryObject<Item> RUBY;
    
}


