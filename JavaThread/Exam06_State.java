package JavaThread;

// 이 클래스로부터는 instance를 우리가 원하는 만큼 외부에서 생성할 수 있다. 
// 프로그램 차원에서 해당 클래스로부터 instance를 딱 하나만 생성하도록 제한 두는 것 가능. 
// 이렇게 만들어 내는 것 -> single tone 

class Exam06_Shared {
	private static Exam06_Shared obj = new Exam06_Shared();
	
	public static Exam06_Shared getInstance() {
		return obj;
	}
	//singleton 패턴 
	private Exam06_Shared() {
		
	}
	// method 자체 동기화 -> 효율이 좋지 않음 -> synchronized 영역 설정 

	public synchronized void printName(){
		for(int i=0; i<10; i++){
			try {
			Thread.sleep(1000); //현재 실행되는 thread를 sleep 상태로 전화 - thread block 한다는 
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // try catch는 예외처리 sleep 쓸 때 반드시 사용 
			
			notify(); // 현재 wait()에 의해 멈춰있는 thread를 runnable 상태로 전환
			try {
				wait();
			} catch (InterruptedException e) {
					e.printStackTrace();
				} // try catch는 예외처리 sleep 쓸 때 반드시 사용 
				

			System.out.println(Thread.currentThread().getName());
		
		} 
	}
}

class Exam06_Runnable implements Runnable {

	private Exam06_Shared shared;
	
	public Exam06_Runnable() {
		
	}
	
	public Exam06_Runnable(Exam06_Shared shared) {
		this.shared = shared;
	}
	
	@Override
	public void run() {
		shared.printName();
	}
	
}

public class Exam06_State {

	public static void main(String[] args) {
		
		// 공용객체 생성(singleton)
		Exam06_Shared obj = Exam06_Shared.getInstance();
		// Thread가 공용객체를 이용하는 것의 의미 = 공용객체를 Thread에게 injection 한다는 것 의미 
		Thread t1 = new Thread(new Exam06_Runnable(obj));
		t1.setName("A");
		Thread t2 = new Thread(new Exam06_Runnable(obj));
		t2.setName("B");
		
		t1.start();
		t2.start();
		
	}

}
