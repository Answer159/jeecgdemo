package org.jeecg.modules.demo.wzh.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: yh_health_cert
 * @Author: jeecg-boot
 * @Date:   2021-09-05
 * @Version: V1.0
 */
@Data
@TableName("yh_health_cert")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="yh_health_cert对象", description="yh_health_cert")
public class YhHealthCert implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**员工id*/
	@Excel(name = "员工id", width = 15)
    @ApiModelProperty(value = "员工id")
    private java.lang.String employeeId;
	/**证书编号*/
	@Excel(name = "证书编号", width = 15)
    @ApiModelProperty(value = "证书编号")
    private java.lang.String certNum;
	/**发证机关*/
	@Excel(name = "发证机关", width = 15)
    @ApiModelProperty(value = "发证机关")
    private java.lang.String issuingAuthority;
	/**部门*/
	@Excel(name = "部门", width = 15)
    @ApiModelProperty(value = "部门")
    private java.lang.String apartment;
	/**发证日期*/
	@Excel(name = "发证日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "发证日期")
    private java.util.Date issueDate;
	/**有效期至*/
	@Excel(name = "有效期至", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "有效期至")
    private java.util.Date validity;
	/**上传文件*/
	@Excel(name = "上传文件", width = 15)
    @ApiModelProperty(value = "上传文件")
    private java.lang.String uploadFileName;
	/**上传人*/
	@Excel(name = "上传人", width = 15)
    @ApiModelProperty(value = "上传人")
    private java.lang.String uploadUserId;
	/**上传时间*/
	@Excel(name = "上传时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "上传时间")
    private java.util.Date uploadDate;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
}
