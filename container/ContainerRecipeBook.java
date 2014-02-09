package clashsoft.mods.morefood.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerRecipeBook extends Container
{
	public InventoryRecipeBook	inventory;
	
	public ContainerRecipeBook(InventoryRecipeBook inventory)
	{
		this.inventory = inventory;
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				int isposX = 23 + (j * 18);
				int isposY = 116 + (i * 18);
				
				this.addSlotToContainer(new SlotRecipeDisplay(inventory, j + (i * 3), isposX, isposY));
			}
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}
	
}
