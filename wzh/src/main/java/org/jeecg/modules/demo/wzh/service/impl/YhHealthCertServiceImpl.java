package org.jeecg.modules.demo.wzh.service.impl;

import org.jeecg.modules.demo.wzh.entity.YhHealthCert;
import org.jeecg.modules.demo.wzh.mapper.YhHealthCertMapper;
import org.jeecg.modules.demo.wzh.service.IYhHealthCertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

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

}
