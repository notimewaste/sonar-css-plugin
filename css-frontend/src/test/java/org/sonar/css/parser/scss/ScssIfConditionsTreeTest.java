/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 Tamas Kende and David RACODON
 * mailto: kende.tamas@gmail.com and david.racodon@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.css.parser.scss;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.scss.ScssIfConditionsTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssIfConditionsTreeTest extends ScssTreeTest {

  public ScssIfConditionsTreeTest() {
    super(LexicalGrammar.SCSS_IF_CONDITIONS);
  }

  @Test
  public void scssIfConditions() {
    ScssIfConditionsTree tree;

    tree = checkParsed("@if $a == 1 {}");
    assertThat(tree.elseif()).isEmpty();
    assertThat(tree.elsee()).isNull();

    tree = checkParsed("@if $a == 1 {} @else if $a == 1 {}");
    assertThat(tree.elseif()).hasSize(1);
    assertThat(tree.elsee()).isNull();

    tree = checkParsed("@if $a == 1 {} @else if $a == 1 {} @else if $a == 1 {}");
    assertThat(tree.elseif()).hasSize(2);
    assertThat(tree.elsee()).isNull();

    tree = checkParsed("@if $a == 1 {} @else if $a == 1 {} @else if $a == 1 {} @else {}");
    assertThat(tree.elseif()).hasSize(2);
    assertThat(tree.elsee()).isNotNull();

    tree = checkParsed("@if $a == 1 {} @else {}");
    assertThat(tree.elseif()).isEmpty();
    assertThat(tree.elsee()).isNotNull();
  }

  private ScssIfConditionsTree checkParsed(String toParse) {
    ScssIfConditionsTree tree = (ScssIfConditionsTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.ife()).isNotNull();
    assertThat(tree.elseif()).isNotNull();
    return tree;
  }

}
