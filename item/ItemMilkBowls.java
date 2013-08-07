package clashsoft.mods.morefood.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemMilkBowls extends ItemFoodMoreFood
{
	public static final String[]	bowls		= new String[] { "Bowl and Milk", "Milk and Cereals", "Milk and Cereals", "Milk and Cereals", "Rice Pudding", "Rice Pudding with Cinnamon", "Rice Pudding with Vanilla", "Pudding", "Pudding" };
	public static final String[]	iconNames	= new String[] { "bowl_milk", "bowl_cereals_1", "bowl_cereals_2", "bowl_cereals_3", "bowl_rice", "bowl_rice_cinnamon", "bowl_rice_vanilla", "bowl_pudding_1", "bowl_pudding_2" };
	
	public Icon[]					icons;
	
	public ItemMilkBowls(int par1, int par2)
	{
		super(par1, par2, 1.0F);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
	
	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
		return new ItemStack(Item.bowlEmpty);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		return EnumAction.drink;
	}
	
	@Override
	public String getItemDisplayName(ItemStack par1ItemStack)
	{
		if (par1ItemStack.getItemDamage() < bowls.length)
			return bowls[par1ItemStack.getItemDamage()];
		return "Bowl with unknown substance";
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
		for (int i = 0; i < bowls.length; i++)
		{
			par3List.add(new ItemStack(par1, 1, i));
		}
	}
}
