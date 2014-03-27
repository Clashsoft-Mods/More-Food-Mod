package clashsoft.mods.morefood.food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import clashsoft.cslib.minecraft.item.CSStacks;
import clashsoft.cslib.minecraft.item.meta.IMetaItem;
import clashsoft.mods.morefood.MoreFoodMod;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;

public class Food implements IMetaItem
{
	public static Food[]		foodTypes				= new Food[1024];
	public static List<Food>	foodList				= new ArrayList<Food>(1024);
	
	// Vanilla Food Items
	
	public static Food			apple					= new Food((ItemFood) Items.apple, 0, null);
	public static Food			appleStomped			= new Food(36, "stomped_apple", "stomped_apple", 4, new FoodRecipe(1, CSStacks.apple));
	public static Food			appleGold1				= new Food((ItemFood) Items.golden_apple, 0, new FoodRecipe(1, "ggg", "gag", "ggg", 'g', CSStacks.gold_ingot, 'a', CSStacks.apple));
	public static Food			appleGold2				= new Food((ItemFood) Items.golden_apple, 1, new FoodRecipe(1, "ggg", "gag", "ggg", 'g', CSStacks.gold_block, 'a', CSStacks.apple));
	public static Food			appleDiamond			= new Food(37, "diamond_apple", "diamond_apple", 8, new FoodRecipe(1, "ddd", "dxd", "ddd", 'd', CSStacks.diamond, 'x', CSStacks.apple)).setEffects(new PotionEffect(Potion.jump.id, 600, 1), new PotionEffect(Potion.damageBoost.id, 1200, 2));
	
	public static Food			melon					= new Food((ItemFood) Items.melon, 0, null);
	public static Food			melonGold1				= new Food(Items.speckled_melon, 0, new FoodRecipe(1, "ggg", "gmg", "ggg", 'g', CSStacks.gold_nugget, 'm', CSStacks.melon));
	
	public static Food			potato					= new Food((ItemFood) Items.potato, 0, null);
	public static Food			potatoCooked			= new Food((ItemFood) Items.baked_potato, 0, new FoodRecipe(1, CSStacks.potato, 0.1F));
	public static Food			potatoStomped			= new Food(38, "stomped_potato", "stomped_potato", 2, new FoodRecipe(1, CSStacks.potato));
	public static Food			potatoGold1				= new Food(39, "golden_potato", "golden_potato", 5, new FoodRecipe(1, "ggg", "gpg", "ggg", 'g', CSStacks.gold_ingot, 'p', CSStacks.potato)).setEffects(new PotionEffect(Potion.field_76444_x.id, 2400, 0));
	public static Food			potatoGold2				= new Food(40, "golden_potato_2", "golden_potato", 7, new FoodRecipe(1, "GGG", "GpG", "GGG", 'G', CSStacks.gold_block, 'p', CSStacks.potato)).setEffects(new PotionEffect(Potion.field_76444_x.id, 2400, 0), new PotionEffect(Potion.regeneration.id, 600, 4), new PotionEffect(Potion.fireResistance.id, 6000, 0));
	public static Food			potatoDiamond			= new Food(41, "diamond_potato", "diamond_potato", 6, new FoodRecipe(1, "ddd", "dxd", "ddd", 'd', CSStacks.diamond, 'x', CSStacks.potato)).setEffects(new PotionEffect(Potion.digSpeed.id, 600, 3), new PotionEffect(Potion.moveSpeed.id, 3000, 1));
	public static Food			poisonousPotato			= new Food((ItemFood) Items.poisonous_potato, 0, null);
	
	public static Food			carrot					= new Food((ItemFood) Items.carrot, 0, null);
	public static Food			carrotCooked			= new Food(42, "cooked_carrot", "cooked_carrot", 4, new FoodRecipe(1, CSStacks.carrot, 0.1F));
	public static Food			carrotStomped			= new Food(43, "stomped_carrot", "stomped_carrot", 2, new FoodRecipe(1, CSStacks.carrot));
	public static Food			carrotGold1				= new Food((ItemFood) Items.golden_carrot, 0, new FoodRecipe(1, "ggg", "gcg", "ggg", 'g', CSStacks.gold_nugget, 'c', CSStacks.carrot));
	public static Food			carrotDiamond			= new Food(44, "diamond_carrot", "diamond_carrot", 6, new FoodRecipe(1, "ddd", "dxd", "ddd", 'd', CSStacks.diamond, 'x', CSStacks.carrot)).setEffects(new PotionEffect(Potion.invisibility.id, 3000, 0));
	
	public static Food			bread					= new Food((ItemFood) Items.bread, 0, new FoodRecipe(1, "www", 'w', CSStacks.wheat));
	public static Food			cookie					= new Food((ItemFood) Items.cookie, 0, new FoodRecipe(1, "wcw", 'w', CSStacks.wheat, 'c', CSStacks.cocoa));
	
	public static Food			porkRaw					= new Food((ItemFood) Items.porkchop, 0, null);
	public static Food			porkCooked				= new Food((ItemFood) Items.cooked_porkchop, 0, new FoodRecipe(1, CSStacks.porkchop, 0.1F));
	public static Food			beefRaw					= new Food((ItemFood) Items.beef, 0, null);
	public static Food			beefCooked				= new Food((ItemFood) Items.cooked_beef, 0, new FoodRecipe(1, CSStacks.beef, 0.1F));
	public static Food			chickenRaw				= new Food((ItemFood) Items.chicken, 0, null);
	public static Food			chickenCooked			= new Food((ItemFood) Items.cooked_chicken, 0, new FoodRecipe(1, CSStacks.chicken, 0.1F));
	public static Food			fishRaw					= new Food((ItemFood) Items.fish, 0, null);
	public static Food			fishCooked				= new Food((ItemFood) Items.cooked_fished, 0, new FoodRecipe(1, CSStacks.fish, 0.1F));
	
	public static Food			rottenFlesh				= new Food((ItemFood) Items.rotten_flesh, 0, null);
	public static Food			spiderEye				= new Food((ItemFood) Items.spider_eye, 0, null);
	
	public static Food			pumpkinPie				= new Food((ItemFood) Items.pumpkin_pie, 0, new FoodRecipe(1, CSStacks.egg, CSStacks.sugar, CSStacks.pumpkin));
	
	public static Food			salad					= new Food(0, "salad", "salad", 2, MoreFoodMod.saladPlant);
	public static Food			cucumber				= new Food(1, "cucumber", "cucumber", 2, MoreFoodMod.cucumberPlant);
	public static Food			rice					= new Food(2, "rice", "rice", 2, MoreFoodMod.ricePlant);
	public static Food			chili					= new Food(3, "chili", "chili", 2, MoreFoodMod.chiliPlant);
	public static Food			tomato					= new Food(4, "tomato", "tomato", 2, MoreFoodMod.tomatoPlant);
	public static Food			paprika					= new Food(5, "paprika", "paprika", 3, MoreFoodMod.paprikaPlant);
	public static Food			onion					= new Food(6, "onion", "onion", 3, MoreFoodMod.onionPlant);
	public static Food			pepperSeeds				= new Food(7, "pepper_seeds", "pepper_seeds", 0, MoreFoodMod.pepperPlant);
	public static Food			vanillaSeeds			= new Food(8, "vanilla_seeds", "vanilla_seeds", 0, MoreFoodMod.vanillaPlant);
	public static Food			corn					= new Food(9, "corn", "corn", 4, MoreFoodMod.cornPlant);
	public static Food			breadSlice				= new Food(10, "bread_slice", "bread_slice", 3, new FoodRecipe(2, CSStacks.bread));
	public static Food			toast					= new Food(11, "toast", "toast", 5, new FoodRecipe(3, breadSlice.asStack()));
	public static Food			toast_toasted			= new Food(12, "toast_toasted", "toast_toasted", 2, new FoodRecipe(1, toast.asStack(), 0.1F));
	public static Food			butter					= new Food(13, "butter", "butter", 3, new FoodRecipe(1, CSStacks.milk_bucket, 0.2F));
	public static Food			salami					= new Food(14, "salami", "salami", 3, new FoodRecipe(4, CSStacks.beef));
	public static Food			cheese					= new Food(15, "cheese", "cheese", 5, new FoodRecipe(1, butter.asStack(), CSStacks.milk_bucket, CSStacks.milk_bucket));
	public static Food			cheese_slice			= new Food(16, "cheese_slice", "cheese_slice", 3, new FoodRecipe(16, cheese.asStack()));
	public static Food			toast_cheese			= new Food(17, "toast_cheese", "toast_cheese", 5, new FoodRecipe(1, toast_toasted.asStack(), cheese_slice.asStack()));
	public static Food			toast_salami			= new Food(18, "toast_salami", "toast_salami", 6, new FoodRecipe(1, toast_toasted.asStack(), salami.asStack()));
	public static Food			bacon_raw				= new Food(19, "raw_bacon", "raw_bacon", 2, new FoodRecipe(3, CSStacks.porkchop, CSStacks.porkchop));
	public static Food			bacon					= new Food(20, "bacon", "bacon", 6, new FoodRecipe(1, bacon_raw.asStack(), 0.1F));
	public static Food			pasta					= new Food(21, "pasta", "pasta", 2, new FoodRecipe(4, "wsw", 'w', CSStacks.wheat, 's', MoreFoodMod.salt));
	public static Food			meatball				= new Food(22, "ham", "ham", 2, new FoodRecipe(4, "bb", "bb", 'b', CSStacks.beef));
	public static Food			hamburger				= new Food(23, "hamburger", "hamburger", 5, new FoodRecipe(1, " b ", "smt", " b ", 'b', breadSlice.asStack(), 's', salad.asStack(), 'm', meatball.asStack(), 't', tomato.asStack()));
	
	public static Food			pizza					= new Food(24, "pizza", "pizza", 0, MoreFoodMod.pizza, new FoodRecipe(1, " S ", "sts", "www", 'S', salad.asStack(), 's', salami.asStack(), 't', tomato.asStack(), 'w', CSStacks.wheat));
	public static Food			fried_egg				= new Food(25, "fried_egg", "fried_egg", 4, new FoodRecipe(1, CSStacks.egg, 0.2F));
	public static Food			omelette				= new Food(26, "omelette", "omelette", 7, new FoodRecipe(1, "eee", 'e', CSStacks.egg));
	public static Food			popcorn					= new Food(27, "popcorn", "popcorn", 4, new FoodRecipe(4, corn.asStack(), 0.25F));
	public static Food			popcorn_sweet			= new Food(28, "popcorn_sweet", "popcorn_sweet", 5, new FoodRecipe(1, popcorn.asStack(), CSStacks.sugar));
	public static Food			popcorn_salty			= new Food(29, "popcorn_salty", "popcorn_salty", 5, new FoodRecipe(1, popcorn.asStack(), new ItemStack(MoreFoodMod.salt)));
	public static Food			candycane				= new Food(30, "candy_cane", "candy_cane", 3, new FoodRecipe(2, "s  ", " s ", "  s", 's', CSStacks.sugar));
	public static Food			chocolate				= new Food(31, "chocolate_bar", "chocolate_bar", 4, new FoodRecipe(4, CSStacks.cocoa, CSStacks.cocoa, CSStacks.milk_bucket, CSStacks.sugar));
	public static Food			chocolateCookie			= new Food(32, "cookie_chocolate", "cookie_chocolate", 2, new FoodRecipe(4, "wcw", 'w', CSStacks.wheat, 'c', chocolate.asStack()));
	public static Food			cereals1				= new Food(33, "cereals_wheat", "cereals_wheat", 0, new FoodRecipe(4, CSStacks.wheat));
	public static Food			cereals2				= new Food(34, "cereals_cocoa", "cereals_cocoa", 0, new FoodRecipe(8, CSStacks.wheat, CSStacks.cocoa));
	public static Food			honeydrop				= new Food(35, "honey_drop", "honey_drop", 1);
	public static Food			chocolateWhite			= new Food(45, "chocolate_bar_white", "chocolate_bar_white", 4, new FoodRecipe(4, butter.asStack(), CSStacks.milk_bucket, CSStacks.milk_bucket, CSStacks.sugar));
	public static Food			chocolateCow			= new Food(46, "chocolate_bar_cow", "chocolate_bar_cow", 4, new FoodRecipe(4, CSStacks.cocoa, CSStacks.cocoa, CSStacks.milk_bucket, butter.asStack(), CSStacks.sugar));
	public static Food			frenchfries				= new Food(47, "french_fries", "french_fries", 5, new FoodRecipe(1, potatoCooked.asStack(), potatoCooked.asStack(), butter.asStack(), CSStacks.paper, CSStacks.dye_red));
	
	public static Food			orange					= new Food(48, "orange", "orange", 3, MoreFoodMod.fruitSaplings).setMetadataPlaced(0);
	public static Food			pear					= new Food(49, "pear", "pear", 3, MoreFoodMod.fruitSaplings).setMetadataPlaced(1);
	public static Food			cherry					= new Food(50, "cherry", "cherry", 3, MoreFoodMod.fruitSaplings).setMetadataPlaced(2);
	public static Food			strawberry				= new Food(51, "strawberry", "strawberry", 3, MoreFoodMod.strawberryBush);
	public static Food			raspberry				= new Food(52, "raspberry", "raspberry", 3, MoreFoodMod.raspberryBush);
	public static Food			blackberry				= new Food(53, "blackberry", "blackberry", 3, MoreFoodMod.blackberryBush);
	public static Food			blueberry				= new Food(54, "blueberry", "blueberry", 2, MoreFoodMod.blueberryBush);
	public static Food			redcurrant				= new Food(55, "redcurrant", "redcurrant", 2, MoreFoodMod.redcurrantBush);
	
	public static Food			plum					= new Food(56, "plum", "plum", 3, MoreFoodMod.fruitSaplings).setMetadataPlaced(3);
	public static Food			banana					= new Food(57, "banana", "banana", 3, MoreFoodMod.fruitSaplings2).setMetadataPlaced(0);
	public static Food			seagrass				= new Food(58, "pineapple", "pineapple", 2);
	
	public static Food			icecube					= new Food(80, "icecube", "icecube", 0, new FoodRecipe(4, CSStacks.ice));
	public static Food			icecream				= new Food(81, "icecream", "icecream", 0, new FoodRecipe(2, icecube.asStack(), CSStacks.snowball, CSStacks.milk_bucket));
	public static Food			icecreamChocolate		= new Food(82, "icecream_chocolate", "icecream_chocolate", 1, new FoodRecipe(1, chocolate.asStack(), icecream.asStack()));
	public static Food			icecreamVanilla			= new Food(83, "icecream_vanilla", "icecream_vanilla", 1, new FoodRecipe(1, new ItemStack(MoreFoodMod.vanilla), icecream.asStack()));
	public static Food			icecreamStrawberry		= new Food(84, "icecream_strawberry", "icecream_strawberry", 1, new FoodRecipe(1, strawberry.asStack(), icecream.asStack()));
	public static Food			icecreamCone			= new Food(85, "icecream_cone", "icecream_cone", 2, new FoodRecipe(6, "w w", " w ", " w ", 'w', CSStacks.wheat));
	public static Food			icecreamConeChocolate	= new Food(86, "icecream_cone_chocolate", "icecream_cone_chocolate", 3, new FoodRecipe(1, "i", "c", 'i', icecreamChocolate.asStack(), 'c', icecreamCone.asStack()));
	public static Food			icecreamConeVanilla		= new Food(87, "icecream_cone_vanilla", "icecream_cone_vanilla", 3, new FoodRecipe(1, "i", "c", 'i', icecreamVanilla.asStack(), 'c', icecreamCone.asStack()));
	public static Food			icecreamConeStrawberry	= new Food(88, "icecream_cone_strawberry", "icecream_cone_strawberry", 3, new FoodRecipe(1, "i", "c", 'i', icecreamStrawberry.asStack(), 'c', icecreamCone.asStack()));
	public static Food			icecreamConeCV			= new Food(89, "icecream_cone_cv", "icecream_cone_cv", 4, new FoodRecipe(1, "c", "v", "C", 'c', icecreamChocolate.asStack(), 'v', icecreamVanilla.asStack(), 'C', icecreamCone.asStack()));
	public static Food			icecreamConeCS			= new Food(90, "icecream_cone_cs", "icecream_cone_cs", 4, new FoodRecipe(1, "c", "s", "C", 'c', icecreamChocolate.asStack(), 's', icecreamStrawberry.asStack(), 'C', icecreamCone.asStack()));
	public static Food			icecreamConeVS			= new Food(91, "icecream_cone_vs", "icecream_cone_vs", 4, new FoodRecipe(1, "v", "s", "C", 'v', icecreamVanilla.asStack(), 's', icecreamStrawberry.asStack(), 'C', icecreamCone.asStack()));
	public static Food			icecreamConeCVS			= new Food(92, "icecream_cone_cvs", "icecream_cone_cvs", 5, new FoodRecipe(1, "cvs", " C ", 'c', icecreamChocolate.asStack(), 'v', icecreamVanilla.asStack(), 's', icecreamStrawberry.asStack(), 'C', icecreamCone.asStack()));
	
	static
	{
		setCategory("food.category.vanilla.meat", 0x442319, new Food[] {
			porkRaw,
			porkCooked,
			beefRaw,
			beefCooked,
			chickenRaw,
			chickenCooked,
			fishRaw,
			fishCooked,
			rottenFlesh,
			spiderEye });
		
		setCategory("food.category.vanilla.fruit", 0x00FF00, new Food[] {
			apple,
			appleStomped,
			appleGold1,
			appleGold2,
			appleDiamond,
			melon,
			melonGold1 });
		
		setCategory("food.category.fruit", 0xFF0000, new Food[] {
			orange,
			pear,
			cherry,
			plum,
			banana,
			strawberry,
			raspberry,
			blackberry,
			blueberry,
			redcurrant });
		
		setCategory("food.category.vanilla.vegetables", 0xFFDB92, new Food[] {
			potato,
			potatoCooked,
			potatoStomped,
			potatoGold1,
			potatoGold2,
			potatoDiamond,
			poisonousPotato,
			carrot,
			carrotCooked,
			carrotStomped,
			carrotGold1,
			carrotDiamond });
		
		setCategory("food.category.vegetables", 0x008100, new Food[] {
			salad,
			cucumber,
			rice,
			chili,
			tomato,
			paprika,
			onion,
			pepperSeeds,
			vanillaSeeds,
			corn,
			seagrass });
		
		setCategory("food.category.wheat", 0x906C22, new Food[] {
			bread,
			breadSlice,
			toast,
			toast_toasted,
			toast_cheese,
			toast_salami });
		
		setCategory("food.category.products", 0xFFFF00, new Food[] {
			cheese,
			cheese_slice,
			bacon_raw,
			bacon,
			pasta,
			meatball,
			salami,
			hamburger,
			frenchfries,
			pizza,
			omelette,
			pumpkinPie,
			fried_egg });
		
		setCategory("food.category.sweet", 0xEFEFEF, new Food[] {
			popcorn,
			popcorn_sweet,
			popcorn_salty,
			candycane,
			butter,
			chocolate,
			chocolateWhite,
			chocolateCow,
			cookie,
			chocolateCookie,
			cereals1,
			cereals2 });
		
		setCategory("food.category.cold", 0xD5FEFF, new Food[] {
			icecube,
			icecream,
			icecreamChocolate,
			icecreamVanilla,
			icecreamStrawberry,
			icecreamCone,
			icecreamConeChocolate,
			icecreamConeVanilla,
			icecreamConeStrawberry,
			icecreamConeCV,
			icecreamConeCS,
			icecreamConeVS,
			icecreamConeCVS });
		
		Drink.init();
	}
	
	/**
	 * The item damage value the Food is represented by
	 */
	private int					foodID;
	private String				name;
	private String				icon;
	private int					foodValue;
	private Block				blockPlaced;
	private int					metadataPlaced;
	private boolean				isEnabled				= true;
	private FoodCategory		category				= FoodCategory.generic;
	
	private FoodRecipe			recipe;
	private PotionEffect[]		effects;
	
	protected ItemStack			stack;
	
	public Food(int id, String name, String icon, int foodValue)
	{
		this(id, name, icon, foodValue, (FoodRecipe) null);
	}
	
	public Food(int id, String name, String icon, int foodValue, FoodRecipe recipe)
	{
		this(id, name, icon, foodValue, null, recipe);
	}
	
	public Food(int id, String name, String icon, int foodValue, Block blockPlaced)
	{
		this(id, name, icon, foodValue, blockPlaced, null);
	}
	
	public Food(int id, String name, String icon, int foodValue, Block blockPlaced, FoodRecipe recipe)
	{
		this.setID(id);
		this.name = name;
		this.icon = icon;
		this.foodValue = foodValue;
		this.recipe = recipe;
		this.blockPlaced = blockPlaced;
		
		this.stack = new ItemStack(MoreFoodMod.foods, 1, this.getID());
		
		if (id >= 0)
		{
			foodTypes[id] = this;
		}
	}
	
	public Food(Item item, int damage, FoodRecipe recipe)
	{
		this(-1, item.getUnlocalizedName(), getIconName(item, damage), 0, null, recipe);
		this.stack = new ItemStack(item, 1, damage);
	}
	
	public Food(ItemFood foodItem, int damage, FoodRecipe recipe)
	{
		this(-1, foodItem.getUnlocalizedName(), getIconName(foodItem, damage), 0, getPlacedBlock(foodItem, damage), recipe);
		this.stack = new ItemStack(foodItem, 1, damage);
		this.foodValue = foodItem.func_150905_g(this.stack);
	}
	
	private static String getIconName(Item item, int metadata)
	{
		try
		{
			IIcon icon = item.getIconFromDamage(metadata);
			return icon.getIconName();
		}
		catch (Throwable ex)
		{
			return ObfuscationReflectionHelper.<String, Item> getPrivateValue(Item.class, item, "iconString");
		}
	}
	
	private static Block getPlacedBlock(ItemFood item, int metadata)
	{
		if (item instanceof ItemSeedFood)
		{
			return ((ItemSeedFood) item).getPlant(null, 0, 0, 0);
		}
		return null;
	}
	
	public static Food fromItemStack(ItemStack stack)
	{
		return fromDamage(stack.getItemDamage());
	}
	
	public static Food fromDamage(int damage)
	{
		return foodTypes[damage];
	}
	
	@Override
	public Food register()
	{
		foodList.add(this);
		return this;
	}
	
	public void addRecipe()
	{
		if (this.recipe != null && this.getID() >= 0)
		{
			this.recipe.addRecipe(MoreFoodMod.foods, this.getID());
		}
	}
	
	@Override
	public EnumAction getAction()
	{
		return EnumAction.eat;
	}
	
	@Override
	public IMetaItem setEnabled(boolean enabled)
	{
		this.isEnabled = enabled;
		return this;
	}
	
	@Override
	public Food setEffects(PotionEffect... effects)
	{
		this.effects = effects;
		return this;
	}
	
	@Override
	public PotionEffect[] getEffects()
	{
		return this.effects;
	}
	
	@Override
	public ItemStack asStack()
	{
		return this.asStack(1);
	}
	
	@Override
	public ItemStack asStack(int i)
	{
		ItemStack is = this.stack;
		is.stackSize = i;
		return is;
	}
	
	@Override
	public int getFoodValue()
	{
		return this.foodValue;
	}
	
	@Override
	public void setFoodValue(int foodValue)
	{
		this.foodValue = foodValue;
	}
	
	@Override
	public Block getBlockPlaced()
	{
		return this.blockPlaced;
	}
	
	@Override
	public void setBlockPlaced(Block block)
	{
		this.blockPlaced = block;
	}
	
	@Override
	public boolean isEnabled()
	{
		return this.isEnabled;
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public String getIconName()
	{
		return this.icon;
	}
	
	@Override
	public void setIconName(String icon)
	{
		this.icon = icon;
	}
	
	@Override
	public int getID()
	{
		return this.foodID;
	}
	
	@Override
	public void setID(int foodID)
	{
		this.foodID = foodID;
	}
	
	@Override
	public FoodRecipe getRecipe()
	{
		return this.recipe;
	}
	
	public static List<Food> getDisplayList()
	{
		return foodList;
	}
	
	@Override
	public Collection<String> getDescription()
	{
		return Collections.EMPTY_LIST;
	}
	
	@Override
	public int getMetadataPlaced()
	{
		return this.metadataPlaced;
	}
	
	public Food setMetadataPlaced(int i)
	{
		this.metadataPlaced = i;
		return this;
	}
	
	public Food setCategory(FoodCategory category)
	{
		this.category = category;
		return this;
	}
	
	public FoodCategory getCategory()
	{
		return this.category;
	}
	
	public static void setCategory(String name, int color, Food... foods)
	{
		FoodCategory category = new FoodCategory(name, color);
		for (Food f : foods)
		{
			f.register();
			f.setCategory(category);
		}
	}
	
	/**
	 * Runs the static initializer of this class
	 */
	public static void init()
	{
	}
}
