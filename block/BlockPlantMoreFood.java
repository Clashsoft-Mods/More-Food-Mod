package clashsoft.mods.morefood.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockPlantMoreFood extends BlockFlower
{
	public final ItemStack	seed;
	public final ItemStack	crop;
	public int				maxMeta;
	public String			texture;
	
	private Icon[]			icons;
	
	public BlockPlantMoreFood(int par1, int par2, ItemStack par3ItemStack, ItemStack par4ItemStack, String tex)
	{
		super(par1, Material.plants);
		this.setTickRandomly(true);
		float var3 = 0.5F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.25F, 0.5F + var3);
		this.setCreativeTab((CreativeTabs) null);
		this.setHardness(0.0F);
		this.setStepSound(soundGrassFootstep);
		this.disableStats();
		
		seed = par3ItemStack;
		crop = par4ItemStack;
		
		maxMeta = par2;
		texture = tex;
	}
	
	/**
	 * Gets passed in the blockID of the block below and supposed to return true
	 * if its allowed to grow on the type of blockID passed in. Args: blockID
	 */
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int par1)
	{
		return par1 == Block.tilledField.blockID;
	}
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return super.canPlaceBlockAt(par1World, par2, par3, par4) && par1World.getBlockId(par2, par3, par4) == Block.tilledField.blockID;
	}
	
	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		super.updateTick(par1World, par2, par3, par4, par5Random);
		
		if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			
			if (var6 < maxMeta)
			{
				float var7 = this.getGrowthRate(par1World, par2, par3, par4);
				
				if (par5Random.nextInt((int) (25.0F / var7) + 1) == 0)
				{
					++var6;
					par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
				}
			}
		}
	}
	
	/**
	 * Apply bonemeal to the crops.
	 */
	public boolean fertilize(World par1World, int par2, int par3, int par4)
	{
		if (par1World.getBlockMetadata(par2, par3, par4) < maxMeta)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, maxMeta, 2);
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the growth rate for the crop. Setup to encourage rows by halving
	 * growth rate if there is diagonals, crops on different sides that aren't
	 * opposing, and by adding growth for every crop next to this one (and for
	 * crop below this one). Args: x, y, z
	 */
	private float getGrowthRate(World par1World, int par2, int par3, int par4)
	{
		float var5 = 1.0F;
		int var6 = par1World.getBlockId(par2, par3, par4 - 1);
		int var7 = par1World.getBlockId(par2, par3, par4 + 1);
		int var8 = par1World.getBlockId(par2 - 1, par3, par4);
		int var9 = par1World.getBlockId(par2 + 1, par3, par4);
		int var10 = par1World.getBlockId(par2 - 1, par3, par4 - 1);
		int var11 = par1World.getBlockId(par2 + 1, par3, par4 - 1);
		int var12 = par1World.getBlockId(par2 + 1, par3, par4 + 1);
		int var13 = par1World.getBlockId(par2 - 1, par3, par4 + 1);
		boolean var14 = var8 == this.blockID || var9 == this.blockID;
		boolean var15 = var6 == this.blockID || var7 == this.blockID;
		boolean var16 = var10 == this.blockID || var11 == this.blockID || var12 == this.blockID || var13 == this.blockID;
		
		for (int var17 = par2 - 1; var17 <= par2 + 1; ++var17)
		{
			for (int var18 = par4 - 1; var18 <= par4 + 1; ++var18)
			{
				int var19 = par1World.getBlockId(var17, par3 - 1, var18);
				float var20 = 0.0F;
				
				if (blocksList[var19] != null && blocksList[var19].canSustainPlant(par1World, var17, par3 - 1, var18, ForgeDirection.UP, this))
				{
					var20 = 1.0F;
					
					if (blocksList[var19].isFertile(par1World, var17, par3 - 1, var18))
					{
						var20 = 3.0F;
					}
				}
				
				if (var17 != par2 || var18 != par4)
				{
					var20 /= 4.0F;
				}
				
				var5 += var20;
			}
		}
		
		if (var16 || var14 && var15)
		{
			var5 /= 2.0F;
		}
		
		return var5;
	}
	
	/**
	 * From the specified side and block metadata retrieves the blocks texture.
	 * Args: side, metadata
	 */
	@Override
	public Icon getIcon(int par1, int par2)
	{
		return par2 < icons.length ? icons[par2] : null;
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		icons = new Icon[maxMeta + 1];
		for (int i = 0; i <= maxMeta; i++)
		{
			icons[i] = par1IconRegister.registerIcon(texture + "_" + (i + 1));
		}
	}
	
	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return 6;
	}
	
	/**
	 * Generate a seed ItemStack for this crop.
	 */
	protected ItemStack getSeedItem()
	{
		seed.stackSize = 1;
		return seed.copy();
	}
	
	/**
	 * Generate a crop produce ItemStack for this crop.
	 */
	protected ItemStack getCropItem()
	{
		crop.stackSize = 1;
		return crop.copy();
	}
	
	/**
	 * Drops the block items with a specified chance of dropping the specified
	 * items
	 */
	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = super.getBlockDropped(world, x, y, z, metadata, fortune);
		
		if (metadata >= maxMeta)
			for (int n = 0; n < 3 + fortune; n++)
			{
				if (world.rand.nextInt(maxMeta + 2) <= metadata)
				{
					ret.add(getSeedItem());
				}
			}
		
		return ret;
	}
	
	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return par1 >= maxMeta ? this.getCropItem().itemID : this.getSeedItem().itemID;
	}
	
	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int damageDropped(int par1)
	{
		return par1 >= maxMeta ? this.getCropItem().getItemDamage() : this.getSeedItem().getItemDamage();
	}
	
	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}
	
	@Override
	public int getDamageValue(World par1World, int par2, int par3, int par4)
	{
		return super.getDamageValue(par1World, par2, par3, par4);
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return seed;
	}
}
