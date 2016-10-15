package com.alphatrader.javagui.control;

import com.alphatrader.javagui.AppState;
import com.alphatrader.javagui.estimation.Estimator;
import com.alphatrader.rest.Company;
import com.alphatrader.rest.Notification;
import javafx.application.Platform;

import java.util.*;

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

    /**
     * This task estimates the value of each company by dividing the value of the portfolio and cash by the number
     * of outstanding shares of the company.
     *
     * @author Christopher Guckes (christopher.guckes@torq-dev.de)
     * @version 1.0
     */
    private static class UpdateCompanyValuation extends TimerTask {
        /**
         * Interval at which to refresh our valuation.
         */
        static final int interval = 3600 * 1000;

        /**
         * The work this task should do regularly.
         */
        @Override
        public void run() {
            final List<Company> companies = Company.getAllCompanies();

            // Get naiive evaluation by just evaluating the company value.
            Estimator estimator = Estimator.get(Estimator.EstimatorType.FUNDAMENTAL);
            System.out.println("Refreshing");
            estimator.refresh(companies);
            System.out.println("Done");
        }
    }

    private Timer timer;

    public BackgroundDaemon() {
        this.timer = new Timer("BackgroundDaemon", true);
    }

    public void start() {
        this.timer.schedule(new UpdateNotificationsTask(), 0, UpdateNotificationsTask.interval);
        this.timer.schedule(new UpdateCompanyValuation(), 0, UpdateCompanyValuation.interval);
    }
}
