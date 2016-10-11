package com.alphatrader.javagui.control;

import com.alphatrader.javagui.AppState;
import com.alphatrader.javagui.data.Notification;
import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Runs recurring tasks in the background.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class BackgroundDaemon {
    /**
     * Adds all new notifications to the notification list.
     *
     * @author Christopher Guckes (christopher.guckes@torq-dev.de)
     * @version 1.0
     */
    private static class UpdateNotificationsTask extends TimerTask {
        /**
         * Interval at which to run this task.
         */
        static final int interval = 5000;

        /**
         * The work this task should do regularly.
         */
        @Override
        public void run() {
            Platform.runLater(
                () -> AppState.getInstance().getNotifications().addAll(Notification.getUnreadNotifications())
            );
            Notification.markAllAsRead();
        }
    }

    private Timer timer;

    public BackgroundDaemon() {
        this.timer = new Timer("BackgroundDaemon", true);
    }

    public void start() {
        this.timer.schedule(new UpdateNotificationsTask(), 0, UpdateNotificationsTask.interval);
    }
}
