package com.matyrobbrt.anvilrecipes;

import com.matyrobbrt.anvilrecipes.AnvilRecipes.RecipeInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnvilEvents {
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void handleAnvilRecipes(AnvilUpdateEvent event) {
		for (final IRecipe<?> recipe : event.getPlayer().level.getRecipeManager().getAllRecipesFor(RecipeInit.ANVIL_RECIPE)) {
			final AnvilRecipe anvilRecipe = (AnvilRecipe) recipe;
			final ItemStack left = event.getLeft();
			final ItemStack right = event.getRight();
			if (anvilRecipe.isValid(left, right)) {
				event.setCost(anvilRecipe.getXPRequired());
				event.setMaterialCost(anvilRecipe.getRightCount());
				ItemStack output = recipe.getResultItem().copy();
				event.setOutput(output);
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void finishAnvilRecipes(AnvilRepairEvent event) {
		for (final IRecipe<?> recipe : event.getPlayer().level.getRecipeManager().getAllRecipesFor(RecipeInit.ANVIL_RECIPE)) {
			final AnvilRecipe anvilRecipe = (AnvilRecipe) recipe;
			final ItemStack left = event.getItemInput();
			final ItemStack right = event.getIngredientInput();
			final PlayerEntity player = event.getPlayer();
			if (anvilRecipe.isValid(left, right)) {
				final ItemStack returnLeft = left.copy();
				returnLeft.shrink(anvilRecipe.getLeftCount());
				if (!player.addItem(returnLeft)) player.drop(returnLeft, false);
			}
		}
	}

}
