package org.sonar.diff;

import com.google.common.io.Files;

import java.io.File;
import java.util.BitSet;
import java.util.List;

public final class CodeChurn {

  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("2 arguments required");
      System.exit(1);
    }
    try {
      Text a = new Text(Files.toByteArray(new File(args[0])));
      Text b = new Text(Files.toByteArray(new File(args[1])));
      CodeChurn r = new CodeChurn(a, b, TextComparator.IGNORE_WHITESPACE);
      System.out.println("Deleted: " + r.getDeleted());
      System.out.println("Added:   " + r.getAdded());
      for (Edit edit : r.getDiff()) {
        System.out.println(edit);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public CodeChurn(Text a, Text b, TextComparator cmp) {
    diff = new DiffAlgorithm().diff(a, b, cmp);

    int added = 0;
    int deleted = 0;
    BitSet remains = new BitSet(a.length());
    for (Edit edit : diff) {
      switch (edit.getType()) {
        case INSERT:
          added += edit.endB - edit.beginB + 1;
          break;
        case MOVE:
          for (int i = edit.beginA; i <= edit.endA; i++) {
            remains.set(i);
          }
          break;
        default:
          throw new IllegalStateException();
      }
    }
    for (int i = 0; i < a.length(); i++) {
      if (!remains.get(i)) {
        deleted++;
      }
    }

    this.added = added;
    this.deleted = deleted;
  }

  private final List<Edit> diff;
  private final int added;
  private final int deleted;

  public int getAdded() {
    return added;
  }

  public int getDeleted() {
    return deleted;
  }

  public List<Edit> getDiff() {
    return diff;
  }

}
