package fr.obelouix.ultimate.config;

import fr.obelouix.ultimate.utils.LuckPermsUtils;
import net.luckperms.api.model.group.Group;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class LuckPermsConfig extends BaseConfig {

    private final CommentedConfigurationNode luckPermsNode = Config.getRoot().node("luckperms");

    @Override
    protected void addToConfig() throws SerializationException {
        if (LuckPermsUtils.getLuckPermsAPI() != null && luckPermsNode.node("chat").empty()) {
            for (final Group group : LuckPermsUtils.getGroups()) {
                luckPermsNode.node("chat").act(n -> {
                    if (group.getName().equals("default")) {
                        n.node("format")
                                .commentIfAbsent("""
                                        This allow you to control the chat formatting for every groups
                                        You case use standard color code like &4 and also hex color codes like &#808080
                                                                                    
                                        Some tags are available to allow you to fully customize the chat:
                                        - {displayname} : player name
                                        - {message} : the message of the player
                                        - {world} : the current world of the player
                                        - {prefix} : the luckperms prefix of the group
                                        - {suffix} : the luckperms suffix of the group
                                                                                    
                                        /!\\ PLEASE NOTE THAT IF YOU ADD COLOR CODES DIRECTLY IN LUCKPERMS META FOR
                                        PREFIX AND SUFFIX, THEY WILL OVERRIDE PREFIX AND SUFFIX COLORS FROM THIS CONFIG
                                        """)
                                .node(group.getName()).set("&#808080{displayname}: {message}")
                                .commentIfAbsent("default LuckPerms group");
                    } else {
                        n.node("format").node(group.getName()).set("&#32cd32[{world}]&r{prefix}{displayname}{suffix}: &r{message}")
                                .commentIfAbsent(group.getName() + " group");
                    }

                });
            }
        }


    }

    @Override
    protected void readConfig() {
        if (LuckPermsUtils.getLuckPermsAPI() == null) return;
        for (final Object group : luckPermsNode.node("chat", "format").childrenMap().keySet()) {
            Config.chatFormat.put(group.toString(), luckPermsNode.node("chat", "format", group.toString()).getString());
        }
    }
}
