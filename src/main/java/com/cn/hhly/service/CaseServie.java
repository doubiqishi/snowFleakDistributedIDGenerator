package com.cn.hhly.service;

import com.cn.hhly.dao.Case;
import com.cn.hhly.mapper.CaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author hhly-pc
 * @create 2018-06-26
 * @desc
 */
@Service
public class CaseServie {

    @Autowired
    private CaseMapper caseMapper;

    public Case getCases(Long id){
        Case cases = caseMapper.selectByPrimaryKey(id);
        return cases;

    }
}
