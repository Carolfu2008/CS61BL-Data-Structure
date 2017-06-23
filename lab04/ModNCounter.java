/**
 * Created by lifesaver on 23/06/2017.
 */
public class ModNCounter extends Counter {

    int mod;

    public ModNCounter(int x){
        super();
        this.mod=x;
    }

    public int value() {
        return super.getCount()%mod;
    }

    public static void main(String [] args){
        /*ModNCounter modCounter = new ModNCounter(3);
        modCounter.increment();
        System.out.println(modCounter.value()); // prints 1
        modCounter.reset();
        modCounter.increment();
        System.out.println(modCounter.value());*/
        ModNCounter modCounter = new ModNCounter(3);
        modCounter.increment();
        modCounter.increment();
        modCounter.increment();
        modCounter.increment();
        System.out.println(modCounter.value());
    }

}
