namespace java com.moseeker.thrift.gen.referral.struct

typedef string Timestamp

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

struct ActivityDTO {
    1 : optional i32 id,
    2 : optional list<i32> amounts,
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
