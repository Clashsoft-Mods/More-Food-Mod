package clashsoft.mods.morefood.item;

import java.util.List;

import clashsoft.cslib.minecraft.item.block.ItemCustomBlock;
import clashsoft.mods.morefood.food.Food;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemFoods extends ItemFood
{
	public IIcon[]	icons;
	
	public ItemFoods(int healAmount, float saturationModifier)
	{
		super(healAmount, saturationModifier, false);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabFood);
	}
	
	public boolean isEdible(ItemStack stack)
	{
		Food food = Food.fromItemStack(stack);
		return food != null && food.isEnabled() && food.getFoodValue() != 0;
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		super.onFoodEaten(stack, world, player);
		
		Food f = Food.fromItemStack(stack);
		if (f != null)
		{
			PotionEffect[] effects = f.getEffects();
			if (effects != null)
			{
				for (PotionEffect effect : effects)
				{
					player.addPotionEffect(effect);
				}
			}
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (player.canEat(false) && this.isEdible(stack))
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
		
		return stack;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		Food food = Food.fromItemStack(stack);
		return food == null ? EnumAction.none : food.getAction();
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "item.food." + Food.fromItemStack(stack).getName();
	}
	
	@Override
	public IIcon getIconFromDamage(int metadata)
	{
		return this.icons != null ? this.icons[metadata] : null;
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		Food[] types = Food.foodTypes;
		this.icons = new IIcon[types.length];
		for (int i = 0; i < types.length; i++)
		{
			Food f = types[i];
			if (f != null)
			{
				this.icons[i] = iconRegister.registerIcon("morefood:" + f.getIconName());
			}
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (Food f : Food.foodList)
		{
			if (f != null && f.isEnabled())
			{
				int id = f.getID();
				list.add(new ItemStack(this, 1, id));
			}
		}
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			Food food = Food.fromItemStack(stack);
			if (food != null)
			{
				Block block = food.getBlockPlaced();
				if (block != null)
				{
					int metadata = food.getMetadataPlaced();
					ItemStack stack1 = new ItemStack(block, stack.stackSize, metadata);
					boolean b = new ItemCustomBlock(block).onItemUse(stack1, player, world, x, y, z, side, hitX, hitY, hitZ);
					stack.stackSize = stack1.stackSize;
					return b;
				}
			}
		}
		return false;
	}
}
