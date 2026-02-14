package model;

public abstract class Model {
    public int id;
    public String name;

    Model(int id ,String name) {
        this.id = id;
        this.name = name;
    }
}
