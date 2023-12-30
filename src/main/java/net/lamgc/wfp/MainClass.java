package net.lamgc.wfp;

import net.minecraft.server.v1_12_R1.World;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public final class MainClass extends JavaPlugin {

    @SuppressWarnings("unchecked")
    @Override
    public void onLoad() {
        try {
            Class<?> aClass = getClassLoader().loadClass("net.minecraft.server.v1_12_R1.MinecraftServer");
            Field worldsField = aClass.getDeclaredField("worlds");
            CraftServer craftServer = (CraftServer) Bukkit.getServer();
            worldsField.setAccessible(true);
            ArrayList<WorldServer> worldsList = (ArrayList<WorldServer>) worldsField.get(craftServer.getServer());
            worldsField.set(craftServer.getServer(), new CopyOnWriteArrayList<>(worldsList));
            getLogger().info("成功应用补丁!");
        } catch (ClassNotFoundException e) {
            getLogger().warning("无法加载 NMS 类, 跳过应用补丁.");
        } catch (NoSuchFieldException e) {
            getLogger().warning("找不到目标字段, 跳过应用补丁.");
        } catch (IllegalAccessException e) {
            getLogger().warning("无法访问目标字段, 跳过应用补丁.");
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
