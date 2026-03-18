
package com.restaurant.controller;

import com.restaurant.dto.RankedItem;
import com.restaurant.service.HybridSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final HybridSearchService service;

    @GetMapping
    public List<RankedItem> search(@RequestParam String q) {
        return service.search(q);
    }
}
