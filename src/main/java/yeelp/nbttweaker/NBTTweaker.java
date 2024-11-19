package yeelp.nbttweaker;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModConsts.MODID, name = ModConsts.NAME, version = ModConsts.VERSION, dependencies = "required-after:"+ModConsts.CRAFTTWEAKER_ID+"@[1.12-4.1.20.674,)")
public class NBTTweaker {

	private static Logger logger;
	private static final String DEBUG_PREFIX = String.format("[%s (DEBUG)]", ModConsts.NAME.toUpperCase());
	private static final String PREFIX = String.format("[%s]", ModConsts.NAME.toUpperCase());

	@SuppressWarnings("static-method")
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		info(String.format("%s is version %s", ModConsts.NAME, ModConsts.VERSION));
	}
	
	public static void debug(String msg) {
		logger.info(String.format("%s %s", DEBUG_PREFIX, msg));
	}
	
	public static void info(String msg) {
		logger.info(String.format("%s %s", PREFIX, msg));
	}
}
