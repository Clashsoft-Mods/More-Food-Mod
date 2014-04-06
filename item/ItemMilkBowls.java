package clashsoft.mods.morefood.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMilkBowls extends ItemFood
{
	public static final String[]	names		= new String[] {
			"item.bowl.milk",
			"item.bowl.cereals_wheat",
			"item.bowl.cereals_cocoa",
			"item.bowl.cereals_mixed",
			"item.bowl.rice",
			"item.bowl.rice.cinnamon",
			"item.bowl.rice.vanilla",
			"item.bowl.pudding.red",
			"item.bowl.pudding.green"		};
	public static final String[]	iconNames	= new String[] {
			"morefood:bowl_milk",
			"morefood:bowl_cereals_wheat",
			"morefood:bowl_cereals_cocoa",
			"morefood:bowl_cereals_mixed",
			"morefood:bowl_rice",
			"morefood:bowl_rice_cinnamon",
			"morefood:bowl_rice_vanilla",
			"morefood:bowl_pudding_red",
			"morefood:bowl_pudding_green"		};
	
	public IIcon[]					icons;
	
	public ItemMilkBowls(int healAmount)
	{
		super(healAmount, 1.0F, true);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		super.onFoodEaten(stack, world, player);
		return new ItemStack(Items.bowl);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		return EnumAction.drink;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if (stack.getItemDamage() < names.length)
		{
			return names[stack.getItemDamage()];
		}
		return "bowl.unknown";
	}
	
	@Override
	public IIcon getIconFromDamage(int metadata)
	{
		return this.icons[metadata];
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		this.icons = new IIcon[iconNames.length];
		for (int i = 0; i < iconNames.length; i++)
		{
			this.icons[i] = iconRegister.registerIcon(iconNames[i]);
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < names.length; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}
}
