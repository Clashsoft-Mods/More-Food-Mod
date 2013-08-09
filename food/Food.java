package clashsoft.mods.morefood.food;

import static clashsoft.mods.morefood.food.FoodRecipe.CRAFTING;
import static clashsoft.mods.morefood.food.FoodRecipe.FURNACE;

import java.util.LinkedList;
import java.util.List;

import clashsoft.mods.morefood.MoreFoodMod;

import net.minecraft.block.Block;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Food
{
	public static Food[]		foodTypes		= new Food[1024];
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
	public static Food			breadslice		= new Food(9, "Bread Slice", "bread_slice", 3, 0, new FoodRecipe(2, new ItemStack(Item.bread)));
	public static Food			toast			= new Food(10, "Toast", "toast_raw", 5, 0, new FoodRecipe(3, new ItemStack(MoreFoodMod.foods, breadslice.foodID)));
	public static Food			toastet_Toast	= new Food(11, "Toastet Toast", "toast_toasted", 2, 0, new FoodRecipe(FURNACE, 1, new ItemStack(MoreFoodMod.foods, 1, toast.foodID), 0.1F));
	public static Food			butter			= new Food(29, "Butter", "butter", 3, new FoodRecipe(FURNACE, 1, new ItemStack(Item.bucketMilk), 0.2F));
	public static Food			salami			= new Food(20, "Salami", "salami", 3, new FoodRecipe(4, new ItemStack(Item.beefRaw)));
	public static Food			cheese			= new Food(14, "Cheese", "cheese", 5, new FoodRecipe(1, new ItemStack(MoreFoodMod.foods, 1, butter.foodID), new ItemStack(Item.bucketMilk), new ItemStack(Item.bucketMilk)));
	public static Food			cheese_slice	= new Food(15, "Cheese Slice", "cheese_slice", 3, new FoodRecipe(16, new ItemStack(MoreFoodMod.foods, 1, cheese.foodID)));
	public static Food			toast_cheese	= new Food(12, "Toast with Cheese", "toast_cheese", 5, new FoodRecipe(1, new ItemStack(MoreFoodMod.foods, 1, toast.foodID), new ItemStack(MoreFoodMod.foods, 1, cheese_slice.foodID)));
	public static Food			toast_salami	= new Food(13, "Toast with Salami", "toast_salami", 6, new FoodRecipe(1, new ItemStack(MoreFoodMod.foods, 1, toast.foodID), new ItemStack(MoreFoodMod.foods, 1, salami.foodID)));
	public static Food			bacon_raw		= new Food(16, "Raw Bacon Slice", "bacon_raw", 2, new FoodRecipe(3, new ItemStack(Item.porkRaw), new ItemStack(Item.porkRaw)));
	public static Food			bacon			= new Food(17, "Bacon", "bacon_cooked", 6, new FoodRecipe(FURNACE, 1, new ItemStack(MoreFoodMod.foods, 1, bacon_raw.foodID), 0.1F));
	public static Food			pasta			= new Food(18, "Pasta", "pasta", 2, new FoodRecipe(CRAFTING, 4, new Object[] { "wsw", 'w', Item.wheat, 's', MoreFoodMod.salt }));
	public static Food			meatball		= new Food(19, "Meat Ball", "ham", 2, new FoodRecipe(CRAFTING, 4, new Object[] { "bb", "bb", 'b', Item.beefCooked }));
	public static Food			hamburger		= new Food(21, "Hamburger", "hamburger", 5, new FoodRecipe(CRAFTING, 1, new Object[] { " b ", "smt", " b ", 'b', breadslice.asStack(), 's', salad.asStack(), 'm', meatball.asStack(), 't', tomato.asStack() }));
	public static Food			pizza			= new Food(22, "Pizza", "pizza", 6, new FoodRecipe(CRAFTING, 1, new Object[] { " S ", "sts", "www", 'S', salad.asStack(), 's', salami.asStack(), 't', tomato.asStack(), 'w', new ItemStack(Item.wheat) }));
	public static Food			fried_egg		= new Food(24, "Fried Egg", "fried_egg", 4, new FoodRecipe(FURNACE, 1, new ItemStack(Item.egg), 0.2F));
	public static Food			omelette		= new Food(23, "Omelette", "omelette", 7, new FoodRecipe(CRAFTING, 1, new Object[] { "eee", 'e', new ItemStack(Item.egg) }));
	public static Food			popcorn			= new Food(25, "Popcorn", "popcorn", 4, new FoodRecipe(FURNACE, 4, corn.asStack(), 0.25F));
	public static Food			popcorn_sweet	= new Food(26, "Sweet Popcorn", "popcorn", 5, new FoodRecipe(1, popcorn.asStack(), new ItemStack(Item.sugar)));
	public static Food			popcorn_salty	= new Food(27, "Salty Popcorn", "popcorn", 5, new FoodRecipe(1, popcorn.asStack(), new ItemStack(MoreFoodMod.salt)));
	public static Food			candycane		= new Food(28, "Candy cane", "candy", 3, new FoodRecipe(CRAFTING, 2, new Object[] { "s  ", " s ", "  s", 's', Item.sugar }));
	public static Food			chocolate		= new Food(30, "Chocolate Bar", "chocolatebar", 4, new FoodRecipe(4, new ItemStack(Item.dyePowder, 1, 3), new ItemStack(Item.dyePowder, 1, 3), new ItemStack(Item.bucketMilk), new ItemStack(Item.sugar)));
	public static Food			chocolateCookie	= new Food(33, "Chocolate Cookie", "chocolatecookie", 2, new FoodRecipe(CRAFTING, 4, new Object[] { "wcw", 'w', Item.wheat, 'c', chocolate.asStack() }));
	public static Food			cereals1		= new Food(31, "Cereals", "cereals1", 0, new FoodRecipe(4, new ItemStack(Item.wheat)));
	public static Food			cereals2		= new Food(32, "Cereals", "cereals2", 0, new FoodRecipe(8, new ItemStack(Item.wheat), new ItemStack(Item.dyePowder, 1, 3)));
	public static Food			honeydrop		= new Food(34, "Honey Drop", "honeydrop", 1, 0);
	public static Food			cookedCarrot	= new Food(35, "Cooked Carrot", "carrot_cooked", 4, new FoodRecipe(FURNACE, 1, new ItemStack(Item.carrot), 0.1F));
	public static Food			stompedCarrot	= new Food(36, "Stomped Carrot", "carrot_stomped", 2, new FoodRecipe(1, new ItemStack(Item.carrot)));
	public static Food			stompedPotato	= new Food(37, "Stomped Potato", "potato_stomped", 2, new FoodRecipe(1, new ItemStack(Item.potato)));
	public static Food			stompedApple	= new Food(38, "Stomped Apple", "apple_stomped", 4, new FoodRecipe(1, new ItemStack(Item.appleRed)));
	public static Food			diamondCarrot	= new Food(39, "Diamond Carrot", "carrot_diamond", 6, new FoodRecipe(CRAFTING, 1, new Object[] { "ddd", "dxd", "ddd", 'd', Item.diamond, 'x', Item.carrot })).setEffects(new PotionEffect(Potion.invisibility.id, 3000, 0));
	public static Food			diamondPotato	= new Food(40, "Diamond Potato", "potato_diamond", 6, new FoodRecipe(CRAFTING, 1, new Object[] { "ddd", "dxd", "ddd", 'd', Item.diamond, 'x', Item.potato })).setEffects(new PotionEffect(Potion.digSpeed.id, 600, 3), new PotionEffect(Potion.moveSpeed.id, 3000, 1));
	public static Food			diamondApple	= new Food(41, "Diamond Apple", "apple_diamond", 8, new FoodRecipe(CRAFTING, 1, new Object[] { "ddd", "dxd", "ddd", 'd', Item.diamond, 'x', Item.appleRed })).setEffects(new PotionEffect(Potion.jump.id, 600, 1), new PotionEffect(Potion.damageBoost.id, 1200, 2));
	public static Food			goldPotato1		= new Food(42, "Golden Potato", "potato_gold", 5, new FoodRecipe(CRAFTING, 1, new Object[] { "ggg", "gpg", "ggg", 'g', Item.ingotGold, 'p', Item.potato })).setEffects(new PotionEffect(Potion.field_76444_x.id, 2400, 0));
	public static Food			goldPotato2		= new Food(43, "Golden Potato", "potato_gold", 7, new FoodRecipe(CRAFTING, 1, new Object[] { "GGG", "GpG", "GGG", 'G', Block.blockGold, 'p', Item.potato })).setEffects(new PotionEffect(Potion.field_76444_x.id, 2400, 0), new PotionEffect(Potion.regeneration.id, 600, 4), new PotionEffect(Potion.fireResistance.id, 6000, 0));
	
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
		butter.register();
		chocolate.register();
		chocolateCookie.register();
		cereals1.register();
		cereals2.register();
		// Stomped + Cooked
		cookedCarrot.register();
		stompedCarrot.register();
		stompedPotato.register();
		stompedApple.register();
		diamondCarrot.register();
		diamondPotato.register();
		diamondApple.register();
		goldPotato1.register();
		goldPotato2.register();
		
		Drink.init();
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
	public PotionEffect[]		effects			= new PotionEffect[0];
	
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
	
	public Food setEnabled(boolean enabled)
	{
		this.isEnabled = enabled;
		return this;
	}
	
	public Food setEffects(PotionEffect... effects)
	{
		this.effects = effects;
		return this;
	}
	
	public PotionEffect[] getEffects()
	{
		return effects;
	}
	
	public ItemStack asStack()
	{
		return asStack(1);
	}
	
	public ItemStack asStack(int i)
	{
		return new ItemStack(MoreFoodMod.foods, 1, this.foodID);
	}
}
