package clashsoft.mods.morefood.client;

import clashsoft.mods.morefood.client.gui.GuiRecipeBook;
import clashsoft.mods.morefood.common.MFMProxy;
import clashsoft.mods.morefood.inventory.ContainerRecipeBook;
import clashsoft.mods.morefood.inventory.InventoryRecipeBook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class MFMClientProxy extends MFMProxy
{
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == 0)
		{
			return new GuiRecipeBook(new ContainerRecipeBook(new InventoryRecipeBook()), player);
		}
		return null;
	}
}
