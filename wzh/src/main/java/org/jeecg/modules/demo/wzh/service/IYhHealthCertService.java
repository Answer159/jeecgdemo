package org.jeecg.modules.demo.wzh.service;

import org.jeecg.modules.demo.wzh.entity.YhHealthCert;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: yh_health_cert
 * @Author: jeecg-boot
 * @Date:   2021-09-05
 * @Version: V1.0
 */
public interface IYhHealthCertService extends IService<YhHealthCert> {
    public YhHealthCert get(String id);

    public List<YhHealthCert> listCert();

    public boolean delete(String id);

    public YhHealthCert edit(YhHealthCert yhHealthCert);

    public boolean add(YhHealthCert yhHealthCert);

}
