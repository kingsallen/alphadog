namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigAtsSourceDO {

	1: optional i32 id,	//null
	2: optional string name,	//Name
	3: optional i32 type,	//1:WSDL
	4: optional string url,	//net url for webservice or other method
	5: optional string fullname,	//null
	6: optional string ftpAddress,	//存放ftp文件
	7: optional string username,	//ATS 用户名
	8: optional string password,	//ATS 密码
	9: optional string apikey,	//ATS API KEY
	10: optional i32 companyId,	//hr_company.id
	11: optional string kenexaId,	//kenexa的sender id
	12: optional string kenexaCridential	//kenexa的sender cridential

}
