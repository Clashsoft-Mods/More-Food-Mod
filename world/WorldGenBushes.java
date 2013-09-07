package clashsoft.mods.morefood.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBushes extends WorldGenerator
{
	public final int amount, rangeX, rangeY, rangeZ;
	public final int bushID, bushMeta;
	
	public WorldGenBushes(int bushID, int bushMeta)
	{
		this(64, 8, 4, 8, bushID, bushMeta);
	}
	
	public WorldGenBushes(int amount, int rangeX, int rangeY, int rangeZ, int bushID, int bushMeta)
	{
		this.amount = amount;
		this.rangeX = rangeX;
		this.rangeY = rangeY;
		this.rangeZ = rangeZ;
		this.bushID = bushID;
		this.bushMeta = bushMeta;
	}
	
	@Override
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		for (int l = 0; l < amount; ++l)
		{
			int i1 = par3 + par2Random.nextInt(rangeX) - par2Random.nextInt(rangeX);
			int j1 = par4 + par2Random.nextInt(rangeY) - par2Random.nextInt(rangeY);
			int k1 = par5 + par2Random.nextInt(rangeZ) - par2Random.nextInt(rangeZ);
			
			if (par1World.isAirBlock(i1, j1, k1) && par1World.getBlockId(i1, j1 - 1, k1) == Block.grass.blockID && Block.blocksList[bushID].canPlaceBlockAt(par1World, i1, j1, k1))
			{
				par1World.setBlock(i1, j1, k1, bushID, bushMeta, 2);
			}
		}
		
		return true;
	}
}
