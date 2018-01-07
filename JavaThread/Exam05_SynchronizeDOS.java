package JavaThread;

//thread에 의해서 사용될 공유객체 

class Shared {
	private int number;
	
	public Shared() {
		
	}

	public int getNumber() {
		return number;
	}
	
	//만약 method에 synchronized keyword 이용하면 method 전체가 임계 영역(Critical Section)으로 할당
	//만약 synchronized block 이용하면 내가 임계 영역을 설정할 수 있다 - 효율이 더 좋음.
	public /*1. synchronized (keyword)*/ void setNumber(int number) {
		synchronized(this){
		this.number = number;
		try {
			Thread.sleep(2000);
			System.out.println("number : " + getNumber());
		} catch (Exception e) {
			e.printStackTrace();
			}
		}
	}
}

class MyRunnable1 implements Runnable {
	    
		private Shared obj;
		private int number;
		
		public MyRunnable1() {
			// TODO Auto-generated constructor stub
		}
		 
		public MyRunnable1(Shared obj, int number){
			super();
			this.obj = obj;
			this.number = number;
		}
		
		//Thread 2개가 동시에 run 수행되고 공용객체를 이용해서 2개의 Thread가 동시에 공용객체 field 값을 setting 하고 출력. 
		
		@Override
	    public void run() {
			obj.setNumber(number);
	    }
}
public class Exam05_SynchronizeDOS {
	
	public static void main(String[] args) {
		Shared obj = new Shared();
		
		Thread t1 = new Thread(new MyRunnable1(obj,100));
		Thread t2 = new Thread(new MyRunnable1(obj,200));
		t1.start();
		t2.start();

	}
}
