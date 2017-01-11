package com.kris.sso.service;

import com.kris.sso.pojo.TbItem;

/**
 * @author kris
 * @create 2016-12-12 15:44
 */
public interface ItemService {
    TbItem getItemById(Long itemId);
}
