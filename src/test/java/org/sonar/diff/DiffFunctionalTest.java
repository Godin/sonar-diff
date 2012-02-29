package org.sonar.diff;

import com.google.common.io.Resources;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DiffFunctionalTest {

  @Test
  public void example0() throws Exception {
    CodeChurn r = diff("example0");
    assertThat(r.getAdded(), is(3));
    assertThat(r.getDeleted(), is(0));
    assertThat(r.getDiff().size(), is(8));
  }

  @Test
  public void example1() throws Exception {
    CodeChurn r = diff("example1");
    assertThat(r.getAdded(), is(2));
    assertThat(r.getDeleted(), is(1));
    assertThat(r.getDiff().size(), is(4));
  }

  private CodeChurn diff(String name) throws IOException {
    return diff("examples/" + name + "/v1.java", "examples/" + name + "/v2.java");
  }

  private CodeChurn diff(String resourceA, String resourceB) throws IOException {
    Text a = new Text(Resources.toByteArray(Resources.getResource(resourceA)));
    Text b = new Text(Resources.toByteArray(Resources.getResource(resourceB)));
    return new CodeChurn(a, b, TextComparator.IGNORE_WHITESPACE);
  }

}
