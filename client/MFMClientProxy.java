package clashsoft.mods.morefood.client;

import clashsoft.mods.morefood.block.render.BushRenderer;
import clashsoft.mods.morefood.common.MFMCommonProxy;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class MFMClientProxy extends MFMCommonProxy
{
	public static int	BUSH_RENDER_ID;
	
	@Override
	public void registerRenderers()
	{
		BUSH_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(BUSH_RENDER_ID, new BushRenderer());
	}
}
