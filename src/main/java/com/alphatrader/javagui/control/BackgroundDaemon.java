package com.alphatrader.javagui.control;

import com.alphatrader.javagui.AppState;
import com.alphatrader.javagui.data.Company;
import com.alphatrader.javagui.data.Notification;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
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
        static int interval = 3600 * 1000;

        /**
         * The work this task should do regularly.
         */
        @Override
        public void run() {
            List<Company> companies = Company.getAllCompanies();

            companies.forEach(company -> {
                AppState.getInstance().getValuationMap().put(company.getId(), evaluate(company));
            });
        }

        private Double evaluate(Company company) {
            Double myReturn = company.getPortfolio().getEstimatedValue();
            System.out.println(company.getName() + ": " + myReturn);
            return myReturn;
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
