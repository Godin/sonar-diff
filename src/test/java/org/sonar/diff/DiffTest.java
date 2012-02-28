package org.sonar.diff;

import org.junit.Test;
import org.sonar.diff.Edit.Type;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DiffTest {

  @Test
  public void emptyInputs() {
    List<Edit> r = diff(t(""), t(""));
    assertThat(r.isEmpty(), is(true));
  }

  @Test
  public void createFile() {
    List<Edit> r = diff(t(""), t("AB"));
    assertThat(r.size(), is(1));
    assertThat(r.get(0), is(new Edit(Type.INSERT, -1, -1, 0, 1)));
  }

  @Test
  public void deleteFile() {
    List<Edit> r = diff(t("AB"), t(""));
    assertThat(r.size(), is(0));
  }

  @Test
  public void insertMiddle() {
    List<Edit> r = diff(t("ac"), t("aBc"));
    assertThat(r.size(), is(3));
    assertThat(r.get(0), is(new Edit(Type.MOVE, 0, 0, 0, 0)));
    assertThat(r.get(1), is(new Edit(Type.INSERT, -1, -1, 1, 1)));
    assertThat(r.get(2), is(new Edit(Type.MOVE, 1, 1, 2, 2)));
  }

  @Test
  public void deleteMiddle() {
    List<Edit> r = diff(t("aBc"), t("ac"));
    assertThat(r.size(), is(2));
    assertThat(r.get(0), is(new Edit(Type.MOVE, 0, 0, 0, 0)));
    assertThat(r.get(1), is(new Edit(Type.MOVE, 2, 2, 1, 1)));
  }

  @Test
  public void replaceMiddle() {
    List<Edit> r = diff(t("aBc"), t("aDc"));
    assertThat(r.size(), is(3));
    assertThat(r.get(0), is(new Edit(Type.MOVE, 0, 0, 0, 0)));
    assertThat(r.get(1), is(new Edit(Type.INSERT, -1, -1, 1, 1)));
    assertThat(r.get(2), is(new Edit(Type.MOVE, 2, 2, 2, 2)));
  }

  @Test
  public void insertStart() {
    List<Edit> r = diff(t("bc"), t("Abc"));
    assertThat(r.size(), is(2));
    assertThat(r.get(0), is(new Edit(Type.INSERT, -1, -1, 0, 0)));
    assertThat(r.get(1), is(new Edit(Type.MOVE, 0, 1, 1, 2)));
  }

  @Test
  public void deleteStart() {
    List<Edit> r = diff(t("Abc"), t("bc"));
    assertThat(r.size(), is(1));
    assertThat(r.get(0), is(new Edit(Type.MOVE, 1, 2, 0, 1)));
  }

  @Test
  public void insertEnd() {
    List<Edit> r = diff(t("ab"), t("abC"));
    assertThat(r.size(), is(2));
    assertThat(r.get(0), is(new Edit(Type.MOVE, 0, 1, 0, 1)));
    assertThat(r.get(1), is(new Edit(Type.INSERT, -1, -1, 2, 2)));
  }

  @Test
  public void deleteEnd() {
    List<Edit> r = diff(t("abC"), t("ab"));
    assertThat(r.size(), is(1));
    assertThat(r.get(0), is(new Edit(Type.MOVE, 0, 1, 0, 1)));
  }

  /**
   * This is important special case, for which other algorithms can not detect movement.
   */
  @Test
  public void move() {
    List<Edit> r = diff(t("Abc"), t("bcA"));
    assertThat(r.size(), is(2));
    assertThat(r.get(0), is(new Edit(Type.MOVE, 1, 2, 0, 1)));
    assertThat(r.get(1), is(new Edit(Type.MOVE, 0, 0, 2, 2)));
  }

  private List<Edit> diff(Text a, Text b) {
    return new DiffAlgorithm().diff(a, b, TextComparator.DEFAULT);
  }

  public static Text t(String text) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
      sb.append(text.charAt(i)).append('\n');
    }
    try {
      return new Text(sb.toString().getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

}
