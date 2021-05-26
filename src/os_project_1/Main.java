package os_project_1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main
{
	// Global Variables
	static int numOfProcessors = 4;
	static Scanner sc= new Scanner(System.in);
	static ArrayList<Process> processesList = new ArrayList<Process>();
	static LinkedList<Process> readyProcesses = new LinkedList<Process>();
	static int globalCount = 0;
//	static int lastArrival = 0;
	
	public static void main(String[] args)
	{
		try
		{
			generateInputFile();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		numOfProcessors = getNumOfProcessers();
		SendToReady sendToReady = new SendToReady();
		sendToReady.run();
	}

	public static int getNumOfProcessers() {
		System.out.println("How many processors would you like?");
		int num = sc.nextInt();
		return num;
	}
	
	public static void generateInputFile() throws IOException {
		File file = null;
		try {
			System.out.println("Enter the name of the input file you want to create:");
			String inputFile;
			inputFile = sc.nextLine();
			if (!inputFile.endsWith(".txt")) {
				inputFile += ".txt";
			}
    	    file = new File(inputFile);
	    if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
    	      } else {
    	        System.out.println("File already exists.");
    	      }
    	    } catch (IOException e) {
    	      System.out.println("An error occurred.");
    	      e.printStackTrace();
    	    }
		
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(file, true);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	BufferedWriter bw = new BufferedWriter(fw);
    	Random rand = new Random();
    	
    	int numOfLines = rand.nextInt(80) + 20;
    	String delim = "\t";
    	for(int i = 1; i <= numOfLines; i++) {
    		int delay = rand.nextInt(10);
    		bw.write(delay + delim); //Delay
    		bw.write(i + delim); 	// PID
    		int numOfBursts = 5 + rand.nextInt(15);
    		ArrayList<Integer> bursts = new ArrayList<Integer>();
    		for(int j = 0; j < numOfBursts; j++) {
    			int burst = 2 + rand.nextInt(118);
    			bursts.add(burst);
    			bw.write(burst + delim); 	// PID
    		}
    		Process p = new Process(i, delay, numOfBursts, bursts);
    		processesList.add(p);
    		
    		if(i != numOfLines)
    			bw.write("\n");
    	}
    	
    	bw.close();
//    	System.out.println("Random num from 100: " + numOfLines);
		
	}

}


class SendToReady extends Thread {
	static int lastArrival = 0;
	public void run() {
		loopThroughProcesses();
		
	}
	public static void loopThroughProcesses() {
		boolean isComplete = haveAllProcessesStarted();
		
		while(!isComplete) {
			Process p = getNextProcess();
			if(p.delay == 0) {
				Main.globalCount--;
			}
			if(lastArrival + p.delay == Main.globalCount) {
				p.hasArrived = true;
				Main.readyProcesses.add(p);
				lastArrival = Main.globalCount;
				System.out.println("Process " + p.getPid() + " has been added to the ready queue at count: " + Main.globalCount);
				
			}
			Main.globalCount++;
			isComplete = haveAllProcessesStarted();
		}
	}
	
	public static boolean haveAllProcessesStarted() {
		for(Process p : Main.processesList) {
			if(!p.hasArrived) {
				return false;
			}
		}
		return true;
	}
	
	public static Process getNextProcess() {
		for(Process p : Main.processesList) {
			if(p.hasArrived == false) {
				return p;
			}
		}
		return null;
	}
}

