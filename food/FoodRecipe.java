package clashsoft.mods.morefood.food;

import clashsoft.cslib.minecraft.crafting.CSCrafting;
import clashsoft.cslib.minecraft.item.meta.IMetaItemRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FoodRecipe implements IMetaItemRecipe
{
	private int				amount;
	private int				craftingType;
	private Object[]		data;
	private ItemStack[][]	analysed;
	
	public FoodRecipe(int craftingType, int amount, Object... data)
	{
		this.craftingType = craftingType;
		this.amount = amount;
		this.data = data;
	}
	
	public FoodRecipe(int amount, ItemStack... data)
	{
		this(CRAFTING_SHAPELESS, amount, (Object[]) data);
	}
	
	public FoodRecipe(int amount, ItemStack input, float exp)
	{
		this(FURNACE, amount, new Object[] { input, exp });
	}
	
	public String getTypeString()
	{
		switch (this.getCraftingType())
		{
			case CRAFTING:
				return "Crafting";
			case CRAFTING_SHAPELESS:
				return "Shapeless Crafting";
			case FURNACE:
				return "Smelting";
			default:
				return "Crafting";
		}
	}
	
	public void addRecipe(Item item, int foodID)
	{
		switch (this.craftingType)
		{
			case CRAFTING:
				GameRegistry.addRecipe(new ItemStack(item, this.amount, foodID), this.data);
				break;
			case CRAFTING_SHAPELESS:
				GameRegistry.addShapelessRecipe(new ItemStack(item, this.amount, foodID), this.data);
				break;
			case FURNACE:
				Float f = (Float) this.data[1];
				CSCrafting.addSmelting((ItemStack) this.data[0], new ItemStack(item, this.amount, foodID), f);
				break;
		}
	}
	
	@Override
	public int getAmount()
	{
		return this.amount;
	}
	
	@Override
	public int getCraftingType()
	{
		return this.craftingType;
	}
	
	@Override
	public Object[] getData()
	{
		return this.data;
	}
	
	/**
	 * @return the analysed recipe
	 */
	public ItemStack[][] getAnalysedRecipe()
	{
		return this.analysed == null ? this.analysed = CSCrafting.analyseCrafting(this) : this.analysed;
	}
}