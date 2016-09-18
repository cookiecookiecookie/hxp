CREATE TABLE IF NOT EXISTS cp_job_exec (
  id varchar(36) NOT NULL,
  flow_id varchar(50) NOT NULL,
  start_time varchar(50) NOT NULL,
  end_time varchar(50) DEFAULT NULL,
  status varchar(50) NOT NULL,
  accounting_date varchar(8) NOT NULL,
  user_id varchar(20) NOT NULL,
  total_time bigint(20) DEFAULT NULL,
  redo_count int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS cp_job_inst (
  id varchar(36) NOT NULL,
  exec_id varchar(36) NOT NULL,
  job_id varchar(20) NOT NULL,
  start_time varchar(50) NOT NULL,
  end_time varchar(50) DEFAULT NULL,
  status varchar(50) NOT NULL,
  description varchar(200) NOT NULL,
  total_time bigint(20) DEFAULT NULL,
  redo_count int(11) DEFAULT NULL,
  order_num int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;