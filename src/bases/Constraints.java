package bases;

public class Constraints {
    public float top;
    public float bottom;
    public float left;
    public float right;

    public Constraints(float top, float bottom, float left, float right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public void make(Vector2D position) {
        if (position.y < top) position.y = top;
        if (position.y > bottom) position.y = bottom;
        if (position.x < left) position.x = left;
        if (position.x > right) position.x = right;
    }
}
