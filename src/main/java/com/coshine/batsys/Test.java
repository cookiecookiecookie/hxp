package com.coshine.batsys;

public class Test {
	public static int fun(int n){


	 if(n==1||n==2){

		return n;

		}else{

		return fun(n-1)+fun(n-2);
		}
	}
	private static int total=0;
	  
    public static void main(String[] args) {
    	int stairNum = 8;
    	int maxStep = 2;
    	int[] hasJumped = new int[stairNum];
    	jumpStair(hasJumped,0,maxStep,maxStep,stairNum);
    	System.out.println("递归方式统计总次数："+total);
    	System.out.println(" 斐波那契（Fibonacci）数列逻辑方式统计总次数："+jumpStairCount(stairNum));
    	int tj=fun(8);
    	System.out.println(tj);
    } 
    
    /**
     * 跳台阶
     * @param hasJumped 已经跳过的台阶路径
     * @param currentStair 当前所在的台阶编号
     * @param jumpStep 跳跃的台阶数
     * @param maxStep 青蛙能跳的最大台阶数 
     * @param stairNum 台阶总数
     */
    public static void jumpStair(int[] hasJumped, int currentStair, int jumpStep, int maxStep,int stairNum){
		if (currentStair > stairNum) {
			return;
		} else if (currentStair >= stairNum) {
			String s = "";
			for (int i = 0; i < hasJumped.length; i++) {
				if (hasJumped[i] > 0) {
					s = s + hasJumped[i] + ",";
				}
			}
			total++;
			System.out.println(s);
			return;
		} else {
			for (int j = maxStep; j >= 1; j--) {
				int newCurrentStair = currentStair + j;
				hasJumped[currentStair] = newCurrentStair;
				for (int i = currentStair + 1; i < hasJumped.length; i++) {
					hasJumped[i] = 0;
				}
				jumpStair(hasJumped, newCurrentStair, j, maxStep, stairNum);
			}
		}
    }

    /**
     * 跳台阶总数统计
     * @param stairNum 台阶总数
     * @return
     */
	public static int jumpStairCount(int stairNum) {
		if (stairNum == 1) {
			return 1;
		}
		else if (stairNum == 2) {
			return 2;
		}
		else if (stairNum == 3) {
			return 4;			
		}
		return jumpStairCount(stairNum - 1) + jumpStairCount(stairNum - 2) + jumpStairCount(stairNum - 3);
	}
}
