package clashsoft.mods.morefood.block;

import java.util.Random;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockBush extends BlockFlower
{
	public ItemStack drop;
	public String bushTexture;
	public String stemTexture;
	public Icon bushIcon;
	public Icon stemIcon;
	
	public BlockBush(int par1, ItemStack drop, String bushtexture, String stemtexture)
	{
		super(par1, Material.plants);
		
		this.drop = drop;
		this.bushTexture = bushtexture;
		this.stemTexture = stemtexture;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.BlockFlower#updateTick(net.minecraft.world.World, int, int, int, java.util.Random)
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		super.updateTick(par1World, par2, par3, par4, par5Random);
		
		int i = par1World.getBlockMetadata(par2, par3, par4);
		if (i < 3 && par5Random.nextInt(100) == 0)
			par1World.setBlockMetadataWithNotify(par2, par3, par4, i + 1, 2);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#breakBlock(net.minecraft.world.World, int, int, int, int, int)
	 */
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		
		par1World.setBlock(par2, par3, par4, this.blockID, 0, 2);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#idDropped(int, java.util.Random, int)
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return drop.getItemDamage();
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#damageDropped(int)
	 */
	@Override
	public int damageDropped(int par1)
	{
		return drop.getItemDamage();
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#quantityDropped(int, int, java.util.Random)
	 */
	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		if (meta == 3)
			return random.nextInt(2) + 2;
		else
			return 1;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#registerIcons(net.minecraft.client.renderer.texture.IconRegister)
	 */
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.bushIcon = par1IconRegister.registerIcon(bushTexture);
		this.stemIcon = par1IconRegister.registerIcon(stemTexture);
	}
	
}
