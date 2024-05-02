import org.junit.Test;
import static org.junit.Assert.*;
import com.example.myapplication.tokenizer.Tokenizer;


public class TokenizerTest {
    String text1 = "";
    String tetx2 = "";
    String text3 = "";
    String text4 = "";




    @Test(timeout = 1000)
    public void testTokenizerEquals(){
        Tokenizer tokenizer1 = new Tokenizer(text1);
        Tokenizer tokenizer2 = new Tokenizer(text2);



    }
    @Test(timeout = 1000)
    public void testTokenizerNotEquals(){
        Tokenizer tokenizer3 = new Tokenizer(text3);
        Tokenizer tokenizer4 = new Tokenizer(text4);



    }
}