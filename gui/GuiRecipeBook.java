package clashsoft.mods.morefood.gui;

import java.util.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import clashsoft.clashsoftapi.util.CSString;
import clashsoft.clashsoftapi.util.CSUtil;
import clashsoft.mods.morefood.container.ContainerRecipeBook;
import clashsoft.mods.morefood.food.Food;
import clashsoft.mods.morefood.food.FoodRecipe;

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
	public int								currentEntry		= 0;
	public String							currentEntryName 	= "";	
	public Food								food;
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
		foodListSlot.drawScreen(par1, par2, 1F);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		GL11.glColor4f(1, 1, 1, 1);
		search.drawTextBox();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(-this.guiLeft, -this.guiTop, 0F);
		foodListSlot.drawScreen(par1, par2, 1F);
		GL11.glPopMatrix();
		
		if (isPointInRegion(next.xPosition - guiLeft, next.yPosition - guiTop, 20, 20, par1, par2) && next.enabled)
		{
			List<String> list = new LinkedList<String>();
			list.add(Food.getDisplayList().get(currentEntry + 1).asStack().getDisplayName());
			this.drawHoveringText(list, par1 - guiLeft, par2 - guiTop, this.mc.fontRenderer);
		}
		if (isPointInRegion(prev.xPosition - guiLeft, prev.yPosition - guiTop, 20, 20, par1, par2) && prev.enabled)
		{
			List<String> list = new LinkedList<String>();
			list.add(Food.getDisplayList().get(currentEntry - 1).asStack().getDisplayName());
			this.drawHoveringText(list, par1 - guiLeft, par2 - guiTop, this.mc.fontRenderer);
		}
		if (isPointInRegion(20, 50, 40, 40, par1, par2))
		{
			this.drawHoveringText(Arrays.asList(currentEntryName), par1 - guiLeft, par2 - guiTop, this.mc.fontRenderer);
		}
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.renderEngine.bindTexture(background);
		this.drawTexturedModalRect(guiLeft, guiTop, 256, 256, 256, 256);
		
		int matchingEntrys = currentDisplayList.size();
		String header = "Recipe Book (" + matchingEntrys + (matchingEntrys != Food.getDisplayList().size() ? " Matching" : "") + " Entry" + (matchingEntrys != 1 ? "s" : "") + ")";
		this.mc.fontRenderer.drawString(header, (this.width - this.mc.fontRenderer.getStringWidth(header)) / 2, guiTop + 10, 0x404040, false);
		
		if (food != null)
		{
			// Name + Description
			
			ItemStack stack = food.asStack();
			
			{
				String name2 = "food." + food.getName().toLowerCase().replace(" ", "") + ".desc";
				String desc = StatCollector.translateToLocal(name2);
				if (desc.equals(name2))
					desc = "" + EnumChatFormatting.DARK_RED + EnumChatFormatting.ITALIC + "No description available. ";
				
				this.mc.fontRenderer.drawString(currentEntryName, (this.width - this.mc.fontRenderer.getStringWidth(currentEntryName)) / 2, guiTop + 27, 4210752, false);
				
				this.mc.fontRenderer.drawString(EnumChatFormatting.ITALIC + food.getCategory().name, guiLeft + 70, guiTop + 45, 0x404040);
				
				String s4 = CSString.cutString(desc, 26);
				String[] lines = CSString.makeLineList(s4);
				for (int i = 0; i < lines.length; i++)
					this.mc.fontRenderer.drawString(lines[i], guiLeft + 70, guiTop + 60 + (i * 10), 4210752);
			}
			
			// Crafting
			
			{
				String crafting = food.getRecipe() == null ? EnumChatFormatting.RED + "Not craftable" : (food.getRecipe().getCraftingType() == FoodRecipe.CRAFTING ? "Crafting" : (food.getRecipe().getCraftingType() == FoodRecipe.CRAFTING_SHAPELESS ? "Shapeless Crafting" : "Cooking"));
				this.mc.fontRenderer.drawString(crafting, guiLeft + 22, guiTop + 103, 4210752, false);
				
				if (food.getRecipe() != null && recipe != null)
				{
					String s = food.getRecipe().getAmount() + "x " + currentEntryName;
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
			
			{
				GL11.glScalef(2.5F, 2.5F, 1F);
				itemRender.zLevel = 100F;
				itemRender.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, stack, (int) ((guiLeft + 22) / 2.5F), (int) ((guiTop + 50) / 2.5F));
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
			if (currentEntry > 0)
				currentEntry--;
		if (par1GuiButton.id == 1)
			if (currentEntry < currentDisplayList.size() - 1)
				currentEntry++;
		this.setRecipe(currentEntry);
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
		
		foodListSlot = new GuiFoodListSlot(this);
		
		this.buttonList.add(prev);
		this.buttonList.add(next);
		
		this.setRecipe(currentEntry);
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
			if (search.getText().isEmpty())
			{
				currentDisplayList = Food.getDisplayList();
			}
			else
			{
				currentDisplayList = new ArrayList();
				String searchText = search.getText().toLowerCase().trim();
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
							currentDisplayList.add(f);
							flag = false;
						}
					}
					else if (s.startsWith(searchText))
					{
						currentDisplayList.add(f);
						flag = false;
					}
				}
				if (flag)
					search.setTextColor(0xFF0000);
				else
					search.setTextColor(0xFFFFFF);
			}
			this.setRecipe(0);
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
			}
			else
			{
				search.setVisible(false);
				search.setFocused(false);
			}
		}
	}
	
	public void setRecipe(int recipe)
	{
		if (recipe >= currentDisplayList.size())
			recipe = currentDisplayList.size() - 1;
		if (recipe < 0)
			recipe = 0;
		
		if (recipe >= 0 && recipe < currentDisplayList.size())
		{
			this.food = currentDisplayList.get(recipe);
			this.recipe = analyseRecipe(food);
			this.container.inventory.stacks = this.recipe;
			this.currentEntry = recipe;
			this.currentEntryName = food.asStack().getDisplayName();
			
			if (currentEntry == 0)
				prev.enabled = false;
			else
				prev.enabled = true;
			
			if (currentEntry == currentDisplayList.size() - 1)
				next.enabled = false;
			else
				next.enabled = true;
		}
	}
	
	public ItemStack[][] analyseRecipe(Food f)
	{
		if (f == null || f.getRecipe() == null)
			return new ItemStack[][] { { null, null, null }, { null, null, null }, { null, null, null } };
		else
			return f.getRecipe().getAnalysedRecipe();
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
			int i2 = 0xFFFF8100;
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
	
	public int getGuiLeft()
	{
		return this.guiLeft;
	}
}
