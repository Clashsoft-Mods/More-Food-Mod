package clashsoft.mods.morefood.world;

import static clashsoft.mods.morefood.MoreFoodMod.*;

import java.util.Random;

import clashsoft.cslib.minecraft.world.gen.CustomTreeGen;
import clashsoft.cslib.minecraft.world.gen.WorldGenRanged;
import cpw.mods.fml.common.IWorldGenerator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.util.ForgeDirection;

public class MFMWorld implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if (world.provider.isSurfaceWorld())
		{
			int x, y, z;
			
			for (int i = 0; i < 10; i++)
			{
				x = chunkX * 16 + random.nextInt(16);
				y = random.nextInt(48);
				z = chunkZ * 16 + random.nextInt(16);
				if (world.getBiomeGenForCoords(x, z) instanceof BiomeGenOcean)
				{
					new WorldGenMinable(saltOre, 8).generate(world, random, x, y, z);
				}
				else
				{
					new WorldGenMinable(saltOre, 4).generate(world, random, x, y, z);
				}
			}
			
			if (random.nextInt(512) == 0)
			{
				x = chunkX * 16 + random.nextInt(16);
				y = 128;
				z = chunkZ * 16 + random.nextInt(16);
				
				while (y > 0)
				{
					if (world.getBlock(x, y, z).isSideSolid(world, x, y, z, ForgeDirection.UP))
					{
						break;
					}
					y--;
				}
				
				new WorldGenGardener().generate(world, random, x, y, z);
			}
			
			x = chunkX * 16 + random.nextInt(16);
			y = 128;
			z = chunkZ * 16 + random.nextInt(16);
			if (world.getBiomeGenForCoords(x, z) instanceof BiomeGenForest)
			{
				while (y > 0)
				{
					Block block = world.getBlock(x, y, z);
					if (block == Blocks.grass || block == Blocks.dirt)
					{
						break;
					}
					y--;
				}
				
				int treeType = random.nextInt(6);
				int height = 4 + random.nextInt(4);
				if (treeType == 4)
				{
					height += 3;
				}
				new CustomTreeGen(false, height, treeType > 3 ? fruitLogs2 : fruitLogs, treeType > 3 ? fruitLeaves2 : fruitLeaves, treeType & 3, treeType & 3).generate(world, random, x, y, z);
			}
			
			if (random.nextInt(10) == 0)
			{
				x = chunkX * 16 + random.nextInt(16);
				y = 128;
				z = chunkZ * 16 + random.nextInt(16);
				
				while (y > 0)
				{
					Block block = world.getBlock(x, y, z);
					if (block == Blocks.grass || block == Blocks.dirt)
					{
						break;
					}
					y--;
				}
				
				Block bushType = getBushTypeForBiome(world.getBiomeGenForCoords(x, y), random);
				new WorldGenRanged(bushType, 3).generate(world, random, x, y, z);
			}
		}
	}
	
	public static Block getBushTypeForBiome(BiomeGenBase biome, Random random)
	{
		if (biome instanceof BiomeGenForest)
		{
			return random.nextBoolean() ? raspberryBush : blackberryBush;
		}
		else if (biome instanceof BiomeGenJungle)
		{
			return random.nextBoolean() ? redcurrantBush : blueberryBush;
		}
		else
		{
			switch (random.nextInt(5))
			{
			case 0:
				return raspberryBush;
			case 1:
				return blackberryBush;
			case 2:
				return redcurrantBush;
			case 3:
				return blueberryBush;
			default:
				return strawberryBush;
			}
		}
	}
}