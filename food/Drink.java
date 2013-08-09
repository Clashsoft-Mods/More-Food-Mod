package clashsoft.mods.morefood.food;

import net.minecraft.item.EnumAction;

public class Drink extends Food
{
	public Drink(int id, String name, String icon, int foodValue)
	{
		this(id, name, icon, foodValue, null);
	}
	
	public Drink(int id, String name, String icon, int foodValue, FoodRecipe recipe)
	{
		super(512 + id, name, icon, foodValue, 0, recipe);
	}

	@Override
	public EnumAction getAction()
	{
		return EnumAction.drink;
	}
	
	public static void init() {}
}
