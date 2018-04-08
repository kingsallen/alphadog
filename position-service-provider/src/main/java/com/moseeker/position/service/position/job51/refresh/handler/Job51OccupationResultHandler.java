package com.moseeker.position.service.position.job51.refresh.handler;

import com.moseeker.position.service.position.base.refresh.handler.DefaultOccupationResultHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import org.springframework.stereotype.Component;

@Component
public class Job51OccupationResultHandler extends DefaultOccupationResultHandler<Dict51jobOccupationDO> implements Job51ResultHandlerAdapter {

}
