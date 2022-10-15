package fr.obelouix.ultimate.api;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
     * Gets the author of the book.
     *
     * @return the author of the book.
     */
    public Component getAuthor() {
        return bookMeta.hasAuthor() ? bookMeta.author() : null;
    }

    /**
     * Sets the author of the book.
     * See {@link BookMeta#author(Component)}
     *
     * @param author the author to set
     */
    public Book setAuthor(@Nullable Component author) {
        bookMeta = bookMeta.author(author);
        return this;
    }

    /**
     * Adds new pages to the end of the book. Up to a maximum of 50 pages with 256 characters per page
     * See {@link BookMeta#addPages(Component...)}
     */
    public Book addPages(@NotNull Component... pages) {
        bookMeta.addPages(pages);
        return this;
    }

    /**
     * Sets the specified page in the book.
     * See {@link BookMeta#page(int, Component)}
     *
     * @param page the page to set
     * @param data the data to set for that page
     */
    public Book setPage(int page, Component data) {
        bookMeta.page(page, data);
        bookItem.setItemMeta(bookMeta);
        return this;
    }

    /**
     * Gets the specified page in the book. The page must exist.
     * Pages are 1-indexed
     *
     * @param page the page number to get, in range [1, getPageCount()]
     * @return the page from the book
     */
    public Component getPage(int page) {
        return bookMeta.page(page);
    }

    /**
     * Gets the number of pages in the book.
     *
     * @return the number of pages in the book.
     */
    public int getPageCount() {
        return bookMeta.getPageCount();
    }

    /**
     * Gets the list of pages.
     *
     * @return an unmodifiable list of pages
     */
    public List<Component> getPages() {
        return bookMeta.pages();
    }

    /**
     * Gets the title of the book.
     *
     * @return the title of the book
     */
    public Component getTitle() {
        return bookMeta.hasTitle() ? bookMeta.title() : null;
    }

    /**
     * Sets the title of the book.
     * Limited to 32 characters
     * See {@link BookMeta#title(Component)}
     *
     * @param title the title to set
     */
    public Book setTitle(@Nullable Component title) {
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

    /**
     * Get the {@link BookMeta}
     *
     * @return the book meta
     */
    public BookMeta getBookMeta() {
        return bookMeta;
    }
}
