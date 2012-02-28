package org.sonar.diff;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Text is a {@link Sequence} of lines.
 */
public class Text implements Sequence {

  final byte[] content;

  /**
   * Map of line number to starting position within {@link #content}.
   */
  final List<Integer> lines;

  public Text(byte[] bytes) {
    this.content = bytes;
    lines = lineMap(content, 0, content.length);
  }

  public int length() {
    return lines.size() - 2;
  }

  /**
   * Get the text for a single line.
   */
  public String getString(int line) {
    int s = getStart(line);
    int e = getEnd(line);
    return new String(content, s, e - s);
  }

  private int getStart(final int line) {
    return lines.get(line + 1);
  }

  private int getEnd(final int line) {
    return lines.get(line + 2);
  }

  private static List<Integer> lineMap(final byte[] buf, int ptr, int end) {
    List<Integer> lines = Lists.newArrayList();
    lines.add(Integer.MIN_VALUE);
    for (; ptr < end; ptr = nextLF(buf, ptr)) {
      lines.add(ptr);
    }
    lines.add(end);
    return lines;
  }

  private static final int nextLF(final byte[] b, int ptr) {
    return next(b, ptr, '\n');
  }

  private static final int next(final byte[] b, int ptr, final char chrA) {
    final int sz = b.length;
    while (ptr < sz) {
      if (b[ptr++] == chrA)
        return ptr;
    }
    return ptr;
  }

}
