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
   *
   * If two items are equal according to this comparator's
   * {@link #equals(Sequence, int, Sequence, int)} method,
   * then this hash method must produce the same integer result for both items.
   * However not required to have different hash values for different items.
   */
  public abstract int hash(S seq, int i);

}
