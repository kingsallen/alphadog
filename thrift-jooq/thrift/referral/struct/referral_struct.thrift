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
