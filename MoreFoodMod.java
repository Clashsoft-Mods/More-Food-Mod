package clashsoft.mods.morefood;

import java.util.Random;

import clashsoft.cslib.minecraft.block.BlockCustomLeaves;
import clashsoft.cslib.minecraft.block.BlockCustomLog;
import clashsoft.cslib.minecraft.block.BlockCustomSapling;
import clashsoft.cslib.minecraft.block.CSBlocks;
import clashsoft.cslib.minecraft.crafting.CSCrafting;
import clashsoft.cslib.minecraft.item.CSItems;
import clashsoft.cslib.minecraft.item.CSStacks;
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
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = MoreFoodMod.MODID, name = MoreFoodMod.NAME, version = MoreFoodMod.VERSION)
public class MoreFoodMod
{
	public static final String			MODID			= "morefood";
	public static final String			NAME			= "More Food Mod";
	public static final String			ACRONYM			= "mfm";
	public static final int				REVISION		= 0;
	public static final String			VERSION			= CSUpdate.CURRENT_VERSION + "-" + REVISION;
	
	@Instance(MODID)
	public static MoreFoodMod			instance;
	
	@SidedProxy(clientSide = "clashsoft.mods.morefood.client.MFMClientProxy", serverSide = "clashsoft.mods.morefood.common.MFMCommonProxy")
	public static MFMCommonProxy		proxy;
	
	public static Item					salt			= new Item().setUnlocalizedName("salt").setTextureName("morefood:salt");
	public static Item					pepper			= new Item().setUnlocalizedName("pepper").setTextureName("morefood:pepper");
	public static Item					cinnamon		= new Item().setUnlocalizedName("cinnamon").setTextureName("morefood:cinnamon");
	public static Item					vanilla			= new Item().setUnlocalizedName("vanilla").setTextureName("vanilla");
	public static ItemJuice				juice			= (ItemJuice) new ItemJuice().setUnlocalizedName("juice_bottles");
	public static ItemMilkBowls			milkBowls		= (ItemMilkBowls) new ItemMilkBowls(4).setUnlocalizedName("milk_bowls");
	public static ItemSoupBowls			soupBowls		= (ItemSoupBowls) new ItemSoupBowls(6).setUnlocalizedName("soup_bowls");
	public static ItemFoods				foods			= (ItemFoods) new ItemFoods(3, 1.0F).setUnlocalizedName("food_items");
	public static ItemRecipeBook		recipeBook		= (ItemRecipeBook) new ItemRecipeBook().setUnlocalizedName("recipe_book").setTextureName("morefood:recipe_book");
	
	public static BlockPlantMoreFood	cucumberPlant	= (BlockPlantMoreFood) new BlockPlantMoreFood(3, Food.cucumber.asStack(), Food.cucumber.asStack()).setBlockName("cucumber_plant").setBlockTextureName("morefood:cucumber");
	public static BlockPlantMoreFood	tomatoPlant		= (BlockPlantMoreFood) new BlockPlantMoreFood(3, Food.tomato.asStack(), Food.tomato.asStack()).setBlockName("tomato_plant").setBlockTextureName("morefood:tomato");
	public static BlockPlantMoreFood	pepperPlant		= (BlockPlantMoreFood) new BlockPlantMoreFood(3, Food.pepperSeeds.asStack(), new ItemStack(pepper)).setBlockName("pepper_plant").setBlockTextureName("morefood:pepper");
	public static BlockPlantMoreFood	saladPlant		= (BlockPlantMoreFood) new BlockPlantMoreFood(3, Food.salad.asStack(), Food.salad.asStack()).setBlockName("salad_plant").setBlockTextureName("morefood:salad");
	public static BlockPlantMoreFood	onionPlant		= (BlockPlantMoreFood) new BlockPlantMoreFood(4, Food.onion.asStack(), Food.onion.asStack()).setBlockName("onion_plant").setBlockTextureName("morefood:onion");
	public static BlockPlantMoreFood	chiliPlant		= (BlockPlantMoreFood) new BlockPlantMoreFood(6, Food.chili.asStack(), Food.chili.asStack()).setBlockName("chili_plant").setBlockTextureName("morefood:chili");
	public static BlockPlantMoreFood	paprikaPlant	= (BlockPlantMoreFood) new BlockPlantMoreFood(6, Food.paprika.asStack(), Food.paprika.asStack()).setBlockName("paprika_plant").setBlockTextureName("morefood:paprika");
	public static BlockPlantMoreFood	ricePlant		= (BlockPlantMoreFood) new BlockPlantMoreFood(6, Food.rice.asStack(), Food.rice.asStack()).setBlockName("rice_plant").setBlockTextureName("morefood:rice");
	public static BlockPlantMoreFood	cornPlant		= (BlockPlantMoreFood) new BlockPlantMoreFood(6, Food.corn.asStack(), Food.corn.asStack()).setBlockName("corn_plant").setBlockTextureName("morefood:corn");
	public static BlockPlantMoreFood	vanillaPlant	= (BlockPlantMoreFood) new BlockPlantMoreFood(4, Food.vanillaSeeds.asStack(), new ItemStack(vanilla)).setBlockName("vanilla_plant").setBlockTextureName("morefood:vanilla");
	
	public static BlockSaltOre			saltOre			= (BlockSaltOre) new BlockSaltOre().setBlockName("salt_ore").setBlockTextureName("morefood:salt_ore");
	public static BlockPizza			pizza			= (BlockPizza) new BlockPizza().setBlockName("pizza").setBlockTextureName("morefood:pizza");
	
	public static BlockBush				strawberryBush	= (BlockBush) new BlockBush(Food.strawberry.asStack(), "morefood:strawberry_bush", "morefood:strawberry_bush_stem").setBlockName("strawberry_bush");
	public static BlockBush				raspberryBush	= (BlockBush) new BlockBush(Food.raspberry.asStack(), "morefood:raspberry_bush", "morefood:raspberry_bush_stem").setBlockName("raspberry_bush");
	public static BlockBush				blueberryBush	= (BlockBush) new BlockBush(Food.blueberry.asStack(), "morefood:blueberry_bush", "morefood:blueberry_bush_stem").setBlockName("blueberry_bush");
	public static BlockBush				blackberryBush	= (BlockBush) new BlockBush(Food.blackberry.asStack(), "morefood:blackberry_bush", "morefood:blackberry_bush_stem").setBlockName("blackberry_bush");
	public static BlockBush				redcurrantBush	= (BlockBush) new BlockBush(Food.redcurrant.asStack(), "morefood:redcurrant_bush", "morefood:redcurrant_bush_stem").setBlockName("redcurrant_bush");
	
	public static BlockCustomSapling	fruitSaplings	= new BlockFruitSapling(new String[] {
			"orange_sapling",
			"pear_sapling",
			"cherry_sapling",
			"plum_sapling"								}, new String[] {
			"morefood:orange_sapling",
			"morefood:pear_sapling",
			"morefood:cherry_sapling",
			"morefood:plum_sapling"					});
	public static BlockCustomSapling	fruitSaplings2	= new BlockFruitSapling(new String[] { "banana_sapling" }, new String[] { "morefood:banana_sapling" });
	public static BlockCustomLog		fruitLogs		= new BlockCustomLog(new String[] {
			"orange_log",
			"pear_log",
			"cherry_log",
			"plum_log"									}, new String[] {
			"morefood:orange_log_top",
			"morefood:pear_log_top",
			"morefood:cherry_log_top",
			"morefood:plum_log_top"					}, new String[] {
			"morefood:orange_log",
			"morefood:pear_log",
			"morefood:cherry_log",
			"morefood:plum_log"						});
	public static BlockCustomLog		fruitLogs2		= new BlockCustomLog(new String[] { "banana_log" }, new String[] { "morefood:banana_log_top" }, new String[] { "morefood:banana_log" });
	
	public static BlockCustomLeaves		fruitLeaves		= new BlockCustomLeaves(new String[] {
			"orange_leaves",
			"pear_leaves",
			"cherry_leaves",
			"plum_leaves"								}, new String[] {
			"morefood:orange_leaves",
			"morefood:pear_leaves",
			"morefood:cherry_leaves",
			"morefood:plum_leaves"						});
	public static BlockCustomLeaves		fruitLeaves2	= new BlockCustomLeaves(new String[] { "banana_leaves", }, new String[] { "morefood:banana_leaves", });
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		CSUpdate.updateCheckCS(NAME, ACRONYM, VERSION);
		
		CSItems.addItem(foods, "food_items");
		CSItems.addItem(salt, "salt");
		CSItems.addItemWithShapelessRecipe(pepper, "pepper", 4, Food.pepperSeeds.asStack());
		CSItems.addItemWithShapelessRecipe(cinnamon, "cinnamon", 3, CSStacks.cocoa, CSStacks.sugar, CSStacks.sugar);
		CSItems.addItemWithShapelessRecipe(vanilla, "vanilla", 4, Food.vanillaSeeds.asStack());
		CSItems.addItem(juice, "juice_bottles");
		CSItems.addItem(milkBowls, "milk_bowls");
		CSItems.addItem(soupBowls, "soup_bowls");
		
		CSItems.addItemWithRecipe(recipeBook, "recipe_book", 1, " s ", "bBp", " t ", 's', Food.salad.asStack(), 'b', Items.beef, 'B', Items.book, 'p', Items.cooked_porkchop, 't', Food.tomato.asStack());
		
		fruitSaplings.setBlockName("fruit").setHardness(0F).setCreativeTab(CreativeTabs.tabDecorations);
		fruitSaplings2.setBlockName("fruit").setHardness(0F).setCreativeTab(CreativeTabs.tabDecorations);
		fruitLogs.setBlockName("fruit").setHardness(2.0F).setCreativeTab(CreativeTabs.tabBlock);
		fruitLogs2.setBlockName("fruit").setHardness(2.0F).setCreativeTab(CreativeTabs.tabBlock);
		fruitLeaves.setBlockName("fruit").setHardness(0.2F).setCreativeTab(CreativeTabs.tabDecorations);
		fruitLeaves.setSaplingStacks(Food.orange.asStack(), Food.pear.asStack(), Food.cherry.asStack(), Food.plum.asStack());
		fruitLeaves2.setBlockName("fruit").setHardness(0.2F).setCreativeTab(CreativeTabs.tabDecorations);
		fruitLeaves2.setSaplingStacks(Food.banana.asStack());
		
		CSBlocks.addBlock(cucumberPlant);
		CSBlocks.addBlock(tomatoPlant);
		CSBlocks.addBlock(pepperPlant);
		CSBlocks.addBlock(saladPlant);
		CSBlocks.addBlock(onionPlant);
		CSBlocks.addBlock(chiliPlant);
		CSBlocks.addBlock(paprikaPlant);
		CSBlocks.addBlock(ricePlant);
		CSBlocks.addBlock(cornPlant);
		CSBlocks.addBlock(vanillaPlant);
		
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
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		GameRegistry.registerWorldGenerator(new WorldGenHandler(), 8);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		
		this.addCraftingRecipes();
		this.addSmeltingRecipes();
		this.addLocalizations();
		
		MinecraftForge.addGrassSeed(Food.pepperSeeds.asStack(), 8);
		MinecraftForge.addGrassSeed(Food.vanillaSeeds.asStack(), 6);
		
		proxy.registerRenderers();
	}
	
	private void addCraftingRecipes()
	{
		for (int i = 0; i < 7; i++)
		{
			ItemStack theSoup = new ItemStack(soupBowls, 1, i);
			CSCrafting.addShapelessCrafting(soupBowls.setModifiers(theSoup.copy(), true, soupBowls.isPeppered(theSoup)), theSoup, salt);
			CSCrafting.addShapelessCrafting(soupBowls.setModifiers(theSoup.copy(), soupBowls.isSalted(theSoup), true), theSoup, pepper);
			CSCrafting.addShapelessCrafting(soupBowls.setModifiers(theSoup.copy(), true, true), theSoup, pepper, salt);
			CSCrafting.addShapelessCrafting(soupBowls.setModifiers(theSoup.copy(), true, soupBowls.isPeppered(theSoup)), theSoup, salt);
			CSCrafting.addShapelessCrafting(soupBowls.setModifiers(theSoup.copy(), soupBowls.isSalted(theSoup), true), theSoup, pepper);
			CSCrafting.addShapelessCrafting(soupBowls.setModifiers(theSoup.copy(), true, true), theSoup, pepper, salt);
		}
		
		CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 0), CSStacks.bowl, CSStacks.water_bucket);
		for (int i = 0; i < 14; i += 7)
		{
			CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 1 + i), new ItemStack(soupBowls, 1, i), Items.baked_potato);
			CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 2 + i), new ItemStack(soupBowls, 1, i), Food.carrotCooked.asStack());
			CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 3 + i), new ItemStack(soupBowls, 1, i), Food.carrotCooked.asStack(), Items.baked_potato);
			CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 4 + i), new ItemStack(soupBowls, 1, i), Food.tomato.asStack());
			CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 5 + i), new ItemStack(soupBowls, 1, i + 4), Food.rice.asStack());
			CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 6 + i), new ItemStack(soupBowls, 1, i), Food.pasta.asStack());
		}
		
		ItemStack milkBowl = new ItemStack(milkBowls, 1, 0);
		
		CSCrafting.addShapelessCrafting(milkBowl, CSStacks.bowl, CSStacks.milk_bucket);
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 1), milkBowl, Food.cereals1.asStack());
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 2), milkBowl, Food.cereals2.asStack());
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 3), milkBowl, Food.cereals1.asStack(), Food.cereals2.asStack());
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 4), milkBowl, Food.rice.asStack());
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 5), milkBowl, Food.rice.asStack(), cinnamon);
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 5), milkBowl, cinnamon);
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 6), milkBowl, Food.rice.asStack(), vanilla);
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 6), milkBowl, vanilla);
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 7), milkBowl, CSStacks.dye_red);
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 8), milkBowl, CSStacks.dye_light_green);
		
		for (Food f : Food.foodList)
		{
			if (f != null)
			{
				f.addRecipe();
			}
		}
		
		CSCrafting.addShapelessCrafting(new ItemStack(juice, 1, 0), Items.glass_bottle, Food.appleStomped.asStack());
		CSCrafting.addShapelessCrafting(new ItemStack(juice, 1, 1), Items.glass_bottle, Food.orange.asStack());
		CSCrafting.addShapelessCrafting(new ItemStack(juice, 1, 2), Items.glass_bottle, Food.tomato.asStack());
	}
	
	private void addSmeltingRecipes()
	{
		CSCrafting.addSmelting(new ItemStack(soupBowls, 1, 0), new ItemStack(soupBowls, 1, 7), 0F);
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
			int randPosX = chunkX * 16 + random.nextInt(16);
			int randPosY = random.nextInt(48);
			int randPosZ = chunkZ * 16 + random.nextInt(16);
			if (world.getBiomeGenForCoords(randPosX, randPosZ) instanceof BiomeGenOcean)
				(new WorldGenMinable(saltOre, 6)).generate(world, random, randPosX, randPosY, randPosZ);
		}
		if (random.nextInt(200) == 0)
		{
			int randPosX = chunkX * 16 + random.nextInt(16);
			int randPosY = 128;
			int randPosZ = chunkZ * 16 + random.nextInt(16);
			for (int j = randPosY; j > 0; j--)
			{
				if ((world.getBlock(randPosX, randPosY, randPosZ) == Blocks.grass || world.getBlock(randPosX, randPosY, randPosZ) == Blocks.dirt))
				{
					randPosY = j;
					break;
				}
				else
				{
					randPosY--;
				}
			}
			if (randPosY >= 0)
			{
				(new WorldGenGardener()).generate(world, random, randPosX, randPosY, randPosZ);
			}
		}
		
		if (random.nextInt(5) == 0)
		{
			int randPosX = chunkX * 16 + random.nextInt(16);
			int randPosY = 128;
			int randPosZ = chunkZ * 16 + random.nextInt(16);
			
			if ((world.getBiomeGenForCoords(randPosX, randPosZ) instanceof BiomeGenForest))
			{
				for (int j = randPosY; j > 0; j--)
				{
					if ((world.getBlock(randPosX, randPosY, randPosZ) == Blocks.grass || world.getBlock(randPosX, randPosY, randPosZ) == Blocks.dirt))
					{
						randPosY = j;
						break;
					}
					else
					{
						randPosY--;
					}
				}
				
				int treeType = random.nextInt(5);
				(new CustomTreeGen(false, 4 + random.nextInt(4), treeType > 3 ? fruitLogs2 : fruitLogs, treeType > 3 ? fruitLeaves2 : fruitLeaves, treeType & 3, treeType & 3)).generate(world, random, randPosX, randPosY, randPosZ);
			}
		}
		if (random.nextInt(10) == 0)
		{
			int randPosX = chunkX * 16 + random.nextInt(16);
			int randPosY = 128;
			int randPosZ = chunkZ * 16 + random.nextInt(16);
			
			for (int j = randPosY; j > 0; j--)
			{
				if ((world.getBlock(randPosX, randPosY, randPosZ) == Blocks.grass || world.getBlock(randPosX, randPosY, randPosZ) == Blocks.dirt))
				{
					randPosY = j + 1;
					break;
				}
				else
				{
					randPosY--;
				}
			}
			
			Block bushType = getBushTypeForBiome(world.getBiomeGenForCoords(randPosX, randPosY), random);
			(new WorldGenRanged(bushType, 3)).generate(world, random, randPosX, randPosY, randPosZ);
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
