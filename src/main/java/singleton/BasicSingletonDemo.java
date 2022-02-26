package singleton;

import java.io.*;

/**
 * Singleton is initializing false 222 111
 * 
 * @author satya
 *
 */

class BasicSingleton implements Serializable {
	// cannot new this class, however
	// * instance can be created deliberately (reflection)
	// * instance can be created accidentally (serialization)
	private BasicSingleton() {
		System.out.println("Singleton is initializing");
	}

	private static final BasicSingleton INSTANCE = new BasicSingleton();

	private int value = 0;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	// required for correct serialization
	// readResolve is used for _replacing_ the object read from the stream

	/**protected Object readResolve() {
		return INSTANCE;
	}*/

	// generated getter
	public static BasicSingleton getInstance() {
		return INSTANCE;
	}
}

public class BasicSingletonDemo {
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