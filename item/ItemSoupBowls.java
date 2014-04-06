package clashsoft.mods.morefood.item;

import java.util.List;

import clashsoft.cslib.minecraft.lang.I18n;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSoupBowls extends ItemFood
{
	public static final String[]	names		= new String[] {
			"item.soup.water",
			"item.soup.potato",
			"item.soup.carrot",
			"item.soup.vegetable",
			"item.soup.tomato",
			"item.soup.tomato_rice",
			"item.soup.pasta"					};
	
	public static final String[]	iconNames	= new String[] {
			"morefood:soup_water",
			"morefood:soup_potato",
			"morefood:soup_carrot",
			"morefood:soup_vegetables",
			"morefood:soup_tomato",
			"morefood:soup_tomato_rice",
			"morefood:soup_pasta"				};
	
	public IIcon[]					icons;
	
	public ItemSoupBowls(int healAmount)
	{
		super(healAmount, 1.0F, true);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		stack.stackSize--;
		
		int heal = this.func_150905_g(stack);
		if (this.isHot(stack.getItemDamage()))
		{
			heal++;
		}
		if (this.isSalted(stack))
		{
			heal++;
		}
		if (this.isPeppered(stack))
		{
			heal++;
		}
		
		player.getFoodStats().addStats(heal, this.func_150906_h(stack));
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		
		return new ItemStack(Items.bowl);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.drink;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return names[stack.getItemDamage() % names.length];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int metadata)
	{
		return this.icons[metadata % this.icons.length];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int metadata, int pass)
	{
		if (pass == 1 && this.isHot(metadata))
		{
			return this.itemIcon;
		}
		return this.getIconFromDamage(metadata);
	}
	
	@Override
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon("morefood:soup_hot");
		
		this.icons = new IIcon[iconNames.length];
		for (int i = 0; i < iconNames.length; i++)
		{
			this.icons[i] = iconRegister.registerIcon(iconNames[i]);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		if (this.isHot(stack.getItemDamage()))
		{
			list.add(I18n.getString("soup.hot"));
		}
		if (this.isSalted(stack))
		{
			list.add(I18n.getString("soup.salt"));
		}
		if (this.isPeppered(stack))
		{
			list.add(I18n.getString("soup.pepper"));
		}
	}
	
	public boolean isHot(int metadata)
	{
		return metadata > 6;
	}
	
	public boolean isSalted(ItemStack stack)
	{
		if (stack != null && stack.hasTagCompound())
		{
			return stack.getTagCompound().getBoolean("salt");
		}
		return false;
	}
	
	public boolean isPeppered(ItemStack stack)
	{
		if (stack != null && stack.hasTagCompound())
		{
			return stack.getTagCompound().getBoolean("pepper");
		}
		return false;
	}
	
	public ItemStack setModifiers(ItemStack stack, boolean salted, boolean peppered)
	{
		if (stack != null)
		{
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt == null)
			{
				nbt = new NBTTagCompound();
				stack.setTagCompound(nbt);
			}
			nbt.setBoolean("salt", salted);
			nbt.setBoolean("pepper", peppered);
		}
		return stack;
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
