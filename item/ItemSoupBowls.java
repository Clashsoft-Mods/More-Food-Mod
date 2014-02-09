package clashsoft.mods.morefood.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSoupBowls extends ItemFoodMoreFood
{
	public static final String[]	soups		= new String[] {
			"Water Bowl",
			"Potato Soup",
			"Carrot Soup",
			"Vegetable Soup",
			"Tomato Soup",
			"Tomato Soup with Rice",
			"Pasta Soup"						};
	public static final String[]	iconNames	= new String[] {
			"bowl_water",
			"bowl_potato",
			"bowl_carrot",
			"bowl_vegetables",
			"bowl_tomato",
			"bowl_tomato_rice",
			"bowl_pasta"						};
	
	public IIcon[]					icons;
	public IIcon[]					icons_hot;
	
	public ItemSoupBowls(int healAmount)
	{
		super(healAmount, 1.0F);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		stack.stackSize--;
		player.getFoodStats().addStats(this.healAmount + (this.isSalted(stack) ? 1 : 0) + (this.isPeppered(stack) ? 1 : 0) + (this.isHot(stack.getItemDamage()) ? 1 : 0), 1.0F);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		
		return stack;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.drink;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String s = this.isHot(stack.getItemDamage()) ? "Hot " : "";
		return s + soups[stack.getItemDamage() % soups.length];
	}
	
	@Override
	public IIcon getIconFromDamage(int metadata)
	{
		return this.isHot(metadata) ? this.icons_hot[metadata % 7] : this.icons[metadata % 7];
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		this.icons = new IIcon[iconNames.length];
		this.icons_hot = new IIcon[iconNames.length];
		for (int i = 0; i < iconNames.length; i++)
		{
			this.icons[i] = iconRegister.registerIcon(iconNames[i]);
			this.icons_hot[i] = iconRegister.registerIcon(iconNames[i] + "_hot");
		}
	}
	
	private boolean isHot(int metadata)
	{
		return (metadata & 8) != 0;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		if (this.isHot(stack.getItemDamage()))
		{
			list.add("Hot");
		}
		if (this.isSalted(stack))
		{
			list.add("Salted");
		}
		if (this.isPeppered(stack))
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
		this.setModifiers(stack, salted, peppered);
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
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < 14; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}
}
