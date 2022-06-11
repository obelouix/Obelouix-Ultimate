package fr.obelouix.ultimate.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "players_options")
public class PlayerOptionsTable {

    @DatabaseField(canBeNull = false)
    private UUID uuid;

    @DatabaseField(canBeNull = false)
    private boolean showCoordinates;

    public PlayerOptionsTable() {
    }

    public PlayerOptionsTable(UUID uuid, boolean showCoordinates) {
        this.uuid = uuid;
        this.showCoordinates = showCoordinates;
    }
}
