package main.java.memoranda;

public enum UserType {
    MEMBER, TRAINER, OWNER;

    public UserType becomeTrainer() {
        if(this == MEMBER) {
            return TRAINER;
        }
        else if(this == TRAINER) {
            System.out.println("Already a Trainer");
            return this;
        }
        else if(this == OWNER) {
            System.out.println("Current User is an owner. They cannot downgrade.");
            return this;
        }
        else {
            System.out.println("Error upgrading UserType");
            return this;
        }
    }
}
