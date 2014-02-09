package clashsoft.mods.morefood.item;

import clashsoft.mods.morefood.MoreFoodMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRecipeBook extends Item
{
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		player.openGui(MoreFoodMod.instance, 0, world, 0, 0, 0);
		return super.onItemRightClick(stack, world, player);
	}
}
