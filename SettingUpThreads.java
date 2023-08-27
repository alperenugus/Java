public class SettingUpThreads {

    static class ExecuteMe implements Runnable {
        @Override
        public void run(){
            System.out.println("Thread3 task!");
        }
    }

    static class ThreadSubClass extends Thread {
        @Override
        public void run(){
            System.out.println("Thread4 task!");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // Thread can be created by passing a Runnable object with run method overriden
        Thread thread1 = new Thread(new Runnable() {
            // Tell the thread what task to execute
            @Override
            public void run(){
                try{
                    System.out.println("Thread1 task!");
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            try{
                System.out.println("Thread2 task!");
            }
            catch (Exception e){
                System.out.println(e);
            }
        });

        Thread thread3 = new Thread(new ExecuteMe());

        ThreadSubClass thread4 = new ThreadSubClass();

        thread1.run();
        thread2.run();
        thread3.run();
        thread4.run();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

    }

}
