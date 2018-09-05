/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.routines;


import com.moseeker.baseorm.db.jobdb.Jobdb;

import javax.annotation.Generated;

import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProFeatureFix extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -203082192;

    /**
     * The parameter <code>jobdb.pro_feature_fix.c_id</code>.
     */
    public static final Parameter<Integer> C_ID = createParameter("c_id", org.jooq.impl.SQLDataType.INTEGER, false, false);

    /**
     * Create a new routine call instance
     */
    public ProFeatureFix() {
        super("pro_feature_fix", Jobdb.JOBDB);

        addInParameter(C_ID);
    }

    /**
     * Set the <code>c_id</code> parameter IN value to the routine
     */
    public void setCId(Integer value) {
        setValue(C_ID, value);
    }
}