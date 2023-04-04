package fr.obelouix.ultimate;

public class Folia {

    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("io.papermc.paper.threadedregions.RegionizedServerInitEvent");
                return true;
            } catch (ClassNotFoundException ex) {
                return false;
            }
        }
    }

}
