package org.sonar.diff;

/**
 * Equivalence function for a {@link Sequence}.
 */
public abstract class SequenceComparator<S extends Sequence> {

  /**
   * Compare two items to determine if they are equivalent.
   */
  public abstract boolean equals(S a, int ai, S b, int bi);

  /**
   * Get a hash value for an item in a sequence.
   */
  public abstract int hash(S seq, int i);

}
