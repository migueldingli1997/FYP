package RE.parser.util;

import org.junit.Assert;
import org.junit.Test;

import static RE.parser.util.CommentRemover.removeComments;
import static org.junit.Assert.*;

public class CommentRemoverTest {

    @Test
    public void removeComments_test1() {
        Assert.assertEquals("", removeComments("/**/"));
    }

    @Test
    public void removeComments_test2() {
        Assert.assertEquals("", removeComments("/*comment*/"));
    }

    @Test
    public void removeComments_test3() {
        Assert.assertEquals("", removeComments("/*comment1*//*comment2*/"));
    }

    @Test
    public void removeComments_test4() {
        Assert.assertEquals("ABCDE", removeComments("A/*c1*/B/*c2*/C/*c3*/D/*c4*/E"));
    }

    @Test
    public void removeComments_test5() {
        Assert.assertEquals(" A  B  C  D  E ", removeComments(" A /*c1*/ B /*c2*/ C /*c3*/ D /*c4*/ E "));
    }

    @Test
    public void removeComments_strayCommentOpen_before() {
        Assert.assertEquals("", removeComments("/*A/*B*/"));
    }

    @Test
    public void removeComments_strayCommentOpen_after() {
        Assert.assertEquals("B/*", removeComments("/*A*/B/*"));
    }

    @Test
    public void removeComments_strayCommentClose_before() {
        Assert.assertEquals("*/A", removeComments("*/A/*B*/"));
    }

    @Test
    public void removeComments_strayCommentClose_after() {
        Assert.assertEquals("B*/", removeComments("/*A*/B*/"));
    }
}