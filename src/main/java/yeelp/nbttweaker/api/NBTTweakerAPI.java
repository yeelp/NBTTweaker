package yeelp.nbttweaker.api;

import crafttweaker.api.data.IData;
import net.minecraft.nbt.NBTBase;

public interface NBTTweakerAPI {
	
	NBTBase convertIData(IData data);
}
