package JavaThread;

public class Exam01_Basic {

	public static void main(String[] args) {
		
		//현재 Thread의 이름을 화면에 출력!
		System.out.println(Thread.currentThread().getName());
		
		//Thread instance 생성 가능
		Thread thread = new MyThread();
		// 단순히 Thread instance 생성한 것. 아직 실행되지는 않음.
		thread.start();
		
		Runnable runnable = new MyRunnable();
		//Runnable interface를 구현한 객체 생성. 아직 Thread가 아님!
		Thread t = new Thread(runnable);
		t.start();
		
		//Thread를 색다르게 만들어 보기
		Thread t1 = new Thread(new Runnable(){
			@Override
			public void run(){
				System.out.println(Thread.currentThread().getName());
			}
		});
		
		//위의 것을 축약형으로 사용 가능. Lambda (Java 8이상에서 사용가능)
		Thread t2 = new Thread(()->{
			System.out.println(Thread.currentThread().getName());
		});
	} 

}

// 1.Thread는 instance 형태로 존재하고 당연히 class 있어야함. 특정 class 상속해서 class 만들어야함.

class MyThread extends Thread {

	//나중에 thread scheduler에 의해 선택되면 특수한 method가 실행된다.

	@Override
	public void run() {
		// 독립적인 실행흐름.
		System.out.println(Thread.currentThread().getName());
	}
	
}

// 2. runnable interface 이용해서 클래스 작성
class MyRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		
	}
	
}