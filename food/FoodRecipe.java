package clashsoft.mods.morefood.food;

import clashsoft.clashsoftapi.util.CSCrafting;
import clashsoft.clashsoftapi.util.IItemMetadataRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FoodRecipe implements IItemMetadataRecipe
{
	public static final int CRAFTING = 0;
	public static final int CRAFTING_SHAPELESS = 1;
	public static final int FURNACE = 2;
	
	private int amount;
	private int craftingType;
	private Object[] data;
	
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
			GameRegistry.addShapelessRecipe(new ItemStack(item, this.amount, foodID), data); break;
		case FURNACE:
			CSCrafting.addSmelting((ItemStack)data[0], new ItemStack(item, this.amount, foodID), (float)data[1]); break;
		}
	}
	
	@Override
	public int getAmount()
	{
		return amount;
	}
	
	@Override
	public int getCraftingType()
	{
		return craftingType;
	}
	
	@Override
	public Object[] getData()
	{
		return data;
	}
}