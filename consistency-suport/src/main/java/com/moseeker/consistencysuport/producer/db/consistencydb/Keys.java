/*
 * This file is generated by jOOQ.
*/
package com.moseeker.consistencysuport.producer.db.consistencydb;


import com.moseeker.consistencysuport.producer.db.consistencydb.tables.ConsistencyBusiness;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.ConsistencyBusinessType;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.ConsistencyMessage;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.ConsistencyMessageType;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.records.ConsistencyBusinessRecord;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.records.ConsistencyBusinessTypeRecord;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.records.ConsistencyMessageRecord;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.records.ConsistencyMessageTypeRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>consistencydb</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<ConsistencyBusinessRecord, Integer> IDENTITY_CONSISTENCY_BUSINESS = Identities0.IDENTITY_CONSISTENCY_BUSINESS;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ConsistencyBusinessRecord> KEY_CONSISTENCY_BUSINESS_PRIMARY = UniqueKeys0.KEY_CONSISTENCY_BUSINESS_PRIMARY;
    public static final UniqueKey<ConsistencyBusinessRecord> KEY_CONSISTENCY_BUSINESS_CONSISTENCY_BUSINESS_NAME = UniqueKeys0.KEY_CONSISTENCY_BUSINESS_CONSISTENCY_BUSINESS_NAME;
    public static final UniqueKey<ConsistencyBusinessTypeRecord> KEY_CONSISTENCY_BUSINESS_TYPE_PRIMARY = UniqueKeys0.KEY_CONSISTENCY_BUSINESS_TYPE_PRIMARY;
    public static final UniqueKey<ConsistencyMessageRecord> KEY_CONSISTENCY_MESSAGE_PRIMARY = UniqueKeys0.KEY_CONSISTENCY_MESSAGE_PRIMARY;
    public static final UniqueKey<ConsistencyMessageTypeRecord> KEY_CONSISTENCY_MESSAGE_TYPE_PRIMARY = UniqueKeys0.KEY_CONSISTENCY_MESSAGE_TYPE_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<ConsistencyBusinessRecord, Integer> IDENTITY_CONSISTENCY_BUSINESS = createIdentity(ConsistencyBusiness.CONSISTENCY_BUSINESS, ConsistencyBusiness.CONSISTENCY_BUSINESS.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<ConsistencyBusinessRecord> KEY_CONSISTENCY_BUSINESS_PRIMARY = createUniqueKey(ConsistencyBusiness.CONSISTENCY_BUSINESS, "KEY_consistency_business_PRIMARY", ConsistencyBusiness.CONSISTENCY_BUSINESS.ID);
        public static final UniqueKey<ConsistencyBusinessRecord> KEY_CONSISTENCY_BUSINESS_CONSISTENCY_BUSINESS_NAME = createUniqueKey(ConsistencyBusiness.CONSISTENCY_BUSINESS, "KEY_consistency_business_consistency_business_name", ConsistencyBusiness.CONSISTENCY_BUSINESS.MESSAGE_ID, ConsistencyBusiness.CONSISTENCY_BUSINESS.NAME);
        public static final UniqueKey<ConsistencyBusinessTypeRecord> KEY_CONSISTENCY_BUSINESS_TYPE_PRIMARY = createUniqueKey(ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE, "KEY_consistency_business_type_PRIMARY", ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.NAME, ConsistencyBusinessType.CONSISTENCY_BUSINESS_TYPE.MESSAGE_NAME);
        public static final UniqueKey<ConsistencyMessageRecord> KEY_CONSISTENCY_MESSAGE_PRIMARY = createUniqueKey(ConsistencyMessage.CONSISTENCY_MESSAGE, "KEY_consistency_message_PRIMARY", ConsistencyMessage.CONSISTENCY_MESSAGE.MESSAGE_ID);
        public static final UniqueKey<ConsistencyMessageTypeRecord> KEY_CONSISTENCY_MESSAGE_TYPE_PRIMARY = createUniqueKey(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE, "KEY_consistency_message_type_PRIMARY", ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.NAME);
    }
}