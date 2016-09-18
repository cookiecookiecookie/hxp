package com.coshine.batsys.entity;

import java.io.Serializable;

/**
 * 
 * @author json
 */
public class CpJobExec implements Serializable {

    private static final long serialVersionUID = 1L;

	/**
	 *  
	 */
	private String id;
	/**
	 *  
	 */
	private String flowId;
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
	private String accountingDate;
	/**
	 *  
	 */
	private String userId;
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
	 * @param flowId
	 */
	public void setFlowId(String flowId){
		this.flowId = flowId;
	}
	
    /**
     * 
     * @return
     */	
    public String getFlowId(){
    	return flowId;
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
	 * @param accountingDate
	 */
	public void setAccountingDate(String accountingDate){
		this.accountingDate = accountingDate;
	}
	
    /**
     * 
     * @return
     */	
    public String getAccountingDate(){
    	return accountingDate;
    }
	/**
	 * 
	 * @param userId
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	
    /**
     * 
     * @return
     */	
    public String getUserId(){
    	return userId;
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
}