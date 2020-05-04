package ua.nure.chernysh.summary_task.web.command;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.web.command.authentification.LoginCommand;
import ua.nure.chernysh.summary_task.web.command.authentification.LogoutCommand;
import ua.nure.chernysh.summary_task.web.command.authentification.RegistrationCommand;
import ua.nure.chernysh.summary_task.web.command.basket.AddToBasketCommand;
import ua.nure.chernysh.summary_task.web.command.basket.RemoveFromBasketCommand;
import ua.nure.chernysh.summary_task.web.command.basket.ViewBasketCommand;
import ua.nure.chernysh.summary_task.web.command.periodical.*;
import ua.nure.chernysh.summary_task.web.command.settings.SettingsCommand;
import ua.nure.chernysh.summary_task.web.command.settings.ViewSettingsCommand;
import ua.nure.chernysh.summary_task.web.command.subscription.*;
import ua.nure.chernysh.summary_task.web.command.user.*;

import java.util.Map;
import java.util.TreeMap;

/**
 * Class contains command map to search command from request
 */
public class CommandContainer {
    private static final Logger LOG = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("login", new LoginCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("logout", new LogoutCommand());

        commands.put("settings", new SettingsCommand());
        commands.put("viewSettings", new ViewSettingsCommand());

        commands.put("ban", new BanCommand());
        commands.put("replenish", new ReplenishAccountCommand());
        commands.put("viewReplenish", new ViewReplenishCommand());
        commands.put("users", new ViewUsersCommand());
        commands.put("info", new ViewInfoCommand());

        commands.put("basketAdd", new AddToBasketCommand());
        commands.put("basketRem", new RemoveFromBasketCommand());
        commands.put("basket", new ViewBasketCommand());

        commands.put("addPeriodical", new AddPeriodicalCommand());
        commands.put("deletePeriodical", new DeletePeriodicalCommand());
        commands.put("editPeriodical", new EditPeriodicalCommand());
        commands.put("periodical", new ViewPeriodicalCommand());
        commands.put("periodicals", new ViewPeriodicalsCommand());
        commands.put("search", new SearchCommand());
        commands.put("viewEditPeriodical", new ViewEditPeriodicalCommand());
        commands.put("viewAddPeriodicalCommand", new ViewAddPeriodicalCommand());

        commands.put("buySubscriptions", new BuySubscriptionsCommand());
        commands.put("renewSubscription", new RenewSubscriptionCommand());
        commands.put("cancelSubscription", new CancelingSubscriptionCommand());
        commands.put("subscription", new ViewSubscriptionCommand());
        commands.put("subscriptions", new ViewSubscriptionsCommand());


        LOG.debug("Command container was successfully initialized");
        LOG.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }
}
