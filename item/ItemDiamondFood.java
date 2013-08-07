package clashsoft.mods.morefood.item;

import clashsoft.mods.morefood.MoreFoodMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.potion.*;

public class ItemDiamondFood extends ItemFood
{
	public ItemDiamondFood(int par1, int par2, float par3, boolean par4)
	{
		super(par1, par2, par3, par4);
	}
	
	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par2World.isRemote)
		{
			if (this.itemID == MoreFoodMod.diamondCarrot.itemID)
			{
				par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, 60000, 1));
			}
			else if (this.itemID == MoreFoodMod.diamondPotato.itemID)
			{
				par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 6000, 3));
				par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60000, 1));
			}
			else if (this.itemID == MoreFoodMod.diamondApple.itemID)
			{
				par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.jump.id, 6000, 3));
				par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 60000, 1));
				par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 60000, 1));
			}
		}
		else
		{
			super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
		}
	}
}
