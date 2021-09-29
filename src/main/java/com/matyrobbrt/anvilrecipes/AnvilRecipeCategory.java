package com.matyrobbrt.anvilrecipes;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;

public class AnvilRecipeCategory implements IRecipeCategory<AnvilRecipe> {

	public static final ResourceLocation ID = new ResourceLocation(AnvilRecipes.MOD_ID, ".anvil_recipe_category");
	private final IDrawable back;
	private final IDrawable icon;

	@Override
	public ResourceLocation getUid() {
		return ID;
	}

	public AnvilRecipeCategory(IGuiHelper helper) {
		back = helper.drawableBuilder(new ResourceLocation(ModIds.JEI_ID, "textures/gui/gui_vanilla.png"), 0, 168, 125, 18).addPadding(0, 20, 0, 0)
				.build();
		icon = helper.createDrawableIngredient(new ItemStack(Blocks.ANVIL));
	}

	@Override
	public Class<? extends AnvilRecipe> getRecipeClass() {
		return AnvilRecipe.class;
	}

	@Override
	public String getTitle() {
		return new TranslationTextComponent("category." + AnvilRecipes.MOD_ID + ".anvil").getString();
	}

	@Override
	public IDrawable getBackground() {
		return back;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void draw(AnvilRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
		int cost = recipe.getXPRequired(); 
		if (cost != 0) {
			String costText = cost < 0 ? "err" : Integer.toString(cost);
			String text = I18n.get("container.repair.cost", costText);

			Minecraft minecraft = Minecraft.getInstance();
			int mainColor = 0xFF80FF20;
			ClientPlayerEntity player = minecraft.player;
			if (player != null &&
				(cost >= 40 || cost > player.experienceLevel) &&
				!player.isCreative()) {
				// Show red if the player doesn't have enough levels
				mainColor = 0xFFFF6060;
			}

			drawRepairCost(minecraft, matrixStack, text, mainColor);
		}
	}

	@Override
	public void setIngredients(AnvilRecipe recipe, IIngredients ingredients) {
		ingredients.setInputIngredients(recipe.getIngredients());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AnvilRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();

		itemStackGroup.init(0, true, 0, 0);
		itemStackGroup.init(1, true, 49, 0);
		itemStackGroup.init(3, false, 107, 0);

		itemStackGroup.set(ingredients);
	}
	
	private void drawRepairCost(Minecraft minecraft, MatrixStack matrixStack, String text, int mainColor) {
		int shadowColor = 0xFF000000 | (mainColor & 0xFCFCFC) >> 2;
		int width = minecraft.font.width(text);
		int x = back.getWidth() - 2 - width;
		int y = 27;

		// TODO 1.13 match the new GuiRepair style
		minecraft.font.draw(matrixStack, text, x + 1, y, shadowColor);
		minecraft.font.draw(matrixStack, text, x, y + 1, shadowColor);
		minecraft.font.draw(matrixStack, text, x + 1, y + 1, shadowColor);
		minecraft.font.draw(matrixStack, text, x, y, mainColor);
	}
	
}
