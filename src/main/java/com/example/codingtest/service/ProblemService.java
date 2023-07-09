package com.example.codingtest.service;

import com.example.codingtest.domain.Problem;
import com.example.codingtest.domain.Query;
import com.example.codingtest.domain.Tags;
import com.example.codingtest.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemService
{
    private final ProblemRepository problemRepository;

    public List<Problem> crawlingProblem(Query Q, String difficulty) throws IOException
    {
        List<Problem> problems = new ArrayList<>();

        List<String> tiers = new ArrayList<>();
        setTiers(difficulty, tiers);

        List<String> tags = setTags(tiers);

        problemRepository.deleteAll();
        for (int i = 0; i < tiers.size(); i++)
        {
            Q.setTier(tiers.get(i));
            Q.setTag(tags.get(i));
            StringBuilder query = Q.getQuery();

            String url = "https://solved.ac/search?query=" + query + "&direction=desc&page=1&sort=solved";
            log.info(url);
            Document doc = Jsoup.connect(url).get();
            Elements numberElements = doc.select(".css-1raije9");
            Elements nameElements = doc.select(".__Latex__");
            Elements tierElements = doc.select(".css-1vnxcg0");

            //todo 한 문제도 없을 때 로직 추가 (아래는 임의 로직)
            if(numberElements.size() == 0) continue;
            if(numberElements.size() <= i) continue;

            Problem problem = new Problem();
            for (int j = 0; j < nameElements.size(); j++)
            {
                if(!isEnglishProblem(nameElements.get(j).text())) continue;

                problem.createProblem(
                        Integer.valueOf(numberElements.get(j).text()),
                        nameElements.get(j).text(),
                        tierElements.get(j).attr("alt"),
                        tags.get(i),
                        "https://www.acmicpc.net/problem/"+numberElements.get(j).text());
                break;
            }

            if(problem.getNumber() == null) continue;
            problemRepository.save(problem);
            problems.add(problem);
        }

        return problems;
    }

    private boolean isEnglishProblem(String text)
    {
        return text.matches(".*[가-힣]+.*");
    }

    private static List<String> setTags(List<String> tiers)
    {
        Set<String> temp = new HashSet<>();

        Tags[] tagValues = Tags.values();
        Random random = new Random();

        while (temp.size() < tiers.size())
        {
            int idx = random.nextInt(tagValues.length);
            temp.add(tagValues[idx].name());
        }

        return new ArrayList<>(temp);
    }

    private static void setTiers(String difficulty, List<String> tiers)
    {
        switch (difficulty)
        {
            case "상" ->
            {
                tiers.add("s2..s1");
                tiers.add("g5..g4");
                tiers.add("g3..g2");
                tiers.add("g1..p5");
            }
            case "중" ->
            {
                tiers.add("s4..s3");
                tiers.add("s2..s1");
                tiers.add("g5..g4");
                tiers.add("g4..g3");
            }
            case "하" ->
            {
                tiers.add("b1..s5");
                tiers.add("s4..s3");
                tiers.add("s2..s1");
                tiers.add("s1..g5");
            }
        }
    }

    public List<Problem> findAll()
    {
        return problemRepository.findAll();
    }
}
