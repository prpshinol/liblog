package com.liblog.service.impl;

import com.liblog.entity.Collect;
import com.liblog.service.ICollectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by linzhi on 2017/3/12.
 */
@Service
@Transactional
public class CollectService extends BaseService<Collect> implements ICollectService {
}
