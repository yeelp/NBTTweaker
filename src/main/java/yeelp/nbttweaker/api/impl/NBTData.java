package yeelp.nbttweaker.api.impl;

import java.util.Queue;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import crafttweaker.api.data.IData;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import yeelp.nbttweaker.ModConsts.NBTConsts;
import yeelp.nbttweaker.api.INBTData;
import yeelp.nbttweaker.api.NBTTweakerAPI;

public final class NBTData implements INBTData {

	private final NBTBase base;
	private final NBTTagCompound tag;
	private final NBTTagList list;
	private final NBTPrimitive primitive;
	private static NBTTweakerAPI api = NBTTweakerAPIImpl.INSTANCE;
	
	public NBTData(NBTBase base) {
		this.base = base;
		this.tag = base instanceof NBTTagCompound ? (NBTTagCompound) base : null;
		this.list = base instanceof NBTTagList ? (NBTTagList) base : null;
		this.primitive = base instanceof NBTPrimitive ? (NBTPrimitive) base : null;
	}
	
	
	@Override
	public boolean hasKey(String key, IData value) {
		if(this.tag != null) {
			boolean hasKey = this.tag.hasKey(key);
			if(hasKey && value != null) {
				return checkNBT(this.tag.getTag(key), value);
			}
			return hasKey;
		}
		return false;
	}

	@Override
	public boolean searchForKey(String key, IData value, boolean doFullSearch) {
		if(this.tag != null) {
			if(this.tag.hasKey(key)) {
				return value == null ? true : checkNBT(this.tag.getTag(key), value);
			}
			Queue<NBTTagCompound> tags = Lists.newLinkedList();
			Predicate<NBTTagCompound> test = value != null ? (tag) -> checkNBT(tag.getTag(key), value) : Predicates.alwaysTrue();
			tags.add(this.tag);
			do {
				NBTTagCompound tag = tags.remove();
				for(String s : tag.getKeySet()) {
					if(tag.getTag(s).getId() == NBTConsts.COMPOUND_TAG_ID) {
						NBTTagCompound subTag = tag.getCompoundTag(s);
						if(subTag.hasKey(key) && test.test(subTag)) {
							return true;
						}
						else if(subTag.hasKey(key) && !doFullSearch) {
							return false;
						}
						tags.add(subTag);
					}
				}				
			}while(!tags.isEmpty());
		}
		return false;
	}

	@Override
	public boolean hasValue(IData value) {
		NBTBase nbtToCheck = api.convertIData(value);
		if(this.tag != null) {
			return this.tag.getKeySet().stream().map(this.tag::getTag).anyMatch((nbt) -> nbt.equals(nbtToCheck));
		}
		if(this.list != null) {
			for(NBTBase nbt : this.list) {
				if(nbt.equals(nbtToCheck)) {
					return true;
				}
			}
			return false;
		}
		return this.base.equals(nbtToCheck);
	}
	
	@Override
	public INBTData get(String key) {
		if(this.tag != null && this.tag.hasKey(key)) {
			return new NBTData(this.tag.getTag(key));
		}
		return null;
	}

	@Override
	public void set(String key, IData value) {
		if(this.tag != null) {
			this.tag.setTag(key, api.convertIData(value));
		}
	}

	@Override
	public boolean asBool() {
		return this.convertPrimitive((p) -> p.getInt() > 0, false);
	}

	@Override
	public byte asByte() {
		return this.convertPrimitive(NBTPrimitive::getByte, (byte) 0);
	}

	@Override
	public byte[] asByteArray() {
		if(this.base instanceof NBTTagByteArray) {
			return ((NBTTagByteArray) this.base).getByteArray();
		}
		return new byte[0];
	}

	@Override
	public double asDouble() {
		return convertPrimitive(NBTPrimitive::getDouble, 0.0);
	}

	@Override
	public float asFloat() {
		return convertPrimitive(NBTPrimitive::getFloat, 0.0f);
	}

	@Override
	public int asInt() {
		return convertPrimitive(NBTPrimitive::getInt, 0);
	}

	@Override
	public int[] asIntArray() {
		if(this.base instanceof NBTTagIntArray) {
			return ((NBTTagIntArray) this.base).getIntArray();
		}
		return new int[0];
	}

	@Override
	public short asShort() {
		return convertPrimitive(NBTPrimitive::getShort, (short) 0);
	}

	@Override
	public long asLong() {
		return convertPrimitive(NBTPrimitive::getLong, 0L);
	}

	@Override
	public String asString() {
		if(this.base instanceof NBTTagString) {
			return ((NBTTagString) this.base).getString();
		}
		return this.base.toString();
	}
	
	private static boolean checkNBT(NBTBase nbt, IData value) {
		return nbt.equals(api.convertIData(value));
	}
	
	private <T> T convertPrimitive(Function<NBTPrimitive, T> f, T fallback) {
		if(this.primitive != null) {
			return f.apply(this.primitive);
		}
		return fallback;
	}
	
	protected static void setAPIToUse(NBTTweakerAPI api) {
		NBTData.api = api;
	}

}
