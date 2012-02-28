package org.sonar.diff;

/**
 * Wrap another {@link SequenceComparator} for use with {@link HashedSequence}.
 */
public class HashedSequenceComparator<S extends Sequence> extends SequenceComparator<HashedSequence<S>> {

  private final SequenceComparator<? super S> cmp;

  public HashedSequenceComparator(SequenceComparator<? super S> cmp) {
    this.cmp = cmp;
  }

  @Override
  public boolean equals(HashedSequence<S> a, int ai, HashedSequence<S> b, int bi) {
    return a.hashes[ai] == b.hashes[bi] && cmp.equals(a.base, ai, b.base, bi);
  }

  @Override
  public int hash(HashedSequence<S> seq, int i) {
    return seq.hashes[i];
  }

}
