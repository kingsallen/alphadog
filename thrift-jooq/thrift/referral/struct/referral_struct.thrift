namespace java com.moseeker.thrift.gen.referral.struct

struct RedPacket {
    1 : optional i32 type,
    2 : optional string name,
    3 : optional bool open,
    4 : optional double value,
    5 : optional string positionTitle,
    6 : optional string candidateName,
    7 : optional i64 openTime,
    8 : optional string cardno,
    9 : optional i32 id,
}

struct RedPackets {
    1 : optional double totalRedpackets,
    2 : optional double totalBonus,
    3 : optional list<RedPacket> redpackets,
} 

struct Bonus {
    1 : optional i32 id,
    2 : optional string name,
    3 : optional double value, 
    4 : optional string positionTitle,
    5 : optional string candidateName,
    6 : optional i64 employmentDate,
    7 : optional bool open,
    8 : optional i32 type,
    9 : optional bool cancel,
}

struct BonusList {
    1 : optional double totalRedpackets,
    2 : optional double totalBonus,
    3 : optional list<Bonus> bonus,
}

struct ReferralProfileTab{
    1: optional string positionTitle,
    2: optional string sender,
    3: optional string uploadTime,
    4: optional string filePath,
    5: optional string name,
    6: optional i32 claim,
    7: optional i32 id,
}

struct ActivityDTO {
    1 : optional i32 id,
    2 : optional list<double> amounts,
    3 : optional i32 target,
    4 : optional string startTime,
    5 : optional string endTime,
    6 : optional double totalAmount,
    7 : optional double rangeMin,
    8 : optional double rangeMax,
    9 : optional i32 probability,
    10: optional i32 dType,
    11: optional string headline,
    12: optional string headlineFailure,
    13: optional string shareTitle,
    14: optional string shareDesc,
    15: optional string shareImg,
    16: optional i32 status,
    17: optional i32 checked,
    18: optional i32 estimatedTotal,
    19: optional i32 actualTotal,
    20: optional list<i32> positionIds,
}

struct ReferralReasonInfo{
    1: optional i32 id,
    2: optional list<string> referralReasons,
    3: optional i32 relationship,
    4: optional string recomReasonText
}

struct ContactPushInfo{
    1: optional i32 userId;
    2: optional string username;
    3: optional i32 positionId;
    4: optional string positionName
}

struct ReferralCardInfo{
    1: optional i32 user_id,
    2: optional i32 company_id,
    3: optional i32 page_number,
    4: optional i32 page_size,
    5: optional i64 timestamp
}

struct ReferralInviteInfo{
    1: optional i32 pid,
    2: optional i32 userId,
    3: optional i32 endUserId,
    4: optional i32 companyId,
    5: optional i64 timestamp
}

struct ConnectRadarInfo{
    1: optional i32 chainId,
    2: optional i32 recomUserId,
    3: optional i32 nextUserId,
}

struct CheckEmployeeInfo{
    1: optional i32 parentChainId,
    2: optional i32 recomUserId,
    3: optional i32 pid
}



