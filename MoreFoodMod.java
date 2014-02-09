package clashsoft.mods.morefood;

import java.util.Random;

import clashsoft.cslib.minecraft.block.BlockCustomLeaves;
import clashsoft.cslib.minecraft.block.BlockCustomLog;
import clashsoft.cslib.minecraft.block.BlockCustomSapling;
import clashsoft.cslib.minecraft.block.CSBlocks;
import clashsoft.cslib.minecraft.crafting.CSCrafting;
import clashsoft.cslib.minecraft.item.CSItems;
import clashsoft.cslib.minecraft.item.block.ItemCustomBlock;
import clashsoft.cslib.minecraft.lang.CSLang;
import clashsoft.cslib.minecraft.update.CSUpdate;
import clashsoft.cslib.minecraft.update.ModUpdate;
import clashsoft.cslib.minecraft.world.gen.CustomTreeGenerator;
import clashsoft.mods.morefood.block.*;
import clashsoft.mods.morefood.common.MFMCommonProxy;
import clashsoft.mods.morefood.food.Food;
import clashsoft.mods.morefood.item.*;
import clashsoft.mods.morefood.world.WorldGenBushes;
import clashsoft.mods.morefood.world.WorldGenGardener;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

@Mod(modid = MoreFoodMod.MODID, name = MoreFoodMod.NAME, version = MoreFoodMod.VERSION)
public class MoreFoodMod
{
	public static final String			MODID		= "morefood";
	public static final String			NAME		= "More Food Mod";
	public static final int				REVISION	= 0;
	public static final String			VERSION		= CSUpdate.CURRENT_VERSION + "-" + REVISION;
	
	@Instance(MODID)
	public static MoreFoodMod			instance;
	
	@SidedProxy(clientSide = "clashsoft.mods.morefood.client.MFMClientProxy", serverSide = "clashsoft.mods.morefood.common.MFMCommonProxy")
	public static MFMCommonProxy		proxy;
	
	public static Item					salt;
	public static Item					pepper;
	public static Item					cinnamon;
	public static Item					vanilla;
	public static ItemJuice				juice;
	public static ItemMilkBowls			milkBowls;
	public static ItemSoupBowls			soupBowls;
	public static ItemFoods				foods;
	public static ItemRecipeBook		recipeBook;
	
	public static BlockPlantMoreFood	cucumberPlant;
	public static BlockPlantMoreFood	tomatoPlant;
	public static BlockPlantMoreFood	pepperPlant;
	public static BlockPlantMoreFood	saladPlant;
	public static BlockPlantMoreFood	onionPlant;
	public static BlockPlantMoreFood	chiliPlant;
	public static BlockPlantMoreFood	paprikaPlant;
	public static BlockPlantMoreFood	ricePlant;
	public static BlockPlantMoreFood	cornPlant;
	public static BlockPlantMoreFood	vanillaPlant;
	
	public static Block					saltOre;
	
	public static BlockBush				strawberryBush;
	public static BlockBush				raspberryBush;
	public static BlockBush				blueberryBush;
	public static BlockBush				blackberryBush;
	public static BlockBush				redcurrantBush;
	
	public static BlockCustomSapling	fruitSaplings;
	public static BlockCustomLog		fruitLogs;
	public static BlockCustomLeaves		fruitLeaves;
	public static BlockCustomSapling	fruitSaplings2;
	public static BlockCustomLog		fruitLogs2;
	public static BlockCustomLeaves		fruitLeaves2;
	
	public static BlockPizza			pizza;
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		GameRegistry.registerWorldGenerator(new WorldGenHandler(), 8);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		
		foods = (ItemFoods) new ItemFoods(3, 1.0F).setUnlocalizedName("edibleIgredient");
		
		this.addItems();
		Food.init();
		this.addBlocks();
		this.addCraftingRecipes();
		this.addSmeltingRecipes();
		this.addLocalizations();
		
		MinecraftForge.addGrassSeed(Food.pepperSeeds.asStack(), 8);
		MinecraftForge.addGrassSeed(Food.vanillaSeeds.asStack(), 6);
		
		proxy.registerRenderers();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void playerJoined(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			ModUpdate update = CSUpdate.checkForUpdate("More Food Mod", "mfm", MoreFoodMod.VERSION);
			CSUpdate.notifyUpdate((EntityPlayer) event.entity, "More Food Mod", update);
		}
	}
	
	private void addBlocks()
	{
		cucumberPlant = new BlockPlantMoreFood(3, Food.cucumber.asStack(), Food.cucumber.asStack(), "cucumber");
		tomatoPlant = new BlockPlantMoreFood(3, Food.tomato.asStack(), Food.tomato.asStack(), "tomato");
		pepperPlant = new BlockPlantMoreFood(3, Food.pepperSeeds.asStack(), new ItemStack(pepper), "pepper");
		saladPlant = new BlockPlantMoreFood(3, Food.salad.asStack(), Food.salad.asStack(), "salad");
		onionPlant = new BlockPlantMoreFood(4, Food.onion.asStack(), Food.onion.asStack(), "onion");
		chiliPlant = new BlockPlantMoreFood(6, Food.chili.asStack(), Food.chili.asStack(), "chili");
		paprikaPlant = new BlockPlantMoreFood(6, Food.paprika.asStack(), Food.paprika.asStack(), "paprika");
		ricePlant = new BlockPlantMoreFood(6, Food.rice.asStack(), Food.rice.asStack(), "rice");
		cornPlant = new BlockPlantMoreFood(6, Food.corn.asStack(), Food.corn.asStack(), "corn");
		vanillaPlant = new BlockPlantMoreFood(4, Food.vanillaSeeds.asStack(), new ItemStack(vanilla), "vanilla");
		saltOre = (new BlockSaltOre()).setHardness(3.0F).setResistance(5.0F).setBlockName("saltOre").setBlockTextureName("saltore");
		
		strawberryBush = new BlockBush(Food.strawberry.asStack(), "strawberry_bush", "strawberry_bush_stem");
		raspberryBush = new BlockBush(Food.raspberry.asStack(), "raspberry_bush", "raspberry_bush_stem");
		blueberryBush = new BlockBush(Food.blueberry.asStack(), "blueberry_bush", "blueberry_bush_stem");
		blackberryBush = new BlockBush(Food.blackberry.asStack(), "blackberry_bush", "blackberry_bush_stem");
		redcurrantBush = new BlockBush(Food.redcurrant.asStack(), "redcurrant_bush", "redcurrant_bush_stem");
		
		fruitSaplings = new BlockFruitSapling(new String[] {
				"Orange Tree Sapling",
				"Pear Tree Sapling",
				"Cherry Tree Sapling",
				"Plum Tree Sapling" }, new String[] {
				"fruitsapling_orange",
				"fruitsapling_pear",
				"fruitsapling_cherry",
				"fruitsapling_plum" });
		fruitSaplings.setBlockName("fruitSaplings").setHardness(0F).setCreativeTab(CreativeTabs.tabDecorations);
		fruitSaplings2 = new BlockFruitSapling(new String[] { "Banana Tree Sapling" }, new String[] { "fruitsapling_banana" });
		fruitSaplings2.setBlockName("fruitSaplings2").setHardness(0F).setCreativeTab(CreativeTabs.tabDecorations);
		
		fruitLogs = new BlockCustomLog(new String[] {
				"Orange Tree Log",
				"Pear Tree Log",
				"Cherry Tree Log",
				"Plum Tree Log" }, new String[] {
				"fruitlog_orange_top",
				"fruitlog_pear_top",
				"fruitlog_cherry_top",
				"fruitlog_plum_top" }, new String[] {
				"fruitlog_orange",
				"fruitlog_pear",
				"fruitlog_cherry",
				"fruitlog_plum" });
		fruitLogs.setBlockName("fruitLogs").setHardness(2.0F).setCreativeTab(CreativeTabs.tabBlock);
		fruitLogs2 = new BlockCustomLog(new String[] { "Banana Tree Log" }, new String[] { "fruitlog_banana_top" }, new String[] { "fruitlog_banana" });
		fruitLogs2.setBlockName("fruitLogs2").setHardness(2.0F).setCreativeTab(CreativeTabs.tabBlock);
		
		fruitLeaves = new BlockCustomLeaves(new String[] {
				"Orange Tree Leaves",
				"Pear Tree Leaves",
				"Cherry Tree Leaves",
				"Plum Tree Leaves" }, new String[] {
				"fruitleaves_orange",
				"fruitleaves_pear",
				"fruitleaves_cherry",
				"fruitleaves_plum" });
		fruitLeaves.setBlockName("fruitLeaves").setHardness(0.2F).setCreativeTab(CreativeTabs.tabDecorations);
		fruitLeaves.setSaplingStacks(Food.orange.asStack(), Food.pear.asStack(), Food.cherry.asStack(), Food.plum.asStack());
		
		fruitLeaves2 = new BlockCustomLeaves(new String[] { "Banana Tree Leaves" }, new String[] { "fruitleaves_banana" });
		fruitLeaves2.setBlockName("fruitLeaves2").setHardness(0.2F).setCreativeTab(CreativeTabs.tabDecorations);
		fruitLeaves2.setSaplingStacks(Food.banana.asStack());
		
		pizza = (BlockPizza) new BlockPizza().setBlockName("pizza").setHardness(0.5F).setStepSound(Block.soundTypeSnow);
		
		CSBlocks.addBlock(saltOre, "Salt Ore");
		CSBlocks.addBlock(fruitSaplings, ItemCustomBlock.class, "Fruit Saplings");
		CSBlocks.addBlock(fruitLogs, ItemCustomBlock.class, "Fruit Logs");
		CSBlocks.addBlock(fruitLeaves, ItemCustomBlock.class, "Fruit Leaves");
		CSBlocks.addBlock(fruitSaplings2, ItemCustomBlock.class, "Fruit Saplings 2");
		CSBlocks.addBlock(fruitLogs2, ItemCustomBlock.class, "Fruit Logs 2");
		CSBlocks.addBlock(fruitLeaves2, ItemCustomBlock.class, "Fruit Leaves 2");
		CSBlocks.addBlock(pizza, "Pizza");
	}
	
	private void addItems()
	{
		salt = new Item().setUnlocalizedName("salt").setTextureName("salt");
		pepper = new Item().setUnlocalizedName("pepper").setTextureName("pepper");
		cinnamon = new Item().setUnlocalizedName("cinnamon").setTextureName("cinnamon");
		vanilla = new Item().setUnlocalizedName("vanilla").setTextureName("vanilla");
		juice = (ItemJuice) new ItemJuice().setUnlocalizedName("juice");
		milkBowls = (ItemMilkBowls) new ItemMilkBowls(4).setUnlocalizedName("cerealsWithMilk");
		soupBowls = (ItemSoupBowls) new ItemSoupBowls(6).setUnlocalizedName("soups");
		recipeBook = (ItemRecipeBook) new ItemRecipeBook().setUnlocalizedName("recipebook").setCreativeTab(CreativeTabs.tabMisc);
		
		CSItems.addItem(salt, "Salt");
		CSItems.addItemWithShapelessRecipe(pepper, "Pepper", 4, new Object[] { Food.pepperSeeds.asStack() });
		CSItems.addItemWithShapelessRecipe(cinnamon, "Cinnamon", 3, new Object[] {
				new ItemStack(Items.dye, 1, 3),
				Items.sugar,
				Items.sugar });
		CSItems.addItem(vanilla, "Vanilla");
		
		GameRegistry.registerItem(foods, "morefood:food_items", MODID);
		
		CSItems.addItemWithRecipe(recipeBook, "Recipe Book", 1, new Object[] {
				" s ",
				"bBp",
				" t ",
				's',
				Food.salad.asStack(),
				'b',
				Items.beef,
				'B',
				Items.book,
				'p',
				Items.cooked_porkchop,
				't',
				Food.tomato.asStack() });
	}
	
	private void addCraftingRecipes()
	{
		CSCrafting.addShapelessCrafting(new ItemStack(pepper, 4, 0), Food.pepperSeeds.asStack());
		CSCrafting.addShapelessCrafting(new ItemStack(vanilla, 4, 0), Food.vanillaSeeds.asStack());
		
		for (int i = 0; i < 7; i++)
		{
			ItemStack theSoup = new ItemStack(soupBowls, 1, i);
			CSCrafting.addShapelessCrafting(soupBowls.addModifierToItemStack(theSoup.copy(), true, soupBowls.isPeppered(theSoup)), new Object[] {
					theSoup,
					salt });
			CSCrafting.addShapelessCrafting(soupBowls.addModifierToItemStack(theSoup.copy(), soupBowls.isSalted(theSoup), true), new Object[] {
					theSoup,
					pepper });
			CSCrafting.addShapelessCrafting(soupBowls.addModifierToItemStack(theSoup.copy(), true, true), new Object[] {
					theSoup,
					pepper,
					salt });
			CSCrafting.addShapelessCrafting(soupBowls.addModifierToItemStack(theSoup.copy(), true, soupBowls.isPeppered(theSoup)), new Object[] {
					theSoup,
					salt });
			CSCrafting.addShapelessCrafting(soupBowls.addModifierToItemStack(theSoup.copy(), soupBowls.isSalted(theSoup), true), new Object[] {
					theSoup,
					pepper });
			CSCrafting.addShapelessCrafting(soupBowls.addModifierToItemStack(theSoup.copy(), true, true), new Object[] {
					theSoup,
					pepper,
					salt });
		}
		
		CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 0), new Object[] {
				Items.bowl,
				Items.water_bucket });
		for (int i = 0; i <= 7; i += 7)
		{
			CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 1 + i), new Object[] {
					new ItemStack(soupBowls, 1, 0 + i),
					Items.baked_potato });
			CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 2 + i), new Object[] {
					new ItemStack(soupBowls, 1, 0 + i),
					Food.carrotCooked.asStack() });
			CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 3 + i), new Object[] {
					new ItemStack(soupBowls, 1, 0 + i),
					Food.carrotCooked.asStack(),
					Items.baked_potato });
			CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 4 + i), new Object[] {
					new ItemStack(soupBowls, 1, 0 + i),
					Food.tomato.asStack() });
			CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 5 + i), new Object[] {
					new ItemStack(soupBowls, 1, 4 + i),
					Food.rice.asStack() });
			CSCrafting.addShapelessCrafting(new ItemStack(soupBowls, 1, 6 + i), new Object[] {
					new ItemStack(soupBowls, 1, 0 + i),
					Food.pasta.asStack() });
		}
		
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 0), new Object[] {
				Items.bowl,
				Items.milk_bucket });
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 1), new Object[] {
				new ItemStack(milkBowls, 1, 0),
				new ItemStack(foods, 1, Food.cereals1.getID()) });
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 2), new Object[] {
				new ItemStack(milkBowls, 1, 0),
				new ItemStack(foods, 1, Food.cereals2.getID()) });
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 3), new Object[] {
				new ItemStack(milkBowls, 1, 0),
				new ItemStack(foods, 1, Food.cereals1.getID()),
				new ItemStack(foods, 1, Food.cereals2.getID()) });
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 4), new Object[] {
				new ItemStack(milkBowls, 1, 0),
				Food.rice.asStack() });
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 5), new Object[] {
				new ItemStack(milkBowls, 1, 0),
				Food.rice.asStack(),
				cinnamon });
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 5), new Object[] {
				new ItemStack(milkBowls, 1, 4),
				cinnamon });
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 6), new Object[] {
				new ItemStack(milkBowls, 1, 0),
				Food.rice.asStack(),
				vanilla });
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 6), new Object[] {
				new ItemStack(milkBowls, 1, 4),
				vanilla });
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 7), new Object[] {
				new ItemStack(milkBowls, 1, 0),
				new ItemStack(Items.dye, 1, 1) });
		CSCrafting.addShapelessCrafting(new ItemStack(milkBowls, 1, 8), new Object[] {
				new ItemStack(milkBowls, 1, 0),
				new ItemStack(Items.dye, 1, 10) });
		
		for (Food f : Food.foodList)
		{
			if (f != null)
				f.addRecipe();
		}
		
		CSCrafting.addShapelessCrafting(new ItemStack(juice, 1, 0), new Object[] {
				Items.glass_bottle,
				Food.appleStomped.asStack() });
		CSCrafting.addShapelessCrafting(new ItemStack(juice, 1, 1), new Object[] {
				Items.glass_bottle,
				Food.orange.asStack() });
		CSCrafting.addShapelessCrafting(new ItemStack(juice, 1, 2), new Object[] {
				Items.glass_bottle,
				Food.tomato.asStack() });
	}
	
	private void addSmeltingRecipes()
	{
		CSCrafting.addSmelting(new ItemStack(soupBowls, 1, 0), new ItemStack(soupBowls, 1, 7), 0F);
	}
	
	private void addLocalizations()
	{
		addFoodDesc(Food.bacon, "Delicious bacon");
		addFoodDesc(Food.bacon_raw, "Raw bacon, cook it to win!");
		addFoodDesc(Food.breadslice, "The half of a bread");
		addFoodDesc(Food.butter, "Delicious butter, makes you fat");
		addFoodDesc(Food.candycane, "Pure sugar");
		addFoodDesc(Food.cereals1, "Cereals to have for breakfast");
		addFoodDesc(Food.cereals2, "Chocolate Cereals!");
		addFoodDesc(Food.cheese, "A big cheese wheel");
		addFoodDesc(Food.cheese_slice, "A tiny slice of the big cheese wheel");
		addFoodDesc(Food.chili, "Hot and spicy!");
		addFoodDesc(Food.chocolate, "Pretty sweet");
		addFoodDesc(Food.chocolateWhite, "SWEET!");
		addFoodDesc(Food.chocolateCow, EnumChatFormatting.UNDERLINE + "Not" + EnumChatFormatting.RESET + " made from cows!");
		addFoodDesc(Food.chocolateCookie, "Chocolate cookies like your grandma would craft them");
		addFoodDesc(Food.corn, "Better make some popcorn!");
		addFoodDesc(Food.cucumber, "Long and green");
		addFoodDesc(Food.fried_egg, "An egg");
		addFoodDesc(Food.hamburger, "Directly from McDerp!");
		addFoodDesc(Food.frenchfries, "Directly from McDerp!");
		addFoodDesc(Food.honeydrop, "Made by bees");
		addFoodDesc(Food.meatball, "Many cows died for this");
		addFoodDesc(Food.omelette, "Many eggs");
		addFoodDesc(Food.onion, "An onion");
		addFoodDesc(Food.paprika, "Another plantable vegetable");
		addFoodDesc(Food.pasta, "Pretty long and salted");
		addFoodDesc(Food.pepperSeeds, "You shouldn't eat those");
		addFoodDesc(Food.pizza, "Everybody loves pizza");
		addFoodDesc(Food.popcorn, "No sugar, no salt");
		addFoodDesc(Food.popcorn_salty, "Salty");
		addFoodDesc(Food.popcorn_sweet, "Sweet popcorn");
		addFoodDesc(Food.rice, "Rice");
		addFoodDesc(Food.salad, "Just normal green salad");
		addFoodDesc(Food.salami, "Made from cows");
		addFoodDesc(Food.toast, "Not toasted yet");
		addFoodDesc(Food.toast_cheese, "Toasted toast with cheese");
		addFoodDesc(Food.toast_salami, "Toasted toast with salami");
		addFoodDesc(Food.toast_toasted, "Toasted toast");
		addFoodDesc(Food.tomato, "A vegatable or a fruit?");
		addFoodDesc(Food.vanillaSeeds, "Do not eat! Plant!");
		
		addFoodDesc(Food.orange, "The color is orange");
		addFoodDesc(Food.pear, "Pearous");
		addFoodDesc(Food.cherry, "Two cherrys. But the name says its one.");
		addFoodDesc(Food.strawberry, "Make a bush");
		addFoodDesc(Food.raspberry, "Make a bush");
		addFoodDesc(Food.blueberry, "Make a bush");
		addFoodDesc(Food.blackberry, "Make a bush");
		addFoodDesc(Food.redcurrant, "Make a bush");
		
		addFoodDesc(Food.plum, "Plum");
		addFoodDesc(Food.banana, "A yellow banana looking weird");
		addFoodDesc(Food.seagrass, "Lives under the sea");
		
		addFoodDesc(Food.icecube, "An icy cube");
		addFoodDesc(Food.icecreamCone, "A cone to be filled with icecream");
		addFoodDesc(Food.icecream, "Tasty, cold icecream");
		addFoodDesc(Food.icecreamChocolate, "Chocolate icecream made from icecream and chocolate");
		addFoodDesc(Food.icecreamStrawberry, "Strawberry icecream made from icecream and strawberrys");
		addFoodDesc(Food.icecreamVanilla, "Vanilla icecream made from icecream and vanilla");
		
		addFoodDesc(Food.apple, "An apple, dropped by an oak tree.");
		addFoodDesc(Food.appleStomped, "Stomped");
		addFoodDesc(Food.appleGold1, "A golden apple");
		addFoodDesc(Food.appleGold2, "A golden apple");
		addFoodDesc(Food.appleDiamond, "An apple, wrapped in diamonds.");
		addFoodDesc(Food.melon, "A green melon");
		addFoodDesc(Food.melonGold1, "A melon with some gold dust");
		addFoodDesc(Food.potato, "A dirty potato. Don't eat it.");
		addFoodDesc(Food.potatoCooked, "A cooked potato");
		addFoodDesc(Food.potatoStomped, "Stomped");
		addFoodDesc(Food.potatoGold1, "A golden potato");
		addFoodDesc(Food.potatoGold2, "A golden potato");
		addFoodDesc(Food.potatoDiamond, "A potato wrapped in diamonds.");
		addFoodDesc(Food.poisonousPotato, "Doesn't look healthy");
		addFoodDesc(Food.carrot, "A carrot");
		addFoodDesc(Food.carrotCooked, "A carrot, but cooked.");
		addFoodDesc(Food.carrotStomped, "Stomped");
		addFoodDesc(Food.carrotGold1, "A golden carrot");
		addFoodDesc(Food.carrotDiamond, "Improves your vision");
		addFoodDesc(Food.bread, "Baked from wheat");
		addFoodDesc(Food.cookie, "A tiny cookie");
		addFoodDesc(Food.porkRaw, "Dropped by a pig.");
		addFoodDesc(Food.porkCooked, "Cooked pig");
		addFoodDesc(Food.beefRaw, "Dropped by a cow.");
		addFoodDesc(Food.beefCooked, "Cooked cow");
		addFoodDesc(Food.chickenRaw, "Dropped by a chicken, may make you hungry.");
		addFoodDesc(Food.chickenCooked, "Cooked chicken");
		addFoodDesc(Food.fishRaw, "Came out of the water");
		addFoodDesc(Food.fishCooked, "Cooked fish");
		addFoodDesc(Food.rottenFlesh, "Unhealthy zombie flesh");
		addFoodDesc(Food.spiderEye, "You shouldn't eat that.");
		addFoodDesc(Food.pumpkinPie, "With whole fruits");
	}
	
	public static void addFoodDesc(Food f, String desc)
	{
		CSLang.addLocalizationUS("food." + f.getName().toLowerCase().replace(" ", "") + ".desc", desc);
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
				(new CustomTreeGenerator(false, 4 + random.nextInt(4), treeType > 3 ? fruitLogs2 : fruitLogs, treeType > 3 ? fruitLeaves2 : fruitLeaves, treeType & 3, treeType & 3)).generate(world, random, randPosX, randPosY, randPosZ);
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
			(new WorldGenBushes(bushType, 3)).generate(world, random, randPosX, randPosY, randPosZ);
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
			return bushes[random.nextInt(bushes.length)];
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
