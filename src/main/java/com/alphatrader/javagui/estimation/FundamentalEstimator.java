package com.alphatrader.javagui.estimation;

import com.alphatrader.javagui.data.Company;

import java.util.List;

/**
 * Fundamental estimator that evaluates the overall value of a company.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
class FundamentalEstimator extends Estimator {
    @Override
    public void refresh(List<Company> companies) {

    }

    @Override
    public Double evaluate(Company company) {
        return 0.0;
    }
}
