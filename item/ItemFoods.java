package clashsoft.mods.morefood.item;

import java.util.List;

import clashsoft.cslib.minecraft.world.CSWorld;
import clashsoft.mods.morefood.food.Food;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemFoods extends ItemFoodMoreFood
{
	public IIcon[]	icons;
	
	public ItemFoods(int healAmount, float saturationModifier)
	{
		super(healAmount, saturationModifier);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabFood);
	}
	
	public boolean isEdible(ItemStack stack)
	{
		Food food = Food.fromItemStack(stack);
		return food != null && food.isEnabled() && food.getFoodValue() != 0;
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		if (this.isEdible(stack))
		{
			--stack.stackSize;
			player.getFoodStats().addStats(Food.fromItemStack(stack).getFoodValue(), 1.0F);
			world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			this.onFoodEaten(stack, world, player);
		}
		return stack;
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		super.onFoodEaten(stack, world, player);
		
		Food f = Food.fromItemStack(stack);
		if (f != null)
		{
			for (PotionEffect effect : f.getEffects())
			{
				player.addPotionEffect(effect);
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
		return this.isEdible(stack) ? Food.fromItemStack(stack).getAction() : EnumAction.none;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return Food.fromItemStack(stack).getName();
	}
	
	@Override
	public IIcon getIconFromDamage(int metadata)
	{
		return this.icons != null ? this.icons[metadata % this.icons.length] : null;
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
				this.icons[i] = iconRegister.registerIcon(f.getIconName());
			}
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (Food f : Food.foodList)
		{
			if (f != null && f.isEnabled() && f.getID() != -1)
			{
				list.add(f.asStack());
			}
		}
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		Food food = Food.fromItemStack(stack);
		Block block = food.getBlockPlaced();
		int metaPlaced = food.getMetadataPlaced();
		
		if (block == null)
		{
			return false;
		}
		else if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
		{
			Block soil = world.getBlock(x, y, z);
			
			if (soil != null && block instanceof IPlantable && soil.canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable) block) && world.isAirBlock(x, y + 1, z))
			{
				world.setBlock(x, y + 1, z, block, metaPlaced, 2);
				--stack.stackSize;
				return true;
			}
			else
			{
				if (block.canPlaceBlockAt(world, x + CSWorld.sideMap[side][0], y + CSWorld.sideMap[side][1], z + CSWorld.sideMap[side][2]))
				{
					CSWorld.setBlockAtSide(world, x, y, z, side, block, metaPlaced);
					--stack.stackSize;
					return true;
				}
			}
		}
		return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
}
