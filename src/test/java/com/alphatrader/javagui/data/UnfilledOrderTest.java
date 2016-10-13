package com.alphatrader.javagui.data;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;

/**
 * Testcase for the {@link UnfilledOrder} class.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class UnfilledOrderTest {
    private static final JSONObject JSON = new JSONObject("{\n" +
        "    \"counterPartyName\": null,\n" +
        "    \"creationDate\": 1475619375635,\n" +
        "    \"committedCash\": 787.18,\n" +
        "    \"numberOfShares\": 39359,\n" +
        "    \"action\": \"BUY\",\n" +
        "    \"securityIdentifier\": \"STG6CFBB\",\n" +
        "    \"listing\": {\n" +
        "      \"startDate\": 1474099164997,\n" +
        "      \"endDate\": null,\n" +
        "      \"securityIdentifier\": \"STG6CFBB\",\n" +
        "      \"name\": \"gierigundstolz Inc.\",\n" +
        "      \"type\": \"STOCK\"\n" +
        "    },\n" +
        "    \"counterParty\": null,\n" +
        "    \"price\": 0.02,\n" +
        "    \"ownerName\": \"Church of Scientology AG\",\n" +
        "    \"owner\": \"26ea411d-f873-4976-80c4-09e3f1cdabea\",\n" +
        "    \"id\": \"a7859ce3-75d0-44fd-98ab-bfc77aa205d5\",\n" +
        "    \"type\": \"LIMIT\"\n" +
        "  }");

    private UnfilledOrder toTest;

    @Before
    public void setUp() {
        toTest = UnfilledOrder.createFromJson(JSON);
    }

    @Test
    public void testToString() throws Exception {
        fail("Not implemented!");
    }

    @Test
    public void testGetVolume() throws Exception {
        fail("Volume not part of the json response, please fix the code!");
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("gierigundstolz Inc.", toTest.getName());
    }

    @Test
    public void testGetCreationDate() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(1475619375635L), ZoneId.systemDefault());
        assertEquals(localDateTime, toTest.getCreationDate());
    }

    @Test
    public void testGetType() throws Exception {
        // TODO: clear up types (Stock <-> Limit)
        assertEquals("LIMIT", toTest.getType());
    }

    @Test
    public void testGetSecurityIdentifier() throws Exception {
        assertEquals("STG6CFBB", toTest.getSecurityIdentifier());
    }

    @Test
    public void testGetNumberOfShares() throws Exception {
        assertEquals(39359, toTest.getNumberOfShares());
    }

}