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

public class ItemJuice extends ItemMoreFood
{
	private String[]	names	= new String[] { "apple", "orange", "tomato" };
	
	private Icon[]		icons;
	
	public ItemJuice(int par1)
	{
		super(par1);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabFood);
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.drink;
	}
	
	@Override
	public String getItemDisplayName(ItemStack par1ItemStack)
	{
		switch (par1ItemStack.getItemDamage())
		{
		case 0:
			return "Apple Juice";
		case 1:
			return "Orange Juice";
		default:
			return "Tomato Juice";
		}
	}
	
	public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par3EntityPlayer.capabilities.isCreativeMode)
		{
			--par1ItemStack.stackSize;
		}
		
		if (!par3EntityPlayer.capabilities.isCreativeMode)
		{
			if (par1ItemStack.stackSize <= 0)
			{
				return new ItemStack(Item.glassBottle);
			}
			
			par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
		}
		
		return par1ItemStack;
	}
	
	public Icon getIconFromDamage(int par1)
	{
		return icons[par1 % icons.length];
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		icons = new Icon[names.length];
		for (int i = 0; i < names.length; i++)
		{
			icons[i] = par1IconRegister.registerIcon("juice_" + names[i]);
		}
	}
	
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i <= 2; i++)
		{
			par3List.add(new ItemStack(par1, 1, i));
		}
	}
}
