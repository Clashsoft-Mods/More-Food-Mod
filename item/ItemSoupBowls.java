package clashsoft.mods.morefood.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemSoupBowls extends ItemFoodMoreFood
{
	public static final String[]	soups		= new String[] { "Water Bowl", "Potato Soup", "Carrot Soup", "Vegetable Soup", "Tomato Soup", "Tomato Soup with Rice", "Pasta Soup" };
	public static final String[]	iconNames	= new String[] { "bowl_water", "bowl_potato", "bowl_carrot", "bowl_vegetables", "bowl_tomato", "bowl_tomato_rice", "bowl_pasta" };
	
	public Icon[]					icons;
	public Icon[]					icons_hot;
	
	public ItemSoupBowls(int itemID, int healAmount)
	{
		super(itemID, healAmount, 1.0F);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		stack.stackSize--;
		player.getFoodStats().addStats(getHealAmount() + (isSalted(stack) ? 1 : 0) + (isPeppered(stack) ? 1 : 0) + (isHot(stack.getItemDamage()) ? 1 : 0), 1.0F);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		
		return stack;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.drink;
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		String s = isHot(stack.getItemDamage()) ? "Hot " : "";
		return s + soups[stack.getItemDamage() % soups.length];
	}
	
	@Override
	public Icon getIconFromDamage(int metadata)
	{
		return isHot(metadata) ? icons_hot[metadata % 7] : icons[metadata % 7];
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		icons = new Icon[iconNames.length];
		icons_hot = new Icon[iconNames.length];
		for (int i = 0; i < iconNames.length; i++)
		{
			icons[i] = iconRegister.registerIcon(iconNames[i]);
			icons_hot[i] = iconRegister.registerIcon(iconNames[i] + "_hot");
		}
	}
	
	private boolean isHot(int metadata)
	{
		return (metadata & 8) != 0;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		if (isHot(stack.getItemDamage()))
		{
			list.add("Hot");
		}
		if (isSalted(stack))
		{
			list.add("Salted");
		}
		if (isPeppered(stack))
		{
			list.add("Peppered");
		}
	}
	
	public boolean isSalted(ItemStack stack)
	{
		if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("Salted"))
		{
			return stack.getTagCompound().getBoolean("Salted");
		}
		return false;
	}
	
	public boolean isPeppered(ItemStack stack)
	{
		if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("Peppered"))
		{
			return stack.getTagCompound().getBoolean("Peppered");
		}
		return false;
	}
	
	public ItemStack addModifierToItemStack(ItemStack stack, boolean salted, boolean peppered)
	{
		setModifiers(stack, salted, peppered);
		return stack;
	}
	
	public void setModifiers(ItemStack stack, boolean salted, boolean peppered)
	{
		if (stack != null && stack.hasTagCompound())
		{
			stack.getTagCompound().setBoolean("Salted", salted);
			stack.getTagCompound().setBoolean("Peppered", peppered);
		}
	}
	
	@Override
	public void getSubItems(int itemID, CreativeTabs creativeTabs, List list)
	{
		for (int i = 0; i < 14; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}
}
