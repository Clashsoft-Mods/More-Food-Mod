package clashsoft.mods.morefood.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemMoreFood extends Item
{
	public ItemMoreFood(int par1)
	{
		super(par1);
		this.setCreativeTab(CreativeTabs.tabFood);
	}
	
	@Override
	public Item setUnlocalizedName(String par1Str)
	{
		this.setTextureName(par1Str);
		return super.setUnlocalizedName(par1Str);
	}
}
