package org.basex.query.func.unit;

import static org.basex.query.QueryError.*;

import org.basex.query.*;
import org.basex.query.func.fn.*;
import org.basex.query.iter.*;
import org.basex.query.value.item.*;
import org.basex.util.*;

/**
 * Function implementation.
 *
 * @author BaseX Team 2005-17, BSD License
 * @author Christian Gruen
 */
public final class UnitAssertEquals extends UnitFn {
  @Override
  public Item item(final QueryContext qc, final InputInfo ii) throws QueryException {
    final Iter iter1 = qc.iter(exprs[0]), iter2 = qc.iter(exprs[1]);
    final DeepEqual comp = new DeepEqual(info);
    Item it1, it2;
    int c = 1;
    while(true) {
      it1 = iter1.next();
      it2 = iter2.next();
      final boolean empty1 = it1 == null, empty2 = it2 == null;
      if(empty1 && empty2) return null;
      if(empty1 || empty2 || !comp.equal(it1.iter(), it2.iter())) break;
      c++;
      qc.checkStop();
    }
    final Item it = toNodeOrAtomItem(2, qc);
    throw new UnitException(info, UNIT_FAIL_X_X_X, it1, it2, c).value(it);
  }
}
