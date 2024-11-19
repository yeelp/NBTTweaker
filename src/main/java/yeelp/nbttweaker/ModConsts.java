package yeelp.nbttweaker;

import net.minecraft.nbt.NBTTagCompound;

public interface ModConsts {
	final String MODID = "nbttweaker";
	final String NAME = "NBTTweaker";
	final String VERSION = "@version@";
	
	final String CRAFTTWEAKER_ID = "crafttweaker";
	
	interface NBTConsts {
		final int COMPOUND_TAG_ID = new NBTTagCompound().getId();
	}
}
