package com.example.codingtest.service;

import com.example.codingtest.domain.Problem;
import com.example.codingtest.domain.Query;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProblemServiceTest
{
    private final Query Q = new Query();

    @Test
    void crawlingProblem()
    {
        List<String> tiers = new ArrayList<>();
        tiers.add("s3");
        tiers.add("s1..g5");

        List<String> tags = new ArrayList<>();
        tags.add("bfs");
        tags.add("dfs");

        Q.setHandles(Arrays.asList("engus525", "cobinding"));

        for (int i = 0; i < tiers.size(); i++)
        {
            Q.setTier(tiers.get(i));
            Q.setTag(tags.get(i));
            StringBuilder query = Q.getQuery();

            System.out.println("query = " + query);
        }
    }

    @Test
    void docTest() throws IOException
    {

        String url = "https://solved.ac/search?query=" + "*p1%26s%40engus525" + "&sort=solved&direction=desc&page=1";
        System.out.println(url);
        Document doc = Jsoup.connect(url).get();
        Elements numbers = doc.select(".css-1raije9");
        Elements names = doc.select(".__Latex__");
        Elements tiers = doc.select(".css-1vnxcg0");

        for (int i = 0; i < numbers.size(); i++)
        {
            Problem problem = new Problem();
            problem.createProblem(
                    Integer.valueOf(numbers.get(i).text()),
                    names.get(i).text(),
                    tiers.get(i).attr("alt"),
                    "bfs",
                    "https://www.acmicpc.net/problem/"+numbers.get(i).text());
            System.out.println(problem);
        }
    }
}