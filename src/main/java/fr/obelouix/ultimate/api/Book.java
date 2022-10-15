package fr.obelouix.ultimate.api;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 * Create a book as an {@link ItemStack}
 */
public class Book {

    private final ItemStack bookItem;
    private BookMeta bookMeta;

    public Book() {
        bookItem = new ItemStack(Material.WRITTEN_BOOK);
        bookMeta = (BookMeta) bookItem.getItemMeta();
    }

    /**
     * Sets the author of the book.
     * See {@link BookMeta#author(Component)}
     *
     * @param author the author to set
     */
    public Book setAuthor(Component author) {
        bookMeta = bookMeta.author(author);
        return this;
    }

    /**
     * Adds new pages to the end of the book. Up to a maximum of 50 pages with 256 characters per page
     * See {@link BookMeta#addPages(Component...)}
     */
    public Book addPages(Component... pages) {
        bookMeta.addPages(pages);
        return this;
    }

    /**
     * Sets the title of the book.
     * Limited to 32 characters
     * See {@link BookMeta#title(Component)}
     *
     * @param title the title to set
     */
    public Book setTitle(Component title) {
        bookMeta = bookMeta.title(title);
        return this;
    }

    /**
     * Build the book
     *
     * @return the book {@link ItemStack}
     */
    public ItemStack build() {
        bookItem.setItemMeta(bookMeta);
        return bookItem;
    }
}
