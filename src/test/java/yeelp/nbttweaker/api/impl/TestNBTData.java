package yeelp.nbttweaker.api.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import crafttweaker.api.data.DataByte;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import yeelp.nbttweaker.api.NBTTweakerAPI;

public final class TestNBTData {

	private static final String FORGE_CAPS = "ForgeCaps";
	private static final String SRP_ENHANCED_CAP = "srpcotesia:enhanced_mob";
	private static final String ENHANCED = "enhanced";
	protected static final NBTTagCompound NBT_ROOT = new NBTTagCompound();
	protected static final NBTTagCompound NBT_FORGE_CAPS = new NBTTagCompound();
	protected static final NBTTagCompound SRP_CAP = new NBTTagCompound();
	protected static final NBTTagByte TRUE_BYTE = new NBTTagByte((byte) 1);
	
	protected static final NBTTweakerAPI API = mock(NBTTweakerAPI.class);
	
	@BeforeAll
	static void setup() {
		SRP_CAP.setByte(ENHANCED, (byte) 1);
		NBT_FORGE_CAPS.setTag(SRP_ENHANCED_CAP, SRP_CAP);
		NBT_ROOT.setTag(FORGE_CAPS, NBT_FORGE_CAPS);
		
		when(API.convertIData(any(DataByte.class))).thenReturn(TRUE_BYTE);
		NBTData.setAPIToUse(API);
	}
	
	@SuppressWarnings("static-method")
	@Test
	void testSearch() {
		assertTrue(new NBTData(NBT_ROOT).searchForKey(ENHANCED, new DataByte((byte) 1), true));
	}
	
	@SuppressWarnings("static-method")
	@Test
	void testHasKey() {
		assertTrue(new NBTData(SRP_CAP).hasKey(ENHANCED, new DataByte((byte) 1)));
	}
	
	@SuppressWarnings("static-method")
	@Test
	void testGet() {
		assertEquals(new NBTData(TRUE_BYTE).asByte(), new NBTData(SRP_CAP).get(ENHANCED).asByte());
	}
}
