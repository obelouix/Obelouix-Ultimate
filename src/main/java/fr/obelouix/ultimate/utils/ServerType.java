package fr.obelouix.ultimate.utils;

import io.papermc.lib.PaperLib;

public class ServerType {


    public static boolean isPurpur(){
        try {
            Class.forName(" org.purpurmc.purpur.PurpurConfig");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }

    }

    public static boolean isPaperServer() {
        return PaperLib.isPaper();
    }

}
