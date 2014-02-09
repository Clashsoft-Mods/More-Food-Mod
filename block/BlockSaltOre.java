package clashsoft.mods.morefood.block;

import java.util.Random;

import clashsoft.mods.morefood.MoreFoodMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BlockSaltOre extends Block
{
	public BlockSaltOre()
	{
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		return 2 + random.nextInt(2);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return MoreFoodMod.salt;
	}
}
