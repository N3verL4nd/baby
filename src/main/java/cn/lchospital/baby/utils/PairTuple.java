package cn.lchospital.baby.utils;

/**
 * @author n3verl4nd
 * @date 2020/8/10
 */
public class PairTuple<T, V> {

    private T left;
    private V right;

    private PairTuple(T left, V right) {
        this.left = left;
        this.right = right;
    }

    public static <T, V> PairTuple<T, V> of(T left, V right) {
        return new PairTuple<>(left, right);
    }

    public T getLeft() {
        return left;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public V getRight() {
        return right;
    }

    public void setRight(V right) {
        this.right = right;
    }
}