package com.liblog.service.impl;

import com.liblog.entity.Vote;
import com.liblog.service.IVoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by linzhi on 2016/12/14.
 */
@Transactional
@Service
public class VoteService extends BaseService<Vote> implements IVoteService {
}
