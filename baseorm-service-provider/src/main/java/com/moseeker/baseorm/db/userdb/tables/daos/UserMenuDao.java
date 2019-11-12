/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.daos;


import com.moseeker.baseorm.db.userdb.tables.UserMenu;
import com.moseeker.baseorm.db.userdb.tables.records.UserMenuRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 菜单
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserMenuDao extends DAOImpl<UserMenuRecord, com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu, Integer> {

    /**
     * Create a new UserMenuDao without any configuration
     */
    public UserMenuDao() {
        super(UserMenu.USER_MENU, com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu.class);
    }

    /**
     * Create a new UserMenuDao with an attached configuration
     */
    public UserMenuDao(Configuration configuration) {
        super(UserMenu.USER_MENU, com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu> fetchById(Integer... values) {
        return fetch(UserMenu.USER_MENU.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu fetchOneById(Integer value) {
        return fetchOne(UserMenu.USER_MENU.ID, value);
    }

    /**
     * Fetch records that have <code>menu_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu> fetchByMenuName(String... values) {
        return fetch(UserMenu.USER_MENU.MENU_NAME, values);
    }

    /**
     * Fetch records that have <code>menu_desc IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu> fetchByMenuDesc(String... values) {
        return fetch(UserMenu.USER_MENU.MENU_DESC, values);
    }

    /**
     * Fetch records that have <code>ico_url IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu> fetchByIcoUrl(String... values) {
        return fetch(UserMenu.USER_MENU.ICO_URL, values);
    }

    /**
     * Fetch records that have <code>menu_order IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu> fetchByMenuOrder(Byte... values) {
        return fetch(UserMenu.USER_MENU.MENU_ORDER, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu> fetchByCreateTime(Timestamp... values) {
        return fetch(UserMenu.USER_MENU.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>creator IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu> fetchByCreator(String... values) {
        return fetch(UserMenu.USER_MENU.CREATOR, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu> fetchByUpdateTime(Timestamp... values) {
        return fetch(UserMenu.USER_MENU.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>updator IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserMenu> fetchByUpdator(String... values) {
        return fetch(UserMenu.USER_MENU.UPDATOR, values);
    }
}
