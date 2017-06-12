package util;

import com.moseeker.baseorm.db.candidatedb.tables.CandidateRecomRecord;
import org.jooq.Field;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

/**
 * BaseDaoImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Feb 16, 2017</pre>
 */
public class BaseDaoImplTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    //@Test
    public void testTableField() {
        CandidateRecomRecord recomRecord = new CandidateRecomRecord();
        Field<Timestamp> field = (Field<Timestamp>) recomRecord.field("createTime");
        System.out.println(field);

        Field<Timestamp> field1 = (Field<Timestamp>) recomRecord.field("create_time");
        System.out.println(field1);
    }
} 
