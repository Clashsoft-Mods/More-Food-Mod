package clashsoft.mods.morefood.block.render;

import clashsoft.mods.morefood.ClientProxy;
import clashsoft.mods.morefood.block.BlockBush;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class BushRenderer implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		BlockBush bush = (BlockBush)block;
		
		renderer.overrideBlockTexture = bush.stemIcon;
		renderer.renderCrossedSquares(block, x, y, z);
		
		int i1 = world.getBlockMetadata(x, y, z);
		
		if (i1 == bush.maxMeta)
		{
			renderer.overrideBlockTexture = bush.bushIcon;
			renderer.renderStandardBlock(block, x, y, z);
		}
		
		renderer.overrideBlockTexture = null;
		
		return true;
	}
	
	@Override
	public boolean shouldRender3DInInventory()
	{
		return false;
	}
	
	@Override
	public int getRenderId()
	{
		return ClientProxy.BUSH_RENDER_ID;
	}
	
}
