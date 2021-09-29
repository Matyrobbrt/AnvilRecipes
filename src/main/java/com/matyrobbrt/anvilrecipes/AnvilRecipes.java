package com.matyrobbrt.anvilrecipes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.matyrobbrt.anvilrecipes.AnvilRecipe.AnvilRecipeType;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("anvilrecipes")
public class AnvilRecipes
{
    public static final String MOD_ID = "anvilrecipes";
    public static final Logger LOGGER = LogManager.getLogger();

    public AnvilRecipes() {
    	IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
    	
    	modBus.addGenericListener(IRecipeSerializer.class, RecipeInit::registerRecipes);
        
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    public static class RecipeInit {
    	
    	public static final IRecipeType<AnvilRecipe> ANVIL_RECIPE = new AnvilRecipeType();
    	
    	public static void registerRecipes(Register<IRecipeSerializer<?>> event) {
    		registerRecipe(event, ANVIL_RECIPE, AnvilRecipe.SERIALIZER);
    	}

    	private static void registerRecipe(Register<IRecipeSerializer<?>> event, IRecipeType<?> type,
    			IRecipeSerializer<?> serializer) {
    		Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(type.toString()), type);
    		event.getRegistry().register(serializer);
    	}
    }

}
