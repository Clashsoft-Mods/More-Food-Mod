package clashsoft.mods.morefood.world;

import java.util.Random;

import clashsoft.cslib.minecraft.world.gen.CustomWorldGen;
import clashsoft.cslib.random.CSRandom;
import clashsoft.mods.morefood.food.Food;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public class WorldGenGardener extends CustomWorldGen
{
	public static final ItemStack[]	possiblestacks	= new ItemStack[] { Food.lettuce.asStack(9), Food.cucumber.asStack(9), Food.rice.asStack(9), Food.chili.asStack(9), Food.tomato.asStack(9), Food.paprika.asStack(9), Food.onion.asStack(9), Food.pepperSeeds.asStack(9), Food.corn.asStack(9), Food.vanillaSeeds.asStack(), new ItemStack(Items.wheat_seeds, 14), new ItemStack(Items.potato, 9), new ItemStack(Items.carrot, 9), new ItemStack(Items.wheat, 14), };
	
	public WorldGenGardener(boolean update)
	{
		super(update);
	}
	
	private void setBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		world.setBlock(x, y, z, block, metadata, this.flags);
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		int direction = 2 + random.nextInt(3);
		this.drawHollowBox(world, x, y, z, 8, 5, 8, Blocks.cobblestone, 0);
		this.drawSolidBox(world, x + 1, y + 1, z + 1, 7, 4, 7, Blocks.air, 0);
		// Walls
		this.drawPlaneX(world, x, y + 1, z + 1, 4, 7, Blocks.planks, 0);
		this.drawPlaneZ(world, x + 1, y + 1, z, 7, 4, Blocks.planks, 0);
		this.drawPlaneX(world, x + 8, y + 1, z + 1, 8, 4, Blocks.planks, 0);
		this.drawPlaneZ(world, x + 1, y + 1, z + 8, 4, 8, Blocks.planks, 0);
		// Floor
		this.drawPlaneY(world, x + 1, y, z + 1, 7, 7, Blocks.log, 0);
		// Ceiling
		this.drawPlaneY(world, x + 1, y + 5, z + 1, 7, 7, Blocks.glass, 0);
		// Windows
		this.setBlock(world, x + 2, y + 2, z + 0, Blocks.glass_pane, 0);
		this.setBlock(world, x + 6, y + 2, z + 0, Blocks.glass_pane, 0);
		this.setBlock(world, x + 0, y + 2, z + 2, Blocks.glass_pane, 0);
		this.setBlock(world, x + 0, y + 2, z + 6, Blocks.glass_pane, 0);
		
		this.setBlock(world, x + 8, y + 2, z + 2, Blocks.glass_pane, 0);
		this.setBlock(world, x + 8, y + 2, z + 6, Blocks.glass_pane, 0);
		this.setBlock(world, x + 2, y + 2, z + 8, Blocks.glass_pane, 0);
		this.setBlock(world, x + 6, y + 2, z + 8, Blocks.glass_pane, 0);
		
		this.setBlock(world, x + 1, y + 1, z + 2, Blocks.crafting_table, 0);
		this.setBlock(world, x + 1, y + 1, z + 1, Blocks.furnace, 0);
		world.setTileEntity(x + 1, y + 1, z + 1, this.createFurnace());
		this.setBlock(world, x + 1, y + 1, z + 3, Blocks.chest, 0);
		world.setTileEntity(x + 1, y + 1, z + 3, this.createGardenerChest());
		
		direction -= 2;
		
		if (direction == 0) // North
		{
			this.setBlock(world, x + 4, y + 1, z, Blocks.wooden_door, direction);
			this.setBlock(world, x + 4, y + 2, z, Blocks.wooden_door, direction + 12);
			
			this.setBlock(world, x + 4, y + 3, z + 1, Blocks.torch, 3);
			this.setBlock(world, x + 4, y + 3, z + 7, Blocks.torch, 0);
		}
		else if (direction == 1)
		{
			this.setBlock(world, x + 4, y + 1, z + 8, Blocks.wooden_door, direction);
			this.setBlock(world, x + 4, y + 2, z + 8, Blocks.wooden_door, direction + 12);
			
			this.setBlock(world, x + 4, y + 3, z + 7, Blocks.torch, 0);
			this.setBlock(world, x + 4, y + 3, z + 1, Blocks.torch, 3);
		}
		else if (direction == 2)
		{
			this.setBlock(world, x + 8, y + 1, z + 4, Blocks.wooden_door, direction);
			this.setBlock(world, x + 8, y + 2, z + 4, Blocks.wooden_door, direction + 12);
			
			this.setBlock(world, x + 7, y + 3, z + 4, Blocks.torch, 2);
			this.setBlock(world, x + 1, y + 3, z + 4, Blocks.torch, 0);
		}
		else if (direction == 3)
		{
			this.setBlock(world, x, y + 1, z + 4, Blocks.wooden_door, direction);
			this.setBlock(world, x, y + 2, z + 4, Blocks.wooden_door, direction + 12);
			
			this.setBlock(world, x + 1, y + 3, z + 4, Blocks.torch, 1);
			this.setBlock(world, x + 7, y + 3, z + 4, Blocks.torch, 2);
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
