package clashsoft.mods.morefood.food;

import java.util.Arrays;

import clashsoft.cslib.minecraft.crafting.CSCrafting;
import clashsoft.cslib.minecraft.item.meta.IMetaItemRecipe;
import clashsoft.cslib.minecraft.lang.I18n;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FoodRecipe implements IMetaItemRecipe
{
	private int				amount;
	private int				craftingType;
	private Object[]		data;
	private ItemStack[][]	analysed;
	
	protected FoodRecipe(int craftingType, int amount, Object... data)
	{
		this.craftingType = craftingType;
		this.amount = amount;
		this.data = data;
	}
	
	public FoodRecipe(int amount, Object... data)
	{
		this(CRAFTING, amount, data);
	}
	
	public FoodRecipe(int amount, ItemStack... data)
	{
		this(CRAFTING_SHAPELESS, amount, (Object[]) data);
	}
	
	public FoodRecipe(int amount, ItemStack input, float exp)
	{
		this(FURNACE, amount, new Object[] { input, exp });
	}
	
	public String getType()
	{
		switch (this.getCraftingType())
		{
		case CRAFTING:
			return "recipe.crafting";
		case CRAFTING_SHAPELESS:
			return "recipe.crafting.shapeless";
		case FURNACE:
			return "recipe.smelting";
		default:
			return "recipe.crafting";
		}
	}
	
	public String getLocalizedType()
	{
		return I18n.getString(this.getType());
	}
	
	public void addRecipe(Item item, int foodID)
	{
		ItemStack result = null;
		try
		{
			result = new ItemStack(item, this.amount, foodID);
			switch (this.craftingType)
			{
			case CRAFTING:
				GameRegistry.addRecipe(result, this.data);
				break;
			case CRAFTING_SHAPELESS:
				GameRegistry.addShapelessRecipe(result, this.data);
				break;
			case FURNACE:
				Float f = (Float) this.data[1];
				CSCrafting.addFurnaceRecipe((ItemStack) this.data[0], result, f);
				break;
			}
		}
		catch (Exception ex)
		{
			System.out.println("Failed to add recipe: " + result + "; " + Arrays.toString(this.data));
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