package clashsoft.mods.morefood.food;

import java.util.LinkedList;
import java.util.List;

import clashsoft.mods.morefood.MoreFoodMod;

import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public class Food
{
	public static Food[]		foodTypes		= new Food[256];
	public static List<Food>	foodList		= new LinkedList<Food>();
	
	public static Food			salad			= new Food(0, "Salad", "salad", 2, MoreFoodMod.saladPlantID);
	public static Food			cucumber		= new Food(1, "Cucumber", "cucumber", 2, MoreFoodMod.cucumberPlantID);
	public static Food			rice			= new Food(2, "Rice", "rice", 2, MoreFoodMod.ricePlantID);
	public static Food			chili			= new Food(3, "Chili", "chili", 2, MoreFoodMod.chiliPlantID);
	public static Food			tomato			= new Food(4, "Tomato", "tomato", 2, MoreFoodMod.tomatoPlantID);
	public static Food			paprika			= new Food(5, "Paprika", "paprika", 3, MoreFoodMod.paprikaPlantID);
	public static Food			onion			= new Food(6, "Onion", "onion", 3, MoreFoodMod.onionPlantID);
	public static Food			pepperballs		= new Food(7, "Pepper Balls", "pepperballs", 0, MoreFoodMod.pepperPlantID);
	public static Food			corn			= new Food(8, "Corn", "corn", 4, MoreFoodMod.cornPlantID);
	public static Food			breadslice		= new Food(9, "Bread Slice", "bread_slice", 3, 0);
	public static Food			toast			= new Food(10, "Toast", "toast_raw", 5, 0);
	public static Food			toastet_Toast	= new Food(11, "Toastet Toast", "toast_toasted", 2, 0);
	public static Food			toast_cheese	= new Food(12, "Toast with Cheese", "toast_cheese", 5, 0);
	public static Food			toast_salami	= new Food(13, "Toast with Salami", "toast_salami", 6, 0);
	public static Food			cheese			= new Food(14, "Cheese", "cheese", 5, 0);
	public static Food			cheese_slice	= new Food(15, "Cheese Slice", "cheese_slice", 3, 0);
	public static Food			bacon_raw		= new Food(16, "Raw Bacon Slice", "bacon_raw", 2, 0);
	public static Food			bacon			= new Food(17, "Bacon", "bacon_cooked", 6, 0);
	public static Food			pasta			= new Food(18, "Pasta", "pasta", 2, 0);
	public static Food			meatball		= new Food(19, "Meat Ball", "ham", 2, 0);
	public static Food			salami			= new Food(20, "Salami", "salami", 3, 0);
	public static Food			hamburger		= new Food(21, "Hamburger", "hamburger", 5, 0);
	public static Food			pizza			= new Food(22, "Pizza", "pizza", 6, 0);
	public static Food			omelette		= new Food(23, "Omelette", "omelette", 7, 0);
	public static Food			fried_egg		= new Food(24, "Fried Egg", "fried_egg", 4, 0);
	public static Food			popcorn			= new Food(25, "Popcorn", "popcorn", 4, 0);
	public static Food			popcorn_sweet	= new Food(26, "Sweet Popcorn", "popcorn", 5, 0);
	public static Food			popcorn_salty	= new Food(27, "Salty Popcorn", "popcorn", 5, 0);
	public static Food			candycane		= new Food(28, "Candy cane", "candy", 3, 0);
	
	static
	{
		// Vegetables
		salad.register();
		cucumber.register();
		rice.register();
		chili.register();
		tomato.register();
		paprika.register();
		onion.register();
		pepperballs.register();
		corn.register();
		// Bread and Toast
		breadslice.register();
		toast.register();
		toastet_Toast.register();
		toast_cheese.register();
		toast_salami.register();
		// Other food
		cheese.register();
		cheese_slice.register();
		bacon_raw.register();
		bacon.register();
		pasta.register();
		meatball.register();
		salami.register();
		hamburger.register();
		pizza.register();
		omelette.register();
		fried_egg.register();
		// Sweet stuff
		popcorn.register();
		popcorn_sweet.register();
		popcorn_salty.register();
		candycane.register();
	}
	
	/**
	 * The item damage value the Food is represented by
	 */
	public int					foodID			= 0;
	public String				name			= "";
	public String				icon			= "";
	public int					foodValue		= 0;
	public int					blockPlaced		= 0;
	public boolean				isEnabled		= true;
	
	public int					recipeAmount	= 1;
	public FoodRecipe			recipe			= null;
	
	public Food(int id, String name, String icon, int foodValue)
	{
		this(id, name, icon, foodValue, null);
	}
	
	public Food(int id, String name, String icon, int foodValue, FoodRecipe recipe)
	{
		this(id, name, icon, foodValue, 0, recipe);
	}
	
	public Food(int id, String name, String icon, int foodValue, int blockPlaced)
	{
		this(id, name, icon, foodValue, blockPlaced, null);
	}
	
	public Food(int id, String name, String icon, int foodValue, int blockPlaced, FoodRecipe recipe)
	{
		this.foodID = id;
		this.name = name;
		this.icon = icon;
		this.foodValue = foodValue;
		this.blockPlaced = blockPlaced;
		this.recipe = recipe;
		
		foodTypes[id] = this;
	}
	
	public static Food fromItemStack(ItemStack stack)
	{
		return fromDamage(stack.getItemDamage());
	}
	
	public static Food fromDamage(int damage)
	{
		return foodTypes[damage];
	}
	
	public void register()
	{
		foodList.add(this);
	}
	
	public void addRecipe()
	{
		if (recipe != null)
			recipe.addRecipe(MoreFoodMod.foods, this.foodID);
	}
	
	public EnumAction getAction()
	{
		return EnumAction.eat;
	}
}
