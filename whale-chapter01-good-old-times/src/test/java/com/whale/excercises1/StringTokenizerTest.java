package com.whale.excercises1;

import org.junit.Assert;
import org.junit.Test;

public class StringTokenizerTest {

    @Test
    public void testTokenize() {
        String[] expected = {"Test", "token", "space"};

        String token = "Test token  space";
        StringTokenizer stringTokenizer = new StringTokenizer(token);

        Assert.assertEquals("", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("", expected[0], stringTokenizer.nextToken());
        Assert.assertEquals("", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("", expected[1], stringTokenizer.nextToken());
        Assert.assertEquals("", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("", expected[2], stringTokenizer.nextToken());
        Assert.assertEquals("", false, stringTokenizer.hasMoreTokens());
    }

    @Test
    public void testSpecialCharTokenize() {
        String testToken = "return Mr. Bennet's visit, and determining when they should ask him to";

        String[] expected = {"return", "Mr", "Bennet", "s","visit",
                            "and", "determining", "when", "they",
                            "should", "ask", "him", "to"};

        StringTokenizer stringTokenizer = new StringTokenizer(testToken);

        Assert.assertEquals("true ", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("return ", expected[0], stringTokenizer.nextToken());
        Assert.assertEquals("true ", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("Mr ", expected[1], stringTokenizer.nextToken());
        Assert.assertEquals("true ", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("Bennet ", expected[2], stringTokenizer.nextToken());
        Assert.assertEquals("true ", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("s ", expected[3], stringTokenizer.nextToken());
        Assert.assertEquals("true ", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("visit ", expected[4], stringTokenizer.nextToken());
        Assert.assertEquals("true ", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("and ", expected[5], stringTokenizer.nextToken());
        Assert.assertEquals("true ", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("determining ", expected[6], stringTokenizer.nextToken());
        Assert.assertEquals("true ", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("when ", expected[7], stringTokenizer.nextToken());
        Assert.assertEquals("true ", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("they ", expected[8], stringTokenizer.nextToken());
        Assert.assertEquals("true ", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("should ", expected[9], stringTokenizer.nextToken());
        Assert.assertEquals("true ", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("ask ", expected[10], stringTokenizer.nextToken());
        Assert.assertEquals("true ", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("him ", expected[11], stringTokenizer.nextToken());
        Assert.assertEquals("true ", true, stringTokenizer.hasMoreTokens());
        Assert.assertEquals("to ", expected[12], stringTokenizer.nextToken());
        Assert.assertEquals("empty token ", false, stringTokenizer.hasMoreTokens());
    }
}
