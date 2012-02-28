package org.sonar.diff;

import com.google.common.base.Objects;

public class Edit {

  public static enum Type {
    INSERT,
    MOVE
  }

  int beginA;
  int endA;
  int beginB;
  int endB;
  final Type type;

  public Edit(Type type, int beginA, int endA, int beginB, int endB) {
    this.beginA = beginA;
    this.endA = endA;
    this.beginB = beginB;
    this.endB = endB;
    this.type = type;
  }

  public Type getType() {
    return type;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Edit)) {
      return false;
    }
    Edit e = (Edit) obj;
    return type == e.type
      && beginA == e.beginA
      && endA == e.endA
      && beginB == e.beginB
      && endB == e.endB;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("type", type)
        .add("beginA", beginA)
        .add("endA", endA)
        .add("beginB", beginB)
        .add("endB", endB)
        .toString();
  }

}
