CREATE TABLE cp_job_exec (
  id varchar(36) NOT NULL,
  flow_id varchar(50) NOT NULL,
  start_time varchar(50) NOT NULL,
  end_time varchar(50),
  status varchar(50) NOT NULL,
  accounting_date varchar(8) NOT NULL,
  user_id varchar(20) NOT NULL,
  total_time number(20),
  redo_count number(11),
  PRIMARY KEY (id)
);

CREATE TABLE cp_job_inst (
  id varchar(36) NOT NULL,
  exec_id varchar(36) NOT NULL,
  job_id varchar(20) NOT NULL,
  start_time varchar(50) NOT NULL,
  end_time varchar(50),
  status varchar(50) NOT NULL,
  description varchar(200) NOT NULL,
  total_time number(20),
  redo_count number(11),
  order_num number(11) NOT NULL,
  PRIMARY KEY (id)
);