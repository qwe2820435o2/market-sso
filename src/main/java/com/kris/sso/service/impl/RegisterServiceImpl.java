package com.kris.sso.service.impl;

import com.kris.sso.mapper.TbUserMapper;
import com.kris.sso.pojo.TaotaoResult;
import com.kris.sso.pojo.TbUser;
import com.kris.sso.pojo.TbUserExample;
import com.kris.sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kris
 * @create 2017-01-11 17:22
 */
@Service
public class RegisterServiceImpl implements RegisterService{

    @Autowired
    private TbUserMapper mTbUserMapper;

    @Override
    public TaotaoResult checkData(String param, int type) {
        //根据数据类型检查数据
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //1,2,3分别代表username、phone、email
        if (1 == type) {
            criteria.andUsernameEqualTo(param);
        } else if (2 == type) {
            criteria.andPhoneEqualTo(param);
        } else if (3 == type) {
            criteria.andEmailEqualTo(param);
        }
        //执行查询
        List<TbUser> list = mTbUserMapper.selectByExample(example);
        //判断查询结果是否为空
        if (list == null || list.isEmpty()) {
            return TaotaoResult.ok(true);//表中找不到，用户可注册
        }

        return TaotaoResult.ok(false);//已经有用户
    }
}
