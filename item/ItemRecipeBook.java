package clashsoft.mods.morefood.item;

import clashsoft.mods.morefood.MoreFoodMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRecipeBook extends Item
{
	public ItemRecipeBook(int par1)
	{
		super(par1);
		this.setTextureName("recipebook");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.openGui(MoreFoodMod.instance, 0, par2World, 0, 0, 0);
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
}
