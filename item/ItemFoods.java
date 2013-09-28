package clashsoft.mods.morefood.item;

import java.util.List;

import clashsoft.clashsoftapi.util.CSWorld;
import clashsoft.mods.morefood.food.Food;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class ItemFoods extends ItemFoodMoreFood
{
	public Icon[]	icons;
	
	public ItemFoods(int par1, int par2, float par3)
	{
		super(par1, par2, par3);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabFood);
	}
	
	public static boolean isEdible(ItemStack stack)
	{
		return Food.fromItemStack(stack) != null && Food.fromItemStack(stack).isEnabled() && Food.fromItemStack(stack).getFoodValue() != 0;
	}
	
	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (isEdible(par1ItemStack))
		{
			--par1ItemStack.stackSize;
			par3EntityPlayer.getFoodStats().addStats(Food.fromItemStack(par1ItemStack).getFoodValue(), 1.0F);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
			this.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
		}
		return par1ItemStack;
	}
	
	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
		
		Food f = Food.fromItemStack(par1ItemStack);
		if (f != null)
			for (PotionEffect effect : f.getEffects())
				par3EntityPlayer.addPotionEffect(effect);
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (par3EntityPlayer.canEat(false) && isEdible(par1ItemStack))
		{
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		
		return par1ItemStack;
	}
	
	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}
	
	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return isEdible(par1ItemStack) ? Food.fromItemStack(par1ItemStack).getAction() : EnumAction.none;
	}
	
	@Override
	public String getItemDisplayName(ItemStack par1ItemStack)
	{
		return Food.fromItemStack(par1ItemStack).getName();
	}
	
	@Override
	public Icon getIconFromDamage(int par1)
	{
		return icons[par1 % icons.length];
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		icons = new Icon[Food.foodTypes.length];
		for (int i = 0; i < Food.foodTypes.length; i++)
		{
			Food f = Food.foodTypes[i];
			if (f != null)
				icons[i] = par1IconRegister.registerIcon(f.getIconName());
		}
	}
	
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (Food f : Food.foodList)
		{
			if (f != null && f.isEnabled() && f.getID() != -1)
				par3List.add(f.asStack());
		}
	}
	
	/**
	 * Callback for item usage. If the item does something special on right
	 * clicking, he will have one of those. Return True if something happen and
	 * false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
		Food food = Food.fromItemStack(par1ItemStack);
		int blockPlaced = food.getBlockPlaced();
		int metaPlaced = food.getMetadataPlaced();
		if (par7 != 1 || blockPlaced <= 0)
		{
			return false;
		}
		else if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack))
		{
			int i1 = par3World.getBlockId(par4, par5, par6);
			Block plant = Block.blocksList[blockPlaced];
			Block soil = Block.blocksList[i1];
			
			if (soil != null && plant instanceof IPlantable && soil.canSustainPlant(par3World, par4, par5, par6, ForgeDirection.UP, (IPlantable) plant) && par3World.isAirBlock(par4, par5 + 1, par6))
			{
				par3World.setBlock(par4, par5 + 1, par6, blockPlaced, metaPlaced, 2);
				--par1ItemStack.stackSize;
				return true;
			}
			else
			{
				if (plant.canPlaceBlockAt(par3World, par4 + CSWorld.sideMap[par7][0], par5 + CSWorld.sideMap[par7][1], par6 + CSWorld.sideMap[par7][2]))
				{
					CSWorld.setBlockAtSide(par3World, par4, par5, par6, par7, blockPlaced, metaPlaced);
					--par1ItemStack.stackSize;
					return true;
				}
			}
		}
		return false;
	}
}
