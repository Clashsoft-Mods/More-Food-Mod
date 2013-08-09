package clashsoft.mods.morefood.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerRecipeBook extends Container
{
	public InventoryRecipeBook inventory;
	
	public ContainerRecipeBook(InventoryRecipeBook par1InventoryRecipeBook)
	{
		this.inventory = par1InventoryRecipeBook;
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				int isposX = 22 + (j * 18);
				int isposY = 115 + (i * 18);
				
				this.addSlotToContainer(new SlotNoPickup(par1InventoryRecipeBook, j + (i * 3), isposX, isposY));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}
	
}
