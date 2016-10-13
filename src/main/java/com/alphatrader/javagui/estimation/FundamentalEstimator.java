package com.alphatrader.javagui.estimation;

import com.alphatrader.javagui.data.Company;
import com.alphatrader.javagui.data.Position;

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
    private static final int ITERATIONS = 10;

    private Map<String, Double> securityEstimation = new HashMap<>();

    @Override
    public void refresh(List<Company> companies) {
        this.companies = companies;
        securityEstimation = new HashMap<>();
        this.companies.forEach(company -> {
            Double value = company.getPortfolio().getPositions().stream().map(Position::getVolume).reduce(0.0, (a, b) -> (a + b));
            value += company.getCash();
            securityEstimation.put(company.getSecurityIdentifier(), value / company.getOutstandingShares());
        });

        recursiveEvaluation();
    }

    @Override
    public Double evaluate(Company company) {
        Double myReturn = null;

        // If the company we are looking for doesn't exist in the map, we return Double.NaN.
        if (!securityEstimation.containsKey(company.getSecurityIdentifier())) {
            myReturn = Double.NaN;
        } else {
            myReturn = securityEstimation.get(company.getSecurityIdentifier());
        }

        return myReturn;
    }

    private void recursiveEvaluation() {
        for (int i = 0; i < ITERATIONS; i++) {
            companies.forEach(company -> {
                Double value = company
                    .getPortfolio()
                    .getPositions()
                    .stream()
                    .map(
                        position -> {
                            if (securityEstimation.containsKey(position.getSecurityIdentifier())) {
                                return securityEstimation.get(position.getSecurityIdentifier()) * position.getNumberOfShares();
                            } else {
                                return 0.0;
                            }
                        }
                    )
                    .reduce(0.0, (a, b) -> (a + b));

                value /= (double) company.getOutstandingShares();
                securityEstimation.put(company.getSecurityIdentifier(), value);
            });
        }
    }
}
