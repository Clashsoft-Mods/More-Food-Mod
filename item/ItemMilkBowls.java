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

public class ItemMilkBowls extends ItemFoodMoreFood
{
	public static final String[]	bowls		= new String[] {
			"Bowl and Milk",
			"Milk and Cereals",
			"Milk and Cereals",
			"Milk and Cereals",
			"Rice Pudding",
			"Rice Pudding with Cinnamon",
			"Rice Pudding with Vanilla",
			"Pudding",
			"Pudding"							};
	public static final String[]	iconNames	= new String[] {
			"bowl_milk",
			"bowl_cereals_1",
			"bowl_cereals_2",
			"bowl_cereals_3",
			"bowl_rice",
			"bowl_rice_cinnamon",
			"bowl_rice_vanilla",
			"bowl_pudding_1",
			"bowl_pudding_2"					};
	
	public IIcon[]					icons;
	
	public ItemMilkBowls(int healAmount)
	{
		super(healAmount, 1.0F);
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
	public String getItemStackDisplayName(ItemStack stack)
	{
		if (stack.getItemDamage() < bowls.length)
		{
			return bowls[stack.getItemDamage()];
		}
		return "Bowl with unknown substance";
	}
	
	@Override
	public IIcon getIconFromDamage(int metadata)
	{
		return this.icons[metadata % this.icons.length];
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
		for (int i = 0; i < bowls.length; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}
}
