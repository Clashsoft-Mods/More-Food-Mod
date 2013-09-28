package clashsoft.mods.morefood.block;

import java.util.Random;

import clashsoft.mods.morefood.MoreFoodMod;
import clashsoft.mods.morefood.food.Food;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPizza extends Block
{
	@SideOnly(Side.CLIENT)
	private Icon	cakeTopIcon;
	@SideOnly(Side.CLIENT)
	private Icon	cakeBottomIcon;
	@SideOnly(Side.CLIENT)
	private Icon	field_94382_c;
	
	public BlockPizza(int par1)
	{
		super(par1, Material.cake);
		this.setTickRandomly(true);
	}
	
	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y,
	 * z
	 */
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		float f1 = (l) / 8F;
		this.setBlockBounds(f1, 0F, 0F, 1F, 0.0625F, 1F);
	}
	
	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	public void setBlockBoundsForItemRender()
	{
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.0625F, 1F);
	}
	
	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this
	 * box can change after the pool has been cleared to be reused)
	 */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		int l = par1World.getBlockMetadata(par2, par3, par4);
		float f1 = (l) / 8F;
		return AxisAlignedBB.getAABBPool().getAABB((double) ((float) par2 + f1), (double) par3, (double) ((float) par4), (double) ((float) (par2 + 1)), (double) ((float) par3 + 0.0625F), (double) ((float) (par4 + 1)));
	}
	
	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	@SideOnly(Side.CLIENT)
	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon("pizza");
	}
	
	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		this.eatPizzaSlice(par1World, par2, par3, par4, par5EntityPlayer);
		return true;
	}
	
	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
		this.eatPizzaSlice(par1World, par2, par3, par4, par5EntityPlayer);
	}
	
	/**
	 * Heals the player and removes a slice from the cake.
	 */
	private void eatPizzaSlice(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
		if (par5EntityPlayer.canEat(false))
		{
			par5EntityPlayer.getFoodStats().addStats(2, 0.1F);
			int l = par1World.getBlockMetadata(par2, par3, par4) + 1;
			
			if (l >= 8)
			{
				par1World.setBlockToAir(par2, par3, par4);
			}
			else
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
			}
		}
	}
	
	/**
	 * Checks to see if its valid to put this block at the specified
	 * coordinates. Args: world, x, y, z
	 */
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return !super.canPlaceBlockAt(par1World, par2, par3, par4) ? false : this.canBlockStay(par1World, par2, par3, par4);
	}
	
	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which
	 * neighbor changed (coordinates passed are their own) Args: x, y, z,
	 * neighbor blockID
	 */
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if (!this.canBlockStay(par1World, par2, par3, par4))
		{
			par1World.setBlockToAir(par2, par3, par4);
		}
	}
	
	/**
	 * Can this block stay at this position. Similar to canPlaceBlockAt except
	 * gets checked often with plants.
	 */
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid();
	}
	
	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}
	
	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return MoreFoodMod.foods.itemID;
	}
	
	@Override
	public int getDamageValue(World par1World, int par2, int par3, int par4)
	{
		return Food.pizza.getID();
	}
}
