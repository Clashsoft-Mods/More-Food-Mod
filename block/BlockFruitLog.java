package clashsoft.mods.morefood.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class BlockFruitLog extends BlockLog
{
	/** The type of tree this log came from. */
	public static final String[]	woodType	= new String[] { "orange", "pear", "cherry" };
	
	@SideOnly(Side.CLIENT)
	public Icon[]					tree_side;
	@SideOnly(Side.CLIENT)
	public Icon[]					tree_top;
	
	public BlockFruitLog(int par1)
	{
		super(par1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < woodType.length; i++)
			par3List.add(new ItemStack(this, 1, i));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.tree_side = new Icon[woodType.length];
		this.tree_top = new Icon[woodType.length];
		
		for (int i = 0; i < this.tree_side.length; ++i)
		{
			this.tree_side[i] = par1IconRegister.registerIcon(this.getTextureName() + "_" + woodType[i]);
			this.tree_top[i] = par1IconRegister.registerIcon(this.getTextureName() + "_" + woodType[i] + "_top");
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * The icon for the side of the block.
	 */
	protected Icon getSideIcon(int par1)
	{
		return this.tree_side[par1];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * The icon for the tops and bottoms of the block.
	 */
	protected Icon getEndIcon(int par1)
	{
		return this.tree_top[par1];
	}
	
	/**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
	public int idDropped(int par1, Random par2Random, int par3)
    {
        return this.blockID;
    }
}
