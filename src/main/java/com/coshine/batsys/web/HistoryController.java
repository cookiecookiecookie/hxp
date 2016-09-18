package com.coshine.batsys.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coshine.batsys.entity.CpJobExec;
import com.coshine.batsys.service.BatsysService;
import com.github.pagehelper.PageInfo;

@Controller
public class HistoryController {
	
	@Autowired
	private BatsysService batsysService;
	
	@WebAction(Permission.NEED_AUTH)
	@RequestMapping("/history")
	public String index(@RequestParam(name="p", defaultValue="1") Integer pageNum, Model m) {
		List<CpJobExec> jobExecs = batsysService.searchJobExec(pageNum);
		m.addAttribute("pageInfo", new PageInfo<CpJobExec>(jobExecs));
		return "history/index";
	}
	
}
