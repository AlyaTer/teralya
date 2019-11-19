package lab0.service;

import java.util.Optional;

public abstract class Writer<T> {

    protected final String path;

    protected Writer(String path){
        this.path = path;
    }

    public abstract Optional<T> write(T object);
}
