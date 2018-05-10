import com.guoys.platform.commons.util.MD5Utils;
import org.junit.Test;

/**
 * Created by gys on 2018/1/2.
 */
public class Tester {

    @Test
    public void test(){
        String s = MD5Utils.md5("1");
        System.out.println(s);
    }
}
