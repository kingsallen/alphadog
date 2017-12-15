package com.moseeker.position.service.position.base.sync;

public interface ITransferStrategy<P,R> {
    R moseekerToThird(P param);

    boolean hasStrategy(P param);
}
