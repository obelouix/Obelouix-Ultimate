package fr.obelouix.ultimate.commands.manager;

import fr.obelouix.ultimate.i18n.I18n;

public abstract class BaseCommand {

    protected I18n i18n = I18n.getInstance();

    public abstract void register();

}
