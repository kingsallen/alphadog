package com.moseeker.position.service.position.base.sync.check;

import com.moseeker.position.service.position.zhilian.pojo.PositionZhilianForm;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyZhilianPositionAddressDO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ZhilianTransferCheckTest {

    @Test
    public void getError(){
        ZhilianTransferCheck zhilianTransferCheck = new ZhilianTransferCheck();

        PositionZhilianForm positionZhilianForm = new PositionZhilianForm();
        JobPositionDO moseekerPosition = new JobPositionDO();

        List<ThirdpartyZhilianPositionAddressDO> address = new ArrayList<>();

        ThirdpartyZhilianPositionAddressDO temp = new ThirdpartyZhilianPositionAddressDO();
        temp.setCityCode(1);
        temp.setAddress("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        address.add(temp);

        positionZhilianForm.setAddress(address);

        System.out.println(zhilianTransferCheck.getError(positionZhilianForm,moseekerPosition));
    }

}