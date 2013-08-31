package clashsoft.mods.morefood.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFoodMoreFood extends ItemFood
{
	/** Number of ticks to run while 'EnumAction'ing until result. */
	public final int	itemUseDuration;
	
	/** The amount this food item heals the player. */
	private final int	healAmount;
	private final float	saturationModifier;
	
	/**
	 * If this field is true, the food can be consumed even if the player don't
	 * need to eat.
	 */
	public boolean		alwaysEdible;
	
	/**
	 * represents the potion effect that will occurr upon eating this food. Set
	 * by setPotionEffect
	 */
	private int			potionId;
	
	/** set by setPotionEffect */
	private int			potionDuration;
	
	/** set by setPotionEffect */
	private int			potionAmplifier;
	
	/** probably of the set potion effect occurring */
	private float		potionEffectProbability;
	
	public ItemFoodMoreFood(int par1, int par2, float par3)
	{
		super(par1, par2, par3, false);
		this.itemUseDuration = 32;
		this.healAmount = par2;
		this.saturationModifier = par3;
		this.setCreativeTab(CreativeTabs.tabFood);
	}
	
	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		--par1ItemStack.stackSize;
		par3EntityPlayer.getFoodStats().addStats(this);
		par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
		this.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
		return par1ItemStack;
	}
	
	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par2World.isRemote && this.potionId > 0 && par2World.rand.nextFloat() < this.potionEffectProbability)
		{
			par3EntityPlayer.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
		}
	}
	
	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}
	
	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.eat;
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (par3EntityPlayer.canEat(this.alwaysEdible))
		{
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		
		return par1ItemStack;
	}
	
	@Override
	public int getHealAmount()
	{
		return this.healAmount;
	}
	
	/**
	 * gets the saturationModifier of the ItemFood
	 */
	@Override
	public float getSaturationModifier()
	{
		return this.saturationModifier;
	}
	
}
