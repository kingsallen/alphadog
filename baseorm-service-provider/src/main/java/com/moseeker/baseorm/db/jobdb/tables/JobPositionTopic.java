/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.jobdb.tables;


import com.moseeker.baseorm.db.jobdb.Jobdb;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionTopicRecord;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;


/**
 * 职位主题活动关系表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPositionTopic extends TableImpl<JobPositionTopicRecord> {

    private static final long serialVersionUID = -1363302021;

    /**
     * The reference instance of <code>jobdb.job_position_topic</code>
     */
    public static final JobPositionTopic JOB_POSITION_TOPIC = new JobPositionTopic();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JobPositionTopicRecord> getRecordType() {
        return JobPositionTopicRecord.class;
    }

    /**
     * The column <code>jobdb.job_position_topic.position_id</code>. hr_position.id, 职位ID
     */
    public final TableField<JobPositionTopicRecord, UInteger> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "hr_position.id, 职位ID");

    /**
     * The column <code>jobdb.job_position_topic.topic_id</code>. hr_topic.id, 主题ID
     */
    public final TableField<JobPositionTopicRecord, UInteger> TOPIC_ID = createField("topic_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "hr_topic.id, 主题ID");

    /**
     * Create a <code>jobdb.job_position_topic</code> table reference
     */
    public JobPositionTopic() {
        this("job_position_topic", null);
    }

    /**
     * Create an aliased <code>jobdb.job_position_topic</code> table reference
     */
    public JobPositionTopic(String alias) {
        this(alias, JOB_POSITION_TOPIC);
    }

    private JobPositionTopic(String alias, Table<JobPositionTopicRecord> aliased) {
        this(alias, aliased, null);
    }

    private JobPositionTopic(String alias, Table<JobPositionTopicRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "职位主题活动关系表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Jobdb.JOBDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionTopic as(String alias) {
        return new JobPositionTopic(alias, this);
    }

    /**
     * Rename this table
     */
    public JobPositionTopic rename(String name) {
        return new JobPositionTopic(name, null);
    }
}
