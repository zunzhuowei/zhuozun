package com.qs.game.service.impl;

import com.qs.game.model.sys.Core;
import com.qs.game.service.ICoreService;
import org.springframework.stereotype.Service;

/**
 *  核心业务层接口实现类
 */
@Service
public class CoreServiceImpl implements ICoreService {


    @Override
    public boolean checkParams(Core core) {
        return false;
    }


}
