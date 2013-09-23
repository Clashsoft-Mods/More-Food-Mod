package clashsoft.mods.morefood.gui;

import clashsoft.clashsoftapi.util.CSString;
import clashsoft.mods.morefood.food.Food;
import cpw.mods.fml.client.GuiScrollingList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;

public class GuiFoodListSlot extends GuiScrollingList
{
	public GuiRecipeBook parentGui;
	
	public GuiFoodListSlot(GuiRecipeBook parent)
	{
		super(Minecraft.getMinecraft(), parent.getGuiLeft(), parent.height, 0, parent.height, 0, 36);
		this.parentGui = parent;
	}
	
	@Override
	protected int getSize()
	{
		return parentGui.currentDisplayList.size();
	}
	
	@Override
	protected void elementClicked(int i, boolean flag)
	{
		parentGui.setRecipe(i);
	}
	
	@Override
	protected boolean isSelected(int i)
	{
		return i == parentGui.currentEntry;
	}
	
	@Override
	protected void drawBackground()
	{
	}
	
	@Override
	protected void drawSlot(int id, int x, int y, int l, Tessellator tessellator)
	{
		Minecraft mc = Minecraft.getMinecraft();
		
		Food food = parentGui.currentDisplayList.get(id);
		Icon icon = food.asStack().getIconIndex();
		
		int offsX = 0;
		
		String name = food.asStack().getDisplayName();
		name = CSString.trimStringToRenderWidth(name, this.listWidth - offsX - 2);
		
		int offsY = 0;
		if (!parentGui.search.getText().isEmpty())
		{
			offsY = 10;
			mc.fontRenderer.drawString(parentGui.search.getText().startsWith("category:") ? "Category Match:" : "Match:", offsX + 2, y + 2, 0xFF8100, true);
		}
		
		mc.fontRenderer.drawString(name, offsX + 2, y + offsY + 2, 0xFFFFFF, true);
		
		String category = EnumChatFormatting.ITALIC + food.getCategory().name;
		category = CSString.trimStringToRenderWidth(category, this.listWidth);
		
		mc.fontRenderer.drawString(category, offsX + 2, y + offsY + 12, food.getCategory().color, true);
	}
}
