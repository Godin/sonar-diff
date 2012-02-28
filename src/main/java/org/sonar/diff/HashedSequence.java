package org.sonar.diff;

/**
 * Wraps a {@link Sequence} to assign hash codes to elements.
 */
public class HashedSequence<S extends Sequence> implements Sequence {

  final S base;
  final int[] hashes;

  public HashedSequence(S base, int[] hashes) {
    this.base = base;
    this.hashes = hashes;
  }

  public int length() {
    return base.length();
  }

}
