package org.sonar.diff;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TextTest {

  @Test
  public void testEmpty() {
    Text r = new Text(new byte[0]);
    assertThat(r.length(), is(0));
  }

  @Test
  public void testTwoLines() {
    Text r = new Text("a\nb".getBytes());
    assertThat(r.length(), is(2));
  }

}
