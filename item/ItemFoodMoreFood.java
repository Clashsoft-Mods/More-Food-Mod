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
	public final int		itemUseDuration;
	
	/** The amount this food item heals the player. */
	protected final int		healAmount;
	protected final float	saturationModifier;
	
	/**
	 * If this field is true, the food can be consumed even if the player don't
	 * need to eat.
	 */
	public boolean			alwaysEdible;
	
	/**
	 * represents the potion effect that will occurr upon eating this food. Set
	 * by setPotionEffect
	 */
	private int				potionId;
	
	/** set by setPotionEffect */
	private int				potionDuration;
	
	/** set by setPotionEffect */
	private int				potionAmplifier;
	
	/** probably of the set potion effect occurring */
	private float			potionEffectProbability;
	
	public ItemFoodMoreFood(int healAmount, float saturationModifier)
	{
		super(healAmount, saturationModifier, false);
		this.itemUseDuration = 32;
		this.healAmount = healAmount;
		this.saturationModifier = saturationModifier;
		this.setCreativeTab(CreativeTabs.tabFood);
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		--stack.stackSize;
		player.getFoodStats().addStats(this.healAmount, this.saturationModifier);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		this.onFoodEaten(stack, world, player);
		return stack;
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote && this.potionId > 0 && world.rand.nextFloat() < this.potionEffectProbability)
		{
			player.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.eat;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (player.canEat(this.alwaysEdible))
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
		
		return stack;
	}
	
}
