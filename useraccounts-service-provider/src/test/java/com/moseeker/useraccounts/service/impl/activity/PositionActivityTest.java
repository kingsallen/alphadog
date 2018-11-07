package com.moseeker.useraccounts.service.impl.activity;

import com.moseeker.baseorm.db.hrdb.tables.records.HrHbPositionBindingRecord;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @Author: jack
 * @Date: 2018/11/7
 */
public class PositionActivityTest {

    @Test
    public void testCheckIfChangePosition() {
        java.util.List<Integer> positionIds = new ArrayList<Integer>(){{add(19500855);}};
        List<HrHbPositionBindingRecord > bindingRecords = new ArrayList<HrHbPositionBindingRecord>(){{
            HrHbPositionBindingRecord record1 = new HrHbPositionBindingRecord();
            record1.setId(2402);
            record1.setPositionId(19500855);
            add(record1);

            HrHbPositionBindingRecord record2 = new HrHbPositionBindingRecord();
            record1.setId(2403);
            record1.setPositionId(19500856);
            add(record2);
        }};

        boolean result;

        if (positionIds.size() == bindingRecords.size()) {
            Optional<Integer> optional = positionIds.stream().filter(integer -> {
                Optional<HrHbPositionBindingRecord> optional1 = bindingRecords
                        .stream()
                        .filter(hrHbPositionBindingRecord -> hrHbPositionBindingRecord.getPositionId().equals(integer))
                        .findAny();
                return !optional1.isPresent();
            }).findAny();

            result = optional.isPresent();

        } else {
            result = true;
        }

        assertEquals(true, result);
    }

}