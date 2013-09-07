package clashsoft.mods.morefood;

import clashsoft.mods.morefood.block.render.BushRenderer;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	public static int	BUSH_RENDER_ID;
	
	@Override
	public void registerRenderers()
	{
		BUSH_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(BUSH_RENDER_ID, new BushRenderer());
	}
}
