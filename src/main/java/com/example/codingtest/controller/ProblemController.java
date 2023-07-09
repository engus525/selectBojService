package com.example.codingtest.controller;

import com.example.codingtest.domain.Problem;
import com.example.codingtest.domain.Query;
import com.example.codingtest.dto.QueryDto;
import com.example.codingtest.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProblemController
{
    private final ProblemService problemService;

    @GetMapping("/")
    public String setOptionPage(Model model)
    {
        model.addAttribute("queryForm",new QueryDto());
        return "set-option";
    }

    @PostMapping("/result")
    public String setOption(QueryDto queryDto, Model model) throws IOException
    {
        Query query = new Query();
        query.setHandles(queryDto.getHandles());
        problemService.crawlingProblem(query, queryDto.getDifficulty());

        List<Problem> problems = problemService.findAll();
        model.addAttribute("problems",problems);
        return "result";
    }

}
