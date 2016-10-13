package com.alphatrader.javagui.data;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test case for the {@link Portfolio} class.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class PortfolioTest {
    private static final JSONObject JSON = new JSONObject("{\n" +
        "  \"committedCash\": 0,\n" +
        "  \"cash\": 400,\n" +
        "  \"positions\": [\n" +
        "    {\n" +
        "      \"currentAskPrice\": 3500,\n" +
        "      \"currentAskSize\": 850,\n" +
        "      \"currentBidPrice\": null,\n" +
        "      \"currentBidSize\": null,\n" +
        "      \"lastPrice\": {\n" +
        "        \"date\": 1475882777051,\n" +
        "        \"value\": 10\n" +
        "      },\n" +
        "      \"numberOfShares\": 100,\n" +
        "      \"volume\": 1000,\n" +
        "      \"committedShares\": 0,\n" +
        "      \"securityIdentifier\": \"ST57FA01\",\n" +
        "      \"listing\": {\n" +
        "        \"startDate\": 1469951384818,\n" +
        "        \"endDate\": null,\n" +
        "        \"securityIdentifier\": \"ST57FA01\",\n" +
        "        \"name\": \"Argo\",\n" +
        "        \"type\": \"STOCK\"\n" +
        "      },\n" +
        "      \"type\": \"STOCK\"\n" +
        "    }\n" +
        "  ]\n" +
        "}");

    private Portfolio toTest;

    @Before
    public void setUp() throws Exception {
        toTest = Portfolio.createFromJson(JSON);
    }

    @Test
    public void testCreateFromJson() throws Exception {
        assertNotNull(toTest);
    }

    @Test
    public void testGetEstimatedValue() throws Exception {
        assertEquals(1400, toTest.getEstimatedValue(), 0.0001);
    }
}