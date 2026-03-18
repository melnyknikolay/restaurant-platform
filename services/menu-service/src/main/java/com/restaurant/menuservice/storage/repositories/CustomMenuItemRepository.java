package com.restaurant.menuservice.storage.repositories;

import com.restaurant.menuservice.dto.SortBy;
import com.restaurant.menuservice.dto.UpdateMenuRequest;
import com.restaurant.menuservice.storage.model.Category;
import com.restaurant.menuservice.storage.model.MenuItem;

import java.util.List;

public interface CustomMenuItemRepository {

    int updateMenu(Long id, UpdateMenuRequest dto);

    List<MenuItem> getMenusFor(Category category, SortBy sortBy);
}
