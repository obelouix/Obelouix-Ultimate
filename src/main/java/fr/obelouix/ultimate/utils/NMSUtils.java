package fr.obelouix.ultimate.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NMSUtils {

    public static String craftBukkitVersion() {
        return "org.bukkit.craftbukkit." + version() + ".";
    }

    public static String nmsVersion() {
        return "net.minecraft.server." + version() + ".";
    }

    public static String version() {
        String pkg = Bukkit.getServer().getClass().getPackage().getName();
        return pkg.substring(pkg.lastIndexOf(".") + 1);
    }

    public static Class<?> wrapperToPrimitive(Class<?> clazz) {
        if (clazz == Boolean.class) return boolean.class;
        if (clazz == Integer.class) return int.class;
        if (clazz == Double.class) return double.class;
        if (clazz == Float.class) return float.class;
        if (clazz == Long.class) return long.class;
        if (clazz == Short.class) return short.class;
        if (clazz == Byte.class) return byte.class;
        if (clazz == Void.class) return void.class;
        if (clazz == Character.class) return char.class;
        return clazz;
    }

    public static Class<?>[] toParamTypes(Object... params) {
        Class<?>[] classes = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++)
            classes[i] = wrapperToPrimitive(params[i].getClass());
        return classes;
    }

    public static Object getHandle(Entity entity) {
        return callMethod(entity, "getHandle");
    }

    public static Object getHandle(World world) {
        return callMethod(world, "getHandle");
    }

    public static Object playerConnection(Player player) {
        return playerConnection(getHandle(player));
    }

    public static Object playerConnection(Object handle) {
        return getDeclaredField(handle, "b");
    }

    public static void sendPacket(Player p, Object packet) {
        Object pc = playerConnection(p);
        try {
            pc.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(pc, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getPacket(String name, Object... params) {
        return callDeclaredConstructor(getNMSClass(name), params);
    }

    public static void sendJson(Player player, String json) {
        Object comp = callDeclaredMethod(getNMSClass("ChatSerializer"),
                "a", json);
        sendPacket(player, getPacket("PacketPlayOutChat", comp, true));
    }

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            return null;
        }
    }

    public static Class<?> getNMSClass(String name) {
        return getClass(nmsVersion() + name);
    }

    public static Class<?> getCBClass(String name) {
        return getClass(craftBukkitVersion() + name);
    }

    public static Object callDeclaredMethod(Object object, String method, Object... params) {
        try {
            Method m = object.getClass().getDeclaredMethod(method, toParamTypes(params));
            m.setAccessible(true);
            return m.invoke(object, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object callMethod(Object object, String method, Object... params) {
        try {
            Method m = object.getClass().getMethod(method, toParamTypes(params));
            m.setAccessible(true);
            return m.invoke(object, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object callDeclaredConstructor(Class<?> clazz, Object... params) {
        try {
            Constructor<?> con = clazz.getDeclaredConstructor(toParamTypes(params));
            con.setAccessible(true);
            return con.newInstance(params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object callConstructor(Class<?> clazz, Object... params) {
        try {
            Constructor<?> con = clazz.getConstructor(toParamTypes(params));
            con.setAccessible(true);
            return con.newInstance(params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getDeclaredField(Object object, String field) {
        try {
            Field f = object.getClass().getDeclaredField(field);
            f.setAccessible(true);
            return f.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getField(Object object, String field) {
        try {
            Field f = object.getClass().getField(field);
            f.setAccessible(true);
            return f.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setDeclaredField(Object object, String field, Object value) {
        try {
            Field f = object.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setField(Object object, String field, Object value) {
        try {
            Field f = object.getClass().getField(field);
            f.setAccessible(true);
            f.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
