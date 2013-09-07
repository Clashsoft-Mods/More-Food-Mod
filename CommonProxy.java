package clashsoft.mods.morefood;

import clashsoft.mods.morefood.container.ContainerRecipeBook;
import clashsoft.mods.morefood.container.InventoryRecipeBook;
import clashsoft.mods.morefood.gui.GuiRecipeBook;
import cpw.mods.fml.common.network.IGuiHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new ContainerRecipeBook(new InventoryRecipeBook());
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == 0)
			return new GuiRecipeBook((ContainerRecipeBook) getServerGuiElement(ID, player, world, x, y, z), player);
		return null;
	}
	
	public void registerRenderers()
	{
	};
}
