package org.jeecg.modules.demo.wzh.controller;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.wzh.entity.YhHealthCert;
import org.jeecg.modules.demo.wzh.service.IYhHealthCertService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: yh_health_cert
 * @Author: jeecg-boot
 * @Date:   2021-09-05
 * @Version: V1.0
 */
@Api(tags="yh_health_cert")
@RestController
@RequestMapping("/wzh/yhHealthCert")
@Slf4j
public class YhHealthCertController extends JeecgController<YhHealthCert, IYhHealthCertService> {
	@Autowired
	private IYhHealthCertService yhHealthCertService;

	/**
	 * 分页列表查询
	 *
	 * @param yhHealthCert
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "yh_health_cert-分页列表查询")
	@ApiOperation(value="yh_health_cert-分页列表查询", notes="yh_health_cert-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(YhHealthCert yhHealthCert,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<YhHealthCert> queryWrapper = QueryGenerator.initQueryWrapper(yhHealthCert, req.getParameterMap());
		Page<YhHealthCert> page = new Page<YhHealthCert>(pageNo, pageSize);
		IPage<YhHealthCert> pageList = yhHealthCertService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param yhHealthCert
	 * @return
	 */
	@AutoLog(value = "yh_health_cert-添加")
	@ApiOperation(value="yh_health_cert-添加", notes="yh_health_cert-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody YhHealthCert yhHealthCert) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		yhHealthCert.setUploadUserId(sysUser.getId());
		Date current_date = new Date();

		yhHealthCert.setUploadDate(current_date);
		yhHealthCert.setCreateTime(current_date);
		yhHealthCertService.save(yhHealthCert);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param yhHealthCert
	 * @return
	 */
	@AutoLog(value = "yh_health_cert-编辑")
	@ApiOperation(value="yh_health_cert-编辑", notes="yh_health_cert-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody YhHealthCert yhHealthCert) {
		Date current_date = new Date();
		yhHealthCert.setUploadDate(current_date);
		yhHealthCertService.updateById(yhHealthCert);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "yh_health_cert-通过id删除")
	@ApiOperation(value="yh_health_cert-通过id删除", notes="yh_health_cert-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		yhHealthCertService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "yh_health_cert-批量删除")
	@ApiOperation(value="yh_health_cert-批量删除", notes="yh_health_cert-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.yhHealthCertService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "yh_health_cert-通过id查询")
	@ApiOperation(value="yh_health_cert-通过id查询", notes="yh_health_cert-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		YhHealthCert yhHealthCert = yhHealthCertService.getById(id);
		if(yhHealthCert==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(yhHealthCert);
	}

	@AutoLog(value = "上传文件")
	@ApiOperation(value = "文件上传")
	@GetMapping(value = "/uploadFile")
	public Result<?> uploadFile(HttpServletRequest request,String id,HttpServletResponse response){
		Result<?> result=new Result<>();
		YhHealthCert yhHealthCert=yhHealthCertService.get(id);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");
		String filename=yhHealthCert.getUploadFileName();
		filename+=file.getOriginalFilename()+"?";
		yhHealthCert.setUploadFileName(filename);

		return result;
	}

	@AutoLog(value = "删除文件")
	@ApiOperation(value = "文件删除")
	@GetMapping(value = "/deleteFile")
	public Result<?> deleteFile(HttpServletRequest request,String id,String fileName,HttpServletResponse response){
		try{
			Result<?> result=new Result<>();
			YhHealthCert yhHealthCert=yhHealthCertService.get(id);
			String files=yhHealthCert.getUploadFileName();
			String[] filename=files.split("\\?");
			String uploadFileName="";
			int flag=0;
			for(String f:filename){
				if(f.equals(fileName)){
					flag=1;
					continue;
				}
				uploadFileName+=f+"?";
			}
			if(flag==0){
				return Result.error("未找到相应的文件！");
			}
			yhHealthCert.setUploadFileName(uploadFileName);
			yhHealthCertService.updateById(yhHealthCert);
			result.setMessage(uploadFileName);
			return result;
		}
		catch (Exception e){
			e.printStackTrace();
			return Result.error("error");
		}
	}



	/**
	 * 导出excel
	 *
	 * @param request
	 * @param yhHealthCert
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, YhHealthCert yhHealthCert) {
		return super.exportXls(request, yhHealthCert, YhHealthCert.class, "yh_health_cert");
	}

	/**
	 * 通过excel导入数据
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		return super.importExcel(request, response, YhHealthCert.class);
	}

}


