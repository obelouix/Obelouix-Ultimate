package fr.obelouix.ultimate.data.player;

public abstract class Player {

    private static String locale;

    public static String getLocale() {
        return locale != null ? locale : "en_US";
    }

    public static void setLocale(String locale) {
        Player.locale = locale;
    }
}
