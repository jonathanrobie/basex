package org.basex.query.func.fn;

import org.basex.query.*;
import org.basex.query.expr.*;
import org.basex.query.func.*;
import org.basex.query.util.*;
import org.basex.query.value.item.*;
import org.basex.util.*;

/**
 * Function implementation.
 *
 * @author BaseX Team 2005-17, BSD License
 * @author Christian Gruen
 */
public final class FnEmpty extends StandardFunc {
  @Override
  public Item item(final QueryContext qc, final InputInfo ii) throws QueryException {
    // if possible, retrieve single item
    final Expr ex = exprs[0];
    return Bln.get((ex.seqType().zeroOrOne() ? ex.item(qc, info) : qc.iter(ex).next()) == null);
  }

  @Override
  protected Expr opt(final CompileContext cc) {
    // ignore non-deterministic expressions (e.g.: empty(error()))
    final Expr ex = exprs[0];
    if(!ex.has(Flag.NDT)) {
      final long es = ex.size();
      if(es != -1) return Bln.get(es == 0);
      if(ex.seqType().oneOrMore()) return Bln.FALSE;
    }
    return this;
  }
}
