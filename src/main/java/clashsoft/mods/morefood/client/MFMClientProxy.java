package clashsoft.mods.morefood.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import clashsoft.mods.morefood.block.render.BushRenderer;
import clashsoft.mods.morefood.client.gui.GuiRecipeBook;
import clashsoft.mods.morefood.common.MFMProxy;
import clashsoft.mods.morefood.container.ContainerRecipeBook;
import clashsoft.mods.morefood.container.InventoryRecipeBook;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class MFMClientProxy extends MFMProxy
{
	public static int	BUSH_RENDER_ID;
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		BUSH_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(BUSH_RENDER_ID, new BushRenderer());
	}
	
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
