package fr.obelouix.ultimate.gui;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.TranslationAPI;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Collection;

public abstract class BaseBookGui {

    protected static final TranslationAPI translationAPI = ObelouixUltimate.getInstance().getTranslationAPI();
    protected Book book;

    public BaseBookGui() {
    }

    public BaseBookGui(Player player) {
    }

    protected abstract Component bookTitle(Player player);

    protected abstract Component bookAuthor();

    protected abstract Collection<Component> bookPages(Player player);

    protected abstract Book show();
}
