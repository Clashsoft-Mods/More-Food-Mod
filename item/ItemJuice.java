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
	private String[]	names	= new String[] { "apple", "orange", "tomato" };
	
	private IIcon[]		icons;
	
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
	public String getItemStackDisplayName(ItemStack stack)
	{
		switch (stack.getItemDamage())
		{
			case 0:
				return "Apple Juice";
			case 1:
				return "Orange Juice";
			default:
				return "Tomato Juice";
		}
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
		return this.icons[metadata % this.icons.length];
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		this.icons = new IIcon[this.names.length];
		for (int i = 0; i < this.names.length; i++)
		{
			this.icons[i] = iconRegister.registerIcon("juice_" + this.names[i]);
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i <= 2; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}
}
