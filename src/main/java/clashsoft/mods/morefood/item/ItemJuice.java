package clashsoft.mods.morefood.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemJuice extends Item
{
	public static final String[]	names		= new String[] { "item.juice.apple", "item.juice.orange", "item.juice.tomato" };
	
	public static final String[]	iconNames	= new String[] { "morefood:juice_apple", "morefood:juice_orange", "morefood:juice_tomato" };
	
	private IIcon[]					icons;
	
	public ItemJuice()
	{
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabFood);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.drink;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return names[stack.getItemDamage()];
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			if (!player.capabilities.isCreativeMode)
			{
				--stack.stackSize;
			}
			
			if (!player.capabilities.isCreativeMode)
			{
				if (stack.stackSize <= 0)
				{
					return new ItemStack(Items.glass_bottle);
				}
				
				player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
			}
		}
		
		return stack;
	}
	
	@Override
	public IIcon getIconFromDamage(int metadata)
	{
		return this.icons[metadata];
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		this.icons = new IIcon[names.length];
		
		for (int i = 0; i < names.length; i++)
		{
			this.icons[i] = iconRegister.registerIcon(iconNames[i]);
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < 3; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}
}
