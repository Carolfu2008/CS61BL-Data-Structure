public class ArraySet<Item>{
    
    private ArrayList<item> elements = new Arraylist<>();

    public boolean insert (Item e){
        for (Item elem : elements){
            if (elem.equals(e)){
                return false;
            }
        }
        elements.add(e);
        return true;
    }
}

getter.apply(a).compareTo(getter,aooly(b));

getComp(User::getEmail());

public <R> List<R> getColFilt(Function<T,R> getter, Predicate<R> pred){
    return entries.stream()
    .map(getter)//map(s ->getter.apply(s))
    .filter(pred)//fukter(s->pred.test(s))
    .collect(Colllector.toList());
}

public <R> List<R> getColFilt(Function<T,R> getter, Predicate<R> pred){
    return entries.stream()
    .filter(s->pred.test(getter.apply(s))).map(getter)
    .collect(Colllector.toList());
}