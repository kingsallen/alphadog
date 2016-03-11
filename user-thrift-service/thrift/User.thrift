# file: userService.thrift
namespace java com.moseeker.user.thrift.service.user

struct User {
    1: i32 id,
    2: string username,
    3: string email
}

service UserService {
    User getById(1: i32 id);
}
