package clashsoft.mods.morefood.block;

import java.util.Random;

import clashsoft.mods.morefood.MoreFoodMod;
import clashsoft.mods.morefood.food.Food;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPizza extends Block
{
	public BlockPizza()
	{
		super(Material.cake);
		this.setHardness(0.5F);
		this.setStepSound(Block.soundTypeSnow);
		this.setTickRandomly(true);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		int l = world.getBlockMetadata(x, y, z);
		float f1 = l / 8F;
		this.setBlockBounds(f1, 0F, 0F, 1F, 0.0625F, 1F);
	}
	
	@Override
	public void setBlockBoundsForItemRender()
	{
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.0625F, 1F);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int l = world.getBlockMetadata(x, y, z);
		double f1 = l / 8F;
		return AxisAlignedBB.getAABBPool().getAABB(x + f1, y, z, x + 1D, y + 0.0625D, z + 1D);
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return this.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		this.eatPizzaSlice(world, x, y, z, player);
		return true;
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		this.eatPizzaSlice(world, x, y, z, player);
	}
	
	private void eatPizzaSlice(World world, int x, int y, int z, EntityPlayer player)
	{
		if (player.canEat(false))
		{
			player.getFoodStats().addStats(2, 0.1F);
			int l = world.getBlockMetadata(x, y, z) + 1;
			
			if (l >= 8)
			{
				world.setBlockToAir(x, y, z);
			}
			else
			{
				world.setBlockMetadataWithNotify(x, y, z, l, 2);
			}
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return !super.canPlaceBlockAt(world, x, y, z) ? false : this.canBlockStay(world, x, y, z);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
	{
		if (!this.canBlockStay(world, x, y, z))
		{
			world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return world.getBlock(x, y - 1, z).getMaterial().isSolid();
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return null;
	}
	
	@Override
	public Item getItem(World world, int x, int y, int z)
	{
		return MoreFoodMod.foods;
	}
	
	@Override
	public int getDamageValue(World world, int x, int y, int z)
	{
		return Food.pizza.getID();
	}
}
