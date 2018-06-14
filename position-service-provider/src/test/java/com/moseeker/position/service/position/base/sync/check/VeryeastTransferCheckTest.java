package com.moseeker.position.service.position.base.sync.check;

import com.moseeker.position.service.position.veryeast.pojo.PositionVeryEastForm;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.junit.Test;

import static org.junit.Assert.*;

public class VeryeastTransferCheckTest {
    @Test
    public void getError() throws Exception {
        VeryeastTransferCheck check = new VeryeastTransferCheck();
        PositionVeryEastForm positionVeryEastForm = new PositionVeryEastForm();

        positionVeryEastForm.setSalaryBottom(0);
        positionVeryEastForm.setSalaryTop(0);
        System.out.println(check.getError(positionVeryEastForm,null));

        positionVeryEastForm.setSalaryBottom(2);
        positionVeryEastForm.setSalaryTop(1);
        System.out.println(check.getError(positionVeryEastForm,null));


    }

}