public class ClassExtendsThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread Class: " + this.getName());
    }
}
