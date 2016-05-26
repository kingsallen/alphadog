# file: company.struct

namespace java com.moseeker.thrift.gen.company.struct

typedef string Timestamp;


struct Hrcompany { 
   1:i32 id,
   2:i32 type,
   3:string name,
   4:string introduction,
   5:string scale,
   6:string address,
   7:i32 property,
   8:string industry,
   9:string homepage,
   10:string logo,
   11:string abbreviation,
   12:string impression,
   13:string banner,
   14:i32 parent_id,
   15:i32 hraccount_id,
   16:i32 disable,
   17:Timestamp create_time,
   18:Timestamp update_time,
   19:i32 source
}

