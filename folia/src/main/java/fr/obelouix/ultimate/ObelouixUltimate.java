package fr.obelouix.ultimate;

public class ObelouixUltimate extends AbstractPlugin {

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        plugin = this;
        super.onEnable();
    }
}
