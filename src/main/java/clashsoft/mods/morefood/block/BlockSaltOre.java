package clashsoft.mods.morefood.block;

import java.util.Random;

import clashsoft.mods.morefood.MoreFoodMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class BlockSaltOre extends Block
{
	private Random	rand	= new Random();
	
	public BlockSaltOre()
	{
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
	}
	
	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune)
	{
		return MathHelper.getRandomIntegerInRange(this.rand, 0, 2);
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		return 2 + random.nextInt(2);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return MoreFoodMod.salt.getItem();
	}
}
