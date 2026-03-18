
package com.restaurant.service;

import com.restaurant.dto.RankedItem;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RankingService {

    private static final double KEYWORD_WEIGHT = 0.7;
    private static final double VECTOR_WEIGHT = 0.3;
    private static final String ITEM_KEY = "item";
    private static final String SCORE_KEY = "score";

    public List<RankedItem> merge(List<Object> keyword, List<String> vector) {

        Map<String, Double> scores = new HashMap<>();

        keyword.forEach(k -> scores.put(k.toString(), KEYWORD_WEIGHT));
        vector.forEach(v -> scores.merge(v, VECTOR_WEIGHT, Double::sum));

        return toRankedItems(scores);
    }

    private List<RankedItem> toRankedItems(Map<String, Double> scores) {
        return scores.entrySet().stream()
                .sorted(Comparator.comparingDouble(Map.Entry<String, Double>::getValue).reversed())
                .map(entry -> new RankedItem(entry.getKey(), entry.getValue()))
                .toList();
    }
}
