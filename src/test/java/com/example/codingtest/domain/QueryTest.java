package com.example.codingtest.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest
{
    @Test
    public void getQuery() throws Exception
    {
        //given
        Query Q = new Query();
        Q.setTier("s5..s4");
        Q.setTag("dfs");
        Q.setHandles(Arrays.asList("engus525", "cobinding"));

        //when
        StringBuilder query = Q.getQuery();

        //then
        System.out.println("query = " + query);
    }
}