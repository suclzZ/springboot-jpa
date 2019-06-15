package com.sucl.jpa.sys.web;

import com.sucl.jpa.sys.entity.Agency;
import com.sucl.jpa.sys.service.AgencyService;
import com.sucl.jpa.core.web.BaseController;
import com.sucl.jpa.core.web.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/agency")
public class AgencyController extends BaseController<AgencyService,Agency> {

}
