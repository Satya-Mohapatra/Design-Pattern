package singleton;

import java.io.*;

/**
 * So one very simple variation on the singleton that we've constructed already something called a 
 * static block singleton. And the reason why this Singleton exists is because you might have exceptions right inside the singleton constructor.
 * So remember if you currently have exceptions inside of the singleton constructor you are in real trouble.
 * But if you use a static blog then things are a bit different.So the only thing I wanted to show in this example is the fact that 
 * if you do have a singleton constructor which can actually throw something then you have to slightly change your approach.
 * so instead of just declaring a variable and exposing it through the get instance what you would make it typically is a static block 
 * away you put in some slow try catch and you try making the instance of the singleton and you catch some exception and handle that.
 * 
 * @author satya
 *
 */

class StaticSingleton implements Serializable {
	// cannot new this class, however
	// * instance can be created deliberately (reflection)
	// * instance can be created accidentally (serialization)
	private StaticSingleton() throws IOException {
		System.out.println("Singleton is initializing");
		throw new RuntimeException();
	}

	private static  StaticSingleton instance;
	
	static {
		try {
			instance = new StaticSingleton();
		} catch (Exception e) {
			System.err.println("Failed to create Singleton");
		}
	}

	private int value = 0;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	// required for correct serialization
	// readResolve is used for _replacing_ the object read from the stream

	protected Object readResolve() {
		return instance;
	}

	// generated getter
	public static StaticSingleton getInstance() {
		return instance;
	}
}

public class StaticBlockSingletonDemo {
	static void saveToFile(StaticSingleton singleton, String filename) throws Exception {
		try (FileOutputStream fileOut = new FileOutputStream(filename);
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(singleton);
		}
	}

	static StaticSingleton readFromFile(String filename) throws Exception {
		try (FileInputStream fileIn = new FileInputStream(filename);
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			return (StaticSingleton) in.readObject();
		}
	}

	public static void main(String[] args) throws Exception {
		StaticSingleton singleton = StaticSingleton.getInstance();
		singleton.setValue(111);

		String filename = "singleton.bin";
		saveToFile(singleton, filename);

		singleton.setValue(222);

		StaticSingleton singleton2 = readFromFile(filename);

		System.out.println(singleton == singleton2);

		System.out.println(singleton.getValue());
		System.out.println(singleton2.getValue());
	}
}