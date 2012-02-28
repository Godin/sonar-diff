package org.sonar.diff;

/**
 * Equivalence function for {@link Text}.
 */
public class TextComparator extends SequenceComparator<Text> {

  @Override
  public boolean equals(Text a, int ai, Text b, int bi) {
    ai++;
    bi++;
    int as = a.lines.get(ai);
    int bs = b.lines.get(bi);
    int ae = a.lines.get(ai + 1);
    int be = b.lines.get(bi + 1);
    if (ae - as != be - bs) {
      return false;
    }
    while (as < ae) {
      if (a.content[as++] != b.content[bs++])
        return false;
    }
    return true;
  }

  @Override
  public int hash(Text seq, int line) {
    final int begin = seq.lines.get(line + 1);
    final int end = seq.lines.get(line + 2);
    return hashRegion(seq.content, begin, end);
  }

  protected int hashRegion(final byte[] raw, int start, final int end) {
    int hash = 5381;
    for (; start < end; start++) {
      hash = ((hash << 5) + hash) + (raw[start] & 0xff);
    }
    return hash;
  }

}
