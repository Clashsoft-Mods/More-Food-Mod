package clashsoft.mods.morefood.block;

import java.util.Random;

import clashsoft.cslib.minecraft.block.BlockCustomSapling;
import clashsoft.cslib.minecraft.world.gen.CustomTreeGen;
import clashsoft.mods.morefood.MoreFoodMod;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockFruitSapling extends BlockCustomSapling
{
	public BlockFruitSapling(String[] names, String[] icons)
	{
		super(names, icons);
	}
	
	@Override
	public WorldGenerator getWorldGen(World world, int x, int y, int z, Random random)
	{
		int metadata = world.getBlockMetadata(x, y, z) & 3;
		boolean flag = this == MoreFoodMod.fruitSaplings2;
		return new CustomTreeGen(true, 4 + random.nextInt(4), flag ? MoreFoodMod.fruitLogs2 : MoreFoodMod.fruitLogs, flag ? MoreFoodMod.fruitLeaves2 : MoreFoodMod.fruitLeaves, metadata, metadata);
	}
	
	@Override
	public boolean isValidGround(int metadata, Block block, int blockMetadata)
	{
		return block == Blocks.grass || block == Blocks.dirt;
	}
}
