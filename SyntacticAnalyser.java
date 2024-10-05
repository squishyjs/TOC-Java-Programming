import java.util.ArrayDeque; // used for DFA -> look up token in parse table
import java.util.Arrays;



import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;



class PDA {
	ArrayDeque<TreeNode> stack = new ArrayDeque<>();
	HashMap<Pair<TreeNode.Label, Token>, ArrayList<TreeNode>> parsingTable = new HashMap<>(); // parsing table
	ParseTree parseTree;

	public PDA() {

		// push the starting variable into the stack
		TreeNode prog = new TreeNode(TreeNode.Label.prog, null); 
		prog.setParent(prog);
		stack.add(prog);
		this.parseTree = new ParseTree(prog);


		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.prog, new Token(Token.TokenType.PUBLIC)), 
		
			new ArrayList<TreeNode>(Arrays.asList(

			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PUBLIC), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.CLASS), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PUBLIC), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.STATIC), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.VOID), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.MAIN), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.STRINGARR), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ARGS), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), null),
			new TreeNode(TreeNode.Label.los, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), null)
		)));

		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.stat, null),
			new TreeNode(TreeNode.Label.los, null)
		)));


		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.RBRACE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));


		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.boolexpr, new Token(Token.TokenType.AND)), new ArrayList<TreeNode>(Arrays.asList(	
			new TreeNode(TreeNode.Label.boolop, null),
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));


		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.stat, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.decl, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), null)
		)));


		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.stat, new Token(Token.TokenType.SEMICOLON)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), null)
		)));


		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.decl, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.type, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), null),
			new TreeNode(TreeNode.Label.possassign, null)
		)));


		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.possassign, new Token(Token.TokenType.ASSIGN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ASSIGN), null),
			new TreeNode(TreeNode.Label.expr, null)
		)));


		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.possassign, new Token(Token.TokenType.SEMICOLON)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));


		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.prog, new Token(Token.TokenType.PUBLIC)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PUBLIC), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.CLASS), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PUBLIC), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.STATIC), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.VOID), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.MAIN), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.STRINGARR), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ARGS), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), null),
			new TreeNode(TreeNode.Label.los, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.FOR)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.stat, null),
			new TreeNode(TreeNode.Label.los, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.stat, null),
			new TreeNode(TreeNode.Label.los, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.PRINT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.stat, null),
			new TreeNode(TreeNode.Label.los, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.WHILE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.stat, null),
			new TreeNode(TreeNode.Label.los, null)
		)));


		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.SEMICOLON)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.stat, null),
			new TreeNode(TreeNode.Label.los, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.stat, null),
			new TreeNode(TreeNode.Label.los, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.TYPE, "char")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.stat, null),
			new TreeNode(TreeNode.Label.los, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.IF)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.stat, null),
			new TreeNode(TreeNode.Label.los, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.TYPE, "boolean")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.stat, null),
			new TreeNode(TreeNode.Label.los, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.RBRACE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.stat, new Token(Token.TokenType.WHILE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.whilestat, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.stat, new Token(Token.TokenType.FOR)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.forstat, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.stat, new Token(Token.TokenType.IF)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.ifstat, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.stat, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.assign, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.stat, new Token(Token.TokenType.TYPE, "char")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.decl, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.stat, new Token(Token.TokenType.TYPE, "boolean")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.decl, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.stat, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.decl, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.stat, new Token(Token.TokenType.PRINT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.print, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.stat, new Token(Token.TokenType.SEMICOLON)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.whilestat, new Token(Token.TokenType.WHILE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.WHILE), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), null),
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), null),
			new TreeNode(TreeNode.Label.los, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.forstat, new Token(Token.TokenType.FOR)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.FOR), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), null),
			new TreeNode(TreeNode.Label.forstart, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), null),
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), null),
			new TreeNode(TreeNode.Label.forarith, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), null),
			new TreeNode(TreeNode.Label.los, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.forstart, new Token(Token.TokenType.TYPE, "char")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.decl, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.forstart, new Token(Token.TokenType.TYPE, "boolean")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.decl, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.forstart, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.decl, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.forstart, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.assign, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.forstart, new Token(Token.TokenType.SEMICOLON)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.forarith, new Token(Token.TokenType.LPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.arithexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.forarith, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.arithexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.forarith, new Token(Token.TokenType.NUM)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.arithexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.forarith, new Token(Token.TokenType.RPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.ifstat, new Token(Token.TokenType.IF)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.IF), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), null),
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), null),
			new TreeNode(TreeNode.Label.los, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), null),
			new TreeNode(TreeNode.Label.elseifstat, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.elseifstat, new Token(Token.TokenType.ELSE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.elseorelseif, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), null),
			new TreeNode(TreeNode.Label.los, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), null),
			new TreeNode(TreeNode.Label.elseifstat, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.elseifstat, new Token(Token.TokenType.LPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.elseifstat, new Token(Token.TokenType.FOR)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.elseifstat, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.elseifstat, new Token(Token.TokenType.PRINT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.elseifstat, new Token(Token.TokenType.WHILE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.elseifstat, new Token(Token.TokenType.SEMICOLON)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.elseifstat, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.elseifstat, new Token(Token.TokenType.TYPE, "char")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.elseifstat, new Token(Token.TokenType.IF)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.elseifstat, new Token(Token.TokenType.TYPE, "boolean")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.elseifstat, new Token(Token.TokenType.RBRACE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.elseorelseif, new Token(Token.TokenType.ELSE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ELSE), null),
			new TreeNode(TreeNode.Label.possif, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.possif, new Token(Token.TokenType.IF)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.IF), null),

			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), null),
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), null),
			new TreeNode(TreeNode.Label.los, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), null),
			new TreeNode(TreeNode.Label.elseifstat, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.possif, new Token(Token.TokenType.LBRACE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.assign, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ASSIGN), null),
			new TreeNode(TreeNode.Label.expr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.decl, new Token(Token.TokenType.TYPE, "char")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.type, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), null),
			new TreeNode(TreeNode.Label.possassign, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.decl, new Token(Token.TokenType.TYPE, "boolean")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.type, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), null),
			new TreeNode(TreeNode.Label.possassign, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.decl, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.type, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), null),
			new TreeNode(TreeNode.Label.possassign, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.possassign, new Token(Token.TokenType.ASSIGN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ASSIGN), null),
			new TreeNode(TreeNode.Label.expr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.possassign, new Token(Token.TokenType.SEMICOLON)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.print, new Token(Token.TokenType.PRINT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PRINT), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), null),
			new TreeNode(TreeNode.Label.printexpr, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.type, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.TYPE, "int"), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.type, new Token(Token.TokenType.TYPE, "boolean")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.TYPE, "boolean"), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.type, new Token(Token.TokenType.TYPE, "char")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.CHARLIT), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.expr, new Token(Token.TokenType.LPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.expr, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.expr, new Token(Token.TokenType.TRUE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.expr, new Token(Token.TokenType.NUM)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.expr, new Token(Token.TokenType.FALSE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.expr, new Token(Token.TokenType.SQUOTE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.charexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.charexpr, new Token(Token.TokenType.SQUOTE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SQUOTE), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.CHARLIT), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SQUOTE), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.boolexpr, new Token(Token.TokenType.NEQUAL)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.boolop, null),
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.boolexpr, new Token(Token.TokenType.OR)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.boolop, null),
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.boolexpr, new Token(Token.TokenType.EQUAL)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.boolop, null),
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.boolexpr, new Token(Token.TokenType.AND)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.boolop, null),
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.boolexpr, new Token(Token.TokenType.SEMICOLON)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.boolexpr, new Token(Token.TokenType.RPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.boolop, new Token(Token.TokenType.NEQUAL)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.booleq, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.boolop, new Token(Token.TokenType.EQUAL)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.booleq, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.boolop, new Token(Token.TokenType.OR)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.boollog, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.boolop, new Token(Token.TokenType.AND)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.boollog, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.booleq, new Token(Token.TokenType.EQUAL)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.EQUAL), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.booleq, new Token(Token.TokenType.NEQUAL)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.NEQUAL), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.boollog, new Token(Token.TokenType.AND)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.AND), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.boollog, new Token(Token.TokenType.OR)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.OR), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexpr, new Token(Token.TokenType.LPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.arithexpr, null),
			new TreeNode(TreeNode.Label.relexprprime, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexpr, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.arithexpr, null),
			new TreeNode(TreeNode.Label.relexprprime, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexpr, new Token(Token.TokenType.NUM)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.arithexpr, null),
			new TreeNode(TreeNode.Label.relexprprime, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexpr, new Token(Token.TokenType.TRUE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.TRUE), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexpr, new Token(Token.TokenType.FALSE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.FALSE), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.LT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relop, null),
			new TreeNode(TreeNode.Label.arithexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.LE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relop, null),
			new TreeNode(TreeNode.Label.arithexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.GT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relop, null),
			new TreeNode(TreeNode.Label.arithexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.GE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relop, null),
			new TreeNode(TreeNode.Label.arithexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.LPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.FOR)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.EQUAL)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.TYPE, "char")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.LBRACE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.OR)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.RBRACE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.PRINT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.WHILE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.AND)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.SEMICOLON)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.RPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.IF)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.NEQUAL)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relexprprime, new Token(Token.TokenType.TYPE, "boolean")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relop, new Token(Token.TokenType.LT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LT), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relop, new Token(Token.TokenType.LE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LE), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relop, new Token(Token.TokenType.GT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.GT), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.relop, new Token(Token.TokenType.GE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.GE), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexpr, new Token(Token.TokenType.LPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.term, null),
			new TreeNode(TreeNode.Label.arithexprprime, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexpr, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.term, null),
			new TreeNode(TreeNode.Label.arithexprprime, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexpr, new Token(Token.TokenType.NUM)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.term, null),
			new TreeNode(TreeNode.Label.arithexprprime, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.PLUS)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PLUS), null),
			new TreeNode(TreeNode.Label.term, null),
			new TreeNode(TreeNode.Label.arithexprprime, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.MINUS)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.MINUS), null),
			new TreeNode(TreeNode.Label.term, null),
			new TreeNode(TreeNode.Label.arithexprprime, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.LPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.FOR)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.LT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.EQUAL)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.TYPE, "char")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.GT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.LBRACE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.OR)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.RBRACE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.PRINT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.WHILE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.LE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.AND)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.RPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.SEMICOLON)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.IF)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.NEQUAL)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.TYPE, "boolean")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.arithexprprime, new Token(Token.TokenType.GE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.term, new Token(Token.TokenType.LPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.factor, null),
			new TreeNode(TreeNode.Label.termprime, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.term, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.factor, null),
			new TreeNode(TreeNode.Label.termprime, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.term, new Token(Token.TokenType.NUM)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.factor, null),
			new TreeNode(TreeNode.Label.termprime, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.TIMES)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.TIMES), null),
			new TreeNode(TreeNode.Label.factor, null),
			new TreeNode(TreeNode.Label.termprime, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.DIVIDE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.DIVIDE), null),
			new TreeNode(TreeNode.Label.factor, null),
			new TreeNode(TreeNode.Label.termprime, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.MOD)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.MOD), null),
			new TreeNode(TreeNode.Label.factor, null),
			new TreeNode(TreeNode.Label.termprime, null)
		)));

		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.EQUAL)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));


		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.LPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.FOR)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.LT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.ASSIGN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.TYPE, "char")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.MINUS)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.GT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.LBRACE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.PLUS)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.OR)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.RBRACE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.PRINT)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.WHILE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.LE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.AND)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.RPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.SEMICOLON)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.IF)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.NEQUAL)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.TYPE, "boolean")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.termprime, new Token(Token.TokenType.GE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.factor, new Token(Token.TokenType.LPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), null),
			new TreeNode(TreeNode.Label.arithexpr, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.factor, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.factor, new Token(Token.TokenType.NUM)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.NUM), null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.printexpr, new Token(Token.TokenType.LPAREN)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.printexpr, new Token(Token.TokenType.ID)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.printexpr, new Token(Token.TokenType.TRUE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.printexpr, new Token(Token.TokenType.NUM)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.printexpr, new Token(Token.TokenType.FALSE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.relexpr, null),
			new TreeNode(TreeNode.Label.boolexpr, null)
		)));



		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.printexpr, new Token(Token.TokenType.DQUOTE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.DQUOTE), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.STRINGLIT), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.DQUOTE), null)
		)));

	}

	public ArrayList<TreeNode> parsingTableLookUp(TreeNode.Label variable, Token terminal) throws SyntaxException {
		Pair<TreeNode.Label, Token> lookupKey = new Pair<TreeNode.Label, Token>(variable,  new Token(terminal.getType()));
		if (terminal.getType() == Token.TokenType.TYPE) {
			lookupKey = new Pair<TreeNode.Label, Token>(variable,  new Token(terminal.getType(), terminal.getValue().get()));
			if (parsingTable.containsKey(lookupKey)) {

				System.out.println("correct: " + lookupKey +"   rule " + parsingTable.get(lookupKey));
				return parsingTable.get(lookupKey);
			} else {
				
				throw new SyntaxException("syntax error " + lookupKey);
			}
		}

		if (parsingTable.containsKey(lookupKey)) {
			System.out.println("correct: " + lookupKey +"   rule " + parsingTable.get(lookupKey));
			return parsingTable.get(lookupKey);
		} else {

			throw new SyntaxException("syntax error" + "syntax error " + lookupKey);
		}
	}

	public TreeNode getLast() {
		return stack.pop();
	}


	public void clearTree() {
		this.parseTree = new ParseTree();
	}

	// push the grammar rule to the stack
	public void pushIntoStack(List<TreeNode> rule, TreeNode parent) {
		for (int i = rule.size() - 1; i >= 0; i--) {

			if (rule.get(i).getLabel() != TreeNode.Label.epsilon) {
				TreeNode currentEntry = rule.get(i);
				currentEntry.setParent(parent);
				stack.push(currentEntry);
			} else {
				TreeNode currentEntry = rule.get(i);
				currentEntry.setParent(parent);

				parent.addChild(currentEntry);
			}
			
		}
	}

	public ParseTree process(List<Token> tokens) throws SyntaxException {
		// create the parse tree
		if (tokens.size() == 0) {
			throw new SyntaxException("syntax exception");
		}
		int i = 0;
	
		while (i < tokens.size()) {
			TreeNode stackEntry = getLast(); // Pop the stack
			Token arrayEntry = tokens.get(i); // Get the current token
			// Print debug info
			System.out.println("Stack entry: " + stackEntry + " | Token: " + arrayEntry + " | Stack top: " + stack.peek() + " | i: " + i);
			
			
			if (stackEntry.getLabel() == TreeNode.Label.terminal) { // Terminal node
				Token stackEntryToken = stackEntry.getToken().get(); // Get the token associated with the terminal
	
				if (stackEntryToken.equals(arrayEntry)) {
					// If the terminal matches the current token, add to the tree and move to the next token
					TreeNode newNode = new TreeNode(TreeNode.Label.terminal, arrayEntry, stackEntry.getParent());
					stackEntry.getParent().addChild(newNode);
					i++; // Consume the token
					continue;
				} else {

					if (stackEntryToken.getType() == arrayEntry.getType()) {
						TreeNode newNode = new TreeNode(TreeNode.Label.terminal, arrayEntry, stackEntry.getParent());
						stackEntry.getParent().addChild(newNode);
						i++; // Consume the token

					} else {
						// Handle mismatches
						throw new SyntaxException("syntax error: Token mismatch. Expected: " + stackEntryToken + ", found: " + arrayEntry);
					}
				}
			} else { // Non-terminal node

				ArrayList<TreeNode> grammarRule = parsingTableLookUp(stackEntry.getLabel(), arrayEntry);
				TreeNode parent = stackEntry;
				if (!stackEntry.getLabel().equals(TreeNode.Label.prog)) {
					stackEntry.getParent().addChild(stackEntry);
				}
				
				
				pushIntoStack(grammarRule, parent);
				
			}

		}
	
		return this.parseTree;
	}
	
} 

public class SyntacticAnalyser {
	public static ParseTree parse(List<Token> tokens) throws SyntaxException {

		PDA pda = new PDA();
		ParseTree root = pda.process(tokens);
		return root;
	}

}

class Pair<A, B> {
	private final A a;
	private final B b;

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	public A fst() {
		return a;
	}

	public B snd() {
		return b;
	}

	@Override
	public int hashCode() {
		return (int) 3 * a.hashCode() + 7 * b.hashCode();
	}

	@Override
	public String toString() {
		return "{" + a + ", " + b + "}";
	}
	@Override
	public boolean equals(Object o) {
		if ((o instanceof Pair<?, ?>)) {
			Pair<?, ?> other = (Pair<?, ?>) o;
			return other.fst().equals(a) && other.snd().equals(b);
		}

		return false;
	}
}