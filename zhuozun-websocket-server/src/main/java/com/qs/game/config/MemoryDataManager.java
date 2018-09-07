package com.qs.game.config;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by zun.wei on 2018/9/7 14:56.
 * Description: 内存数据管理者
 */
@Data
@Slf4j
@Accessors(chain = true)
@Component
public class MemoryDataManager implements GameManager, Serializable {




}
