
namespace java com.moseeker.thrift.gen.foundation.wordpress.struct

typedef string Datetime;

struct NewsletterForm {
	1: i32 appid,
	2: i32 account_id,
	3: byte plateform_type,
	4: byte return_last_version
}


struct NewsletterData {
	1: byte show_new_version,
	2: string version,
	3: Datetime update_time,
	4: list<string> update_list
}
