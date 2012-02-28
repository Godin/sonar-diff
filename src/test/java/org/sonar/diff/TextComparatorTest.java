package org.sonar.diff;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TextComparatorTest {

  @Test
  public void testEqualsWithoutWhitespace() {
    TextComparator cmp = TextComparator.DEFAULT;

    Text a = new Text("abc\nabc\na bc".getBytes());
    Text b = new Text("abc\nabc d\nab c".getBytes());

    assertThat("abc == abc", cmp.equals(a, 0, b, 0), is(true));
    assertThat("abc != abc d", cmp.equals(a, 1, b, 1), is(false));
    assertThat("a bc == ab c", cmp.equals(a, 2, b, 2), is(false));
    assertThat(cmp.hash(a, 0), equalTo(cmp.hash(b, 0)));
  }

  @Test
  public void testEqualsWithWhitespace() {
    TextComparator cmp = TextComparator.IGNORE_WHITESPACE;

    Text a = new Text("abc\nabc\na bc".getBytes());
    Text b = new Text("abc\nabc d\nab c".getBytes());

    assertThat("abc == abc", cmp.equals(a, 0, b, 0), is(true));
    assertThat("abc != abc d", cmp.equals(a, 1, b, 1), is(false));
    assertThat("a bc == ab c", cmp.equals(a, 2, b, 2), is(true));
    assertThat(cmp.hash(a, 0), equalTo(cmp.hash(b, 0)));
    assertThat(cmp.hash(a, 2), equalTo(cmp.hash(b, 2)));
  }

}
