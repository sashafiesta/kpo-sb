package com.sashafiesta.antiplagiarism.analysisservice.controller;

import com.sashafiesta.antiplagiarism.analysisservice.dto.AnalysisRequest;
import com.sashafiesta.antiplagiarism.analysisservice.service.AnalysisLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internal/analysis")
@RequiredArgsConstructor
public class InternalAnalysisController {

    private final AnalysisLogicService analysisLogicService;

    @PostMapping
    public void startAnalysis(@RequestBody AnalysisRequest request) {
        analysisLogicService.performAnalysis(request);
    }
}
