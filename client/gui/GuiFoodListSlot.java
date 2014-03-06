package clashsoft.mods.morefood.client.gui;

import clashsoft.cslib.minecraft.lang.I18n;
import clashsoft.cslib.minecraft.util.CSString;
import clashsoft.mods.morefood.food.Food;
import cpw.mods.fml.client.GuiScrollingList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class GuiFoodListSlot extends GuiScrollingList
{
	public GuiRecipeBook	parentGui;
	
	public GuiFoodListSlot(GuiRecipeBook parent)
	{
		super(Minecraft.getMinecraft(), parent.getGuiLeft(), parent.height, 0, parent.height, 0, 36);
		this.parentGui = parent;
	}
	
	@Override
	protected int getSize()
	{
		return this.parentGui.currentDisplayList.size();
	}
	
	@Override
	protected void elementClicked(int i, boolean flag)
	{
		this.parentGui.setRecipe(i);
	}
	
	@Override
	protected boolean isSelected(int i)
	{
		return i == this.parentGui.currentEntryID;
	}
	
	@Override
	protected void drawBackground()
	{
	}
	
	@Override
	protected void drawSlot(int id, int x, int y, int l, Tessellator tessellator)
	{
		Minecraft mc = Minecraft.getMinecraft();
		
		Food food = this.parentGui.currentDisplayList.get(id);
		String name = food.asStack().getDisplayName();
		String search = this.parentGui.search.getText();
		IIcon icon = food.asStack().getIconIndex();
		
		int offsX = 0;
		
		name = CSString.trimStringToRenderWidth(name, this.listWidth - offsX - 2);
		
		int offsY = 0;
		if (!search.isEmpty())
		{
			offsY = 10;
			String s = I18n.getString(search.startsWith("category:") ? "search.match.category" : "search.match");
			mc.fontRenderer.drawString(s, offsX + 2, y + 2, 0xFF8100, true);
		}
		
		mc.fontRenderer.drawString(name, offsX + 2, y + offsY + 2, 0xFFFFFF, true);
		
		String category = EnumChatFormatting.ITALIC + food.getCategory().getLocalizedName();
		category = CSString.trimStringToRenderWidth(category, this.listWidth);
		
		mc.fontRenderer.drawString(category, offsX + 2, y + offsY + 12, food.getCategory().getColor(), true);
	}
}
