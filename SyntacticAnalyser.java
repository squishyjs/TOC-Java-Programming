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
		stack.add(prog);
		this.parseTree = new ParseTree(prog);
		

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

		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.stat, null),
			new TreeNode(TreeNode.Label.los, null)
		)));
		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.los, new Token(Token.TokenType.RBRACE)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));

		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.stat, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.decl, null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), null)
		)));

		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.stat, new Token(Token.TokenType.SEMICOLON)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), null)
		)));

		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.decl, new Token(Token.TokenType.TYPE, "int")), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.TYPE), null),
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), null),
			new TreeNode(TreeNode.Label.possassign, null)
		)));

		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.possassign, new Token(Token.TokenType.EQUAL)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.EQUAL), null),
			new TreeNode(TreeNode.Label.expr, null)
		)));

		parsingTable.put(new Pair<TreeNode.Label, Token>(TreeNode.Label.possassign, new Token(Token.TokenType.SEMICOLON)), new ArrayList<TreeNode>(Arrays.asList(
			new TreeNode(TreeNode.Label.epsilon, null)
		)));

	}

	public ArrayList<TreeNode> parsingTableLookUp(TreeNode.Label variable, Token terminal) throws SyntaxException {
		Pair<TreeNode.Label, Token> lookupKey = new Pair<TreeNode.Label, Token>(variable,  new Token(terminal.getType()));
		if (terminal.getType() == Token.TokenType.TYPE) {
			lookupKey = new Pair<TreeNode.Label, Token>(variable,  new Token(terminal.getType(), terminal.getValue().get()));
			if (parsingTable.containsKey(lookupKey)) {
				return parsingTable.get(lookupKey);
			} else {
				System.out.println(lookupKey);
				throw new SyntaxException("syntax error");
			}
		}

		if (parsingTable.containsKey(lookupKey)) {
			return parsingTable.get(lookupKey);
		} else {

			throw new SyntaxException("syntax error");
		}
	}

	public TreeNode getLast() {
		return stack.pop();
	}


	public void clearTree() {
		this.parseTree = new ParseTree();
	}

	// push the grammar rule to the stack
	public void pushIntoStack(List<TreeNode> rule) {
		for (int i = rule.size() - 1; i >= 0; i--) {
			if (rule.get(i).getLabel() != TreeNode.Label.epsilon) {
				stack.push(rule.get(i));
			}
			
		}
	}


	public ParseTree process(List<Token> tokens) throws SyntaxException{
		// create the parse tree

		// TreeNode parentNode = new TreeNode(TreeNode.Label.prog, null);

		if (tokens.size() == 0) {
			throw new SyntaxException("syntax exception");
		}

		TreeNode parent = parseTree.getRoot();

		int i = 0;

		while (i < tokens.size()) {
			TreeNode stackEntry = getLast(); // Pop the stack
			Token arrayEntry = tokens.get(i); // Get the current token
			
			// Print useful debug info
			System.out.println("Stack entry: " + stackEntry + " | Token: " + arrayEntry + " | Stack top: " + stack.peek() + " | i: " + i);
		
			if (stackEntry.getLabel() == TreeNode.Label.terminal) { // Terminal node
				Token stackEntryToken = stackEntry.getToken().get(); // Get the token associated with the terminal
		
				if (stackEntryToken.equals(arrayEntry)) {
					// If the terminal matches the current token, add to the tree and move to the next token
					TreeNode newNode = new TreeNode(TreeNode.Label.terminal, arrayEntry, parent);
					parent.addChild(newNode);
					i++; // Consume the token
					continue; // Move to the next iteration
				} else {
					// Handle token type comparison (special tokens)
					if (stackEntryToken.getType() == arrayEntry.getType()) {
						TreeNode newNode = new TreeNode(TreeNode.Label.terminal, arrayEntry, parent);
						parent.addChild(newNode);
						i++; // Consume the token
						continue;
					} else {
						// Handle mismatches
						throw new SyntaxException("syntax error: Token mismatch. Expected: " + stackEntryToken + ", found: " + arrayEntry);
					}
				}
			} else { // Non-terminal node
				// Look up the parsing rule for this non-terminal and the current token
				ArrayList<TreeNode> grammarRule = parsingTableLookUp(stackEntry.getLabel(), arrayEntry);
				if (grammarRule == null) {
					throw new SyntaxException("syntax error: No rule found for non-terminal: " + stackEntry.getLabel() + " with token: " + arrayEntry);
				}
		
				// Add the current non-terminal (stackEntry) as a child of the parent node
				parent.addChild(stackEntry);
		
				// Now this non-terminal becomes the parent for the next set of rules
				parent = stackEntry;
		
				// Push the rule onto the stack
				pushIntoStack(grammarRule);
			}
		}
		
		parent = null; // Reset parent after parsing
		return this.parseTree;
	}

	// 
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












//  OLD CODE 
// import java.util.ArrayDeque; // used for DFA -> look up token in parse table
// import java.util.Arrays;
// import java.util.Deque;
// import java.util.HashMap;
// import java.util.List;
// import java.util.ArrayList;
// import java.util.Map;


// class PDA {
// 		ArrayDeque<Symbol> stack = new ArrayDeque<>();
// 		HashMap<Pair<Symbol, Symbol>, List<Symbol>> parsingTable = new HashMap<>(); // parsing table
// 		// [pair] : [terminals and and variable name]
// 		// parsetree method

// 		public PDA() {

// 			// push the starting variable into the stack
// 			stack.add(TreeNode.Label.prog);

// 			//parsing table = actual table (initialise the table)
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.los, Token.TokenType.ID), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.stat, 
// 					TreeNode.Label.los
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.stat, Token.TokenType.ID), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.assign, 
// 					Token.TokenType.SEMICOLON
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.assign, Token.TokenType.ID), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.ID, 
// 					Token.TokenType.ASSIGN, 
// 					TreeNode.Label.expr
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.expr, Token.TokenType.ID), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.relexpr, 
// 					TreeNode.Label.boolexpr
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.expr, Token.TokenType.NUM), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.relexpr, 
// 					TreeNode.Label.boolexpr
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.relexpr, Token.TokenType.ID), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.arithexpr, 
// 					TreeNode.Label.relexprprime
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.relexpr, Token.TokenType.NUM), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.arithexpr, 
// 					TreeNode.Label.relexprprime
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.boolexpr, Token.TokenType.AND), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.boolop, 
// 					TreeNode.Label.relexpr, 
// 					TreeNode.Label.boolexpr
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.boolexpr, Token.TokenType.OR), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.boolop, 
// 					TreeNode.Label.relexpr, 
// 					TreeNode.Label.boolexpr
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.arithexpr, Token.TokenType.ID), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.term, 
// 					TreeNode.Label.arithexprprime
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.arithexpr, Token.TokenType.NUM), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.term, 
// 					TreeNode.Label.arithexprprime
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.term, Token.TokenType.ID), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.factor, 
// 					TreeNode.Label.termprime
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.term, Token.TokenType.NUM), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.factor, 
// 					TreeNode.Label.termprime
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.factor, Token.TokenType.LPAREN), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.LPAREN, 
// 					TreeNode.Label.expr, 
// 					Token.TokenType.RPAREN
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.factor, Token.TokenType.ID), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.ID
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.factor, Token.TokenType.NUM), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.NUM
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.relexprprime, Token.TokenType.EQUAL), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.relop, 
// 					TreeNode.Label.arithexpr
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.relexprprime, Token.TokenType.NEQUAL), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.relop, 
// 					TreeNode.Label.arithexpr
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.relop, Token.TokenType.LT), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.LT
// 				)));

// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.relop, Token.TokenType.LE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.LE
// 				)));
// 				parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.relop, Token.TokenType.GT), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.GT
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.relop, Token.TokenType.GE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.GE
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.boolop, Token.TokenType.AND), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.booleq
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.boolop, Token.TokenType.OR), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.boollog
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.booleq, Token.TokenType.EQUAL), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.EQUAL
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.booleq, Token.TokenType.NEQUAL), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.NEQUAL
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.boollog, Token.TokenType.AND), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.AND
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.boollog, Token.TokenType.OR), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.OR
// 				)));
// 				parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.arithexprprime, Token.TokenType.PLUS), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.PLUS, 
// 					TreeNode.Label.term, 
// 					TreeNode.Label.arithexprprime
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.arithexprprime, Token.TokenType.MINUS), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.MINUS, 
// 					TreeNode.Label.term, 
// 					TreeNode.Label.arithexprprime
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.arithexprprime, Token.TokenType.SEMICOLON), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.arithexprprime, Token.TokenType.RPAREN), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.termprime, Token.TokenType.TIMES), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.TIMES, 
// 					TreeNode.Label.factor, 
// 					TreeNode.Label.termprime
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.termprime, Token.TokenType.DIVIDE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.DIVIDE, 
// 					TreeNode.Label.factor, 
// 					TreeNode.Label.termprime
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.termprime, Token.TokenType.MOD), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.MOD, 
// 					TreeNode.Label.factor, 
// 					TreeNode.Label.termprime
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.termprime, Token.TokenType.SEMICOLON), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
// 				parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.termprime, Token.TokenType.RPAREN), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.stat, Token.TokenType.WHILE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.whilestat
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.stat, Token.TokenType.FOR), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.forstat
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.stat, Token.TokenType.IF), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.ifstat
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.whilestat, Token.TokenType.WHILE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.WHILE, 
// 					Token.TokenType.LPAREN, 
// 					TreeNode.Label.relexpr, 
// 					TreeNode.Label.boolexpr, 
// 					Token.TokenType.RPAREN, 
// 					Token.TokenType.LBRACE, 
// 					TreeNode.Label.los, 
// 					Token.TokenType.RBRACE
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.forstat, Token.TokenType.FOR), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.FOR, 
// 					Token.TokenType.LPAREN, 
// 					TreeNode.Label.forstart, 
// 					Token.TokenType.SEMICOLON, 
// 					TreeNode.Label.relexpr, 
// 					TreeNode.Label.boolexpr, 
// 					Token.TokenType.SEMICOLON, 
// 					TreeNode.Label.forarith, 
// 					Token.TokenType.RPAREN, 
// 					Token.TokenType.LBRACE, 
// 					TreeNode.Label.los, 
// 					Token.TokenType.RBRACE
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.forstart, Token.TokenType.TYPE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.decl
// 				)));
// 				parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.forstart, Token.TokenType.ID), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.assign
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.forstart, Token.TokenType.SEMICOLON), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.forarith, Token.TokenType.ID), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.arithexpr
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.forarith, Token.TokenType.RPAREN), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.ifstat, Token.TokenType.IF), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.IF, 
// 					Token.TokenType.LPAREN, 
// 					TreeNode.Label.relexpr, 
// 					TreeNode.Label.boolexpr, 
// 					Token.TokenType.RPAREN, 
// 					Token.TokenType.LBRACE, 
// 					TreeNode.Label.los, 
// 					Token.TokenType.RBRACE, 
// 					TreeNode.Label.elseifstat
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.elseifstat, Token.TokenType.ELSE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.elseorelseif, 
// 					Token.TokenType.LBRACE, 
// 					TreeNode.Label.los, 
// 					Token.TokenType.RBRACE, 
// 					TreeNode.Label.elseifstat
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.elseifstat, Token.TokenType.SEMICOLON), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
// 				parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.elseorelseif, Token.TokenType.ELSE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.elseorelseif
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.elseorelseif, Token.TokenType.ELSE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.ELSE, 
// 					TreeNode.Label.possif
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.possif, Token.TokenType.IF), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.IF, 
// 					Token.TokenType.LPAREN, 
// 					TreeNode.Label.relexpr, 
// 					TreeNode.Label.boolexpr, 
// 					Token.TokenType.RPAREN
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.possif, Token.TokenType.LBRACE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.decl, Token.TokenType.TYPE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.type, 
// 					Token.TokenType.ID, 
// 					TreeNode.Label.possassign
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.possassign, Token.TokenType.ASSIGN), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.ASSIGN, 
// 					TreeNode.Label.expr
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.possassign, Token.TokenType.SEMICOLON), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
// 				parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.type, Token.TokenType.TYPE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.TYPE
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.print, Token.TokenType.PRINT), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.PRINT, 
// 					Token.TokenType.LPAREN, 
// 					TreeNode.Label.printexpr, 
// 					Token.TokenType.RPAREN, 
// 					Token.TokenType.SEMICOLON
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.printexpr, Token.TokenType.ID), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.relexpr, 
// 					TreeNode.Label.boolexpr
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.printexpr, Token.TokenType.NUM), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.relexpr, 
// 					TreeNode.Label.boolexpr
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.printexpr, Token.TokenType.DQUOTE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.DQUOTE, 
// 					Token.TokenType.STRINGLIT, 
// 					Token.TokenType.DQUOTE
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.printexpr, Token.TokenType.SQUOTE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.charexpr
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.charexpr, Token.TokenType.SQUOTE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.SQUOTE, 
// 					Token.TokenType.CHARLIT, 
// 					Token.TokenType.SQUOTE
// 				)));
// 				parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.boolexpr, Token.TokenType.SEMICOLON), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.boolexpr, Token.TokenType.RPAREN), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.relexprprime, Token.TokenType.SEMICOLON), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.relexprprime, Token.TokenType.RPAREN), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.stat, Token.TokenType.PRINT), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.print
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.stat, Token.TokenType.SEMICOLON), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
// 				parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.los, Token.TokenType.RBRACE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.prog, Token.TokenType.RBRACE), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.prog, Token.TokenType.PUBLIC), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					Token.TokenType.PUBLIC, 
// 					Token.TokenType.CLASS, 
// 					Token.TokenType.ID, 
// 					Token.TokenType.LBRACE, 
// 					Token.TokenType.PUBLIC, 
// 					Token.TokenType.STATIC, 
// 					Token.TokenType.VOID, 
// 					Token.TokenType.MAIN, 
// 					Token.TokenType.LPAREN, 
// 					Token.TokenType.STRINGARR, 
// 					Token.TokenType.ARGS, 
// 					Token.TokenType.RPAREN, 
// 					Token.TokenType.LBRACE, 
// 					TreeNode.Label.los, 
// 					Token.TokenType.RBRACE, 
// 					Token.TokenType.RBRACE
// 				)));
			
// 			parsingTable.put(new Pair<Symbol, Symbol>(TreeNode.Label.prog, Token.TokenType.SEMICOLON), 
// 				new ArrayList<Symbol>(Arrays.asList(
// 					TreeNode.Label.epsilon
// 				)));
			

// 			// hashmap = actual hashmap (initialise the hashmap) -> stores grammar rules
// 			// {[variable]: [terminal, terminal, variable]}
// 		}

// 		// lookup the parsing table for the grammar rule
// 		// throws an exception if the hashmap does not have a mapping
// 		public List<Symbol> parsingTableLookUp(Symbol variable, Symbol terminal) throws SyntaxException {
// 			Pair<Symbol, Symbol> lookupKey = new Pair<Symbol, Symbol>(variable, terminal);
// 			if (parsingTable.containsKey(lookupKey)) {
// 				return parsingTable.get(lookupKey);
// 			} else {
// 				throw new SyntaxException("syntax error");
// 			}
// 		}

// 		// returns top element of stack
// 		// note: when the element is returned it is removed from the stack
// 		public Symbol getLast() {
// 			return stack.pop();
// 		}


// 		// push the grammar rule to the stack
// 		public void pushIntoStack(List<Symbol> rule) {
// 			for (int i = rule.size() - 1; i >= 0; i--) {
// 				stack.push(rule.get(i));
// 			}
// 		}


// 		public ParseTree process(List<Token> tokens) throws SyntaxException{
// 		// create the parse tree
// 		int i = 0;
// 		// yet to handle: create node and parse tree
// 		while (i < tokens.size()) {
// 			Symbol stackEntry = getLast();
// 			Symbol arrayEntry = tokens.get(i).getType();
	
// 			if (stackEntry.isVariable()) {
// 				List<Symbol> grammarRule = parsingTableLookUp(stackEntry, arrayEntry);	
// 				pushIntoStack(grammarRule);
// 			} else {
// 				if (stackEntry.equals(arrayEntry)) {
// 					i++;
// 				} else {
// 					throw new SyntaxException("Syntax error");
// 				}
// 			}
			
// 			// if token is a terminal
// 				// if the stack has a matching terminal on top
// 					// Pop terminal, move on to next token
// 				// else if token is a non-terminal (variable):
// 					// use parseTableLookUp(non-terminal) -> find the grammar rule in the parsing table 
// 					// append production rule of grammar symbol -> some child node?
// 				// else:
// 					// throw lexical exception

// 		}

// 		return new ParseTree();
// 	}

// 		// 
// 	} 

// public class SyntacticAnalyser {
	
// 	// create the class to handle PDA logic
	
	

// 	public static ParseTree parse(List<Token> tokens) throws SyntaxException {
// 		//Turn the List of Tokens into a ParseTree.

// 		// {
// 			// [prog] : [public, class, ID, {, public, static, void, main, ( String [] args) { [los] }}] 
// 			//
// 		//}
		
// 		PDA pda = new PDA();
// 		root = pda.process(tokens);

// 		return root;
// 	}

// }

// // The following class may be helpful.

// class Pair<A, B> {
// 	private final A a;
// 	private final B b;

// 	public Pair(A a, B b) {
// 		this.a = a;
// 		this.b = b;
// 	}

// 	public A fst() {
// 		return a;
// 	}

// 	public B snd() {
// 		return b;
// 	}

// 	@Override
// 	public int hashCode() {
// 		return 3 * a.hashCode() + 7 * b.hashCode();
// 	}

// 	@Override
// 	public String toString() {
// 		return "{" + a + ", " + b + "}";
// 	}

// 	@Override
// 	public boolean equals(Object o) {
// 		if ((o instanceof Pair<?, ?>)) {
// 			Pair<?, ?> other = (Pair<?, ?>) o;
// 			return other.fst().equals(a) && other.snd().equals(b);
// 		}

// 		return false;
// 	}

// }