package readfiles;
import java.util.*;

public class User {
	private String userId;
	private Map<Integer, Integer> train;
	//private int[] test;
	private ArrayList<Integer>[] testHeirachy;
	//private double[] scores; 
	private ArrayList<TestInfo> tests;
	//private int testInd;
	
	final public static int TEST_NUM = 6;
	
	private class TestInfo{
		public boolean prefer;
		public double scores;
		public int trackId;
	}
	
	public User(String id) {
		userId = id;
		train = new HashMap<>();
		//test = new int[TEST_NUM];
		//testInd = 0;
		//scores = new double[TEST_NUM];
		tests = new ArrayList<>(); 
		testHeirachy = new ArrayList[TEST_NUM];
		for(int i = 0; i < TEST_NUM; i++) {
			testHeirachy[i] = new ArrayList<>();
		}
	}
	
	public void addTraining(int trainId, int score) {
		train.put(trainId, score);
	}
	
	public void addTesting(int testId) {
		TestInfo temp = new TestInfo();
		temp.trackId = testId;
		tests.add(temp);
	}
	
	public int getScore(int trainId) {
		return train.get(trainId);
	}
	
	public int getTest(int index) {
		return tests.get(index).trackId;
	}
	
	public String getTrackId(int index) {
		return userId+"_"+tests.get(index).trackId;
	}
	
	public String getPredictor(int index) {
		if(tests.get(index).prefer)
			return "1";
		return "0";
	}
	
	public void addHeirachy(int index, int data) {
		testHeirachy[index].add(data);
	}
	
	public void sortById() {
		Collections.sort(tests, new SortbyId());
	}
	
	public void sortByScore() {
		Collections.sort(tests, new SortbyScores());
	}
		
	private class SortbyId implements Comparator<TestInfo> {

		@Override
		public int compare(TestInfo o1, TestInfo o2) {
			// TODO Auto-generated method stub
			return o1.trackId - o2.trackId;
		}
	}
	
	private class SortbyScores implements Comparator<TestInfo> {

		@Override
		public int compare(TestInfo o1, TestInfo o2) {
			// TODO Auto-generated method stub
			if(o1.scores < o2.scores) return -1;
			if(o1.scores > o2.scores) return 1;
			return 0;
		}
		
	}
	
	public boolean contains(int id) {
		return train.containsKey(id);
	}
	
	public void compute() {		
		for(int i = 0; i < TEST_NUM; i++) {
			double sum = 0;
			int albumId = testHeirachy[i].get(1);
			if(train.containsKey(albumId)) {
				sum += train.get(albumId)*0.3;
			}
			double restAver = 0;
			double restNum = 0;
			for(int j = 2; j < testHeirachy[i].size(); j++) {
				if(train.containsKey(testHeirachy[i].get(j))) {
					restAver += train.get(testHeirachy[i].get(j));
					restNum++;
				}
			}
			if(restNum != 0)
				restAver = restAver/restNum;
			sum += 0.7*restAver;
			tests.get(i).scores = sum;
		}
		sortByScore();
		for(int i = 0; i < 3; i++)
			tests.get(i).prefer = false; 
		for(int i = 3; i < TEST_NUM; i++)
			tests.get(i).prefer = true;
	}
	
	//use to debug
	public void printHeirachy() {
		for(int i = 0; i < TEST_NUM; i++) {
			for(int j = 0; j < testHeirachy[i].size(); j++)
				System.out.print(testHeirachy[i].get(j)+"|");
			System.out.println("");
		}
	}
	
	public void printScores() {
		for(int i = 0; i < TEST_NUM; i++) {
			System.out.println(tests.get(i).trackId + ": " + tests.get(i).scores);
		}
	}
	
	public void print() {
		System.out.println("userId: "+ userId);
		System.out.println("test ");
		for(int i = 0; i < TEST_NUM; i++) {
			System.out.println(tests.get(i).trackId);
		}
		System.out.println("train: ");
		
		for(Integer key : train.keySet()) {
			System.out.println(key+"  "+train.get(key));
		}
	}
}
