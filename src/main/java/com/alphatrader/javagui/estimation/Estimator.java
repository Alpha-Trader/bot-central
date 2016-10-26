package com.alphatrader.javagui.estimation;

import com.alphatrader.rest.Company;

import java.util.*;

/**
 * Abstract class allowing the creation of different kinds of estimators.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public abstract class Estimator {
    public enum EstimatorType {
        FUNDAMENTAL,
        CleanFundamental, 
    }

    private static Map<EstimatorType, Estimator> estimators = new HashMap<>();

    public static Estimator get(EstimatorType type) {
        if(!estimators.containsKey(type)) {
            switch (type) {
                case FUNDAMENTAL:
                    estimators.put(type, new FundamentalEstimator());
                    break;
                case CleanFundamental:
                    // create technical estimator here.
                	estimators.put(type, new CleanFundValue());

                	break;
                default:
                    break;
            }
        }

        return estimators.get(type);
    }

    List<Company> companies = new LinkedList<>();

    public abstract void refresh(List<Company> companies);

    public abstract Double evaluate(Company company);

    public abstract Map<String, Double> getSecurityEstimations();
}
