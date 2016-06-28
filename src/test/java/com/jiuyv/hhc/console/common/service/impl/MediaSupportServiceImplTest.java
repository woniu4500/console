package com.jiuyv.hhc.console.common.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.jiuyv.hhc.console.common.service.IMediaImageService;
import com.jiuyv.hhc.model.security.SysUserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:com/jiuyv/hhc/configs/spring/applicationContext.xml")
// @Transactional
public class MediaSupportServiceImplTest {

	@Autowired
	private IMediaImageService mediaImageService;
	
	@Test
	public void testDoGenerateZipFile() throws Exception {
		String medUrls = " upload/201501/3f9d684e-4af2-4c94-80db-691cf2f65312/LicFtl.jpg, upload/201501/bb2ae3b8-112b-4bd7-afec-9b34fb5c8243/59440529.jpg, upload/201501/98b69abd-80ac-4d77-bb1a-2b6a1a12c7a5/LicFtl.jpg";
		SysUserVo userVo = new SysUserVo();
		userVo.setLoginId("admin");
		mediaImageService.doGenerateZipFile(userVo, medUrls, "测试文件", "abcdefghijk");
	}

}
