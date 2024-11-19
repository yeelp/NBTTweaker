package yeelp.nbttweaker.api.impl;

import crafttweaker.api.data.IData;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.nbt.NBTBase;
import yeelp.nbttweaker.api.NBTTweakerAPI;

public enum NBTTweakerAPIImpl implements NBTTweakerAPI {
	INSTANCE;

	@Override
	public NBTBase convertIData(IData data) {
		return CraftTweakerMC.getNBT(data);
	}
	
}
