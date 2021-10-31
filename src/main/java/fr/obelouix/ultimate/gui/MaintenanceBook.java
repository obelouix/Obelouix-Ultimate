package fr.obelouix.ultimate.gui;

import com.google.common.collect.ImmutableList;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class MaintenanceBook extends BaseBookGui {

    public MaintenanceBook(Player player) {
        book = Book.book(bookTitle(player), bookAuthor(), bookPages(player));
    }

    @Override
    protected Component bookTitle(Player player) {
        return Component.text(i18n.getTranslation(player, "obelouix.book.maintenance.title"));
    }

    @Override
    protected Component bookAuthor() {
        return Component.text("Server");
    }

    @Override
    protected Collection<Component> bookPages(Player player) {
        final Component index = Component.text(">" + i18n.getTranslation(player, "obelouix.book.maintenance.schedule"), NamedTextColor.DARK_AQUA, TextDecoration.UNDERLINED)
                .clickEvent(ClickEvent.changePage(2));
        final Component schedulePage = Component.text("start date:\n")
                .clickEvent(openSign(player))
                .append(Component.text("hour:\n"))
                .append(Component.text("duration: \n"));
        return ImmutableList.of(index, schedulePage);
    }

    public Book show() {
        return book;
    }

    private @Nullable ClickEvent openSign(Player player) {
        new SignGui(new String[]{"test", "", "", ""}).openFakeGui(player);
        return null;
    }

}
