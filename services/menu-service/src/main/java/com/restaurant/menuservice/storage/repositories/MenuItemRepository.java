package com.restaurant.menuservice.storage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.restaurant.menuservice.storage.model.MenuItem;
import com.restaurant.menuservice.storage.model.MenuItemProjection;

import java.util.List;
import java.util.Set;


public interface MenuItemRepository extends JpaRepository<MenuItem, Long>, CustomMenuItemRepository {
    @Query("""
            select new com.restaurant.menuservice.storage.model.MenuItemProjection(
                m.name,
                m.price
            ) from MenuItem m where m.name in :names
            """)
    List<MenuItemProjection> getMenuInfoForNames(@Param("names") Set<String> names);
}
