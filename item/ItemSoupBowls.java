package clashsoft.mods.morefood.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemSoupBowls extends ItemFoodMoreFood
{
	public static final String[]	soups		= new String[] { "Water Bowl", "Potato Soup", "Carrot Soup", "Vegetable Soup", "Tomato Soup", "Tomato Soup with Rice", "Pasta Soup" };
	public static final String[]	iconNames	= new String[] { "bowl_water", "bowl_potato", "bowl_carrot", "bowl_vegetables", "bowl_tomato", "bowl_tomato_rice", "bowl_pasta" };
	
	public Icon[]					icons;
	public Icon[]					icons_hot;
	
	public ItemSoupBowls(int par1, int par2)
	{
		super(par1, par2, 1.0F);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
	
	@Override
	public void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		--par1ItemStack.stackSize;
		par3EntityPlayer.getFoodStats().addStats(getHealAmount() + (isSalted(par1ItemStack) ? 1 : 0) + (isPeppered(par1ItemStack) ? 1 : 0) + (isHot(par1ItemStack.getItemDamage()) ? 1 : 0), 1.0F);
		par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
		super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		return EnumAction.drink;
	}
	
	@Override
	public String getItemDisplayName(ItemStack par1ItemStack)
	{
		String s = par1ItemStack.getItemDamage() >= 7 ? "Hot " : "";
		return s + soups[par1ItemStack.getItemDamage() % 7];
	}
	
	@Override
	public Icon getIconFromDamage(int par1)
	{
		return isHot(par1) ? icons_hot[par1 % 7] : icons[par1 % 7];
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		icons = new Icon[iconNames.length];
		icons_hot = new Icon[iconNames.length];
		for (int i = 0; i < iconNames.length; i++)
		{
			icons[i] = par1IconRegister.registerIcon(iconNames[i]);
			icons_hot[i] = par1IconRegister.registerIcon(iconNames[i] + "_hot");
		}
	}
	
	private boolean isHot(int par1)
	{
		return par1 >= 7;
	}
	
	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 */
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (isHot(par1ItemStack.getItemDamage()))
		{
			par3List.add("Hot");
		}
		if (isSalted(par1ItemStack))
		{
			par3List.add("Salted");
		}
		if (isPeppered(par1ItemStack))
		{
			par3List.add("Peppered");
		}
	}
	
	public static boolean isSalted(ItemStack par1ItemStack)
	{
		if (par1ItemStack != null)
		{
			if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("Modifiers"))
			{
				boolean var1 = false;
				NBTTagList var3 = par1ItemStack.getTagCompound().getTagList("Modifiers");
				
				NBTTagCompound var5 = (NBTTagCompound) var3.tagAt(0);
				var1 = var5.getBoolean("Salted");
				return var1;
			}
		}
		return false;
	}
	
	public static boolean isPeppered(ItemStack par1ItemStack)
	{
		if (par1ItemStack != null)
		{
			if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("Modifiers"))
			{
				boolean var1 = false;
				NBTTagList var3 = par1ItemStack.getTagCompound().getTagList("Modifiers");
				
				NBTTagCompound var5 = (NBTTagCompound) var3.tagAt(0);
				if (var5.hasKey("Peppered"))
				{
					var1 = var5.getBoolean("Peppered");
				}
				return var1;
			}
		}
		return false;
	}
	
	public static ItemStack addModifierToItemStack(ItemStack par1, boolean par2, boolean par3)
	{
		if (par1 != null)
		{
			ItemStack var1 = par1;
			if (par1.stackTagCompound == null)
			{
				par1.setTagCompound(new NBTTagCompound());
			}
			
			if (!par1.stackTagCompound.hasKey("Modifiers"))
			{
				par1.stackTagCompound.setTag("Modifiers", new NBTTagList("Modifiers"));
			}
			NBTTagList var2 = (NBTTagList) var1.stackTagCompound.getTag("Modifiers");
			var2.appendTag(createNBT(par1, par2, par3));
			
			return var1;
		}
		else
		{
			return null;
		}
	}
	
	private static NBTTagCompound createNBT(ItemStack par1, boolean par2, boolean par3)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("Salted", par2);
		nbt.setBoolean("Peppered", par3);
		return nbt;
	}
	
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < 14; i++)
		{
			par3List.add(new ItemStack(par1, 1, i));
		}
	}
}
