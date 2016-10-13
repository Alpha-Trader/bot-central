package com.alphatrader.javagui.estimation;

import com.alphatrader.javagui.data.Company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fundamental estimator that evaluates the overall value of a company.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
class FundamentalEstimator extends Estimator {
    Map<String, Double> securityEstimation = new HashMap<>();

    @Override
    public void refresh(List<Company> companies) {
        securityEstimation = new HashMap<>();
        companies.forEach(company -> securityEstimation.put(company.getSecurityIdentifier(), Double.NaN));
    }

    @Override
    public Double evaluate(Company company) {
        // If the company we are looking for doesn't exist in the map, we need to refresh all.
        if(!securityEstimation.containsKey(company.getSecurityIdentifier())) {
            refresh(Company.getAllCompanies());
        }

        

        return 0.0;
    }
}
