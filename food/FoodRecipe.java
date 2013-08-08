package clashsoft.mods.morefood.food;

import clashsoft.clashsoftapi.util.CSCrafting;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FoodRecipe
{
	public static final int CRAFTING = 0;
	public static final int CRAFTING_SHAPELESS = 1;
	public static final int FURNACE = 2;
	
	public int amount;
	public int craftingType;
	public Object[] data;
	
	public FoodRecipe(int craftingType, int amount, Object... data)
	{
		this.craftingType = craftingType;
		this.amount = amount;
		this.data = data;
	}
	
	public FoodRecipe(int amount, ItemStack... data)
	{
		this(CRAFTING_SHAPELESS, amount, (Object[])data);
	}
	
	public void addRecipe(Item item, int foodID)
	{
		switch(this.craftingType)
		{
		case CRAFTING:
			GameRegistry.addRecipe(new ItemStack(item, this.amount, foodID), data); break;
		case CRAFTING_SHAPELESS:
			GameRegistry.addRecipe(new ItemStack(item, this.amount, foodID), data); break;
		case FURNACE:
			CSCrafting.addSmelting((ItemStack)data[0], new ItemStack(item, this.amount, foodID), (float)data[1]); break;
		}
	}
}