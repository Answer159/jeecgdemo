package org.jeecg.modules.demo.wzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.demo.wzh.entity.YhHealthCert;
import org.jeecg.modules.demo.wzh.entity.YhParameter;
import org.jeecg.modules.demo.wzh.mapper.YhHealthCertMapper;
import org.jeecg.modules.demo.wzh.mapper.YhParameterMapper;
import org.jeecg.modules.demo.wzh.service.IYhHealthCertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.sql.Wrapper;
import java.util.*;

/**
 * @Description: yh_health_cert
 * @Author: jeecg-boot
 * @Date:   2021-09-05
 * @Version: V1.0
 */
@Service
public class YhHealthCertServiceImpl extends ServiceImpl<YhHealthCertMapper, YhHealthCert> implements IYhHealthCertService {
    @Autowired
    YhHealthCertMapper yhHealthCertMapper;
    @Autowired
    YhParameterMapper yhParameterMapper;
    @Override
    public YhHealthCert get(String id){
        return yhHealthCertMapper.selectById(id);
    }
    @Override
    public List<YhHealthCert> listCert(){
        List<YhHealthCert> list=new ArrayList<>();
        list=list();
        return list;
    }

    @Override
    public boolean delete(String id){
        try{
            yhHealthCertMapper.deleteById(id);
        }
        catch (Exception e){
            return false;
        }
        return true;

    }
    @Override
    public YhHealthCert edit(YhHealthCert yhHealthCert){
        int id=yhHealthCert.getId();
        yhHealthCertMapper.updateById(yhHealthCert);
        return yhHealthCertMapper.selectById(id);
    }
    @Override
    public boolean add(YhHealthCert yhHealthCert){
        try{
            yhHealthCertMapper.insert(yhHealthCert);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 列出所有快到期党的健康证
     * @return
     */
    @Override
    public List<YhHealthCert> alarm(){
        Date date=new Date();
        List<YhHealthCert> yhHealthCerts=list();
        List<YhHealthCert> alarmCert=new ArrayList<>();
        QueryWrapper<YhParameter> queryWrapper=new QueryWrapper();
        queryWrapper.eq("parameter_name","certificate_alarm");
        YhParameter yhParameter=yhParameterMapper.selectOne(queryWrapper);
        BigDecimal alarmTime=yhParameter.getParameterValue();
        for(YhHealthCert yhHealthCert:yhHealthCerts){
            long time=(date.getTime()-yhHealthCert.getIssueDate().getTime())/1000/60/60/24;
            BigDecimal t=new BigDecimal(time);
            if(t.compareTo(alarmTime)<1){
                alarmCert.add(yhHealthCert);
            }
        }
        return alarmCert;
    }

    /**
     * 根据用户id判断该用户的健康证是否快到期
     * @param id
     * @return
     */
    @Override
    public boolean alarmedById(String id){
        QueryWrapper<YhHealthCert> queryWrapper=new QueryWrapper();
        queryWrapper.eq("employee_id",id);
        YhHealthCert yhHealthCert=yhHealthCertMapper.selectOne(queryWrapper);
        QueryWrapper<YhParameter> queryWrapper1=new QueryWrapper();
        queryWrapper.eq("parameter_name","certificate_alarm");
        YhParameter yhParameter=yhParameterMapper.selectOne(queryWrapper1);
        BigDecimal alarmTime=yhParameter.getParameterValue();
        Date date=new Date();
        long time=(date.getTime()-yhHealthCert.getIssueDate().getTime())/1000/60/60/24;
        BigDecimal t=new BigDecimal(time);
        if(t.compareTo(alarmTime)<1){
            return false;
        }
        return true;
    }
}
