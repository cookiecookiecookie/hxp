package com.coshine.batsys.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.coshine.batsys.context.BatsysTask;
import com.coshine.batsys.service.BatsysService;

@Component("calculationFeeTask")
public class CalculationFeeTask implements BatsysTask {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private BatsysService batsysService;

	@Override
	@Transactional
	public int exec(String... args) {
		try {
			String sql1 = "update cp_mertrx "
					+ "set mt_payable_amt=(select cp_mertrx.mt_trxn_amt*(1-cp_mermst.mm_corr_line1/100) from cp_mermst where cp_mertrx.mt_merchant_id=cp_mermst.mm_merchant_no),"
					+ "	   mt_payment_disc=(select cp_mertrx.mt_trxn_amt*cp_mermst.mm_corr_line1/100 from cp_mermst where cp_mertrx.mt_merchant_id=cp_mermst.mm_merchant_no) "
					+ "where mt_channel_type ='V' and mt_trxn_code='SALE' and mt_paydue_date <= ?";
			String sql2 = "update cp_mertrx "
					+ "set mt_payable_amt=(select cp_mertrx.mt_trxn_amt*(1-cp_mermst.mm_corr_line2/100) from cp_mermst where cp_mertrx.mt_merchant_id=cp_mermst.mm_merchant_no),"
					+ "    mt_payment_disc=(select cp_mertrx.mt_trxn_amt*cp_mermst.mm_corr_line2/100 from cp_mermst where cp_mertrx.mt_merchant_id=cp_mermst.mm_merchant_no) "
					+ "where mt_channel_type ='M' and mt_trxn_code='SALE' and mt_paydue_date <= ?";
			String sql3 = "update cp_mertrx "
					+ "set mt_payable_amt=(select cp_mertrx.mt_trxn_amt*(1-cp_mermst.mm_corr_line3/100) from cp_mermst where cp_mertrx.mt_merchant_id=cp_mermst.mm_merchant_no),"
					+ "    mt_payment_disc=(select cp_mertrx.mt_trxn_amt*cp_mermst.mm_corr_line3/100 from cp_mermst where cp_mertrx.mt_merchant_id=cp_mermst.mm_merchant_no) "
					+ "where mt_channel_type ='J' and mt_trxn_code='SALE' and mt_paydue_date <= ?";
			String sql4 = "update cp_mertrx "
					+ "set mt_payable_amt=(select cp_mertrx.mt_trxn_amt*(1-cp_mermst.mm_corr_line4/100) from cp_mermst where cp_mertrx.mt_merchant_id=cp_mermst.mm_merchant_no),"
					+ "    mt_payment_disc=(select cp_mertrx.mt_trxn_amt*cp_mermst.mm_corr_line4/100 from cp_mermst where cp_mertrx.mt_merchant_id=cp_mermst.mm_merchant_no) "
					+ "where mt_channel_type ='A' and mt_trxn_code='SALE' and mt_paydue_date <= ?";
			String accountingDate = batsysService.getAccountingDate();
			jdbcTemplate.update(sql1, accountingDate);
			jdbcTemplate.update(sql2, accountingDate);
			jdbcTemplate.update(sql3, accountingDate);
			jdbcTemplate.update(sql4, accountingDate);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

}
