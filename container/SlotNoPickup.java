package clashsoft.mods.morefood.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotNoPickup extends Slot
{
	public SlotNoPickup(IInventory par1iInventory, int par2, int par3, int par4)
	{
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		return super.isItemValid(par1ItemStack);
	}

	@Override
	public boolean getHasStack()
	{
		return false;
	}

	@Override
	public void putStack(ItemStack par1ItemStack)
	{
		;
	}

	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
	{
		return false;
	}
}
