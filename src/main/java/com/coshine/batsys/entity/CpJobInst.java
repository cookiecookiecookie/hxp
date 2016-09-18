package com.coshine.batsys.entity;

import java.io.Serializable;

/**
 * 
 * @author json
 */
public class CpJobInst implements Serializable {

    private static final long serialVersionUID = 1L;

	/**
	 *  
	 */
	private String id;
	/**
	 *  
	 */
	private String execId;
	/**
	 *  
	 */
	private String jobId;
	/**
	 *  
	 */
	private String startTime;
	/**
	 *  
	 */
	private String endTime;
	/**
	 *  
	 */
	private String status;
	/**
	 *  
	 */
	private String description;
	/**
	 *  
	 */
	private Long totalTime;
	/**
	 *  
	 */
	private Integer redoCount;
	/**
	 *  
	 */
	private Integer orderNum;
	/**
	 * 
	 * @param id
	 */
	public void setId(String id){
		this.id = id;
	}
	
    /**
     * 
     * @return
     */	
    public String getId(){
    	return id;
    }
	/**
	 * 
	 * @param execId
	 */
	public void setExecId(String execId){
		this.execId = execId;
	}
	
    /**
     * 
     * @return
     */	
    public String getExecId(){
    	return execId;
    }
	/**
	 * 
	 * @param jobId
	 */
	public void setJobId(String jobId){
		this.jobId = jobId;
	}
	
    /**
     * 
     * @return
     */	
    public String getJobId(){
    	return jobId;
    }
	/**
	 * 
	 * @param startTime
	 */
	public void setStartTime(String startTime){
		this.startTime = startTime;
	}
	
    /**
     * 
     * @return
     */	
    public String getStartTime(){
    	return startTime;
    }
	/**
	 * 
	 * @param endTime
	 */
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	
    /**
     * 
     * @return
     */	
    public String getEndTime(){
    	return endTime;
    }
	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status){
		this.status = status;
	}
	
    /**
     * 
     * @return
     */	
    public String getStatus(){
    	return status;
    }
	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
    /**
     * 
     * @return
     */	
    public String getDescription(){
    	return description;
    }
	/**
	 * 
	 * @param totalTime
	 */
	public void setTotalTime(Long totalTime){
		this.totalTime = totalTime;
	}
	
    /**
     * 
     * @return
     */	
    public Long getTotalTime(){
    	return totalTime;
    }
	/**
	 * 
	 * @param redoCount
	 */
	public void setRedoCount(Integer redoCount){
		this.redoCount = redoCount;
	}
	
    /**
     * 
     * @return
     */	
    public Integer getRedoCount(){
    	return redoCount;
    }
	/**
	 * 
	 * @param orderNum
	 */
	public void setOrderNum(Integer orderNum){
		this.orderNum = orderNum;
	}
	
    /**
     * 
     * @return
     */	
    public Integer getOrderNum(){
    	return orderNum;
    }
}