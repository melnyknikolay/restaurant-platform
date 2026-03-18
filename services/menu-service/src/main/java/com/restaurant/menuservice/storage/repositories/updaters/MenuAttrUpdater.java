package com.restaurant.menuservice.storage.repositories.updaters;

import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.AllArgsConstructor;
import com.restaurant.menuservice.dto.UpdateMenuRequest;
import com.restaurant.menuservice.storage.model.MenuItem;

import java.util.function.Function;

@AllArgsConstructor
public class MenuAttrUpdater<V> {
    SingularAttribute<MenuItem, V> attr;
    Function<UpdateMenuRequest, V> dtoValueExtractor;

    public void updateAttr(CriteriaUpdate<MenuItem> criteria, UpdateMenuRequest dto) {
        V dtoValue = dtoValueExtractor.apply(dto);
        if (dtoValue != null) {
            criteria.set(attr, dtoValue);
        }
    }
}
