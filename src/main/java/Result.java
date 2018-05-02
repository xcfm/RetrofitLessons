public class Result<T> {
    public T data;
    public int code;

    public Result(T res, int code) {
        this.data = res;
        this.code = code;
    }

    public static Result<String> success(String res) {
        return new Result<>(res, 0);
    }
}
