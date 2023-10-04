package model;

/**
 Representing the state of an entity
 */
public enum EntityState {
    IDLE,RUNNING,JUMP,FALLING,ATTACK,HIT,DEAD;


    // EFFECTS: get the corresponding index in the image arr
    public int getState() {
        switch (this) {
            case IDLE:
                return 0;
            case RUNNING:
                return 1;
            case JUMP:
                return 2;
            case FALLING:
                return 3;

            case ATTACK:
                return 4;

            case HIT:
                return 5;

            default:
                return 6;

        }
    }

    // EFFECTS: get the corresponding number of images of a state
    public int getSprite() {
        switch (this) {
            case DEAD:
                return 8;
            case RUNNING:
                return 6;
            case IDLE:
                return 5;
            case HIT:
                return 4;
            case JUMP:
            case ATTACK:
                return 3;
            case FALLING:
            default:
                return 1;

        }
    }

}
