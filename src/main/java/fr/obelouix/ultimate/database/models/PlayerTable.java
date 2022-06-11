package fr.obelouix.ultimate.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "players")
public class PlayerTable {

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false, id = true)
    private UUID uuid;

    @DatabaseField(canBeNull = false)
    private String language;

    public PlayerTable() {
    }

    public PlayerTable(UUID uniqueId, String name) {
        uuid = uniqueId;
        this.name = name;
    }

    public PlayerTable(UUID uniqueId, String name, String language) {
        uuid = uniqueId;
        this.name = name;
        this.language = language;
    }

}
