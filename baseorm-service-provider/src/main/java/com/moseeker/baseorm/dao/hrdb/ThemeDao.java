package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.daos.HrHbThemeDao;
import org.jooq.Configuration;
import org.jooq.Param;
import org.springframework.beans.factory.annotation.Autowired;

import static com.moseeker.baseorm.db.hrdb.tables.HrHbTheme.HR_HB_THEME;
import static org.jooq.impl.DSL.*;

/**
 * @ClassName ThemeDao
 * @Description TODO
 * @Author jack
 * @Date 2019/1/23 2:11 PM
 * @Version 1.0
 */
public class ThemeDao extends HrHbThemeDao {

    @Autowired
    public ThemeDao(Configuration configuration) {
        super(configuration);
    }

    public void upsert(Integer id, Integer theme) {
        Param<Integer> configIdParam = param(HR_HB_THEME.CONFIG_ID.getName(), id);
        Param<Integer> themeParam = param(HR_HB_THEME.THEME_ID.getName(), theme);

        int execute = using(configuration())
                .insertInto(HR_HB_THEME)
                .columns(
                        HR_HB_THEME.CONFIG_ID,
                        HR_HB_THEME.THEME_ID
                )
                .select(
                        select(
                                configIdParam,
                                themeParam
                        )
                        .whereNotExists(
                                selectOne()
                                .from(HR_HB_THEME)
                                .where(HR_HB_THEME.CONFIG_ID.eq(id))
                        )
                )
                .execute();
        if (execute == 0) {
            using(configuration())
                    .update(HR_HB_THEME)
                    .set(HR_HB_THEME.THEME_ID, theme)
                    .where(HR_HB_THEME.CONFIG_ID.eq(id))
                    .execute();
        }
    }
}
