package os_project_1;

import java.util.ArrayList;

public class Process
{
	int pid;
	int delay;
	int numOfBursts;
	ArrayList<Integer> burstsList;
	boolean hasArrived = false;
	
	
	public Process(int pid, int delay, int numOfBursts, ArrayList<Integer> burstsList)
	{
		super();
		this.pid = pid;
		this.delay = delay;
		this.numOfBursts = numOfBursts;
		this.burstsList = burstsList;
	}
	public int getPid()
	{
		return pid;
	}
	public void setPid(int pid)
	{
		this.pid = pid;
	}
	public int getDelay()
	{
		return delay;
	}
	public void setDelay(int delay)
	{
		this.delay = delay;
	}
	public int getNumOfBursts()
	{
		return numOfBursts;
	}
	public void setNumOfBursts(int numOfBursts)
	{
		this.numOfBursts = numOfBursts;
	}
	public ArrayList<Integer> getBurstsList()
	{
		return burstsList;
	}
	public void setBurstsList(ArrayList<Integer> burstsList)
	{
		this.burstsList = burstsList;
	}
	
	
}