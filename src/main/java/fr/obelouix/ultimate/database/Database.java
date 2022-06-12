package fr.obelouix.ultimate.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.database.models.PlayerOptionsTable;
import fr.obelouix.ultimate.database.models.PlayerTable;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class Database {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    private static ConnectionSource connection;
    private static Dao<PlayerTable, String> playerDao;
    private static Dao<PlayerOptionsTable, String> playerOptionsDao;

    public Database(String url) throws Exception {

        connection = new JdbcConnectionSource(url, Config.getDatabaseUsername(), Config.getDatabasePassword());
        playerDao = DaoManager.createDao(connection, PlayerTable.class);
        TableUtils.createTableIfNotExists(connection, PlayerTable.class);

        playerOptionsDao = DaoManager.createDao(connection, PlayerOptionsTable.class);
        TableUtils.createTableIfNotExists(connection, PlayerOptionsTable.class);

    }

    public static Dao<PlayerTable, String> getPlayerDao() {
        return playerDao;
    }

    public static Dao<PlayerOptionsTable, String> getPlayerOptionsDao() {
        return playerOptionsDao;
    }

    public ConnectionSource getConnection() {
        return connection;
    }

    public static class PlayerDao {

        public static CompletableFuture<Void> createData(PlayerTable data) {
            return CompletableFuture.runAsync(() -> {
                try {
                    getPlayerDao().createIfNotExists(data);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        public static CompletableFuture<Void> createOrUpdateData(PlayerTable data) {
            return CompletableFuture.runAsync(() -> {
                try {
                    getPlayerDao().createOrUpdate(data);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }

    public static class PlayerOptionsDao {
        public static CompletableFuture<Void> createData(PlayerOptionsTable data) {
            return CompletableFuture.runAsync(() -> {
                try {
                    getPlayerOptionsDao().createIfNotExists(data);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        public static CompletableFuture<Void> createOrUpdateData(PlayerOptionsTable data) {
            return CompletableFuture.runAsync(() -> {
                try {
                    getPlayerOptionsDao().createOrUpdate(data);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        public static CompletableFuture<Void> query(PreparedQuery<PlayerOptionsTable> query) {
            return CompletableFuture.runAsync(() -> {
                try {
                    getPlayerOptionsDao().query(query);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }


}
