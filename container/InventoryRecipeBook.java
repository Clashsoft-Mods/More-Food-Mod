package clashsoft.mods.morefood.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryRecipeBook implements IInventory
{
	public ItemStack[][]	stacks	= new ItemStack[3][3];
	
	@Override
	public int getSizeInventory()
	{
		return 9;
	}
	
	@Override
	public ItemStack getStackInSlot(int i)
	{
		return stacks[(i / 3) % 3][i % 3];
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (stacks[(i / 3) % 3][i % 3] != null)
			stacks[(i / 3) % 3][i % 3].stackSize -= j;
		return stacks[(i / 3) % 3][i % 3];
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		stacks[(i / 3) % 3][i % 3] = itemstack;
	}
	
	@Override
	public String getInvName()
	{
		return "";
	}
	
	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public void onInventoryChanged()
	{
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}
	
	@Override
	public void openChest()
	{
	}
	
	@Override
	public void closeChest()
	{
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}
	
}
