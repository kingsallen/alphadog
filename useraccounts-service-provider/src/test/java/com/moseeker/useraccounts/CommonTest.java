package com.moseeker.useraccounts;


import com.moseeker.thrift.gen.referral.struct.ReferralCardInfo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonTest {

    static Logger logger = LoggerFactory.getLogger(CommonTest.class);

    @Test
    public void test00(){
        ReferralCardInfo cardInfo = new ReferralCardInfo();
        logger.info("属性名>>>>>>>>>>>>>> {}", cardInfo.fieldForId(1).toString());
    }

}
