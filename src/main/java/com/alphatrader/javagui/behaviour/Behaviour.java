package com.alphatrader.javagui.behaviour;

import com.alphatrader.javagui.data.Company;

/**
 * This interface allows the user to define different behaviours for different events.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public interface Behaviour {
    /**
     * This function allows the bot to react to certain events. This function is called for every occurring event in the
     * event queue. It is called before the {@link #action(Company)} function in the reaction loop.
     *
     * @param company The company controlled by the bot.
     * @param event   The event to react to.
     */
    void react(Company company, Event event);

    /**
     * This function allows the user to define a default behaviour that is executed on every round of the event loop.
     *
     * @param company The company controlled by the bot.
     */
    void action(Company company);
}
