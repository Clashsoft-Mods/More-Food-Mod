package clashsoft.mods.morefood.item;

import java.util.List;

import clashsoft.mods.morefood.MoreFoodMod;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemEdibles extends ItemFoodMoreFood
{
	public static final String[]	names		= new String[] { "Salad", "Cucumber", "Pasta", "Rice", "Chili", "Tomato", "Paprika", "Salami", "Meat Ball", "Onion", "Bread Slice", "Toast", "Toastet Toast", "Cheese", "Cheese Slice", "Raw Bacon Slice", "Hamburger", "Pizza", "Pepper Balls", "Toast with Cheese", "Toast with Salami", "Bacon", "Omelette", "Fried Egg", "Corn", "Popcorn", "Sweet Popcorn", "Salty Popcorn", "Candy cane" };
	public static final String[]	iconNames	= new String[] { "salad", "cucumber", "pasta", "rice", "chili", "tomato", "paprika", "salami", "ham", "onion", "bread_slice", "toast_raw", "toast_toasted", "cheese", "cheese_slice", "bacon_raw", "hamburger", "pizza", "pepperballs", "toast_cheese", "toast_salami", "bacon_cooked", "omelette", "fried_egg", "corn", "popcorn", "popcorn", "popcorn", "candy" };
	
	public static final int[]		blockPlaced	= new int[] { MoreFoodMod.saladPlantID, MoreFoodMod.cucumberPlantID, 0, MoreFoodMod.ricePlantID, MoreFoodMod.chiliPlantID, MoreFoodMod.tomatoPlantID, MoreFoodMod.paprikaPlantID, 0, 0, MoreFoodMod.onionPlantID, 0, 0, 0, 0, 0, 0, 0, 0, MoreFoodMod.pepperPlantID, 0, 0, 0, 0, 0, MoreFoodMod.cornPlantID, 0, 0, 0, 0 };
	public static final int[]		foodRefill	= new int[] { 2, 2, 2, 2, 2, 2, 2, 3, 3, 2, 3, 3, 5, 2, 5, 3, 2, 5, 6, 0, 5, 6, 6, 7, 4 /* Cooked Egg */, 4, 4, 5, 5, 3 };
	public static final boolean[]	edible		= new boolean[] { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true, true, true, true, true /* Cooked egg */, true, true, true, true, true };
	
	public Icon[]					icons;
	
	public ItemEdibles(int par1, int par2, float par3)
	{
		super(par1, par2, par3);
		this.setHasSubtypes(true);
	}
	
	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (edible[par1ItemStack.getItemDamage()])
		{
			--par1ItemStack.stackSize;
			par3EntityPlayer.getFoodStats().addStats(foodRefill[par1ItemStack.getItemDamage()], 1.0F);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
			this.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
		}
		return par1ItemStack;
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (par3EntityPlayer.canEat(false) && edible[par1ItemStack.getItemDamage()])
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
		if (edible[par1ItemStack.getItemDamage()])
		{
			return EnumAction.eat;
		}
		return EnumAction.none;
	}
	
	@Override
	public String getItemDisplayName(ItemStack par1ItemStack)
	{
		if (par1ItemStack.getItemDamage() < names.length)
		{
			return names[par1ItemStack.getItemDamage()];
		}
		return "Unknown Food";
	}
	
	@Override
	public Icon getIconFromDamage(int par1)
	{
		return icons[par1 % icons.length];
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		icons = new Icon[iconNames.length];
		for (int i = 0; i < iconNames.length; i++)
		{
			icons[i] = par1IconRegister.registerIcon(iconNames[i]);
		}
	}
	
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < names.length; i++)
		{
			par3List.add(new ItemStack(this, 1, i));
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
		if (par1ItemStack.getItemDamage() < blockPlaced.length && blockPlaced[par1ItemStack.getItemDamage()] != 0)
		{
			int var11 = par3World.getBlockId(par4, par5, par6);
			
			if (var11 == Block.snow.blockID)
			{
				par7 = 1;
			}
			else if (var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID)
			{
				if (par7 == 0)
				{
					--par5;
				}
				
				if (par7 == 1)
				{
					++par5;
				}
				
				if (par7 == 2)
				{
					--par6;
				}
				
				if (par7 == 3)
				{
					++par6;
				}
				
				if (par7 == 4)
				{
					--par4;
				}
				
				if (par7 == 5)
				{
					++par4;
				}
			}
			
			if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
			{
				return false;
			}
			else if (par1ItemStack.stackSize == 0)
			{
				return false;
			}
			else
			{
				if (par3World.canPlaceEntityOnSide(blockPlaced[par1ItemStack.getItemDamage()], par4, par5, par6, false, par7, (Entity) null, par1ItemStack))
				{
					Block var12 = Block.blocksList[blockPlaced[par1ItemStack.getItemDamage()]];
					int var13 = var12.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, 0);
					
					if (par3World.setBlock(par4, par5, par6, blockPlaced[par1ItemStack.getItemDamage()], var13, 2))
					{
						if (par3World.getBlockId(par4, par5, par6) == blockPlaced[par1ItemStack.getItemDamage()])
						{
							Block.blocksList[blockPlaced[par1ItemStack.getItemDamage()]].onBlockPlacedBy(par3World, par4, par5, par6, par2EntityPlayer, par1ItemStack);
							Block.blocksList[blockPlaced[par1ItemStack.getItemDamage()]].onPostBlockPlaced(par3World, par4, par5, par6, var13);
						}
						
						par3World.playSoundEffect((double) ((float) par4 + 0.5F), (double) ((float) par5 + 0.5F), (double) ((float) par6 + 0.5F), var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
						--par1ItemStack.stackSize;
					}
					return true;
				}
				
				return false;
			}
		}
		return false;
	}
}
