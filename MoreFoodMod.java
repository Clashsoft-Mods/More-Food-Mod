package clashsoft.mods.morefood;

import java.util.Random;

import clashsoft.cslib.minecraft.CSLib;
import clashsoft.cslib.minecraft.ClashsoftMod;
import clashsoft.cslib.minecraft.block.BlockCustomLeaves;
import clashsoft.cslib.minecraft.block.BlockCustomLog;
import clashsoft.cslib.minecraft.block.BlockCustomSapling;
import clashsoft.cslib.minecraft.block.CSBlocks;
import clashsoft.cslib.minecraft.crafting.CSCrafting;
import clashsoft.cslib.minecraft.item.CSItems;
import clashsoft.cslib.minecraft.item.CSStacks;
import clashsoft.cslib.minecraft.item.CustomItem;
import clashsoft.cslib.minecraft.update.CSUpdate;
import clashsoft.cslib.minecraft.world.gen.CustomTreeGen;
import clashsoft.cslib.minecraft.world.gen.WorldGenRanged;
import clashsoft.mods.morefood.block.*;
import clashsoft.mods.morefood.common.MFMCommonProxy;
import clashsoft.mods.morefood.food.Food;
import clashsoft.mods.morefood.item.*;
import clashsoft.mods.morefood.world.WorldGenGardener;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

@Mod(modid = MoreFoodMod.MODID, name = MoreFoodMod.NAME, version = MoreFoodMod.VERSION, dependencies = MoreFoodMod.DEPENDENCIES)
public class MoreFoodMod extends ClashsoftMod
{
	public static final String			MODID			= "morefood";
	public static final String			NAME			= "More Food Mod";
	public static final String			ACRONYM			= "mfm";
	public static final String			VERSION			= CSUpdate.CURRENT_VERSION + "-1.0.0";
	public static final String			DEPENDENCIES	= CSLib.DEPENDENCY;
	
	@Instance(MODID)
	public static MoreFoodMod			instance;
	
	@SidedProxy(clientSide = "clashsoft.mods.morefood.client.MFMClientProxy", serverSide = "clashsoft.mods.morefood.common.MFMCommonProxy")
	public static MFMCommonProxy		proxy;
	
	public static CustomItem			spices			= new CustomItem(new String[] {
			"salt",
			"pepper",
			"cinnamon",
			"vanilla"									}, "morefood");
	public static ItemJuice				juice			= (ItemJuice) new ItemJuice().setUnlocalizedName("juice_bottles");
	public static ItemMilkBowls			milkBowls		= (ItemMilkBowls) new ItemMilkBowls(4).setUnlocalizedName("milk_bowls");
	public static ItemSoupBowls			soupBowls		= (ItemSoupBowls) new ItemSoupBowls(6).setUnlocalizedName("soup_bowls");
	public static ItemFoods				foods			= (ItemFoods) new ItemFoods(3, 1.0F).setUnlocalizedName("food_items");
	public static ItemRecipeBook		recipeBook		= (ItemRecipeBook) new ItemRecipeBook().setUnlocalizedName("recipe_book").setTextureName("morefood:recipe_book");
	
	public static BlockPlantMoreFood	lettucePlant	= (BlockPlantMoreFood) new BlockPlantMoreFood(3).setBlockName("lettuce_plant").setBlockTextureName("morefood:lettuce");
	public static BlockPlantMoreFood	cucumberPlant	= (BlockPlantMoreFood) new BlockPlantMoreFood(3).setBlockName("cucumber_plant").setBlockTextureName("morefood:cucumber");
	public static BlockPlantMoreFood	ricePlant		= (BlockPlantMoreFood) new BlockPlantMoreFood(6).setBlockName("rice_plant").setBlockTextureName("morefood:rice");
	public static BlockPlantMoreFood	chiliPlant		= (BlockPlantMoreFood) new BlockPlantMoreFood(6).setBlockName("chili_plant").setBlockTextureName("morefood:chili");
	public static BlockPlantMoreFood	tomatoPlant		= (BlockPlantMoreFood) new BlockPlantMoreFood(3).setBlockName("tomato_plant").setBlockTextureName("morefood:tomato");
	public static BlockPlantMoreFood	paprikaPlant	= (BlockPlantMoreFood) new BlockPlantMoreFood(6).setBlockName("paprika_plant").setBlockTextureName("morefood:paprika");
	public static BlockPlantMoreFood	onionPlant		= (BlockPlantMoreFood) new BlockPlantMoreFood(4).setBlockName("onion_plant").setBlockTextureName("morefood:onion");
	public static BlockPlantMoreFood	pepperPlant		= (BlockPlantMoreFood) new BlockPlantMoreFood(3).setBlockName("pepper_plant").setBlockTextureName("morefood:pepper");
	public static BlockPlantMoreFood	vanillaPlant	= (BlockPlantMoreFood) new BlockPlantMoreFood(4).setBlockName("vanilla_plant").setBlockTextureName("morefood:vanilla");
	public static BlockPlantMoreFood	cornPlant		= (BlockPlantMoreFood) new BlockPlantMoreFood(6).setBlockName("corn_plant").setBlockTextureName("morefood:corn");
	
	public static BlockSaltOre			saltOre			= (BlockSaltOre) new BlockSaltOre().setBlockName("salt_ore").setBlockTextureName("morefood:salt_ore");
	public static BlockPizza			pizza			= (BlockPizza) new BlockPizza().setBlockName("pizza").setBlockTextureName("morefood:pizza");
	
	public static BlockBush				strawberryBush	= (BlockBush) new BlockBush("morefood:strawberry_bush", "morefood:strawberry_bush_stem").setBlockName("strawberry_bush");
	public static BlockBush				raspberryBush	= (BlockBush) new BlockBush("morefood:raspberry_bush", "morefood:raspberry_bush_stem").setBlockName("raspberry_bush");
	public static BlockBush				blueberryBush	= (BlockBush) new BlockBush("morefood:blueberry_bush", "morefood:blueberry_bush_stem").setBlockName("blueberry_bush");
	public static BlockBush				blackberryBush	= (BlockBush) new BlockBush("morefood:blackberry_bush", "morefood:blackberry_bush_stem").setBlockName("blackberry_bush");
	public static BlockBush				redcurrantBush	= (BlockBush) new BlockBush("morefood:redcurrant_bush", "morefood:redcurrant_bush_stem").setBlockName("redcurrant_bush");
	
	public static String[]				fruitTypes1		= new String[] {
			"orange",
			"pear",
			"cherry",
			"plum"										};
	public static String[]				fruitTypes2		= new String[] {
			"banana",
			"pineapple"								};
	
	public static BlockCustomSapling	fruitSaplings	= new BlockFruitSapling(fruitTypes1, new String[] {
			"morefood:orange_sapling",
			"morefood:pear_sapling",
			"morefood:cherry_sapling",
			"morefood:plum_sapling"					});
	public static BlockCustomSapling	fruitSaplings2	= new BlockFruitSapling(fruitTypes2, new String[] {
			"morefood:banana_sapling",
			"morefood:pineapple_sapling"				});
	public static BlockCustomLog		fruitLogs		= new BlockCustomLog(fruitTypes1, new String[] {
			"morefood:orange_log_top",
			"morefood:pear_log_top",
			"morefood:cherry_log_top",
			"morefood:plum_log_top"					}, new String[] {
			"morefood:orange_log",
			"morefood:pear_log",
			"morefood:cherry_log",
			"morefood:plum_log"						});
	public static BlockCustomLog		fruitLogs2		= new BlockCustomLog(fruitTypes2, new String[] {
			"morefood:banana_log_top",
			"morefood:pineapple_log_top"				}, new String[] {
			"morefood:banana_log",
			"morefood:pineapple_log"					});
	
	public static BlockCustomLeaves		fruitLeaves		= new BlockCustomLeaves(fruitTypes1, new String[] {
			"morefood:orange_leaves",
			"morefood:pear_leaves",
			"morefood:cherry_leaves",
			"morefood:plum_leaves"						});
	public static BlockCustomLeaves		fruitLeaves2	= new BlockCustomLeaves(fruitTypes2, new String[] {
			"morefood:banana_leaves",
			"morefood:pineapple_leaves"				});
	
	public static ItemStack				salt			= new ItemStack(spices, 1, 0);
	public static ItemStack				pepper			= new ItemStack(spices, 1, 1);
	public static ItemStack				cinnamon		= new ItemStack(spices, 1, 2);
	public static ItemStack				vanilla			= new ItemStack(spices, 1, 3);
	
	public MoreFoodMod()
	{
		super(MODID, NAME, ACRONYM, VERSION);
		this.url = "https://github.com/Clashsoft/More-Food-Mod/wiki";
	}
	
	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		
		CSItems.addItem(foods, "food_items");
		CSItems.addItem(spices, "spices");
		CSItems.addItem(juice, "juice_bottles");
		CSItems.addItem(milkBowls, "milk_bowls");
		CSItems.addItem(soupBowls, "soup_bowls");
		
		CSItems.addItemWithRecipe(recipeBook, "recipe_book", 1, " s ", "bBp", " t ", 's', Food.lettuce.asStack(), 'b', Items.beef, 'B', Items.book, 'p', Items.cooked_porkchop, 't', Food.tomato.asStack());
		
		fruitSaplings.setBlockName("fruit").setHardness(0F).setCreativeTab(CreativeTabs.tabDecorations);
		fruitSaplings2.setBlockName("fruit").setHardness(0F).setCreativeTab(CreativeTabs.tabDecorations);
		
		fruitLogs.setBlockName("fruit").setHardness(2.0F).setCreativeTab(CreativeTabs.tabBlock);
		fruitLogs2.setBlockName("fruit").setHardness(2.0F).setCreativeTab(CreativeTabs.tabBlock);
		
		fruitLeaves.setBlockName("fruit").setHardness(0.2F).setCreativeTab(CreativeTabs.tabDecorations);
		fruitLeaves.setSaplingStacks(Food.orange.asStack(), Food.pear.asStack(), Food.cherry.asStack(), Food.plum.asStack());
		
		fruitLeaves2.setBlockName("fruit").setHardness(0.2F).setCreativeTab(CreativeTabs.tabDecorations);
		fruitLeaves2.setSaplingStacks(Food.banana.asStack(), Food.pineapple.asStack());
		
		lettucePlant.setItem(Food.lettuce.asStack());
		cucumberPlant.setItem(Food.cucumber.asStack());
		ricePlant.setItem(Food.rice.asStack());
		chiliPlant.setItem(Food.chili.asStack());
		tomatoPlant.setItem(Food.tomato.asStack());
		paprikaPlant.setItem(Food.paprika.asStack());
		onionPlant.setItem(Food.onion.asStack());
		pepperPlant.setSeed(Food.pepperSeeds.asStack()).setCrop(new ItemStack(spices, 1, 1));
		vanillaPlant.setSeed(Food.vanillaSeeds.asStack()).setCrop(new ItemStack(spices, 1, 3));
		cornPlant.setItem(Food.corn.asStack());
		
		strawberryBush.setItem(Food.strawberry.asStack());
		strawberryBush.setItem(Food.strawberry.asStack());
		raspberryBush.setItem(Food.raspberry.asStack());
		blueberryBush.setItem(Food.blueberry.asStack());
		blackberryBush.setItem(Food.blackberry.asStack());
		redcurrantBush.setItem(Food.redcurrant.asStack());
		
		Food.lettuce.setBlockPlaced(lettucePlant, 0);
		Food.cucumber.setBlockPlaced(cucumberPlant, 0);
		Food.rice.setBlockPlaced(ricePlant, 0);
		Food.chili.setBlockPlaced(chiliPlant, 0);
		Food.tomato.setBlockPlaced(tomatoPlant, 0);
		Food.paprika.setBlockPlaced(paprikaPlant, 0);
		Food.onion.setBlockPlaced(onionPlant, 0);
		Food.pepperSeeds.setBlockPlaced(pepperPlant, 0);
		Food.vanillaSeeds.setBlockPlaced(vanillaPlant, 0);
		Food.corn.setBlockPlaced(cornPlant, 0);
		
		Food.strawberry.setBlockPlaced(strawberryBush, 0);
		Food.raspberry.setBlockPlaced(raspberryBush, 0);
		Food.blackberry.setBlockPlaced(blackberryBush, 0);
		Food.blueberry.setBlockPlaced(blueberryBush, 0);
		Food.redcurrant.setBlockPlaced(redcurrantBush, 0);
		
		Food.orange.setBlockPlaced(fruitSaplings, 0);
		Food.pear.setBlockPlaced(fruitSaplings, 1);
		Food.cherry.setBlockPlaced(fruitSaplings, 2);
		Food.plum.setBlockPlaced(fruitSaplings, 3);
		Food.banana.setBlockPlaced(fruitSaplings2, 0);
		Food.pineapple.setBlockPlaced(fruitSaplings2, 1);
		
		Food.pizza.setBlockPlaced(pizza, 0);
		
		CSBlocks.addBlock(lettucePlant);
		CSBlocks.addBlock(cucumberPlant);
		CSBlocks.addBlock(ricePlant);
		CSBlocks.addBlock(chiliPlant);
		CSBlocks.addBlock(tomatoPlant);
		CSBlocks.addBlock(paprikaPlant);
		CSBlocks.addBlock(onionPlant);
		CSBlocks.addBlock(pepperPlant);
		CSBlocks.addBlock(vanillaPlant);
		CSBlocks.addBlock(cornPlant);
		
		CSBlocks.addBlock(strawberryBush);
		CSBlocks.addBlock(raspberryBush);
		CSBlocks.addBlock(blueberryBush);
		CSBlocks.addBlock(blackberryBush);
		CSBlocks.addBlock(redcurrantBush);
		
		CSBlocks.addBlock(fruitSaplings, "fruit_saplings_1");
		CSBlocks.addBlock(fruitSaplings2, "fruit_saplings_2");
		CSBlocks.addBlock(fruitLogs, "fruit_logs_1");
		CSBlocks.addBlock(fruitLogs2, "fruit_logs_2");
		CSBlocks.addBlock(fruitLeaves, "fruit_leaves_1");
		CSBlocks.addBlock(fruitLeaves2, "fruit_leaves_2");
		
		CSBlocks.addBlock(saltOre);
		CSBlocks.addBlock(pizza);
	}
	
	@Override
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		
		GameRegistry.registerWorldGenerator(new WorldGenHandler(), 8);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		
		this.addCraftingRecipes();
		this.addSmeltingRecipes();
		this.addLocalizations();
		
		MinecraftForge.addGrassSeed(Food.pepperSeeds.asStack(), 8);
		MinecraftForge.addGrassSeed(Food.vanillaSeeds.asStack(), 6);
		
		proxy.registerRenderers();
	}
	
	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}
	
	private void addCraftingRecipes()
	{
		CSCrafting.addShapelessRecipe(new ItemStack(spices, 4, 1), Food.pepperSeeds.asStack());
		CSCrafting.addShapelessRecipe(new ItemStack(spices, 3, 2), CSStacks.cocoa, CSStacks.sugar, CSStacks.sugar);
		CSCrafting.addShapelessRecipe(new ItemStack(spices, 4, 3), Food.vanillaSeeds.asStack());
		
		for (int i = 0; i < 7; i++)
		{
			ItemStack theSoup = new ItemStack(soupBowls, 1, i);
			CSCrafting.addShapelessRecipe(soupBowls.setModifiers(theSoup.copy(), true, soupBowls.isPeppered(theSoup)), theSoup, salt);
			CSCrafting.addShapelessRecipe(soupBowls.setModifiers(theSoup.copy(), soupBowls.isSalted(theSoup), true), theSoup, pepper);
			CSCrafting.addShapelessRecipe(soupBowls.setModifiers(theSoup.copy(), true, true), theSoup, pepper, salt);
			CSCrafting.addShapelessRecipe(soupBowls.setModifiers(theSoup.copy(), true, soupBowls.isPeppered(theSoup)), theSoup, salt);
			CSCrafting.addShapelessRecipe(soupBowls.setModifiers(theSoup.copy(), soupBowls.isSalted(theSoup), true), theSoup, pepper);
			CSCrafting.addShapelessRecipe(soupBowls.setModifiers(theSoup.copy(), true, true), theSoup, pepper, salt);
		}
		
		CSCrafting.addShapelessRecipe(new ItemStack(soupBowls, 1, 0), CSStacks.bowl, CSStacks.water_bucket);
		for (int i = 0; i < 14; i += 7)
		{
			CSCrafting.addShapelessRecipe(new ItemStack(soupBowls, 1, 1 + i), new ItemStack(soupBowls, 1, i), Items.baked_potato);
			CSCrafting.addShapelessRecipe(new ItemStack(soupBowls, 1, 2 + i), new ItemStack(soupBowls, 1, i), Food.carrotCooked.asStack());
			CSCrafting.addShapelessRecipe(new ItemStack(soupBowls, 1, 3 + i), new ItemStack(soupBowls, 1, i), Food.carrotCooked.asStack(), Items.baked_potato);
			CSCrafting.addShapelessRecipe(new ItemStack(soupBowls, 1, 4 + i), new ItemStack(soupBowls, 1, i), Food.tomato.asStack());
			CSCrafting.addShapelessRecipe(new ItemStack(soupBowls, 1, 5 + i), new ItemStack(soupBowls, 1, i + 4), Food.rice.asStack());
			CSCrafting.addShapelessRecipe(new ItemStack(soupBowls, 1, 6 + i), new ItemStack(soupBowls, 1, i), Food.pasta.asStack());
		}
		
		ItemStack milkBowl = new ItemStack(milkBowls, 1, 0);
		
		CSCrafting.addShapelessRecipe(milkBowl, CSStacks.bowl, CSStacks.milk_bucket);
		CSCrafting.addShapelessRecipe(new ItemStack(milkBowls, 1, 1), milkBowl, Food.cereals1.asStack());
		CSCrafting.addShapelessRecipe(new ItemStack(milkBowls, 1, 2), milkBowl, Food.cereals2.asStack());
		CSCrafting.addShapelessRecipe(new ItemStack(milkBowls, 1, 3), milkBowl, Food.cereals1.asStack(), Food.cereals2.asStack());
		CSCrafting.addShapelessRecipe(new ItemStack(milkBowls, 1, 4), milkBowl, Food.rice.asStack());
		CSCrafting.addShapelessRecipe(new ItemStack(milkBowls, 1, 5), milkBowl, Food.rice.asStack(), cinnamon);
		CSCrafting.addShapelessRecipe(new ItemStack(milkBowls, 1, 5), milkBowl, cinnamon);
		CSCrafting.addShapelessRecipe(new ItemStack(milkBowls, 1, 6), milkBowl, Food.rice.asStack(), vanilla);
		CSCrafting.addShapelessRecipe(new ItemStack(milkBowls, 1, 6), milkBowl, vanilla);
		CSCrafting.addShapelessRecipe(new ItemStack(milkBowls, 1, 7), milkBowl, CSStacks.dye_red);
		CSCrafting.addShapelessRecipe(new ItemStack(milkBowls, 1, 8), milkBowl, CSStacks.dye_light_green);
		
		for (Food f : Food.foodList)
		{
			if (f != null)
			{
				f.addRecipe();
			}
		}
		
		CSCrafting.addShapelessRecipe(new ItemStack(juice, 1, 0), Items.glass_bottle, Food.appleStomped.asStack());
		CSCrafting.addShapelessRecipe(new ItemStack(juice, 1, 1), Items.glass_bottle, Food.orange.asStack());
		CSCrafting.addShapelessRecipe(new ItemStack(juice, 1, 2), Items.glass_bottle, Food.tomato.asStack());
	}
	
	private void addSmeltingRecipes()
	{
		CSCrafting.addFurnaceRecipe(new ItemStack(soupBowls, 1, 0), new ItemStack(soupBowls, 1, 7), 0F);
	}
	
	private void addLocalizations()
	{
		// addFoodDesc(Food.bacon, "Delicious bacon");
		// addFoodDesc(Food.bacon_raw, "Raw bacon, cook it to win!");
		// addFoodDesc(Food.breadslice, "The half of a bread");
		// addFoodDesc(Food.butter, "Delicious butter, makes you fat");
		// addFoodDesc(Food.candycane, "Pure sugar");
		// addFoodDesc(Food.cereals1, "Cereals to have for breakfast");
		// addFoodDesc(Food.cereals2, "Chocolate Cereals!");
		// addFoodDesc(Food.cheese, "A big cheese wheel");
		// addFoodDesc(Food.cheese_slice, "A tiny slice of the big cheese wheel");
		// addFoodDesc(Food.chili, "Hot and spicy!");
		// addFoodDesc(Food.chocolate, "Pretty sweet");
		// addFoodDesc(Food.chocolateWhite, "SWEET!");
		// addFoodDesc(Food.chocolateCow, EnumChatFormatting.UNDERLINE + "Not" +
		// EnumChatFormatting.RESET + " made from cows!");
		// addFoodDesc(Food.chocolateCookie,
		// "Chocolate cookies like your grandma would craft them");
		// addFoodDesc(Food.corn, "Better make some popcorn!");
		// addFoodDesc(Food.cucumber, "Long and green");
		// addFoodDesc(Food.fried_egg, "An egg");
		// addFoodDesc(Food.hamburger, "Directly from McDerp!");
		// addFoodDesc(Food.frenchfries, "Directly from McDerp!");
		// addFoodDesc(Food.honeydrop, "Made by bees");
		// addFoodDesc(Food.meatball, "Many cows died for this");
		// addFoodDesc(Food.omelette, "Many eggs");
		// addFoodDesc(Food.onion, "An onion");
		// addFoodDesc(Food.paprika, "Another plantable vegetable");
		// addFoodDesc(Food.pasta, "Pretty long and salted");
		// addFoodDesc(Food.pepperSeeds, "You shouldn't eat those");
		// addFoodDesc(Food.pizza, "Everybody loves pizza");
		// addFoodDesc(Food.popcorn, "No sugar, no salt");
		// addFoodDesc(Food.popcorn_salty, "Salty");
		// addFoodDesc(Food.popcorn_sweet, "Sweet popcorn");
		// addFoodDesc(Food.rice, "Rice");
		// addFoodDesc(Food.salad, "Just normal green salad");
		// addFoodDesc(Food.salami, "Made from cows");
		// addFoodDesc(Food.toast, "Not toasted yet");
		// addFoodDesc(Food.toast_cheese, "Toasted toast with cheese");
		// addFoodDesc(Food.toast_salami, "Toasted toast with salami");
		// addFoodDesc(Food.toast_toasted, "Toasted toast");
		// addFoodDesc(Food.tomato, "A vegatable or a fruit?");
		// addFoodDesc(Food.vanillaSeeds, "Do not eat! Plant!");
		//
		// addFoodDesc(Food.orange, "The color is orange");
		// addFoodDesc(Food.pear, "Pearous");
		// addFoodDesc(Food.cherry, "Two cherrys. But the name says its one.");
		// addFoodDesc(Food.strawberry, "Make a bush");
		// addFoodDesc(Food.raspberry, "Make a bush");
		// addFoodDesc(Food.blueberry, "Make a bush");
		// addFoodDesc(Food.blackberry, "Make a bush");
		// addFoodDesc(Food.redcurrant, "Make a bush");
		//
		// addFoodDesc(Food.plum, "Plum");
		// addFoodDesc(Food.banana, "A yellow banana looking weird");
		// addFoodDesc(Food.seagrass, "Lives under the sea");
		//
		// addFoodDesc(Food.icecube, "An icy cube");
		// addFoodDesc(Food.icecreamCone, "A cone to be filled with icecream");
		// addFoodDesc(Food.icecream, "Tasty, cold icecream");
		// addFoodDesc(Food.icecreamChocolate,
		// "Chocolate icecream made from icecream and chocolate");
		// addFoodDesc(Food.icecreamStrawberry,
		// "Strawberry icecream made from icecream and strawberrys");
		// addFoodDesc(Food.icecreamVanilla, "Vanilla icecream made from icecream and vanilla");
		//
		// addFoodDesc(Food.apple, "An apple, dropped by an oak tree.");
		// addFoodDesc(Food.appleStomped, "Stomped");
		// addFoodDesc(Food.appleGold1, "A golden apple");
		// addFoodDesc(Food.appleGold2, "A golden apple");
		// addFoodDesc(Food.appleDiamond, "An apple, wrapped in diamonds.");
		// addFoodDesc(Food.melon, "A green melon");
		// addFoodDesc(Food.melonGold1, "A melon with some gold dust");
		// addFoodDesc(Food.potato, "A dirty potato. Don't eat it.");
		// addFoodDesc(Food.potatoCooked, "A cooked potato");
		// addFoodDesc(Food.potatoStomped, "Stomped");
		// addFoodDesc(Food.potatoGold1, "A golden potato");
		// addFoodDesc(Food.potatoGold2, "A golden potato");
		// addFoodDesc(Food.potatoDiamond, "A potato wrapped in diamonds.");
		// addFoodDesc(Food.poisonousPotato, "Doesn't look healthy");
		// addFoodDesc(Food.carrot, "A carrot");
		// addFoodDesc(Food.carrotCooked, "A carrot, but cooked.");
		// addFoodDesc(Food.carrotStomped, "Stomped");
		// addFoodDesc(Food.carrotGold1, "A golden carrot");
		// addFoodDesc(Food.carrotDiamond, "Improves your vision");
		// addFoodDesc(Food.bread, "Baked from wheat");
		// addFoodDesc(Food.cookie, "A tiny cookie");
		// addFoodDesc(Food.porkRaw, "Dropped by a pig.");
		// addFoodDesc(Food.porkCooked, "Cooked pig");
		// addFoodDesc(Food.beefRaw, "Dropped by a cow.");
		// addFoodDesc(Food.beefCooked, "Cooked cow");
		// addFoodDesc(Food.chickenRaw, "Dropped by a chicken, may make you hungry.");
		// addFoodDesc(Food.chickenCooked, "Cooked chicken");
		// addFoodDesc(Food.fishRaw, "Came out of the water");
		// addFoodDesc(Food.fishCooked, "Cooked fish");
		// addFoodDesc(Food.rottenFlesh, "Unhealthy zombie flesh");
		// addFoodDesc(Food.spiderEye, "You shouldn't eat that.");
		// addFoodDesc(Food.pumpkinPie, "With whole fruits");
	}
	
	public static void generate(Random random, int chunkX, int chunkZ, World world)
	{
		for (int i = 0; i < 10; i++)
		{
			int x = chunkX * 16 + random.nextInt(16);
			int y = random.nextInt(48);
			int z = chunkZ * 16 + random.nextInt(16);
			if (world.getBiomeGenForCoords(x, z) instanceof BiomeGenOcean)
				(new WorldGenMinable(saltOre, 6)).generate(world, random, x, y, z);
		}
		if (random.nextInt(200) == 0)
		{
			int x = chunkX * 16 + random.nextInt(16);
			int y = 128;
			int z = chunkZ * 16 + random.nextInt(16);
			
			while (y > 0)
			{
				if (world.getBlock(x, y, z).isSideSolid(world, x, y, z, ForgeDirection.UP))
				{
					break;
				}
				y--;
			}
			
			(new WorldGenGardener()).generate(world, random, x, y, z);
		}
		
		if (random.nextInt(5) == 0)
		{
			int x = chunkX * 16 + random.nextInt(16);
			int y = 128;
			int z = chunkZ * 16 + random.nextInt(16);
			
			if ((world.getBiomeGenForCoords(x, z) instanceof BiomeGenForest))
			{
				while (y > 0)
				{
					Block block = world.getBlock(x, y, z);
					if (block == Blocks.grass || block == Blocks.dirt)
					{
						break;
					}
					y--;
				}
				
				int treeType = random.nextInt(5);
				int height = 4 + random.nextInt(4);
				if (treeType == 4)
				{
					height += 3;
				}
				(new CustomTreeGen(false, height, treeType > 3 ? fruitLogs2 : fruitLogs, treeType > 3 ? fruitLeaves2 : fruitLeaves, treeType & 3, treeType & 3)).generate(world, random, x, y, z);
			}
		}
		if (random.nextInt(10) == 0)
		{
			int x = chunkX * 16 + random.nextInt(16);
			int y = 128;
			int z = chunkZ * 16 + random.nextInt(16);
			
			while (y > 0)
			{
				Block block = world.getBlock(x, y, z);
				if (block == Blocks.grass || block == Blocks.dirt)
				{
					break;
				}
				y--;
			}
			
			Block bushType = getBushTypeForBiome(world.getBiomeGenForCoords(x, y), random);
			(new WorldGenRanged(bushType, 3)).generate(world, random, x, y, z);
		}
	}
	
	public static Block getBushTypeForBiome(BiomeGenBase biome, Random random)
	{
		Block[] bushes = new Block[] {
				raspberryBush,
				blackberryBush,
				redcurrantBush,
				blueberryBush,
				strawberryBush };
		
		if (biome instanceof BiomeGenForest)
		{
			return random.nextBoolean() ? raspberryBush : blackberryBush;
		}
		else if (biome instanceof BiomeGenJungle)
		{
			return random.nextBoolean() ? redcurrantBush : blueberryBush;
		}
		else
		{
			switch (random.nextInt(5))
			{
				case 0:
					return raspberryBush;
				case 1:
					return blackberryBush;
				case 2:
					return redcurrantBush;
				case 3:
					return blueberryBush;
				default:
					return strawberryBush;
			}
		}
	}
	
	public class WorldGenHandler implements IWorldGenerator
	{
		@Override
		public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
		{
			if (world.provider.isSurfaceWorld())
			{
				MoreFoodMod.generate(random, chunkX, chunkZ, world);
			}
		}
	}
}
