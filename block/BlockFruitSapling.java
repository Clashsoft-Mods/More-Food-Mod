package clashsoft.mods.morefood.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import clashsoft.cslib.minecraft.block.BlockCustomSapling;
import clashsoft.mods.morefood.MoreFoodMod;
import clashsoft.mods.morefood.world.WorldGenFruitTree;

public class BlockFruitSapling extends BlockCustomSapling
{
	public BlockFruitSapling(int blockID, String[] names, String[] icons)
	{
		super(blockID, names, icons);
	}
	
	@Override
	public WorldGenerator getWorldGen(World world, int x, int y, int z, Random random)
	{
		int metadata = world.getBlockMetadata(x, y, z);
		boolean flag = this == MoreFoodMod.fruitSaplings2;
		return new WorldGenFruitTree(true, random.nextInt(4), flag ? MoreFoodMod.fruitLogsID2 : MoreFoodMod.fruitLogsID, flag ? MoreFoodMod.fruitLeavesID2 : MoreFoodMod.fruitLeavesID, metadata, metadata);
	}
	
	@Override
	public boolean isValidGround(int blockID, int blockMetadata)
	{
		return blockID == Block.grass.blockID || blockID == Block.dirt.blockID;
	}
}
