package readfiles;

public class Main {
	public static void main(String[] args) throws Exception{
		ReadFile rf = new ReadFile();
		rf.readTest("testItem2.txt");
		rf.readTraining("trainItem2.txt");
		rf.readTrack("trackData2.txt");
		rf.addHeirachy();
		rf.computeAll();
		rf.writeCSV("testwrite.csv");
	}
}
