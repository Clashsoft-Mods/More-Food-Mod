package clashsoft.mods.morefood.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotRecipeDisplay extends Slot
{
	public SlotRecipeDisplay(IInventory inventory, int slotID, int x, int y)
	{
		super(inventory, slotID, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}
	
	@Override
	public void putStack(ItemStack stack)
	{
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player)
	{
		return false;
	}
}
