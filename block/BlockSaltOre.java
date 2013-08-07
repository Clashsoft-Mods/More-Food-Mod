package clashsoft.mods.morefood.block;

import java.util.Random;

import clashsoft.mods.morefood.MoreFoodMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockSaltOre extends Block
{
	
	public BlockSaltOre(int par1)
	{
		super(par1, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		return 2 + random.nextInt(2);
	}
	
	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return MoreFoodMod.salt.itemID;
	}
	
}
