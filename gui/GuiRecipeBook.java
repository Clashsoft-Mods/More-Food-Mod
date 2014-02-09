package clashsoft.mods.morefood.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import clashsoft.cslib.util.CSString;
import clashsoft.mods.morefood.container.ContainerRecipeBook;
import clashsoft.mods.morefood.food.Food;
import clashsoft.mods.morefood.food.FoodCategory;
import clashsoft.mods.morefood.food.FoodRecipe;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiRecipeBook extends GuiContainer
{
	public static final ResourceLocation	background			= new ResourceLocation("gui/recipebook.png");
	
	public GuiButton						prevButton;
	public GuiButton						nextButton;
	
	public GuiTextField						search;
	public GuiFoodListSlot					foodListSlot;
	
	public ContainerRecipeBook				container;
	public int								currentEntryID;
	public String							currentEntryName	= "";
	public Food								currentEntry;
	public List<Food>						currentDisplayList	= Food.getDisplayList();
	public ItemStack[][]					recipe;
	
	public EntityPlayer						player;
	
	private List<String>					temp				= new ArrayList<String>();
	
	public GuiRecipeBook(ContainerRecipeBook container, EntityPlayer player)
	{
		super(container);
		this.container = container;
		this.player = player;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTickTime)
	{
		this.foodListSlot.drawScreen(mouseX, mouseY, partialTickTime);
		super.drawScreen(mouseX, mouseY, partialTickTime);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		GL11.glColor4f(1, 1, 1, 1);
		this.search.drawTextBox();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(-this.guiLeft, -this.guiTop, 0F);
		this.foodListSlot.drawScreen(mouseX, mouseY, 1F);
		GL11.glPopMatrix();
		
		if (this.nextButton.enabled && this.func_146978_c(this.nextButton.xPosition - this.guiLeft, this.nextButton.yPosition - this.guiTop, 20, 20, mouseX, mouseY))
		{
			Food f = this.currentDisplayList.get(this.currentEntryID + 1);
			
			this.temp.add(f.asStack().getDisplayName());
			this.drawHoveringText(this.temp, mouseX - this.guiLeft, mouseY - this.guiTop, this.fontRendererObj, f.getCategory().color);
			this.temp.clear();
		}
		if (this.prevButton.enabled && this.func_146978_c(this.prevButton.xPosition - this.guiLeft, this.prevButton.yPosition - this.guiTop, 20, 20, mouseX, mouseY))
		{
			Food f = this.currentDisplayList.get(this.currentEntryID - 1);
			
			this.temp.add(f.asStack().getDisplayName());
			this.drawHoveringText(this.temp, mouseX - this.guiLeft, mouseY - this.guiTop, this.fontRendererObj, f.getCategory().color);
			this.temp.clear();
		}
		if (this.func_146978_c(20, 50, 40, 40, mouseX, mouseY))
		{
			this.drawHoveringText(Arrays.asList(this.currentEntryName), mouseX - this.guiLeft, mouseY - this.guiTop, this.fontRendererObj, this.currentEntry.getCategory().color);
		}
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.renderEngine.bindTexture(background);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 256, 256, 256, 256);
		
		int matchingEntrys = this.currentDisplayList.size();
		String header = "Recipe Book (" + matchingEntrys + (matchingEntrys != Food.getDisplayList().size() ? " Matching" : "") + " Entry" + (matchingEntrys != 1 ? "s" : "") + ")";
		this.fontRendererObj.drawString(header, (this.width - this.fontRendererObj.getStringWidth(header)) / 2, this.guiTop + 10, 0x404040, false);
		
		if (this.currentEntry != null)
		{
			// Name + Description
			
			String name = this.currentEntryName;
			ItemStack stack = this.currentEntry.asStack();
			FoodCategory category = this.currentEntry.getCategory();
			PotionEffect[] effects = this.currentEntry.getEffects();
			
			String desc = "food." + name.toLowerCase().replace(" ", "") + ".desc";
			String desc1 = StatCollector.translateToLocal(desc);
			
			if (desc1.equals(desc))
			{
				desc1 = "" + EnumChatFormatting.DARK_RED + EnumChatFormatting.ITALIC + "No description available. ";
			}
			
			this.fontRendererObj.drawString(name, (this.width - this.fontRendererObj.getStringWidth(name)) / 2, this.guiTop + 27, 4210752, false);
			
			this.fontRendererObj.drawString(EnumChatFormatting.ITALIC + category.name, this.guiLeft + 70, this.guiTop + 45, 0x404040);
			
			String desc2 = CSString.cutString(desc1, 26);
			String[] lines = CSString.lineArray(desc2);
			for (int i = 0; i < lines.length; i++)
			{
				this.fontRendererObj.drawString(lines[i], this.guiLeft + 70, this.guiTop + 60 + (i * 10), 4210752);
			}
			
			// Crafting
			
			FoodRecipe recipe = this.currentEntry.getRecipe();
			
			String crafting = recipe == null ? EnumChatFormatting.RED + "Not craftable" : recipe.getTypeString();
			this.fontRendererObj.drawString(crafting, this.guiLeft + 22, this.guiTop + 103, 4210752, false);
			
			if (recipe != null)
			{
				String s = recipe.getAmount() + "x " + name;
				String[] split = CSString.lineArray(CSString.cutString(s, 15));
				
				for (int i = 0; i < split.length; i++)
				{
					this.fontRendererObj.drawString(split[i], this.guiLeft + 22, this.guiTop + 175 + (i * 10), 4210752, false);
				}
			}
			
			// Stats
			
			int statsX = this.guiLeft + 140;
			
			this.fontRendererObj.drawString("Stats", statsX, this.guiTop + 103, 4210752);
			
			String foodValue = this.currentEntry.getFoodValue() == 0 ? EnumChatFormatting.RED + "Not eatable" : ((this.currentEntry.getAction() == EnumAction.eat ? "Food value: " : "Drink value: ") + EnumChatFormatting.DARK_GREEN + this.currentEntry.getFoodValue());
			this.fontRendererObj.drawString(foodValue, statsX, this.guiTop + 120, 4210752, false);
			
			String plantable = this.currentEntry.getBlockPlaced() == null ? "Not plantable" : EnumChatFormatting.DARK_GREEN + "Plantable";
			this.fontRendererObj.drawString(plantable, statsX, this.guiTop + 130, 4210752, false);
			
			boolean hasEffects = effects != null && effects.length > 0;
			String effectsTitle = hasEffects ? "Effects:" : "No effects";
			this.fontRendererObj.drawString(effectsTitle, statsX, this.guiTop + 145, 4210752, false);
			
			for (int i = 0; i < effects.length; i++)
			{
				PotionEffect effect = effects[i];
				String effectName = " " + StatCollector.translateToLocal(effect.getEffectName());
				this.fontRendererObj.drawString(effectName, statsX, this.guiTop + 155 + (i * 10), 4210752, false);
			}
			
			// Icon
			
			GL11.glScalef(2.5F, 2.5F, 1F);
			itemRender.zLevel = 100F;
			itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, stack, (int) ((this.guiLeft + 22) / 2.5F), (int) ((this.guiTop + 50) / 2.5F));
			itemRender.zLevel = 0F;
			GL11.glScalef(0.4F, 0.4F, 1F);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 0)
		{
			if (this.currentEntryID > 0)
			{
				this.currentEntryID--;
			}
		}
		if (button.id == 1)
		{
			if (this.currentEntryID < this.currentDisplayList.size() - 1)
			{
				this.currentEntryID++;
			}
		}
		this.setRecipe(this.currentEntryID);
	}
	
	@Override
	public void initGui()
	{
		this.xSize = 256;
		this.ySize = 200;
		super.initGui();
		
		this.prevButton = new GuiButton(0, this.guiLeft + 20, this.guiTop + 20, 20, 20, "<");
		this.nextButton = new GuiButton(1, this.guiLeft + this.xSize - 40, this.guiTop + 20, 20, 20, ">");
		
		this.search = new GuiTextField(this.fontRendererObj, 44, 20, this.xSize - 40 - 48, 20);
		this.search.setVisible(false);
		
		this.foodListSlot = new GuiFoodListSlot(this);
		
		this.buttonList.add(this.prevButton);
		this.buttonList.add(this.nextButton);
		
		this.setRecipe(this.currentEntryID);
	}
	
	@Override
	protected void keyTyped(char c, int key)
	{
		if (!this.search.textboxKeyTyped(c, key))
		{
			super.keyTyped(c, key);
		}
		if (this.search.isFocused() && this.search.getVisible())
		{
			boolean flag = true;
			if (this.search.getText().isEmpty())
			{
				this.currentDisplayList = Food.getDisplayList();
			}
			else
			{
				this.currentDisplayList = new ArrayList();
				String searchText = this.search.getText().toLowerCase().trim();
				for (int i = 0; i < Food.getDisplayList().size(); i++)
				{
					Food f = Food.getDisplayList().get(i);
					String s = f.asStack().getDisplayName().toLowerCase().trim();
					
					if (searchText.startsWith("category:"))
					{
						String s1 = searchText.substring(searchText.indexOf(':') + 1);
						String s2 = f.getCategory().name.toLowerCase().trim();
						if (s2.startsWith(s1))
						{
							this.currentDisplayList.add(f);
							flag = false;
						}
					}
					else if (s.startsWith(searchText))
					{
						this.currentDisplayList.add(f);
						flag = false;
					}
				}
				if (flag)
				{
					this.search.setTextColor(0xFF0000);
				}
				else
				{
					this.search.setTextColor(0xFFFFFF);
				}
			}
			this.setRecipe(0);
		}
	}
	
	@Override
	protected void mouseClicked(int x, int y, int which)
	{
		super.mouseClicked(x, y, which);
		this.search.mouseClicked(x, y, which);
		if (which == 0)
		{
			if (this.func_146978_c(40, 20, this.search.getWidth(), 20, x, y))
			{
				this.search.setVisible(true);
				this.search.setFocused(true);
			}
			else
			{
				this.search.setVisible(false);
				this.search.setFocused(false);
			}
		}
	}
	
	public void setRecipe(int recipe)
	{
		int size = this.currentDisplayList.size();
		
		if (recipe >= size)
		{
			recipe = size - 1;
		}
		if (recipe < 0)
		{
			recipe = 0;
		}
		
		if (recipe >= 0 && recipe < size)
		{
			this.currentEntry = this.currentDisplayList.get(recipe);
			this.recipe = this.analyseRecipe(this.currentEntry);
			this.container.inventory.stacks = this.recipe;
			this.currentEntryID = recipe;
			this.currentEntryName = this.currentEntry.asStack().getDisplayName();
			
			if (this.currentEntryID == 0)
			{
				this.prevButton.enabled = false;
			}
			else
			{
				this.prevButton.enabled = true;
			}
			
			if (this.currentEntryID == this.currentDisplayList.size() - 1)
			{
				this.nextButton.enabled = false;
			}
			else
			{
				this.nextButton.enabled = true;
			}
		}
	}
	
	public ItemStack[][] analyseRecipe(Food food)
	{
		if (food == null || food.getRecipe() == null)
		{
			return new ItemStack[][] {
					{ null, null, null },
					{ null, null, null },
					{ null, null, null } };
		}
		else
		{
			return food.getRecipe().getAnalysedRecipe();
		}
	}
	
	@Override
	protected void drawHoveringText(List list, int x, int y, FontRenderer font)
	{
		this.drawHoveringText(list, x, y, font, 0xFFFFFF);
	}
	
	protected void drawHoveringText(List list, int x, int y, FontRenderer font, int color)
	{
		if (!list.isEmpty())
		{
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			int k = 0;
			Iterator iterator = list.iterator();
			
			while (iterator.hasNext())
			{
				String s = (String) iterator.next();
				int l = font.getStringWidth(s);
				
				if (l > k)
				{
					k = l;
				}
			}
			
			int i1 = x + 12;
			int j1 = y - 12;
			int k1 = 8;
			
			if (list.size() > 1)
			{
				k1 += 2 + (list.size() - 1) * 10;
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
			itemRender.zLevel = 300.0F;
			int l1 = 0xF0100010;
			this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
			this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
			this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
			this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
			this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
			int i2 = 0xFF000000 | color;
			int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
			this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
			this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
			this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
			this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
			
			for (int k2 = 0; k2 < list.size(); ++k2)
			{
				String s1 = (String) list.get(k2);
				font.drawStringWithShadow(s1, i1, j1, -1);
				
				if (k2 == 0)
				{
					j1 += 2;
				}
				
				j1 += 10;
			}
			
			this.zLevel = 0.0F;
			itemRender.zLevel = 0.0F;
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			RenderHelper.enableStandardItemLighting();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}
	
	public int getGuiLeft()
	{
		return this.guiLeft;
	}
}
