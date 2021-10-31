package fr.obelouix.ultimate.gui;

import fr.obelouix.ultimate.i18n.I18n;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Collection;

public abstract class BaseBookGui {

    protected final I18n i18n = I18n.getInstance();
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
