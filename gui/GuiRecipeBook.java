package clashsoft.mods.morefood.gui;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import clashsoft.clashsoftapi.util.CSString;
import clashsoft.mods.morefood.container.ContainerRecipeBook;
import clashsoft.mods.morefood.food.Food;
import clashsoft.mods.morefood.food.FoodRecipe;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiRecipeBook extends GuiContainer
{
	public static final ResourceLocation	background	= new ResourceLocation("gui/recipebook.png");
	
	public static RenderItem				itemRender	= new RenderItem();
	
	public ContainerRecipeBook				container;
	public int								recipeID	= 0;
	public Food								food;
	public ItemStack[][]					recipe		= null;
	
	public EntityPlayer						player;
	
	public GuiRecipeBook(ContainerRecipeBook container, EntityPlayer player)
	{
		super(container);
		this.container = container;
		this.player = player;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.func_110434_K().func_110577_a(background);
		//this.drawTexturedModalRect(guiLeft, guiTop, 256, 256, 256, 256);
		
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		if (food != null)
		{
			// GuiMerchant
			
			String s = food.name;
			String s2 = food.name.toLowerCase().replace(" ", "");
			String s3 = StatCollector.translateToLocal("food." + s2 + ".desc");
			
			this.mc.fontRenderer.drawString(food.name, (this.width - this.mc.fontRenderer.getStringWidth(food.name)) / 2, guiTop + 27, 4210752, false);
			
			String s4 = CSString.cutString(s3, 30);
			String[] lines = CSString.makeLineList(s4);
			for (int i = 0; i < lines.length; i++)
				this.mc.fontRenderer.drawString(lines[i], guiLeft + 60, guiTop + 50 + (i * 10), 4210752, false);
			
			if (food.recipe != null && recipe != null)
			{
				String crafting = food.recipe.craftingType == FoodRecipe.CRAFTING ? "Crafting" : (food.recipe.craftingType == FoodRecipe.CRAFTING_SHAPELESS ? "Shapeless Crafting" : "Smelting");
				this.mc.fontRenderer.drawString(crafting, guiLeft + 22, guiTop + 100, 4210752, false);
				this.mc.fontRenderer.drawString("= " + food.recipe.amount + " x " + food.asStack().getDisplayName(), guiLeft + 22, guiTop + 180, 4210752, false);
			}
			
			GL11.glScalef(2F, 2F, 1F);
			itemRender.zLevel = 100F;
			ItemStack stack = food.asStack();
			itemRender.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, stack, (guiLeft + 22) / 2, (guiTop + 50) / 2);
			itemRender.zLevel = 0F;
			GL11.glScalef(0.5F, 0.5F, 1F);
			GL11.glColor4f(1F, 1F, 1F, 1F);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.id == 0)
			if (recipeID > 0)
				recipeID--;
		if (par1GuiButton.id == 1)
			if (recipeID < Food.foodList.size() - 1)
				recipeID++;
		this.setRecipe(recipeID);
	}
	
	@Override
	public void initGui()
	{
		this.xSize = 256;
		this.ySize = 256;
		
		int left = (this.width - 256) / 2;
		int top = (this.height - 256) / 2;
		
		this.buttonList.add(new GuiButton(0, left + 20, top + 20, 20, 20, "<"));
		this.buttonList.add(new GuiButton(1, left + 256 - 40, top + 20, 20, 20, ">"));
		
		this.setRecipe(recipeID);
		
		super.initGui();
	}
	
	public void setRecipe(int recipe)
	{
		this.food = Food.foodList.get(recipe);
		this.recipe = getRecipe(food);
		this.container.inventory.stacks = this.recipe;
	}
	
	public ItemStack[][] getRecipe(Food f)
	{
		if (f != null && f.recipe != null)
		{
			FoodRecipe r = f.recipe;
			
			if (r.craftingType == FoodRecipe.FURNACE)
			{
				return new ItemStack[][] { { null, (ItemStack) r.data[0], null }, { null, new ItemStack(Block.fire), null }, { null, null, null } };
			}
			else if (r.craftingType == FoodRecipe.CRAFTING_SHAPELESS)
			{
				ItemStack[][] ret = new ItemStack[3][3];
				
				for (int i = 0; i < r.data.length; i++)
				{
					int x = (i / 3) % 3;
					int y = i % 3;
					ret[x][y] = (ItemStack) r.data[i];
				}
				
				return ret;
			}
			else if (r.craftingType == FoodRecipe.CRAFTING)
			{
				return getCrafting(r.data);
			}
		}
		return new ItemStack[][] { { null, null, null }, { null, null, null }, { null, null, null } };
	}
	
	public ItemStack[][] getCrafting(Object... objects)
	{
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		
		if (objects[i] instanceof String[])
		{
			String[] astring = (String[]) ((String[]) objects[i++]);
			
			for (int l = 0; l < astring.length; ++l)
			{
				String s1 = astring[l];
				++k;
				j = s1.length();
				s = s + s1;
			}
		}
		else
		{
			while (objects[i] instanceof String)
			{
				String s2 = (String) objects[i++];
				++k;
				j = s2.length();
				s = s + s2;
			}
		}
		
		HashMap hashmap;
		
		for (hashmap = new HashMap(); i < objects.length; i += 2)
		{
			Character character = (Character) objects[i];
			ItemStack itemstack1 = null;
			
			if (objects[i + 1] instanceof Item)
			{
				itemstack1 = new ItemStack((Item) objects[i + 1]);
			}
			else if (objects[i + 1] instanceof Block)
			{
				itemstack1 = new ItemStack((Block) objects[i + 1], 1, 32767);
			}
			else if (objects[i + 1] instanceof ItemStack)
			{
				itemstack1 = (ItemStack) objects[i + 1];
			}
			
			hashmap.put(character, itemstack1);
		}
		
		ItemStack[][] ret = new ItemStack[3][3];
		
		for (int i1 = 0; i1 < j * k; ++i1)
		{
			char c0 = s.charAt(i1);
			
			if (hashmap.containsKey(Character.valueOf(c0)))
			{
				ret[(i1 / 3) % 3][i1 % 3] = ((ItemStack) hashmap.get(Character.valueOf(c0))).copy();
			}
			else
			{
				ret[(i1 / 3) % 3][i1 % 3] = null;
			}
		}
		return ret;
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}
}
