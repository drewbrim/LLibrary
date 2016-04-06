package net.ilexiconn.llibrary;

import net.ilexiconn.llibrary.server.ServerProxy;
import net.ilexiconn.llibrary.server.capability.IEntityDataCapability;
import net.ilexiconn.llibrary.server.config.Config;
import net.ilexiconn.llibrary.server.config.LLibraryConfig;
import net.ilexiconn.llibrary.server.network.NetworkWrapper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "llibrary", name = "LLibrary", version = LLibrary.VERSION)
public class LLibrary {
    public static final String VERSION = "1.2.0-develop";

    @SidedProxy(serverSide = "net.ilexiconn.llibrary.server.ServerProxy", clientSide = "net.ilexiconn.llibrary.client.ClientProxy")
    public static ServerProxy PROXY;
    @Mod.Instance("llibrary")
    public static LLibrary INSTANCE;
    @CapabilityInject(IEntityDataCapability.class)
    public static Capability<IEntityDataCapability> ENTITY_DATA_CAPABILITY;
    @Config
    public static LLibraryConfig CONFIG;
    @NetworkWrapper(messages = {"net.ilexiconn.llibrary.server.network.AnimationMessage", "net.ilexiconn.llibrary.server.network.PropertiesMessage", "net.ilexiconn.llibrary.server.network.SnackbarMessage"})
    public static SimpleNetworkWrapper NETWORK_WRAPPER;

    public static final Logger LOGGER = LogManager.getLogger("LLibrary");

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        LLibrary.PROXY.onPreInit();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        LLibrary.PROXY.onInit();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        LLibrary.PROXY.onPostInit();
    }
}
