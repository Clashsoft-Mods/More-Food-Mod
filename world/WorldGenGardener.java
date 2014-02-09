package clashsoft.mods.morefood.world;

import java.util.Random;

import clashsoft.cslib.minecraft.world.CSWorld;
import clashsoft.cslib.util.CSRandom;
import clashsoft.mods.morefood.food.Food;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGardener extends WorldGenerator
{
	public static final ItemStack[]	possiblestacks	= new ItemStack[] {
			Food.salad.asStack(9),
			Food.cucumber.asStack(9),
			Food.rice.asStack(9),
			Food.chili.asStack(9),
			Food.tomato.asStack(9),
			Food.paprika.asStack(9),
			Food.onion.asStack(9),
			Food.pepperSeeds.asStack(9),
			Food.corn.asStack(9),
			Food.vanillaSeeds.asStack(),
			new ItemStack(Items.wheat_seeds, 14),
			new ItemStack(Items.potato, 9),
			new ItemStack(Items.carrot, 9),
			new ItemStack(Items.wheat, 14),		};
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		int direction = 2 + random.nextInt(3);
		CSWorld.setFrame(world, x, y, z, x + 8, y + 5, z + 8, Blocks.cobblestone, 0);
		CSWorld.setCube(world, x + 1, y + 1, z + 1, x + 7, y + 4, z + 7, Blocks.air, 0);
		// Walls
		CSWorld.setCube(world, x, y + 1, z + 1, x, y + 4, z + 7, Blocks.planks, 0);
		CSWorld.setCube(world, x + 1, y + 1, z, x + 7, y + 4, z, Blocks.planks, 0);
		CSWorld.setCube(world, x + 8, y + 1, z + 1, x + 8, y + 4, z + 7, Blocks.planks, 0);
		CSWorld.setCube(world, x + 1, y + 1, z + 8, x + 7, y + 4, z + 8, Blocks.planks, 0);
		// Floor
		CSWorld.setCube(world, x + 1, y, z + 1, x + 7, y, z + 7, Blocks.log, 0);
		// Ceiling
		CSWorld.setCube(world, x + 1, y + 5, z + 1, x + 7, y + 5, z + 7, Blocks.glass, 0);
		// Windows
		CSWorld.setBlock(world, x + 2, y + 2, z + 0, Blocks.glass_pane, 0);
		CSWorld.setBlock(world, x + 6, y + 2, z + 0, Blocks.glass_pane, 0);
		CSWorld.setBlock(world, x + 0, y + 2, z + 2, Blocks.glass_pane, 0);
		CSWorld.setBlock(world, x + 0, y + 2, z + 6, Blocks.glass_pane, 0);
		
		CSWorld.setBlock(world, x + 8, y + 2, z + 2, Blocks.glass_pane, 0);
		CSWorld.setBlock(world, x + 8, y + 2, z + 6, Blocks.glass_pane, 0);
		CSWorld.setBlock(world, x + 2, y + 2, z + 8, Blocks.glass_pane, 0);
		CSWorld.setBlock(world, x + 6, y + 2, z + 8, Blocks.glass_pane, 0);
		
		CSWorld.setBlock(world, x + 1, y + 1, z + 2, Blocks.crafting_table, 0);
		CSWorld.setBlock(world, x + 1, y + 1, z + 1, Blocks.furnace, 0);
		world.setTileEntity(x + 1, y + 1, z + 1, this.createFurnace());
		CSWorld.setBlock(world, x + 1, y + 1, z + 3, Blocks.chest, 0);
		world.setTileEntity(x + 1, y + 1, z + 3, this.createGardenerChest());
		
		direction -= 2;
		
		if (direction == 0) // North
		{
			CSWorld.setBlock(world, x + 4, y + 1, z, Blocks.wooden_door, direction);
			CSWorld.setBlock(world, x + 4, y + 2, z, Blocks.wooden_door, direction + 12);
			
			CSWorld.setBlock(world, x + 4, y + 3, z + 1, Blocks.torch, 3);
			CSWorld.setBlock(world, x + 4, y + 3, z + 7, Blocks.torch, 0);
		}
		else if (direction == 1)
		{
			CSWorld.setBlock(world, x + 4, y + 1, z + 8, Blocks.wooden_door, direction);
			CSWorld.setBlock(world, x + 4, y + 2, z + 8, Blocks.wooden_door, direction + 12);
			
			CSWorld.setBlock(world, x + 4, y + 3, z + 7, Blocks.torch, 0);
			CSWorld.setBlock(world, x + 4, y + 3, z + 1, Blocks.torch, 3);
		}
		else if (direction == 2)
		{
			CSWorld.setBlock(world, x + 8, y + 1, z + 4, Blocks.wooden_door, direction);
			CSWorld.setBlock(world, x + 8, y + 2, z + 4, Blocks.wooden_door, direction + 12);
			
			CSWorld.setBlock(world, x + 7, y + 3, z + 4, Blocks.torch, 2);
			CSWorld.setBlock(world, x + 1, y + 3, z + 4, Blocks.torch, 0);
		}
		else if (direction == 3)
		{
			CSWorld.setBlock(world, x, y + 1, z + 4, Blocks.wooden_door, direction);
			CSWorld.setBlock(world, x, y + 2, z + 4, Blocks.wooden_door, direction + 12);
			
			CSWorld.setBlock(world, x + 1, y + 3, z + 4, Blocks.torch, 1);
			CSWorld.setBlock(world, x + 7, y + 3, z + 4, Blocks.torch, 2);
		}
		return false;
	}
	
	private TileEntity createFurnace()
	{
		TileEntityFurnace furnace = new TileEntityFurnace();
		furnace.furnaceBurnTime = 40000;
		furnace.setInventorySlotContents(1, new ItemStack(Items.mushroom_stew, 1));
		return furnace;
	}
	
	private TileEntity createGardenerChest()
	{
		TileEntityChest chest = new TileEntityChest();
		Random rnd = new Random();
		int amount = CSRandom.nextInt(rnd, 7, 20);
		
		for (int i = 0; i < amount; i++)
		{
			ItemStack is = possiblestacks[rnd.nextInt(possiblestacks.length)];
			is = new ItemStack(is.getItem(), CSRandom.nextInt(rnd, 1, is.stackSize), is.getItemDamage());
			chest.setInventorySlotContents(rnd.nextInt(chest.getSizeInventory() - 1), is);
		}
		return chest;
	}
}
