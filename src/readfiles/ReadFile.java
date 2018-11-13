package readfiles;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ReadFile {
	private Map<String, User> usersMap;
	private Map<Integer, String> trackData;
	
	private static final String COMMA = ",";
	private static final String ENTER = "\n";
	
	private static final String FILE_HEADER = "TrackId,Predictor";
	
	public ReadFile() {
		usersMap = new HashMap<>();
		trackData = new HashMap<>();
	}
	
	public void readTest(String testName) throws Exception{
		Scanner sc = new Scanner(new FileReader(testName));
		String userId = "";
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			if(line.isEmpty()) {
				break;
			} else if(line.contains("|")) {
				String[] nameLine = line.split("\\|");
				userId = nameLine[0];
				User user = new User(userId);
				usersMap.put(userId, user);				
			}else {
				int trackId = Integer.parseInt(line);
				usersMap.get(userId).addTesting(trackId);
			}
		}
		
	}
	
	public void readTraining(String trainName) throws Exception{
		Scanner sc = new Scanner(new FileReader(trainName));
		String userId = "";
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			if(line.isEmpty()) {
				break;
			}else if(line.contains("|")) {
				String[] nameLine = line.split("\\|");
				userId = nameLine[0];
				if(!usersMap.containsKey(userId)) {
					int trackLines = Integer.parseInt(nameLine[1]);
					for(int i = 0; i < trackLines; i++) {
						sc.nextLine();
					}
				}
			}else {
				String[] scoreLine = line.split("\\s+");
				int trackId = Integer.parseInt(scoreLine[0]);
				int score = Integer.parseInt(scoreLine[1]);
				usersMap.get(userId).addTraining(trackId, score);
			}
		}
	}
	
	public void readTrack(String trackName) throws Exception {
		Scanner sc = new Scanner(new FileReader(trackName));
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			if(line.isEmpty()) {
				break;
			} else {
				String[] trackLine = line.split("\\|");
				trackData.put(Integer.parseInt(trackLine[0]), line);
			}
		}
	}
	
	/*
	public void readTrack(String trackName) throws Exception{
		for(String userId: usersMap.keySet()) {
			System.out.println("begin user:"+userId);
			User user = usersMap.get(userId);
			user.sortById();
			int currTest = 0;
			Scanner sc = new Scanner(new FileReader(trackName));
			while(sc.hasNextLine() && currTest < User.TEST_NUM) {
				String line = sc.nextLine();
				if(line.isEmpty()) {
					break;
				} else {
					String[] trackLine = line.split("\\|");
					if(Integer.parseInt(trackLine[0]) == user.getTest(currTest)) {
						for(int i = 0; i < trackLine.length; i++) {
							int temp = -1;
							if(!trackLine[i].equals("None")) {
								temp = Integer.parseInt(trackLine[i]);
							}
							user.addHeirachy(currTest, temp);
						}
						currTest++;
					}
				}
			}
			sc.close();
		}
	}
	*/
	
	public void addHeirachy() {
		for(String userId : usersMap.keySet()) {
			User user = usersMap.get(userId);
			user.sortById();
			int currTest = 0;
			while(currTest < User.TEST_NUM) {
				String line = trackData.get(user.getTest(currTest));
				String[] trackLine = line.split("\\|");
				for(int i = 0; i < trackLine.length; i++) {
					int temp = -1;
					if(!trackLine[i].equals("None"))
						temp = Integer.parseInt(trackLine[i]);
					user.addHeirachy(currTest, temp);
				}
				currTest++;
			}
		}
	}
	
	
	
	public void writeCSV(String outputName) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(outputName);
			
			fileWriter.append(FILE_HEADER);
			fileWriter.append(ENTER);
			
			for(String userId: usersMap.keySet()) {
				for(int i = 0; i < User.TEST_NUM; i++) {
					fileWriter.append(usersMap.get(userId).getTrackId(i));
					fileWriter.append(COMMA);
					fileWriter.append(usersMap.get(userId).getPredictor(i));
					fileWriter.append(ENTER);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void compute(String userId) {
		usersMap.get(userId).compute();
	}
	
	public void computeAll() {
		for(String userId: usersMap.keySet())
			compute(userId);
	}
	
	public void printUsers() {
		Set<String> names = usersMap.keySet();
		for(String s: names) {
			System.out.println(s);
		}
	}
	
	//use for debugging
	public void printHeirachy(String userId) {
		usersMap.get(userId).printHeirachy();
	}
	
	public void printUser(String userId) {
		usersMap.get(userId).print();
	}
	
	public void printScores(String userId) {
		usersMap.get(userId).printScores();
	}

}
