package clashsoft.mods.morefood.block;

import java.util.Random;

import clashsoft.mods.morefood.client.MFMClientProxy;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBush extends Block implements IGrowable
{
	/** The metadata value this block has to reach to by fully grown. */
	public int			fullGrownMetadata;
	
	public ItemStack	drop;
	
	public String		bushIconName;
	
	public IIcon		bushIcon;
	
	/**
	 * Instantiates a new bush block.
	 * 
	 * @param drop
	 *            the drop
	 * @param bushIconName
	 *            the bushtexture
	 * @param stemIconName
	 *            the stemtexture
	 */
	public BlockBush(String bushIconName, String stemIconName)
	{
		super(Material.plants);
		this.setBlockTextureName(stemIconName);
		this.bushIconName = bushIconName;
		this.fullGrownMetadata = 3;
	}
	
	public BlockBush setItem(ItemStack item)
	{
		this.drop = item;
		return this;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		if (world.getBlockMetadata(x, y, z) == this.fullGrownMetadata)
		{
			this.setBlockBounds(0.1F, 0F, 0.1F, 0.9F, 0.9F, 0.9F);
		}
		else
		{
			this.setBlockBounds(0.3F, 0F, 0.3F, 0.7F, 0.9F, 0.7F);
		}
		this.setBlockBoundsForItemRender();
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) == this.fullGrownMetadata ? 0.6F : 0.2F;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		super.updateTick(world, x, y, z, random);
		
		int i = world.getBlockMetadata(x, y, z);
		if (i < this.fullGrownMetadata && random.nextInt(100) == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, i + 1, 2);
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y - 1, z);
		return block == Blocks.grass || block == Blocks.dirt && super.canPlaceBlockAt(world, x, y, z);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata)
	{
		if (metadata == this.fullGrownMetadata)
		{
			world.setBlock(x, y, z, this, 0, 2);
		}
	}
	
	@Override
	public Item getItem(World world, int x, int y, int z)
	{
		return this.drop.getItem();
	}
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return this.drop.getItem();
	}
	
	@Override
	public int damageDropped(int metadata)
	{
		return this.drop.getItemDamage();
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		if (meta == this.fullGrownMetadata)
		{
			return random.nextInt(2) + 2;
		}
		else
		{
			return 0;
		}
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		super.registerBlockIcons(iconRegister);
		this.bushIcon = iconRegister.registerIcon(this.bushIconName);
	}
	
	@Override
	public int getRenderType()
	{
		return MFMClientProxy.BUSH_RENDER_ID;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		return true;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	// canApplyBonemeal
	@Override
	public boolean func_149851_a(World world, int x, int y, int z, boolean flag)
	{
		return world.getBlockMetadata(x, y, z) < this.fullGrownMetadata;
	}
	
	// ???
	@Override
	public boolean func_149852_a(World world, Random random, int x, int y, int z)
	{
		return true;
	}
	
	// fertilize
	@Override
	public void func_149853_b(World world, Random random, int x, int y, int z)
	{
		world.setBlockMetadataWithNotify(x, y, z, this.fullGrownMetadata, 2);
	}
}
