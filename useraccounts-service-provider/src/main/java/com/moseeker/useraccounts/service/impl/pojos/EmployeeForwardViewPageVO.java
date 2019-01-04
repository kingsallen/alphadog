package com.moseeker.useraccounts.service.impl.pojos;

import com.moseeker.thrift.gen.useraccounts.struct.Connection;
import java.util.List;

/**
 * Created by moseeker on 2019/1/4.
 */
public class EmployeeForwardViewPageVO {

    public int userId; // optional
    public String nickname; // optional
    public int viewCount; // optional
    public int connection; // optional
    public int depth; // optional
    public String headimgurl; // optional
    public String positionTitle; // optional
    public int positionId; // optional
    public String forwardName; // optional
    public boolean forwardSourceWx; // optional
    public String clickTime; // optional
    public int invitationStatus; // optional
    public List<Connection> chain; // op
}
