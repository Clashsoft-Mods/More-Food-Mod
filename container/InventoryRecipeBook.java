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
		return stacks[i / 3][i % 3];
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		ItemStack stack = this.getStackInSlot(i);
		if (stack != null)
		{
			stack.stackSize -= j;
		}
		return stack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		stacks[i / 3][i % 3] = itemstack;
	}
	
	@Override
	public String getInventoryName()
	{
		return "";
	}
	
	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}
	
	@Override
	public void closeInventory()
	{
	}
	
	@Override
	public void markDirty()
	{
	}
	
	@Override
	public void openInventory()
	{
	}
}
