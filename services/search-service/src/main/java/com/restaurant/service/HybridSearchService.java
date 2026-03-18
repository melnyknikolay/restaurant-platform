
package com.restaurant.service;

import com.restaurant.dto.RankedItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HybridSearchService {

    private final ElasticsearchService elastic;
    private final VectorSearchService vector;
    private final RankingService ranking;

    public List<RankedItem> search(String query) {
        var keyword = elastic.search(query);
        var vectorResults = vector.search(query);
        return ranking.merge(keyword, vectorResults);
    }
}
