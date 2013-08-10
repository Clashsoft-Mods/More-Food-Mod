package clashsoft.mods.morefood;

import java.util.Random;

import clashsoft.clashsoftapi.util.CSBlocks;
import clashsoft.clashsoftapi.util.CSCrafting;
import clashsoft.clashsoftapi.util.CSItems;
import clashsoft.clashsoftapi.util.CSLang;
import clashsoft.mods.morefood.block.BlockPlantMoreFood;
import clashsoft.mods.morefood.block.BlockSaltOre;
import clashsoft.mods.morefood.food.Food;
import clashsoft.mods.morefood.item.*;
import clashsoft.mods.morefood.world.WorldGenGardener;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "MoreFoodMod", name = "More Food Mod", version = "1.6.2")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class MoreFoodMod
{
	@Instance("MoreFoodMod")
	public static MoreFoodMod			instance;
	
	@SidedProxy(modId = "MoreFoodMod", clientSide = "clashsoft.mods.morefood.ClientProxy", serverSide = "clashsoft.mods.morefood.CommonProxy")
	public static CommonProxy			proxy;
	
	public static int					itemsID			= 13000;
	public static int					cucumberPlantID	= 510;
	public static int					tomatoPlantID	= 511;
	public static int					pepperPlantID	= 512;
	public static int					saladPlantID	= 513;
	public static int					onionPlantID	= 514;
	public static int					chiliPlantID	= 515;
	public static int					paprikaPlantID	= 516;
	public static int					ricePlantID		= 517;
	public static int					cornPlantID		= 518;
	public static int					vanillaPlantID	= 519;
	public static int					saltOreID		= 520;
	
	public static ItemMoreFood			salt;
	public static ItemMoreFood			pepper;
	public static ItemMoreFood			cinnamon;
	public static ItemMoreFood			vanilla;
	public static ItemJuice				juice;
	public static ItemFertilizer		fertilizer;
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
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		cucumberPlantID = config.getBlock("Cucumber Plant ID", 510).getInt();
		tomatoPlantID = config.getBlock("Tomato Plant ID", 511).getInt();
		pepperPlantID = config.getBlock("Pepper Plant ID", 512).getInt();
		saladPlantID = config.getBlock("Salad Plant ID", 513).getInt();
		onionPlantID = config.getBlock("Onion Plant ID", 514).getInt();
		chiliPlantID = config.getBlock("Chili Plant ID", 515).getInt();
		paprikaPlantID = config.getBlock("Paprika Plant ID", 516).getInt();
		ricePlantID = config.getBlock("Rice Plant ID", 517).getInt();
		cornPlantID = config.getBlock("Corn Plant ID", 518).getInt();
		vanillaPlantID = config.getBlock("Vanilla Plant ID", 519).getInt();
		saltOreID = config.getBlock("Salt Ore ID", 520).getInt();
		
		itemsID = config.getItem("Items ID", 13000).getInt();
		
		config.save();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		GameRegistry.registerWorldGenerator(new WorldGenHandler());
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		
		foods = (ItemFoods) new ItemFoods(itemsID + 8, 3, 1.0F).setUnlocalizedName("edibleIgredient");
		
		addItems();
		addBlocks();
		addCraftingRecipes();
		addSmeltingRecipes();
		addLocalizations();
		
		MinecraftForge.addGrassSeed(new ItemStack(foods, 1, 19), 10);
		MinecraftForge.setBlockHarvestLevel(saltOre, "pickaxe", 1);
	}
	
	private void addBlocks()
	{
		cucumberPlant = new BlockPlantMoreFood(cucumberPlantID, 3, Food.cucumber.asStack(), Food.cucumber.asStack(), "cucumber");
		tomatoPlant = new BlockPlantMoreFood(tomatoPlantID, 3, Food.tomato.asStack(), Food.tomato.asStack(), "tomato");
		pepperPlant = new BlockPlantMoreFood(pepperPlantID, 3, Food.pepperSeeds.asStack(), new ItemStack(pepper), "pepper");
		saladPlant = new BlockPlantMoreFood(saladPlantID, 3, Food.salad.asStack(), Food.salad.asStack(), "salad");
		onionPlant = new BlockPlantMoreFood(onionPlantID, 4, Food.onion.asStack(), Food.onion.asStack(), "onion");
		chiliPlant = new BlockPlantMoreFood(chiliPlantID, 6, Food.chili.asStack(), Food.chili.asStack(), "chili");
		paprikaPlant = new BlockPlantMoreFood(paprikaPlantID, 6, Food.paprika.asStack(), Food.paprika.asStack(), "paprika");
		ricePlant = new BlockPlantMoreFood(ricePlantID, 6, Food.rice.asStack(), Food.rice.asStack(), "rice");
		cornPlant = new BlockPlantMoreFood(cornPlantID, 6, Food.corn.asStack(), Food.corn.asStack(), "corn");
		vanillaPlant = new BlockPlantMoreFood(vanillaPlantID, 4, Food.vanillaSeeds.asStack(), new ItemStack(vanilla), "vanilla");
		saltOre = (new BlockSaltOre(saltOreID)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("saltOre").func_111022_d("saltore");
		
		CSBlocks.addBlock(saltOre, "Salt Ore");
	}
	
	private void addItems()
	{
		salt = (ItemMoreFood) new ItemMoreFood(itemsID).setUnlocalizedName("salt");
		pepper = (ItemMoreFood) new ItemMoreFood(itemsID + 1).setUnlocalizedName("pepper");
		cinnamon = (ItemMoreFood) new ItemMoreFood(itemsID + 2).setUnlocalizedName("cinnamon");
		vanilla = (ItemMoreFood) new ItemMoreFood(itemsID + 3).setUnlocalizedName("vanilla");
		juice = (ItemJuice) new ItemJuice(itemsID + 4).setUnlocalizedName("juice");
		fertilizer = (ItemFertilizer) new ItemFertilizer(itemsID + 5).setUnlocalizedName("fertilizer");
		milkBowls = (ItemMilkBowls) new ItemMilkBowls(itemsID + 6, 4).setUnlocalizedName("cerealsWithMilk");
		soupBowls = (ItemSoupBowls) new ItemSoupBowls(itemsID + 7, 6).setUnlocalizedName("soups");
		recipeBook = (ItemRecipeBook) new ItemRecipeBook(itemsID + 9).setUnlocalizedName("recipebook").setCreativeTab(CreativeTabs.tabMisc);
		
		CSItems.addItem(salt, "Salt");
		CSItems.addItemWithShapelessRecipe(pepper, "Pepper", 4, new Object[] { Food.pepperSeeds.asStack() });
		CSItems.addItemWithShapelessRecipe(cinnamon, "Cinnamon", 3, new Object[] { new ItemStack(Item.dyePowder, 1, 3), Item.sugar, Item.sugar });
		CSItems.addItem(vanilla, "Vanilla");
		CSItems.addItemWithRecipe(fertilizer, "Fertilizer", 16, new Object[] { " w ", "sDs", " w ", 'w', Item.wheat, 's', Item.seeds, 'D', Block.dirt });
		
		CSItems.addItemWithRecipe(recipeBook, "Recipe Book", 1, new Object[] {" s ", "bBp", " c ", 's', Food.salad.asStack(), 'b', Item.beefCooked, 'B', Item.book, 'p', Item.porkCooked, 'c', Food.cheese_slice.asStack()});
	}
	
	private void addCraftingRecipes()
	{	
		CSCrafting.addCrafting(true, new ItemStack(pepper, 4, 0), Food.pepperSeeds.asStack());
		CSCrafting.addCrafting(true, new ItemStack(vanilla, 4, 0), Food.vanillaSeeds.asStack());
		
		for (int i = 0; i < 7; i++)
		{
			ItemStack theSoup = new ItemStack(soupBowls, 1, i);
			CSCrafting.addCrafting(true, ItemSoupBowls.addModifierToItemStack(theSoup.copy(), true, ItemSoupBowls.isPeppered(theSoup)), new Object[] { theSoup, salt });
			CSCrafting.addCrafting(true, ItemSoupBowls.addModifierToItemStack(theSoup.copy(), ItemSoupBowls.isSalted(theSoup), true), new Object[] { theSoup, pepper });
			CSCrafting.addCrafting(true, ItemSoupBowls.addModifierToItemStack(theSoup.copy(), true, true), new Object[] { theSoup, pepper, salt });
			CSCrafting.addCrafting(true, ItemSoupBowls.addModifierToItemStack(theSoup.copy(), true, ItemSoupBowls.isPeppered(theSoup)), new Object[] { theSoup, salt });
			CSCrafting.addCrafting(true, ItemSoupBowls.addModifierToItemStack(theSoup.copy(), ItemSoupBowls.isSalted(theSoup), true), new Object[] { theSoup, pepper });
			CSCrafting.addCrafting(true, ItemSoupBowls.addModifierToItemStack(theSoup.copy(), true, true), new Object[] { theSoup, pepper, salt });
		}
		
		CSCrafting.addCrafting(true, new ItemStack(soupBowls, 1, 0), new Object[] { Item.bowlEmpty, Item.bucketWater });
		for (int i = 0; i <= 7; i += 7)
		{
			CSCrafting.addCrafting(true, new ItemStack(soupBowls, 1, 1 + i), new Object[] { new ItemStack(soupBowls, 1, 0 + i), Item.bakedPotato });
			CSCrafting.addCrafting(true, new ItemStack(soupBowls, 1, 2 + i), new Object[] { new ItemStack(soupBowls, 1, 0 + i), Food.cookedCarrot.asStack() });
			CSCrafting.addCrafting(true, new ItemStack(soupBowls, 1, 3 + i), new Object[] { new ItemStack(soupBowls, 1, 0 + i), Food.cookedCarrot.asStack(), Item.bakedPotato });
			CSCrafting.addCrafting(true, new ItemStack(soupBowls, 1, 4 + i), new Object[] { new ItemStack(soupBowls, 1, 0 + i), Food.tomato.asStack() });
			CSCrafting.addCrafting(true, new ItemStack(soupBowls, 1, 5 + i), new Object[] { new ItemStack(soupBowls, 1, 4 + i), Food.rice.asStack() });
			CSCrafting.addCrafting(true, new ItemStack(soupBowls, 1, 6 + i), new Object[] { new ItemStack(soupBowls, 1, 0 + i), Food.pasta.asStack() });
		}
		
		CSCrafting.addCrafting(true, new ItemStack(milkBowls, 1, 0), new Object[] { Item.bowlEmpty, Item.bucketMilk });
		CSCrafting.addCrafting(true, new ItemStack(milkBowls, 1, 1), new Object[] { new ItemStack(milkBowls, 1, 0), new ItemStack(foods, 1, Food.cereals1.foodID) });
		CSCrafting.addCrafting(true, new ItemStack(milkBowls, 1, 2), new Object[] { new ItemStack(milkBowls, 1, 0), new ItemStack(foods, 1, Food.cereals2.foodID) });
		CSCrafting.addCrafting(true, new ItemStack(milkBowls, 1, 3), new Object[] { new ItemStack(milkBowls, 1, 0), new ItemStack(foods, 1, Food.cereals1.foodID), new ItemStack(foods, 1, Food.cereals2.foodID) });
		CSCrafting.addCrafting(true, new ItemStack(milkBowls, 1, 4), new Object[] { new ItemStack(milkBowls, 1, 0), Food.rice.asStack() });
		CSCrafting.addCrafting(true, new ItemStack(milkBowls, 1, 5), new Object[] { new ItemStack(milkBowls, 1, 0), Food.rice.asStack(), cinnamon });
		CSCrafting.addCrafting(true, new ItemStack(milkBowls, 1, 5), new Object[] { new ItemStack(milkBowls, 1, 4), cinnamon });
		CSCrafting.addCrafting(true, new ItemStack(milkBowls, 1, 6), new Object[] { new ItemStack(milkBowls, 1, 0), Food.rice.asStack(), vanilla });
		CSCrafting.addCrafting(true, new ItemStack(milkBowls, 1, 6), new Object[] { new ItemStack(milkBowls, 1, 4), vanilla });
		CSCrafting.addCrafting(true, new ItemStack(milkBowls, 1, 7), new Object[] { new ItemStack(milkBowls, 1, 0), new ItemStack(Item.dyePowder, 1, 1) });
		CSCrafting.addCrafting(true, new ItemStack(milkBowls, 1, 8), new Object[] { new ItemStack(milkBowls, 1, 0), new ItemStack(Item.dyePowder, 1, 10) });
		
		for (Food f : Food.foodList)
		{
			if (f != null)
				f.addRecipe();
		}
		
		CSCrafting.addCrafting(true, new ItemStack(juice, 1, 0), new Object[] { Item.glassBottle, new ItemStack(foods, 1, Food.stompedApple.foodID) });
		CSCrafting.addCrafting(true, new ItemStack(juice, 1, 0), new Object[] { Item.glassBottle, new ItemStack(foods, 1, Food.tomato.foodID) });
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
		addFoodDesc(Food.chocolateCookie, "Chocolate cookies like your grandma would craft them");
		addFoodDesc(Food.cookedCarrot, "A carrot, but cooked");
		addFoodDesc(Food.corn, "Better make some popcorn!");
		addFoodDesc(Food.cucumber, "Long and green");
		addFoodDesc(Food.diamondApple, "Expensive and blue");
		addFoodDesc(Food.diamondCarrot, "Carrots improve your vision");
		addFoodDesc(Food.diamondPotato, "A normal potato, but with diamonds");
		addFoodDesc(Food.fried_egg, "An egg");
		addFoodDesc(Food.goldPotato1, "A golden potato");
		addFoodDesc(Food.goldPotato2, "A more expensive golden potato");
		addFoodDesc(Food.hamburger, "Directly from McDerp!");
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
		addFoodDesc(Food.stompedApple, "Stomped");
		addFoodDesc(Food.stompedCarrot, "Stomped");
		addFoodDesc(Food.stompedPotato, "Stomped");
		addFoodDesc(Food.toast, "Not toasted yet");
		addFoodDesc(Food.toast_cheese, "Toasted toast with cheese");
		addFoodDesc(Food.toast_salami, "Toasted toast with salami");
		addFoodDesc(Food.toast_toasted, "Toasted toast");
		addFoodDesc(Food.tomato, "A vegatable or a fruit?");
		addFoodDesc(Food.vanillaSeeds, "Do not eat! Plant!");
	}
	
	public static void addFoodDesc(Food f, String desc)
	{
		CSLang.addLocalizationUS("food." + f.name.toLowerCase().replace(" ", "") + ".desc", desc);
	}
	
	public static void generate(Random random, int chunkX, int chunkZ, World world)
	{
		for (int i = 0; i < 10; i++)
		{
			int randPosX = chunkX * 16 + random.nextInt(16);
			int randPosY = random.nextInt(48);
			int randPosZ = chunkZ * 16 + random.nextInt(16);
			if (world.getBiomeGenForCoords(randPosX, randPosZ) instanceof BiomeGenOcean)
				(new WorldGenMinable(saltOre.blockID, 6)).generate(world, random, randPosX, randPosY, randPosZ);
		}
		if (random.nextInt(100) == 0)
		{
			int randPosX = chunkX * 16 + random.nextInt(16);
			int randPosY = 128;
			int randPosZ = chunkZ * 16 + random.nextInt(16);
			for (int j = randPosY; j > 0; j--)
			{
				if (Block.blocksList[world.getBlockId(randPosX, randPosY, randPosZ)] != null && Block.blocksList[world.getBlockId(randPosX, randPosY, randPosZ)].isBlockSolid(world, randPosX, randPosY, randPosZ, 0) && (world.getBlockId(randPosX, randPosY, randPosZ) == Block.grass.blockID || world.getBlockId(randPosX, randPosY, randPosZ) == Block.dirt.blockID))
				{
					randPosY = j;
					break;
				}
				else
				{
					randPosY--;
				}
			}
			if (world.getBlockId(randPosX, randPosY, randPosZ) == Block.grass.blockID || world.getBlockId(randPosX, randPosY, randPosZ) == Block.dirt.blockID)
			{
				(new WorldGenGardener()).generate(world, random, randPosX, randPosY, randPosZ);
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
