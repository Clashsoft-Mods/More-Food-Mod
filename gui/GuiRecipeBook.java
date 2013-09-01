package clashsoft.mods.morefood.gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import clashsoft.clashsoftapi.util.CSString;
import clashsoft.clashsoftapi.util.CSUtil;
import clashsoft.mods.morefood.container.ContainerRecipeBook;
import clashsoft.mods.morefood.food.Food;
import clashsoft.mods.morefood.food.FoodRecipe;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiRecipeBook extends GuiContainer
{
	public static final ResourceLocation	background	= new ResourceLocation("gui/recipebook.png");
	
	public static RenderItem				itemRender	= new RenderItem();
	
	public GuiButton						prev;
	public GuiButton						next;
	
	public GuiTextField						search;
	
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
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		GL11.glColor4f(1, 1, 1, 1);
		search.drawTextBox();
		if (isPointInRegion(next.xPosition - guiLeft, next.yPosition - guiTop, 20, 20, par1, par2) && next.enabled)
		{
			List<String> list = new LinkedList<String>();
			list.add(Food.getDisplayList().get(recipeID + 1).asStack().getDisplayName());
			this.drawHoveringText(list, par1 - guiLeft, par2 - guiTop, this.mc.fontRenderer);
		}
		if (isPointInRegion(prev.xPosition - guiLeft, prev.yPosition - guiTop, 20, 20, par1, par2) && prev.enabled)
		{
			List<String> list = new LinkedList<String>();
			list.add(Food.getDisplayList().get(recipeID - 1).asStack().getDisplayName());
			this.drawHoveringText(list, par1 - guiLeft, par2 - guiTop, this.mc.fontRenderer);
		}
		if (isPointInRegion(20, 50, 40, 40, par1, par2))
		{
			List<String> list = new LinkedList<String>();
			list.add(Food.getDisplayList().get(recipeID).asStack().getDisplayName());
			this.drawHoveringText(list, par1 - guiLeft, par2 - guiTop, this.mc.fontRenderer);
		}
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.func_110434_K().func_110577_a(background);
		this.drawTexturedModalRect(guiLeft, guiTop, 256, 256, 256, 256);
		
		String header = "Recipe Book (" + Food.getDisplayList().size() + " Entrys)";
		this.mc.fontRenderer.drawString(header, (this.width - this.mc.fontRenderer.getStringWidth(header)) / 2, guiTop + 10, 4210752, false);
		
		if (food != null)
		{
			// Name + Description
			
			{
				String name = food.asStack().getDisplayName();
				String name2 = food.getName().toLowerCase().replace(" ", "");
				String desc = StatCollector.translateToLocal("food." + name2 + ".desc");
				
				this.mc.fontRenderer.drawString(name, (this.width - this.mc.fontRenderer.getStringWidth(name)) / 2, guiTop + 27, 4210752, false);
				
				String s4 = CSString.cutString(desc, 26);
				String[] lines = CSString.makeLineList(s4);
				for (int i = 0; i < lines.length; i++)
					this.mc.fontRenderer.drawString(lines[i], guiLeft + 70, guiTop + 50 + (i * 10), 4210752, false);
			}
			
			// Crafting
			
			{
				String crafting = food.getRecipe() == null ? EnumChatFormatting.RED + "Not craftable" : (food.getRecipe().getCraftingType() == FoodRecipe.CRAFTING ? "Crafting" : (food.getRecipe().getCraftingType() == FoodRecipe.CRAFTING_SHAPELESS ? "Shapeless Crafting" : "Cooking"));
				this.mc.fontRenderer.drawString(crafting, guiLeft + 22, guiTop + 103, 4210752, false);
				
				if (food.getRecipe() != null && recipe != null)
				{
					String s = food.getRecipe().getAmount() + "x " + food.asStack().getDisplayName();
					String[] split = CSUtil.makeLineList(CSString.cutString(s, 15));
					
					for (int i = 0; i < split.length; i++)
						this.mc.fontRenderer.drawString(split[i], guiLeft + 22, guiTop + 175 + (i * 10), 4210752, false);
				}
			}
			
			// Stats
			
			{
				int statsX = 140;
				
				this.mc.fontRenderer.drawString("Stats", guiLeft + statsX, guiTop + 103, 4210752);
				
				String foodValue = food.getFoodValue() == 0 ? EnumChatFormatting.RED + "Not eatable" : ((food.getAction() == EnumAction.eat ? "Food value: " : "Drink value: ") + EnumChatFormatting.DARK_GREEN + food.getFoodValue());
				this.mc.fontRenderer.drawString(foodValue, guiLeft + statsX, guiTop + 120, 4210752, false);
				
				String plantable = food.getBlockPlaced() == 0 ? "Not plantable" : EnumChatFormatting.DARK_GREEN + "Plantable";
				this.mc.fontRenderer.drawString(plantable, guiLeft + statsX, guiTop + 130, 4210752, false);
				
				boolean hasEffects = food.getEffects() != null && food.getEffects().length > 0;
				String effects = hasEffects ? "Effects:" : "No effects";
				this.mc.fontRenderer.drawString(effects, guiLeft + statsX, guiTop + 145, 4210752, false);
				if (hasEffects)
					for (int i = 0; i < food.getEffects().length && i < 3; i++)
					{
						PotionEffect effect = food.getEffects()[i];
						String var = " " + StatCollector.translateToLocal(effect.getEffectName());
						this.mc.fontRenderer.drawString(var, guiLeft + statsX, guiTop + 155 + (i * 10), 4210752, false);
					}
			}
			
			// Icon
			
			GL11.glScalef(2.5F, 2.5F, 1F);
			itemRender.zLevel = 100F;
			ItemStack stack = food.asStack();
			itemRender.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, stack, (int) ((guiLeft + 22) / 2.5F), (int) ((guiTop + 50) / 2.5F));
			itemRender.zLevel = 0F;
			GL11.glScalef(0.4F, 0.4F, 1F);
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
			if (recipeID < Food.getDisplayList().size() - 1)
				recipeID++;
		this.setRecipe(recipeID);
	}
	
	@Override
	public void initGui()
	{
		this.xSize = 256;
		this.ySize = 200;
		super.initGui();
		
		prev = new GuiButton(0, guiLeft + 20, guiTop + 20, 20, 20, "<");
		next = new GuiButton(1, guiLeft + xSize - 40, guiTop + 20, 20, 20, ">");
		
		search = new GuiTextField(fontRenderer, 44, 20, xSize - 40 - 48, 20);
		search.setVisible(false);
		
		this.buttonList.add(prev);
		this.buttonList.add(next);
		
		this.setRecipe(recipeID);
		
	}
	
	/**
	 * Fired when a key is typed. This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e).
	 */
	@Override
	protected void keyTyped(char par1, int par2)
	{
		if (!this.search.textboxKeyTyped(par1, par2))
		{
			super.keyTyped(par1, par2);
		}
		if (this.search.isFocused() && this.search.getVisible())
		{
			boolean flag = true;
			for (int i = 0; i < Food.getDisplayList().size(); i++)
			{
				String s = Food.getDisplayList().get(i).asStack().getDisplayName().toLowerCase().trim();
				String s2 = search.getText().toLowerCase().trim();
				if (s.startsWith(s2))
				{
					this.setRecipe(i);
					flag = false;
					break;
				}
			}
			if (flag)
				search.setTextColor(0xFF0000);
			else
				search.setTextColor(0xFFFFFF);
		}
	}
	
	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		this.search.mouseClicked(par1, par2, par3);
		if (par3 == 0)
		{
			if (isPointInRegion(40, 20, search.getWidth(), 20, par1, par2))
			{
				search.setVisible(true);
				search.setFocused(true);
				search.setText(food.asStack().getDisplayName());
			}
			else
			{
				search.setVisible(false);
				search.setFocused(false);
				search.setText("");
			}
		}
	}
	
	public void setRecipe(int recipe)
	{
		this.food = Food.getDisplayList().get(recipe);
		this.recipe = getRecipe(food);
		this.container.inventory.stacks = this.recipe;
		this.recipeID = recipe;
		
		if (recipeID == 0)
			prev.enabled = false;
		else
			prev.enabled = true;
		
		if (recipeID == Food.getDisplayList().size() - 1)
			next.enabled = false;
		else
			next.enabled = true;
	}
	
	public ItemStack[][] getRecipe(Food f)
	{
		if (f != null && f.getRecipe() != null)
		{
			FoodRecipe r = f.getRecipe();
			
			if (r.getCraftingType() == FoodRecipe.FURNACE)
			{
				return new ItemStack[][] { { null, (ItemStack) r.getData()[0], null }, { null, new ItemStack(Block.fire, 1, -1), null }, { null, new ItemStack(Item.coal), null } };
			}
			else if (r.getCraftingType() == FoodRecipe.CRAFTING_SHAPELESS)
			{
				ItemStack[][] ret = new ItemStack[3][3];
				
				for (int i = 0; i < r.getData().length; i++)
				{
					int x = (i / 3) % 3;
					int y = i % 3;
					ret[x][y] = (ItemStack) r.getData()[i];
				}
				
				return ret;
			}
			else if (r.getCraftingType() == FoodRecipe.CRAFTING)
			{
				return getCrafting(r.getData());
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
			String[] astring = ((String[]) objects[i++]);
			
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
	protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font)
	{
		if (!par1List.isEmpty())
		{
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			int k = 0;
			Iterator iterator = par1List.iterator();
			
			while (iterator.hasNext())
			{
				String s = (String) iterator.next();
				int l = font.getStringWidth(s);
				
				if (l > k)
				{
					k = l;
				}
			}
			
			int i1 = par2 + 12;
			int j1 = par3 - 12;
			int k1 = 8;
			
			if (par1List.size() > 1)
			{
				k1 += 2 + (par1List.size() - 1) * 10;
			}
			
			if (i1 + k > this.width)
			{
				i1 -= 28 + k;
			}
			
			if (j1 + k1 + 6 > this.height)
			{
				j1 = this.height - k1 - 6;
			}
			
			this.zLevel = 300.0F;
			itemRenderer.zLevel = 300.0F;
			int l1 = 0xF0100010;
			this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
			this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
			this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
			this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
			this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
			int i2 = 0xFFFFFFFF;
			int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
			this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
			this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
			this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
			this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
			
			for (int k2 = 0; k2 < par1List.size(); ++k2)
			{
				String s1 = (String) par1List.get(k2);
				font.drawStringWithShadow(s1, i1, j1, -1);
				
				if (k2 == 0)
				{
					j1 += 2;
				}
				
				j1 += 10;
			}
			
			this.zLevel = 0.0F;
			itemRenderer.zLevel = 0.0F;
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			RenderHelper.enableStandardItemLighting();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return super.doesGuiPauseGame();
	}
}
