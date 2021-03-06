/*******************************************************************************
 * Copyright (c) 2003-2015 John Green All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: John Green - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.prorefactor.core.unittest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import org.prorefactor.core.JPNode;
import org.prorefactor.core.JsonNodeLister;
import org.prorefactor.core.NodeTypes;
import org.prorefactor.core.ProparseRuntimeException;
import org.prorefactor.core.unittest.util.UnitTestModule;
import org.prorefactor.refactor.RefactorSession;
import org.prorefactor.treeparser.ParseUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import antlr.Token;
import antlr.TokenStream;

/**
 * Test the tree parsers against problematic syntax. These tests just run the tree parsers against the data/bugsfixed
 * directory. If no exceptions are thrown, then the tests pass. The files in the "bugsfixed" directories are subject to
 * change, so no other tests should be added other than the expectation that they parse clean.
 */
public class BugFixTest {
  private final static String SRC_DIR = "src/test/resources/data/bugsfixed";
  private final static String TEMP_DIR = "target/nodes-lister/data/bugsfixed";

  private RefactorSession session;
  private File tempDir = new File(TEMP_DIR);

  @BeforeTest
  public void setUp() throws Exception {
    Injector injector = Guice.createInjector(new UnitTestModule());
    session = injector.getInstance(RefactorSession.class);
    session.getSchema().createAlias("foo", "sports2000");

    tempDir.mkdirs();
  }

  private ParseUnit genericTest(String file) throws Exception {
    ParseUnit pu = new ParseUnit(new File(SRC_DIR, file), session);
    assertNull(pu.getTopNode());
    assertNull(pu.getRootScope());
    pu.parse();
    pu.treeParser01();
    assertNotNull(pu.getTopNode());
    assertNotNull(pu.getRootScope());

    PrintWriter writer = new PrintWriter(new File(tempDir, file + ".json"));
    JsonNodeLister nodeLister = new JsonNodeLister(pu.getTopNode(), writer,
        new Integer[] {
            NodeTypes.LEFTPAREN, NodeTypes.RIGHTPAREN, NodeTypes.COMMA, NodeTypes.PERIOD, NodeTypes.LEXCOLON,
            NodeTypes.OBJCOLON, NodeTypes.THEN, NodeTypes.END});
    nodeLister.print();
    writer.close();

    return pu;
  }

  private TokenStream genericLex(String file) throws Exception {
    ParseUnit pu = new ParseUnit(new File(SRC_DIR, file), session);
    assertNull(pu.getTopNode());
    assertNull(pu.getRootScope());
    assertNull(pu.getMetrics());
    pu.lexAndGenerateMetrics();
    assertNotNull(pu.getMetrics());
    return pu.lex();
  }

  @Test
  public void test01() throws Exception {
    genericTest("bug01.p");
  }

  @Test
  public void test02() throws Exception {
    genericTest("bug02.p");
  }

  @Test
  public void test03() throws Exception {
    genericTest("bug03.p");
  }

  @Test
  public void test04() throws Exception {
    genericTest("bug04.p");
  }

  @Test
  public void test05() throws Exception {
    genericTest("bug05.p");
  }

  @Test
  public void test06() throws Exception {
    genericTest("bug06.p");
  }

  @Test
  public void test07() throws Exception {
    genericTest("interface07.cls");
  }

  @Test
  public void test08() throws Exception {
    genericTest("bug08.cls");
  }

  @Test
  public void test09() throws Exception {
    genericTest("bug09.p");
  }

  @Test
  public void test10() throws Exception {
    genericTest("bug10.p");
  }

  @Test
  public void test11() throws Exception {
    genericTest("bug11.p");
  }

  @Test
  public void test12() throws Exception {
    genericTest("bug12.p");
  }

  @Test
  public void test13() throws Exception {
    genericTest("bug13.p");
  }

  @Test
  public void test14() throws Exception {
    genericTest("bug14.p");
  }

  @Test
  public void test15() throws Exception {
    genericTest("bug15.p");
  }

  @Test
  public void test16() throws Exception {
    genericTest("bug16.p");
  }

  @Test
  public void test17() throws Exception {
    genericTest("bug17.p");
  }

  @Test
  public void test18() throws Exception {
    genericTest("bug18.p");
  }

  @Test
  public void test19() throws Exception {
    genericTest("bug19.p");
  }

  @Test
  public void test20() throws Exception {
    genericTest("bug20.p");
  }

  @Test
  public void test21() throws Exception {
    genericTest("bug21.cls");
  }

  @Test
  public void test22() throws Exception {
    genericTest("bug22.cls");
  }

  @Test
  public void test23() throws Exception {
    genericTest("bug23.cls");
  }

  @Test
  public void test24() throws Exception {
    genericTest("bug24.p");
  }

  @Test
  public void test25() throws Exception {
    genericTest("bug25.p");
  }

  @Test
  public void test26() throws Exception {
    genericTest("bug26.cls");
  }

  @Test
  public void test27() throws Exception {
    genericTest("bug27.cls");
  }

  @Test
  public void test28() throws Exception {
    genericTest("bug28.cls");
  }

  @Test
  public void test29() throws Exception {
    genericTest("bug29.p");
  }

  @Test
  public void test30() throws Exception {
    genericTest("bug30.p");
  }

  @Test
  public void test31() throws Exception {
    genericTest("bug31.cls");
  }

  @Test
  public void test32() throws Exception {
    genericLex("bug32.i");
  }

  @Test
  public void test33() throws Exception {
    genericTest("bug33.cls");
  }

  // Next two tests : same exception should be thrown in both cases
  @Test(expectedExceptions = {ProparseRuntimeException.class})
  public void testCache1() throws Exception {
    genericTest("CacheChild.cls");
  }

  @Test(expectedExceptions = {ProparseRuntimeException.class})
  public void testCache2() throws Exception {
    genericTest("CacheChild.cls");
  }

  @Test
  public void testSaxWriter() throws Exception {
    genericTest("sax-writer.p");
  }

  @Test
  public void testNoBox() throws Exception {
    genericTest("nobox.p");
  }

  @Test
  public void testIncludeInComment() throws Exception {
    genericTest("include_comment.p");
  }

  @Test
  public void testCreateComObject() throws Exception {
    ParseUnit unit = genericTest("createComObject.p");
    List<JPNode> list = unit.getTopNode().query(NodeTypes.CREATE);
    // COM automation
    assertEquals(list.get(0).getLine(), 3);
    assertEquals(list.get(0).getState2(), NodeTypes.Automationobject);
    assertEquals(list.get(1).getLine(), 4);
    assertEquals(list.get(1).getState2(), NodeTypes.Automationobject);
    // Widgets
    assertEquals(list.get(2).getLine(), 8);
    assertEquals(list.get(2).getState2(), NodeTypes.WIDGET);
    assertEquals(list.get(3).getLine(), 12);
    assertEquals(list.get(3).getState2(), NodeTypes.WIDGET);
    // Ambiguous
    assertEquals(list.get(4).getLine(), 15);
    assertEquals(list.get(4).getState2(), NodeTypes.WIDGET);
  }

  @Test
  public void testCopyLob() throws Exception {
    genericTest("copylob.p");
  }

  @Test
  public void testGetDbClient() throws Exception {
    genericTest("getdbclient.p");
  }

  @Test
  public void testDoubleColon() throws Exception {
    genericTest("double-colon.p");
  }

  @Test
  public void testTildeInComment() throws Exception {
    TokenStream stream = genericLex("comment-tilde.p");
    Token tok = stream.nextToken();
    assertEquals(tok.getType(), NodeTypes.COMMENT);
    assertEquals(tok.getText(), "// \"~n\"");
    assertEquals(stream.nextToken().getType(), NodeTypes.WS);
    assertEquals(stream.nextToken().getType(), NodeTypes.DEFINE);
  }

  @Test
  public void testTildeInComment2() throws Exception {
    TokenStream stream = genericLex("comment-tilde2.p");
    assertEquals(stream.nextToken().getType(), NodeTypes.DEFINE);
    assertEquals(stream.nextToken().getType(), NodeTypes.WS);
    Token tok = stream.nextToken();
    assertEquals(tok.getType(), NodeTypes.COMMENT);
    assertEquals(tok.getText(), "// \"~n\"");
  }

  @Test
  public void testLexer01() throws Exception {
    TokenStream stream = genericLex("lex.p");
  }

  @Test
  public void testDataset() throws Exception {
    genericTest("DatasetParentFields.p");
  }
}
