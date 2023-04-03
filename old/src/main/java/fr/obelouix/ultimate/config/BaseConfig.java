package fr.obelouix.ultimate.config;

import io.leangen.geantyref.TypeToken;
import org.bukkit.Location;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class BaseConfig {

    public static void addBooleanNode(CommentedConfigurationNode node, Boolean defaultValue) throws SerializationException {
        if(node.empty()) {
            node.set(defaultValue);
        }
    }

    public static void addStringNode(CommentedConfigurationNode node, String defaultValue) throws SerializationException {
        if(node.empty()) {
            node.set(defaultValue);
        }
    }

    public static void addLocationNode(CommentedConfigurationNode node, Location location) throws SerializationException {
        if(node.empty()) {
            node.node("x").set(location.getX());
            node.node("y").set(location.getY());
            node.node("z").set(location.getZ());
        }
    }

    public static void setTimeNode(CommentedConfigurationNode node, LocalDateTime dateTime) throws SerializationException {
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        node.set(dateTime.format(timeFormatter)).commentIfAbsent("Date time format is: dd/MM/yyyy HH:mm:ss");
    }

    public static void setBooleanNode(CommentedConfigurationNode node, Boolean value) throws SerializationException {
        node.set(value);
    }

    public static boolean getBooleanNode(CommentedConfigurationNode node){
        if(node.empty()) return false;
        return node.getBoolean();
    }

}
