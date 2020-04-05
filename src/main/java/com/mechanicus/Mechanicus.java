package com.mechanicus;

import com.mechanicus.client.renderer.ServoSkullRenderer;
import com.mechanicus.client.renderer.UpgradeStationRenderer;
import com.mechanicus.client.sound.MSoundRegistry;
import com.mechanicus.common.entity.MEntityServoSkull;
import com.mechanicus.common.tileentities.MTEUpgradeStation;
import com.mechanicus.lib.MLib;
import com.mechanicus.registry.BlockRegistry;
import com.mechanicus.registry.ItemRegistry;
import com.mechanicus.registry.TileEntityRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MLib.MODID)
public class Mechanicus {

	public Mechanicus() {
		//register mod construction event
		final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		eventBus.addListener(this::modConstruction);
		eventBus.addListener(this::setupClient);
		//register ourself
		MinecraftForge.EVENT_BUS.register(this);
		
		//register other handlers
		
	}
	
	
	private void modConstruction(final FMLCommonSetupEvent event) {
		TileEntityRegistry.prepareTileEntities();
	}

	@OnlyIn(Dist.CLIENT)
	public void setupClient(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(MEntityServoSkull.class, ServoSkullRenderer::new);
        
    }
	
	
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        	BlockRegistry.parseBlockRegistry(event);
        }
        
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
        	ItemRegistry.parseItemRegistry(event);
        	BlockRegistry.parseItemRegistry(event);
        }
        
        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
        	//TileEntityRegistry.parseTileEntityRegistry(event);
        }
        
        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void registerModels (ModelBakeEvent event) {
    		ClientRegistry.bindTileEntitySpecialRenderer(MTEUpgradeStation.class, new UpgradeStationRenderer());
    	}
	}
} 
