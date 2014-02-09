package clashsoft.mods.morefood.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import clashsoft.cslib.minecraft.item.meta.IMetaItemRecipe;
import clashsoft.cslib.util.CSString;
import clashsoft.mods.morefood.container.ContainerRecipeBook;
import clashsoft.mods.morefood.food.Food;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
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
	
	public static RenderItem				itemRender			= new RenderItem();
	
	public GuiButton						prev;
	public GuiButton						next;
	
	public GuiTextField						search;
	public GuiFoodListSlot					foodListSlot;
	
	public ContainerRecipeBook				container;
	public int								currentEntryID		= 0;
	public String							currentEntryName	= "";
	public Food								currentEntry;
	public List<Food>						currentDisplayList	= Food.getDisplayList();
	public ItemStack[][]					recipe				= null;
	
	public EntityPlayer						player;
	
	public GuiRecipeBook(ContainerRecipeBook container, EntityPlayer player)
	{
		super(container);
		this.container = container;
		this.player = player;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		this.foodListSlot.drawScreen(par1, par2, 1F);
		super.drawScreen(par1, par2, par3);
	}
	
	List<String>	temp	= new ArrayList<String>();
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		GL11.glColor4f(1, 1, 1, 1);
		this.search.drawTextBox();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(-this.guiLeft, -this.guiTop, 0F);
		this.foodListSlot.drawScreen(par1, par2, 1F);
		GL11.glPopMatrix();
		
		if (this.next.enabled && this.func_146978_c(this.next.xPosition - this.guiLeft, this.next.yPosition - this.guiTop, 20, 20, par1, par2))
		{
			Food f = this.currentDisplayList.get(this.currentEntryID + 1);
			
			this.temp.add(f.asStack().getDisplayName());
			this.drawHoveringText(this.temp, par1 - this.guiLeft, par2 - this.guiTop, this.mc.fontRenderer, f.getCategory().color);
			this.temp.clear();
		}
		if (this.prev.enabled && this.func_146978_c(this.prev.xPosition - this.guiLeft, this.prev.yPosition - this.guiTop, 20, 20, par1, par2))
		{
			Food f = this.currentDisplayList.get(this.currentEntryID - 1);
			
			this.temp.add(f.asStack().getDisplayName());
			this.drawHoveringText(this.temp, par1 - this.guiLeft, par2 - this.guiTop, this.mc.fontRenderer, f.getCategory().color);
			this.temp.clear();
		}
		if (this.func_146978_c(20, 50, 40, 40, par1, par2))
		{
			this.drawHoveringText(Arrays.asList(this.currentEntryName), par1 - this.guiLeft, par2 - this.guiTop, this.mc.fontRenderer, this.currentEntry.getCategory().color);
		}
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.renderEngine.bindTexture(background);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 256, 256, 256, 256);
		
		int matchingEntrys = this.currentDisplayList.size();
		String header = "Recipe Book (" + matchingEntrys + (matchingEntrys != Food.getDisplayList().size() ? " Matching" : "") + " Entry" + (matchingEntrys != 1 ? "s" : "") + ")";
		this.mc.fontRenderer.drawString(header, (this.width - this.mc.fontRenderer.getStringWidth(header)) / 2, this.guiTop + 10, 0x404040, false);
		
		if (this.currentEntry != null)
		{
			// Name + Description
			
			ItemStack stack = this.currentEntry.asStack();
			
			{
				String desc = "food." + this.currentEntryName.toLowerCase().replace(" ", "") + ".desc";
				String desc1 = StatCollector.translateToLocal(desc);
				
				if (desc1.equals(desc))
				{
					desc1 = "" + EnumChatFormatting.DARK_RED + EnumChatFormatting.ITALIC + "No description available. ";
				}
				
				this.mc.fontRenderer.drawString(this.currentEntryName, (this.width - this.mc.fontRenderer.getStringWidth(this.currentEntryName)) / 2, this.guiTop + 27, 4210752, false);
				
				this.mc.fontRenderer.drawString(EnumChatFormatting.ITALIC + this.currentEntry.getCategory().name, this.guiLeft + 70, this.guiTop + 45, 0x404040);
				
				String desc2 = CSString.cutString(desc1, 26);
				String[] lines = CSString.lineArray(desc2);
				for (int i = 0; i < lines.length; i++)
				{
					this.mc.fontRenderer.drawString(lines[i], this.guiLeft + 70, this.guiTop + 60 + (i * 10), 4210752);
				}
			}
			
			// Crafting
			
			{
				String crafting = this.currentEntry.getRecipe() == null ? EnumChatFormatting.RED + "Not craftable" : (this.currentEntry.getRecipe().getCraftingType() == IMetaItemRecipe.CRAFTING ? "Crafting" : (this.currentEntry.getRecipe().getCraftingType() == IMetaItemRecipe.CRAFTING_SHAPELESS ? "Shapeless Crafting" : "Cooking"));
				this.mc.fontRenderer.drawString(crafting, this.guiLeft + 22, this.guiTop + 103, 4210752, false);
				
				if (this.currentEntry.getRecipe() != null && this.recipe != null)
				{
					String s = this.currentEntry.getRecipe().getAmount() + "x " + this.currentEntryName;
					String[] split = CSString.lineArray(CSString.cutString(s, 15));
					
					for (int i = 0; i < split.length; i++)
					{
						this.mc.fontRenderer.drawString(split[i], this.guiLeft + 22, this.guiTop + 175 + (i * 10), 4210752, false);
					}
				}
			}
			
			// Stats
			
			{
				int statsX = 140;
				
				this.mc.fontRenderer.drawString("Stats", this.guiLeft + statsX, this.guiTop + 103, 4210752);
				
				String foodValue = this.currentEntry.getFoodValue() == 0 ? EnumChatFormatting.RED + "Not eatable" : ((this.currentEntry.getAction() == EnumAction.eat ? "Food value: " : "Drink value: ") + EnumChatFormatting.DARK_GREEN + this.currentEntry.getFoodValue());
				this.mc.fontRenderer.drawString(foodValue, this.guiLeft + statsX, this.guiTop + 120, 4210752, false);
				
				String plantable = this.currentEntry.getBlockPlaced() == null ? "Not plantable" : EnumChatFormatting.DARK_GREEN + "Plantable";
				this.mc.fontRenderer.drawString(plantable, this.guiLeft + statsX, this.guiTop + 130, 4210752, false);
				
				boolean hasEffects = this.currentEntry.getEffects() != null && this.currentEntry.getEffects().length > 0;
				String effects = hasEffects ? "Effects:" : "No effects";
				this.mc.fontRenderer.drawString(effects, this.guiLeft + statsX, this.guiTop + 145, 4210752, false);
				if (hasEffects)
					for (int i = 0; i < this.currentEntry.getEffects().length && i < 3; i++)
					{
						PotionEffect effect = this.currentEntry.getEffects()[i];
						String var = " " + StatCollector.translateToLocal(effect.getEffectName());
						this.mc.fontRenderer.drawString(var, this.guiLeft + statsX, this.guiTop + 155 + (i * 10), 4210752, false);
					}
			}
			
			// Icon
			
			{
				GL11.glScalef(2.5F, 2.5F, 1F);
				itemRender.zLevel = 100F;
				itemRender.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, stack, (int) ((this.guiLeft + 22) / 2.5F), (int) ((this.guiTop + 50) / 2.5F));
				itemRender.zLevel = 0F;
				GL11.glScalef(0.4F, 0.4F, 1F);
				GL11.glColor4f(1F, 1F, 1F, 1F);
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.id == 0)
		{
			if (this.currentEntryID > 0)
			{
				this.currentEntryID--;
			}
		}
		if (par1GuiButton.id == 1)
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
		
		this.prev = new GuiButton(0, this.guiLeft + 20, this.guiTop + 20, 20, 20, "<");
		this.next = new GuiButton(1, this.guiLeft + this.xSize - 40, this.guiTop + 20, 20, 20, ">");
		
		this.search = new GuiTextField(this.fontRendererObj, 44, 20, this.xSize - 40 - 48, 20);
		this.search.setVisible(false);
		
		this.foodListSlot = new GuiFoodListSlot(this);
		
		this.buttonList.add(this.prev);
		this.buttonList.add(this.next);
		
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
				this.prev.enabled = false;
			}
			else
			{
				this.prev.enabled = true;
			}
			
			if (this.currentEntryID == this.currentDisplayList.size() - 1)
			{
				this.next.enabled = false;
			}
			else
			{
				this.next.enabled = true;
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
