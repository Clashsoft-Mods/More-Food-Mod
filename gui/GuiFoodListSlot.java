package clashsoft.mods.morefood.gui;

import clashsoft.mods.morefood.food.Food;
import cpw.mods.fml.client.GuiScrollingList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;

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
		String name = food.asStack().getDisplayName();
		
		int i = mc.fontRenderer.getStringWidth("...");
		boolean flag = false;
		while(name.length() > 0 && mc.fontRenderer.getStringWidth(name) + i > this.listWidth - 8)
		{
			name = name.substring(0, name.length() - 1);
			flag = true;
		}
		if (flag)
			name += "...";
		
		int offs = 0;
		
		if (!parentGui.search.getText().isEmpty())
		{
			offs = 10;
			mc.fontRenderer.drawString("Match:", 2, y + 2, 0xFF8100, true);
		}
		
		mc.fontRenderer.drawString(name, 2, y + offs + 2, 0xFFFFFF, true);
		
		int number = food.getID() + 1;
		if (number != 0)
			mc.fontRenderer.drawString(EnumChatFormatting.ITALIC + "Food Item", 2, y + offs + 12, 0x0081FF, true);
		else
			mc.fontRenderer.drawString(EnumChatFormatting.ITALIC + "Vanilla Item", 2, y + offs + 12, 0x00EE00);
	}
}
