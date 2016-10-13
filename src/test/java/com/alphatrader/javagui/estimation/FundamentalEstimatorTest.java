package com.alphatrader.javagui.estimation;

import com.alphatrader.javagui.data.Company;
import com.alphatrader.javagui.data.Portfolio;
import org.json.JSONObject;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Test case for the {@link Estimator} class.
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class FundamentalEstimatorTest {
    private static final JSONObject jsonPortfolio = new JSONObject("{\n" +
        "  \"committedCash\": 0,\n" +
        "  \"cash\": 50000,\n" +
        "  \"positions\": [\n" +
        "    {\n" +
        "      \"currentAskPrice\": null,\n" +
        "      \"currentAskSize\": null,\n" +
        "      \"currentBidPrice\": null,\n" +
        "      \"currentBidSize\": null,\n" +
        "      \"lastPrice\": {\n" +
        "        \"date\": 1476192276474,\n" +
        "        \"value\": 1\n" +
        "      },\n" +
        "      \"numberOfShares\": 50000,\n" +
        "      \"volume\": 50000,\n" +
        "      \"committedShares\": 0,\n" +
        "      \"securityIdentifier\": \"STS8F1AF\",\n" +
        "      \"listing\": {\n" +
        "        \"startDate\": 1476192276469,\n" +
        "        \"endDate\": null,\n" +
        "        \"securityIdentifier\": \"STS8F1AF\",\n" +
        "        \"name\": \"Sunni Islam AG\",\n" +
        "        \"type\": \"STOCK\"\n" +
        "      },\n" +
        "      \"type\": \"STOCK\"\n" +
        "    }\n" +
        "  ]\n" +
        "}");

    private final Estimator toTest = Estimator.get(Estimator.EstimatorType.FUNDAMENTAL);

    @Test
    public void testEvaluate() throws Exception {
        Portfolio testPortfolio = Portfolio.createFromJson(jsonPortfolio);
        Company testCompany = new Company(
            "43986f13-edde-486c-9ef0-718b100a1949",
            "Sunni Islam AG",
            "STS8F1AF",
            "ffe1ce85-7de9-4de8-88d6-4499a83bcfcf",
            50000,
            50000
        );
        testCompany.setPortfolio(testPortfolio);
        List<Company> companyList = Collections.singletonList(testCompany);

        toTest.refresh(companyList);

        System.out.println(toTest.evaluate(testCompany));
    }
}