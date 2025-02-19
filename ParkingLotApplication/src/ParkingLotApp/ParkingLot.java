package ParkingLotApp;

import java.util.*;
import java.util.Map.Entry;

public class ParkingLot {
	
	private final int capacity;
	private final TreeSet<Integer> availableSlots;
	private final HashMap<String, List<String>> colorToReg;
	private final HashMap<String, List<Integer>> colorToSlot;
	private final HashMap<String, Integer> regToSlot;
	private final HashMap<Integer, Car> slotToCar;

	public ParkingLot(int capacity) {
		this.capacity = capacity;
		this.availableSlots = new TreeSet();
		this.colorToReg = new HashMap();
		this.colorToSlot = new HashMap();
		this.regToSlot = new HashMap();
		this.slotToCar = new HashMap();
		for (int i = 1; i <= capacity; i++) {
			availableSlots.add(i);
		}
		
        System.out.println("Created a parking lot with " + capacity + " slots");
	}
	
	public void park(String regNumber, String color) {
		
		if(availableSlots.isEmpty()) {
			System.out.println("Sorry, parking lot is full");
			return;
		}
		int slot = availableSlots.pollFirst();
		Car car = new Car(regNumber, color);
		slotToCar.put(slot, car);
		regToSlot.put(regNumber, slot);
		colorToSlot.computeIfAbsent(color, k -> new ArrayList<>()).add(slot);
		colorToReg.computeIfAbsent(color, k -> new ArrayList<>()).add(regNumber);
		System.out.println("Allocated slot number : " + slot);
	}
	
	public void leave(int slot) {
		if(!slotToCar.containsKey(slot)) {
			System.out.println("Slot number " + slot + " is already empty");
			return;
		}
		Car car = slotToCar.remove(slot);
		regToSlot.remove(car.getRegNumber());
		colorToSlot.get(car.getColor()).removeIf(s -> s == slot);
		colorToReg.get(car.getColor()).removeIf(r -> r == car.getRegNumber());
		
		availableSlots.add(slot);
		System.out.println("Slot number " + slot + " is free");
	}
	
	public void status() {
		System.out.println("Slot No.\tRegistration No\tColour");
		
		slotToCar.forEach((k,v) -> {System.out.println(k.toString() + "\t" + v.getRegNumber() + 
				          "\t" + v.getColor());});
	}
	
	public void regNumForColor(String color) {
		System.out.println(String.join(",", colorToReg.getOrDefault(color, new ArrayList<>())));
	}
	
	public void slotForColor(String color) {
		System.out.println(String.join(",", colorToSlot.getOrDefault(color, new ArrayList<>()).toString()));
	}
	
	public void slotForRegNumber(String regNumber) {
		if(!regToSlot.containsKey(regNumber)) {
			System.out.println("Not found");
			return;
		}
        System.out.println(regToSlot.get(regNumber));
    }

}
