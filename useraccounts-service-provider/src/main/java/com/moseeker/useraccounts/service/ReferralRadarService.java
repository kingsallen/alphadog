package com.moseeker.useraccounts.service;

import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.referral.struct.CheckEmployeeInfo;
import com.moseeker.thrift.gen.referral.struct.ConnectRadarInfo;
import com.moseeker.thrift.gen.referral.struct.ReferralCardInfo;
import com.moseeker.thrift.gen.referral.struct.ReferralInviteInfo;
import org.apache.thrift.TException;

/**
 * 人脉雷达service
 *
 * @author cjm
 * @date 2018-12-19 10:20
 **/
public interface ReferralRadarService {

    String getRadarCards(ReferralCardInfo cardInfo) throws BIZException, TException;

    String inviteApplication(ReferralInviteInfo inviteInfo) throws BIZException, TException;

    void ignoreCurrentViewer(ReferralInviteInfo ignoreInfo) throws BIZException, TException;

    String connectRadar(ConnectRadarInfo radarInfo) throws BIZException, TException;

    String checkEmployee(CheckEmployeeInfo checkInfo) throws BIZException, TException;
}
