package yeelp.nbttweaker.api;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import yeelp.nbttweaker.api.impl.NBTData;

@ZenClass("mods.nbttweaker.INBTData")
@ZenRegister
public interface INBTData {

	/**
	 * Does this data have the corresponding key?
	 * @param key name of key to check
	 * @param value optional IData value to check against.
	 * @return True if the key is present and matches the optional value, false if not.
	 */
	@ZenMethod
	boolean hasKey(String key, @Optional IData value);
	
	@ZenMethod
	boolean searchForKey(String key, @Optional IData value, @Optional("true") boolean doFullSearch);
	
	@ZenMethod
	boolean hasValue(IData value);
	
	@ZenMethod
	INBTData get(String key);
	
	/**
	 * Set the value in this NBT data to the specified value
	 * @param key
	 * @param value
	 */
	@ZenMethod
	void set(String key, IData value);
	
	@ZenMethod
	boolean asBool();
	
	@ZenMethod
	byte asByte();
	
	@ZenMethod
	byte[] asByteArray();
	
	@ZenMethod
	double asDouble();
	
	@ZenMethod
	float asFloat();
	
	@ZenMethod
	int asInt();
	
	@ZenMethod
	int[] asIntArray();
	
	@ZenMethod
	short asShort();
	
	@ZenMethod
	long asLong();
	
	@ZenMethod
	String asString();
	
	@ZenMethod
	static INBTData getNBTData(IEntity entity) {
		return new NBTData(CraftTweakerMC.getEntity(entity).getEntityData());
	}
	
	@ZenMethod
	static INBTData getNBTData(IItemStack stack) {
		return new NBTData(CraftTweakerMC.getItemStack(stack).getTagCompound());
	}
	
	@ZenMethod
	static INBTData asNBTData(IData data) {
		return new NBTData(CraftTweakerMC.getNBT(data));
	}
}
