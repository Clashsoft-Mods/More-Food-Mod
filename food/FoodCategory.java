package clashsoft.mods.morefood.food;

import com.sun.imageio.plugins.common.I18N;

public class FoodCategory
{
	public static final FoodCategory	generic	= new FoodCategory("Food Items", 0x818181);
	
	protected String					name;
	protected int						color;
	
	public FoodCategory(String name, int color)
	{
		this.name = name;
		this.color = color;
	}
	
	public String getLocalizedName()
	{
		return I18N.getString(this.getName());
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getColor()
	{
		return this.color;
	}
}
