package clashsoft.mods.morefood.common;

import clashsoft.cslib.minecraft.common.BaseProxy;
import clashsoft.mods.morefood.inventory.ContainerRecipeBook;
import clashsoft.mods.morefood.inventory.InventoryRecipeBook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class MFMProxy extends BaseProxy
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new ContainerRecipeBook(new InventoryRecipeBook());
	}
}
