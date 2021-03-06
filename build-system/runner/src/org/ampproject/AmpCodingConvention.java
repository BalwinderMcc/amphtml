/**
 * Copyright 2016 The AMP HTML Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS-IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ampproject;

import com.google.javascript.jscomp.CodingConvention;
import com.google.javascript.jscomp.CodingConventions;
import com.google.javascript.jscomp.ClosureCodingConvention;


/**
 * A coding convention for AMP.
 */
public final class AmpCodingConvention extends CodingConventions.Proxy {
  /** By default, decorate the ClosureCodingConvention. */
  public AmpCodingConvention() {
    this(new ClosureCodingConvention());
  }

  /** Decorates a wrapped CodingConvention. */
  public AmpCodingConvention(CodingConvention convention) {
    super(convention);
  }

  /**
   * {@inheritDoc}
   * Because AMP objects can travel between compilation units, we consider
   * non-private methods exported.
   * Should we decide to do full-program compilation (for version bound JS
   * delivery), this could go away there.
   */
  @Override public boolean isExported(String name, boolean local) {
    if (local) {
      return false;
    }
    // ES6 generated module names are not exported.
    if (name.contains("$")) {
      return false;
    }
    // We do not export class names and similar by default.
    if (Character.isUpperCase(name.charAt(0))) {
      return false;
    }
    // Starting with _ explicitly exports a name.
    if (name.startsWith("_")) {
      return true;
    }
    return !name.endsWith("_") && !name.endsWith("ForTesting");
  }
}
