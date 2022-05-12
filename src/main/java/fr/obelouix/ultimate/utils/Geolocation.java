package fr.obelouix.ultimate.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;
import java.util.Scanner;

public class Geolocation {

    private Geolocation() {
    }

    public static String getCountry(Player player) {
        try {
            try {
                URLConnection urlConnection = new URL("http://ip-api.com/json/"
                        + Objects.requireNonNull(Objects.requireNonNull(Bukkit.getPlayer(player.getUniqueId())).getAddress())
                        + "?fields=country")
                        .openConnection();

                Scanner scanner = new Scanner(urlConnection.getInputStream()).useDelimiter("\\A");
                String response = scanner.next();

                Gson gson = new Gson();
                IP ip = gson.fromJson(response, IP.class);

                return ip.country;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static class IP {
        String country;
    }


}
