package com.matyrobbrt.anvilrecipes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

public class PlayerHelper {
	
	public static boolean hasItem(PlayerEntity player, Item item) {
		for (int i = 0; i <= player.inventory.getContainerSize(); ++i) {
			if (player.inventory.getItem(i).getItem() == item)
				return true;
		}
		return false;
	}

	public static ItemStack getFirstStackMatchingItem(PlayerEntity player, Item item) {
		if (hasItem(player, item)) {
			for (int i = 0; i <= player.inventory.getContainerSize(); ++i) {
				if (player.inventory.getItem(i).getItem() == item)
					return player.inventory.getItem(i);
			}
		} else
			return ItemStack.EMPTY;
		return ItemStack.EMPTY;
	}

	public static boolean canFitItem(PlayerEntity player, ItemStack item) {

		for (int i = 0; i <= player.inventory.getContainerSize(); i++) {
			if (player.inventory.canPlaceItem(i, item))
				return true;
		}

		return false;
	}

	public static void applyTickEffect(PlayerEntity player, Effect effect) {
		if (!player.hasEffect(effect)) {
			player.addEffect(new EffectInstance(effect, 320));
		} else {
			if (player.getEffect(effect).getDuration() <= 200)
				player.addEffect(new EffectInstance(effect, 320));
		}
	}

}
