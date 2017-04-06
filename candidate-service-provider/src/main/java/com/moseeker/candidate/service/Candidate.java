package com.moseeker.candidate.service;

import com.moseeker.thrift.gen.candidate.struct.CandidateList;
import com.moseeker.thrift.gen.candidate.struct.CandidateListParam;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;

/**
 * 候选人
 * Created by jack on 10/02/2017.
 */
public interface Candidate {

    /**
     * 用户查看职位，可能促使用户成为候选人。接口调用方不需要理会这个接口是否成功，所以不需要返回值
     * @param userID 用户编号
     * @param positionID 职位编号
     * @param shareChainID 转发编号
     */
    public void glancePosition(int userID, int positionID, int shareChainID);
    
    /**
     * 设置感兴趣职位
     * @param user_id
     * @param position_id
     * @param is_interested
     * @return
     */
    public Response changeInteresting(int user_id, int position_id, byte is_interested);

    /**
     * 查找被动求职者列表
     * @param param 查询参数
     * @return 被动求职者列表
     * @throws BIZException 业务异常
     */
    CandidateList candidateList(CandidateListParam param) throws BIZException;
}
